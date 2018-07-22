package utils;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class MessageCreator {

    public static void createError(String trigger, String reason, String solution, TextChannel channel) {

        channel.sendMessage(
                new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle("Fehler")
                        .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                        .addField("Auslöser",  "`" + trigger + "`", false)
                        .addField("Grund", reason, false)
                        .addField("Mögliche Lösung", solution, false)
                        .setFooter("SapphireBot for CodersClash © ConnysCode/ConCode 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
        ).queue();

    }

    public static void createError(String trigger, String reason, TextChannel channel) {
            channel.sendMessage(
                    new EmbedBuilder().setColor(VariableStoring.COLOR)
                            .setTitle("Fehler")
                            .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                            .addField("Auslöser",  "`" + trigger + "`", false)
                            .addField("Möglicher Grund", reason, false)
                            .setFooter("SapphireBot for CodersClash © ConnysCode/ConCode 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
            ).queue();
    }

    public static void createError(String reason, TextChannel channel) {

        channel.sendMessage(
                    new EmbedBuilder().setColor(VariableStoring.COLOR)
                            .setTitle("Fehler")
                            .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                            .addField("Möglicher Grund", reason, false)
                            .setFooter("SapphireBot for CodersClash © ConnysCode/ConCode 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
            ).queue();


    }

    public static void illegalAction(String reason, TextChannel channel) {

        channel.sendMessage(
                new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle("Aktion abgebrochen!")
                        .setThumbnail("https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                        .addField("Grund", reason, false)
                        .setFooter("SapphireBot for CodersClash © ConnysCode/ConCode 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png").build()
        ).queue();
    }

    public static void normalMessage(String title, String field_1_title, String field_1_value, String field_2_title, String field_2_value, TextChannel channel) {
        channel.sendMessage(
                new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle(title)
                        .addField(field_1_title, field_1_value, false)
                        //.setImage(VariableStoring.LOGO_TEXT)
                        .addField(field_2_title, field_2_value, false).build()
        ).queue();
    }

    public static void normalMessage(String title, String field_1_title, String field_1_value, TextChannel channel) {
        channel.sendMessage(
                new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle(title)
                        //.setImage(VariableStoring.LOGO_TEXT)
                        .addField(field_1_title, field_1_value, false).build()
        ).queue();
    }

    public static void normalMessage(String title, String description, TextChannel channel) {
        channel.sendMessage(
                new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle(title)
                        //.setImage(VariableStoring.LOGO_TEXT)
                        .setDescription(description).build()
        ).queue();
    }

    public static void normalUnbeddedMessage(String description, TextChannel channel) {
        channel.sendMessage(description).queue();
    }

    public static void sayMessage(Member member, String title, String description, TextChannel channel) {
        channel.sendMessage(
                new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle(title)
                        .setAuthor(member.getEffectiveName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl())
                        .setImage(VariableStoring.LOGO_TEXT)
                        .setDescription(description).build()
        ).queue();
    }

    public static EmbedBuilder rawNormalMessage(String title, String description) {
        return new EmbedBuilder().setColor(VariableStoring.COLOR)
                        .setTitle(title)
                        .setImage(VariableStoring.LOGO_TEXT)
                        .setDescription(description);
    }

}
