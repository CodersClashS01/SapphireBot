package commands.moderation;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import utils.MessageCreator;
import utils.PrivateMessageCreator;
import utils.VariableStoring;
import utils.Tools;
import utils.sql.ReportSQL;

import java.util.List;

import static handler.PermissionsHandler.hasPermission;

public class WarnCommand implements ICommand {

    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        User u;
        try {
            event.getMessage().delete();
            switch (args[0].toLowerCase()) {
                case "-l":

                    if (args.length == 2) {
                    if (Tools.isMention(args[1]) || event.getJDA().getUserById(args[1]) != null) {
                        if (Tools.isMention(args[1])) {
                            u = event.getMessage().getMentionedUsers().get(0);
                        } else {
                            u = event.getJDA().getUserById(args[1]);
                        }
                        List warns = ReportSQL.getReportsByUser(u.getId(), event.getGuild().getId());

                        if (warns.size() >= 1) {

                            String message = "```md";
                            String messageBefore;

                            ReportSQL reportSQL_Intern;
                            for (Object warn : warns) {
                                reportSQL_Intern = (ReportSQL) warn;
                                messageBefore = message;
                                if (reportSQL_Intern.getGuild().equals(event.getGuild().getId())) {
                                    message = message + "\nNutzer: [" + reportSQL_Intern.getVictim() + "][" + event.getJDA().getUserById(reportSQL_Intern.getVictim()).getName() + "] | Moderator: " + event.getJDA().getUserById(reportSQL_Intern.getExecutor()).getName() + "#" + event.getJDA().getUserById(reportSQL_Intern.getExecutor()).getDiscriminator() + "\n" +
                                            "    Grund: " + reportSQL_Intern.getReason() + " - " + reportSQL_Intern.getDate() + "\n";
                                }

                                if (message.length() > 2000) {
                                    message = messageBefore;
                                    message = message + "\n```";
                                    channel.sendMessage(message).queue();
                                    message = "```md";
                                    if (reportSQL_Intern.getGuild().equals(event.getGuild().getId())) {
                                        message = message + "\nNutzer: [" + reportSQL_Intern.getVictim() + "][" + event.getJDA().getUserById(reportSQL_Intern.getVictim()).getName() + "] | Moderator: " + event.getJDA().getUserById(reportSQL_Intern.getExecutor()).getName() + "#" + event.getJDA().getUserById(reportSQL_Intern.getExecutor()).getDiscriminator() + "\n" +
                                                "    Grund: " + reportSQL_Intern.getReason() + " - " + reportSQL_Intern.getDate() + "\n";
                                    }
                                }
                            }
                            message = message + "\n```";

                            if (message.equals("```\n```")) {
                                MessageCreator.normalMessage("Weiße Weste!", "Dieser Spieler wurde auf diesem Guild noch nie verwarnt!", channel);
                            } else {
                                channel.sendMessage(message).queue();
                            }
                        } else {
                            MessageCreator.normalMessage("Weiße Weste!", "Dieser Spieler wurde noch nie verwarnt!", channel);
                        }
                    } else {
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Dieser Nutzer ist uns nicht bekannt!", "``" + VariableStoring.PREFIX +  "help warn``", channel);
                    }

                    } else {
                        List warns =  ReportSQL.getReportsByAll(event.getGuild().getId());
                        if (warns.size() >= 1) {

                            String message = "```md";
                            String messageBefore;

                            ReportSQL reportSQL_Intern;
                            for (Object warn : warns) {
                                reportSQL_Intern = (ReportSQL) warn;
                                messageBefore = message;
                                if (reportSQL_Intern.getGuild().equals(event.getGuild().getId())) {
                                    message = message + "\nNutzer: [" + reportSQL_Intern.getVictim() + "][" + event.getJDA().getUserById(reportSQL_Intern.getVictim()).getName() + "] | Moderator: " + event.getJDA().getUserById(reportSQL_Intern.getExecutor()).getName() + "#" + event.getJDA().getUserById(reportSQL_Intern.getExecutor()).getDiscriminator() + "\n" +
                                            "    Grund: " + reportSQL_Intern.getReason() + " - " + reportSQL_Intern.getDate() + "\n";
                                }

                                if (message.length() > 2000) {
                                    message = messageBefore;
                                    message = message + "\n```";
                                    channel.sendMessage(message).queue();
                                    message = "```md";
                                    if (reportSQL_Intern.getGuild().equals(event.getGuild().getId())) {
                                        message = message + "\nNutzer: [" + reportSQL_Intern.getVictim() + "][" + event.getJDA().getUserById(reportSQL_Intern.getVictim()).getName() + "] | Moderator: " + event.getJDA().getUserById(reportSQL_Intern.getExecutor()).getName() + "#" + event.getJDA().getUserById(reportSQL_Intern.getExecutor()).getDiscriminator() + "\n" +
                                                "    Grund: " + reportSQL_Intern.getReason() + " - " + reportSQL_Intern.getDate() + "\n";
                                    }
                                }
                            }
                            message = message + "\n```";

                            if (message.equals("```\n```")) {
                                MessageCreator.normalMessage("Weiße Weste!", "Dieser Spieler wurde auf diesem Guild noch nie verwarnt!", channel);
                            } else  {
                                channel.sendMessage(message).queue();
                            }
                        } else {
                            MessageCreator.normalMessage("Weiße Weste!", "Auf diesem Guild wurde noch kein Nutzer verwarnt!", channel);
                        }
                    }

                    break;

                case "-c":
                    if (hasPermission(false, new Permission[]{Permission.MANAGE_SERVER}, channel, member))

                    if (Tools.isMention(args[1]) && !args[2].equals("") ) {
                        u = event.getMessage().getMentionedUsers().get(0);
                        List warns =  ReportSQL.getReportsByUser(u.getId(), event.getGuild().getId());
                        ReportSQL.createNew(event.getGuild().getId(), u.getId(), member.getUser().getId(), Tools.argsToString(args, 2, " "));
                        PrivateMessageCreator.normalUnbeddedMessage( "```md\nDu wurdest verwarnt! Dies ist deine " + warns.size() +1 + ". Verwarnung!\n\nGuild: <" + event.getGuild().getName() + ">\nModerator: < " + member.getEffectiveName() + " >\nGrund: *" + Tools.argsToString(args, 2, " ") + "*```", event.getGuild().getMemberById(u.getId()).getUser());
                        MessageCreator.normalMessage("Spieler verwarnt!", "Der Spieler " + u.getAsMention() + " wurde soeben verwarnt!", channel);
                    } else if (event.getGuild().getMemberById(args[1]) != null && !args[2].equals("") ) {
                        u = event.getGuild().getMemberById(args[1]).getUser();
                        List warns =  ReportSQL.getReportsByUser(u.getId(), event.getGuild().getId());
                        ReportSQL.createNew(event.getGuild().getId(), u.getId(), member.getUser().getId(), Tools.argsToString(args, 2, " "));
                        PrivateMessageCreator.normalUnbeddedMessage( "```md\nDu wurdest verwarnt! Dies ist deine " + warns.size() +1 + ". Verwarnung!\n\nGuild: <" + event.getGuild().getName() + ">\nModerator: < " + member.getEffectiveName() + " >\nGrund: *" + Tools.argsToString(args, 2, " ") + "*```", event.getGuild().getMemberById(u.getId()).getUser());
                        MessageCreator.normalMessage("Spieler verwarnt!", "Der Spieler " + u.getAsMention() + " wurde soeben verwarnt!", channel);

                    } else {
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Entweder dieser Nutzer wurde nicht gefunden, oder es wurde kein Grund angegeben!", "``" + VariableStoring.PREFIX +  "help warn``", channel);
                    }

                    break;

                    default:
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Falsche Syntax!", "``" + VariableStoring.PREFIX +  "help warn``", channel);
                        break;

            }




        } catch (Exception e) {
            MessageCreator.createError(e.getCause().toString(), "Ein Fehler im Code ist aufgetreten! Das sollte nicht passieren! :thinking:", "Überprüfe die Konsole", channel);
            e.printStackTrace();
        }
    }
}
