package commands.general;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.MessageCreator;
import utils.PrivateMessageCreator;
import utils.VariableStoring;
import utils.Tools;
import utils.sql.UserReportSQL;

import java.util.List;

import static handler.PermissionsHandler.hasPermission;

public class ReportCommand implements ICommand {

    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        int members = event.getGuild().getMembers().size();
        int mods_info = 0;
        boolean exist;

        exist = UserReportSQL.getReportsByGuild(event.getGuild().getId()).size() != 0;

        switch (args[0]) {
            case "set":

                if (hasPermission(false, new Permission[]{Permission.ADMINISTRATOR}, channel, member))

                    try {
                        if (args.length == 4) {
                            if (exist) {
                                if (event.getGuild().getTextChannelById(args[2]) == null || event.getGuild().getRoleById(args[3]) == null) {
                                    MessageCreator.createError(event.getMessage().getContentRaw(), "Die Rolle oder der TextChannel konnte nicht gefunden werden!", VariableStoring.PREFIX + "help report", channel);
                                } else if (args[1].equals("true") || args[1].equals("false")){
                                    UserReportSQL.updateOld(event.getGuild().getId(), args[2], args[1], args[3]);
                                    MessageCreator.normalMessage("Re-Konfiguration abgeschlossen!", "Der Report Befehl wurde erfolgreich re-konfiguriert.", channel);
                                } else {
                                    MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise!", VariableStoring.PREFIX + "help report", channel);
                                }
                            } else {
                                if (event.getGuild().getTextChannelById(args[2]) == null || event.getGuild().getRoleById(args[3]) == null) {
                                    MessageCreator.createError(event.getMessage().getContentRaw(), "Die Rolle oder der TextChannel konnte nicht gefunden werden!", VariableStoring.PREFIX + "help report", channel);
                                } else if (args[1].equals("true") || args[1].equals("false")){
                                    UserReportSQL.createNew(event.getGuild().getId(), args[2], args[1], args[3]);
                                    MessageCreator.normalMessage("Konfiguration abgeschlossen!", "Der Report Befehl wurde erfolgreich konfiguriert.", channel);
                                } else {
                                    MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise!", VariableStoring.PREFIX + "help report", channel);

                                }
                            }
                        } else {
                            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise!", VariableStoring.PREFIX + "help report", channel);
                        }
                    } catch (Exception e) {
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise!", VariableStoring.PREFIX + "help report", channel);


                    }



                break;

            default:

                if (!exist) {
                    MessageCreator.createError("Dieser Befehl wurde noch nicht konfigurert!\n`" + VariableStoring.PREFIX + "help report`", channel);
                } else {
                    List userReportList = UserReportSQL.getReportsByGuild(event.getGuild().getId());
                    UserReportSQL userReportSQLs = (UserReportSQL) userReportList.get(0);
                    String roles;
                    if (userReportSQLs.getUse_text().equals("true")) {
                        try {
                            MessageCreator.normalUnbeddedMessage("```md\nDas Mitglied \"" + event.getMessage().getMentionedUsers().get(0).getName() + "#" + event.getMessage().getMentionedUsers().get(0).getDiscriminator() + "\" wurde soeben von \"" + member.getEffectiveName() + "\" reportet!\n\nGuild: <" + event.getGuild().getName() + ">\nReport von: < " + member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + " >\nGrund: *" + Tools.argsToString(args, 1, " ") + "*```", event.getGuild().getTextChannelById(userReportSQLs.getText_channel_id()));
                            MessageCreator.normalMessage("Report erstellt!", "Es wurden `" + event.getGuild().getTextChannelById(userReportSQLs.getText_channel_id()).getMembers().size() + "` Moderatoren informiert.\nDie Moderatoren werden nun nach Beweisen Ausschau halten und den Nutzer eventuell verwarnen!\n\nReport von: `" + member.getEffectiveName() + "`\nReport an: `" + event.getMessage().getMentionedMembers().get(0).getEffectiveName() + "`\nGrund: `" + Tools.argsToString(args, 1, " ") + "`", channel);
                        } catch (Exception e) {
                            MessageCreator.createError(event.getMessage().getContentRaw(), "Der TextChannel konnte nicht gefunden werden! Informiere einen Administrator!", "Starte den Sapphire-Konfigurationsassistenten erneut!", channel);
                        }
                    } else {
                        for (int i = 0; i < members; i++) {
                            try {

                                roles = event.getGuild().getMembers().get(i).getRoles().toString();

                                if (!roles.contains(userReportSQLs.getNo_notify()) && !event.getGuild().getMembers().get(i).getUser().isBot() && event.getGuild().getMembers().get(i).hasPermission(Permission.KICK_MEMBERS) || event.getGuild().getMembers().get(i).hasPermission(Permission.BAN_MEMBERS) && !roles.contains(userReportSQLs.getNo_notify()) && !event.getGuild().getMembers().get(i).getUser().isBot()) {
                                    PrivateMessageCreator.normalUnbeddedMessage("```md\nDas Mitglied \"" + event.getMessage().getMentionedUsers().get(0).getName() + "#" + event.getMessage().getMentionedUsers().get(0).getDiscriminator() + "\" wurde soeben von \"" + member.getEffectiveName() + "\" reportet!\n\nGuild: <" + event.getGuild().getName() + ">\nReport von: < " + member.getEffectiveName() + "#" + member.getUser().getDiscriminator() + " >\nGrund: *" + Tools.argsToString(args, 1, " ") + "*```", event.getGuild().getMembers().get(i).getUser());
                                    mods_info++;
                                }
                            } catch (Exception ignored) {

                            }
                        }
                        MessageCreator.normalMessage("Report erstellt!", "Es wurden `" + mods_info + "` Moderatoren informiert.\nDie Moderatoren werden nun nach Beweisen Ausschau halten und den Nutzer eventuell verwarnen!\n\nReport von: `" + member.getEffectiveName() + "`\nReport an: `" + event.getMessage().getMentionedMembers().get(0).getEffectiveName() + "`\nGrund: `" + Tools.argsToString(args, 1, " ") + "`", channel);

                    }
                }
                break;
        }
    }
}
