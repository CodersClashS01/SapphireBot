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
import utils.VariableStoring;
import utils.sql.UserSQL;

import static handler.PermissionsHandler.hasPermission;

public class EPCommand implements ICommand {

    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {

        if (hasPermission(true, new Permission[]{}, channel, member))

        if (args.length >= 2) {

            if (event.getMessage().getMentionedUsers().size() == 1) {
                UserSQL userSQL = UserSQL.fromUser(event.getMessage().getMentionedUsers().get(0));

                User u = event.getMessage().getMentionedUsers().get(0);

                switch (args[0]) {

                    case "set":
                        if (args.length == 3) {
                            if (event.getMessage().getMentionedUsers().get(0) == event.getMember()) {
                                userSQL.setXp(Integer.parseInt(args[2]) - 6);
                                MessageCreator.normalMessage("Erfahrung", "Du hast die EP von " + event.getGuild().getMember(u).getEffectiveName() + " auf " + args[2] + " gesetzt", channel);
                            } else {
                                userSQL.setXp(Integer.parseInt(args[2]));
                                MessageCreator.normalMessage("Erfahrung", "Du hast die EP von " + event.getGuild().getMember(u).getEffectiveName() + " auf " + args[2] + " gesetzt", channel);
                            }
                        } else {
                            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help ep``", channel);
                        }

                        break;

                    case "add":
                        if (args.length == 3) {

                            if (event.getMessage().getMentionedUsers().get(0) == event.getMember()) {
                                userSQL.addXp(Integer.parseInt(args[2]) - 6);
                                MessageCreator.normalMessage("Erfahrung", "Du hast die EP von " + event.getGuild().getMember(u).getEffectiveName() + " um " + args[2] + " erhöht", channel);
                            } else {
                                userSQL.addXp(Integer.parseInt(args[2]));
                                MessageCreator.normalMessage("Erfahrung", "Du hast die EP von " + event.getGuild().getMember(u).getEffectiveName() + " um " + args[2] + " erhöht", channel);
                            }

                        } else {
                            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help ep``", channel);
                        }
                        break;

                    case "take":
                        if (args.length == 3) {
                            if (event.getMessage().getMentionedUsers().get(0) == event.getMember()) {
                                userSQL.takeXp(Integer.parseInt(args[2]) + 6);
                                MessageCreator.normalMessage("Erfahrung", "Du hast die EP von " + event.getGuild().getMember(u).getEffectiveName() + " um " + args[2] + " verringert", channel);
                            } else {
                                userSQL.takeXp(Integer.parseInt(args[2]));
                                MessageCreator.normalMessage("Erfahrung", "Du hast die EP von " + event.getGuild().getMember(u).getEffectiveName() + " um " + args[2] + " verringert", channel);
                            }

                        } else {
                            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help ep``", channel);
                        }
                        break;

                    case "reset":
                        if (event.getMessage().getMentionedUsers().get(0) == event.getMember()) {
                            userSQL.setXp(0 - 6);
                            MessageCreator.normalMessage("Erfahrung", "Du hast die EP von " + event.getGuild().getMember(u).getEffectiveName() + " auf 0 zurückgesetzt.", channel);
                        } else {
                            userSQL.setXp(0);
                            MessageCreator.normalMessage("Erfahrung", "Du hast die EP von " + event.getGuild().getMember(u).getEffectiveName() + " auf 0 zurückgesetzt.", channel);
                        }

                        break;

                    default:
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help ep``",channel);
                        break;

                }
            } else {
                MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help ep``", channel);

            }

        } else {
            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help ep``", channel);
        }
    }
}
