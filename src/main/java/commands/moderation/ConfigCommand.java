package commands.moderation;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import config.Config;
import core.CLI;
import core.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.MessageCreator;
import utils.PrivateMessageCreator;
import utils.VariableStoring;
import utils.Tools;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.nio.file.Files;

import static config.Config.get;
import static config.Config.get_list;
import static handler.PermissionsHandler.hasPermission;

public class ConfigCommand implements ICommand {

    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {

        if (hasPermission(true, new Permission[]{}, channel, member))

            if (args.length >= 1) {


        switch (args[0]){

            case "set":

                if (args.length >= 3) {

                    if (Config.get(args[1].toLowerCase()) != null){

                        if (Config.get(args[1]).equalsIgnoreCase("color")) {
                            String new_value = Tools.argsToString(args, 2, " ");
                            String new_settings_value = new_value;
                            String old_value = Config.get("color");


                            if (new_value.startsWith("#")) {
                                Config.overrideProperties("color", new_settings_value.replaceAll("#", ""));
                            } else {
                                new_value = "#" + new_value;
                                Config.overrideProperties("color", new_settings_value);
                            }

                            setMessage("color", "#" + old_value, new_value, channel);

                            VariableStoring.COLOR = Color.decode(new_value);
                        } else {

                            String new_value = Tools.argsToString(args, 2, " ");
                            String old_value = Config.get(args[1].toLowerCase());
                            Config.overrideProperties(args[1].toLowerCase(), new_value);
                            setMessage(args[1].toLowerCase(), old_value, new_value, channel);
                            Config.init(Main.config);
                        }



                    } else {
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Diese Einstellung ist mir nicht bekannt.", "Überprüfe deine Schreibweise", channel);
                    }

                } else {
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help config``", channel);
                }


                event.getMessage().delete().queue();
                break;

            case "check":

                if (Config.get(args[1].toLowerCase()) != null){
                    checkMessage(args[1].toLowerCase(), Config.get(args[1].toLowerCase()), channel);
                } else {
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Diese Einstellung ist mir nicht bekannt.", "Überprüfe deine Schreibweise", channel);
                }

                event.getMessage().delete().queue();
                break;

            case "list":

                String[] Desc_List = get_list();
                EmbedBuilder message = new EmbedBuilder().setTitle("Liste aller Einstellungen").setColor(VariableStoring.COLOR);
                int realcount = Desc_List.length - 1;
                StringBuilder first_builder = new StringBuilder();
                String[] first;
                for (int i = 0; i < realcount; i++) {
                    first_builder.append(Desc_List[i]).append("|");
                    i = i + 1;
                }
                first_builder.replace(first_builder.length()-1, first_builder.length(), "");

                first = first_builder.toString().split("\\|", -1);
                Arrays.sort(first);
                for (int i = 0; i < first.length - 1; i++) {
                    message.addField(first[i], get(first[i]), false);
                }
                PrivateMessageCreator.sendCustomEmbed(message, member.getUser());
                event.getMessage().delete().queue();

                break;

            case "reset":

                try {
                    Files.delete(Main.config.toPath());
                    Config.init(Main.config);
                    resetMessage(channel);
                } catch (IOException e) {
                    CLI.error("Could not delete Config File");
                    e.printStackTrace();
                }

                event.getMessage().delete().queue();

                break;

            case "reload":
                Config.init(Main.config);

                MessageCreator.normalMessage("Konfiguration", "Config erfolgreich neu geladen!", channel);
                break;

                default:
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help config``", channel);
                    break;
        }
            } else {
                MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help config``", channel);
            }
    }

    private static void setMessage(String setting, String old_value, String value, TextChannel channel) {
        channel.sendMessage(
                new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle("Konfiguration abgeändert")
                        .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                        .addField("Einstellung", "`" + setting + "`", false)
                        .addField("Alter Wert", "`" + old_value + "`", true)
                        .addField("Neuer Wert", "`" + value + "`", true)
                        .setFooter("SapphireBot for CodersClash © Knight 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
        ).queue();
    }

    private static void checkMessage(String setting, String value, TextChannel channel) {
        channel.sendMessage(
                new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle("Konfigurations-System")
                        .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                        .addField("Einstellung", "`" + setting + "`", false)
                        .addField("Aktueller Wert", "`" + value + "`", true)
                        .setFooter("SapphireBot for CodersClash © Knight 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
        ).queue();
    }

    private static void resetMessage(TextChannel channel) {
        channel.sendMessage(
                new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle("Konfigurations-System")
                        .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                        .setDescription("Konfigurations-Datei wurde auf Standartwerte zurückgesetzt!\nBot wird neu gestartet...\nBitte stelle die neue Konfigurations-Datei erneut korrekt ein!")
                        .setFooter("SapphireBot for CodersClash © Knight 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
        ).queue();
    }
}
