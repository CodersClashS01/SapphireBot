package utils;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

public class PrivateMessageCreator {

        public static void createError(String trigger, String reason, String solution, Guild guild, User user) {

            user.openPrivateChannel().queue(pc -> pc.sendMessage(
                    new EmbedBuilder().setColor(VariableStoring.COLOR)
                            .setTitle("Fehler")
                            .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                            .addField("Discord Server",  guild.getName(), false)
                            .addField("Auslöser",  "`" + trigger + "`", false)
                            .addField("Grund", reason, false)
                            .addField("Mögliche Lösung", solution, false)
                            .setFooter("SapphireBot for CodersClash © ConnysCode/ConCode 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
            ).queue());

        }

        public static void createError(String trigger, String reason, User user) {
            user.openPrivateChannel().queue(pc -> pc.sendMessage(
                    new EmbedBuilder().setColor(VariableStoring.COLOR)
                            .setTitle("Fehler")
                            .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                            .addField("Auslöser",  "`" + trigger + "`", false)
                            .addField("Möglicher Grund", reason, false)
                            .setFooter("SapphireBot for CodersClash © ConnysCode/ConCode 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
            ).queue());
        }

        public static void createError(String reason, User user) {

            user.openPrivateChannel().queue(pc -> pc.sendMessage(
                    new EmbedBuilder().setColor(VariableStoring.COLOR)
                            .setTitle("Fehler")
                            .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                            .addField("Möglicher Grund", reason, false)
                            .setFooter("SapphireBot for CodersClash © ConnysCode/ConCode 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
            ).queue());


        }

    @Deprecated
        public static void illegalAction(String reason, User user) {

            user.openPrivateChannel().queue(pc -> pc.sendMessage(
                    new EmbedBuilder().setColor(VariableStoring.COLOR)
                            .setTitle("Aktion abgebrochen!")
                            .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                            .addField("Grund", reason, false)
                            .setFooter("SapphireBot for CodersClash © ConnysCode/ConCode 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
            ).queue());
        }

        @Deprecated
        public static void normalMessage(String title, String field_1_title, String field_1_value, String field_2_title, String field_2_value, User user) {
            user.openPrivateChannel().queue(pc -> pc.sendMessage(
                    new EmbedBuilder().setColor(VariableStoring.COLOR)
                            .setTitle(title)
                            .addField(field_1_title, field_1_value, false)
                            .addField(field_2_title, field_2_value, false).build()
            ).queue());
        }

    @Deprecated
        public static void normalMessage(String title, String field_1_title, String field_1_value, User user) {
            user.openPrivateChannel().queue(pc -> pc.sendMessage(
                    new EmbedBuilder().setColor(VariableStoring.COLOR)
                            .setTitle(title)
                            .addField(field_1_title, field_1_value, false).build()
            ).queue());
        }

    @Deprecated
    public static void normalMessage(String title, String description, User user) {
        user.openPrivateChannel().queue(pc -> pc.sendMessage(
                new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle(title)
                        .setDescription(description).build()
        ).queue());
    }

    public static void normalUnbeddedMessage(String description, User user) {
        user.openPrivateChannel().queue(pc -> pc.sendMessage(description).queue());
    }

        public static void sendCustomEmbed(EmbedBuilder message, User user) {
            user.openPrivateChannel().queue(pc -> pc.sendMessage(message.build()).queue());
        }

    }
