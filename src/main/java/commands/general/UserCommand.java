package commands.general;
/*
    Created by ConnysSoftware / ConCode.
    Some of the code was written by dear 'shinixsensei-dev' due to lack of time

    (c) ConnysSoftware / ConCode 2018
    (c) shinixsensei-dev 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.MessageCreator;
import utils.VariableStoring;

public class UserCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        if (args.length == 0) {
            channel.sendMessage(
                    new EmbedBuilder().setColor(VariableStoring.COLOR)
                            .setTitle(member.getUser().getName() + "'s Profil | " + member.getUser().getId())
                            .addField("Username", member.getUser().getName() + "#" + event.getMessage().getAuthor().getDiscriminator(), true)
                            .addField("Servername", member.getEffectiveName(), true)
                            .addField("Guild join", member.getJoinDate().getDayOfMonth() + "." + member.getJoinDate().getMonthValue() + "." + member.getJoinDate().getYear(), true)
                            .addField("Discord join", member.getUser().getCreationTime().getDayOfMonth() + "." + member.getUser().getCreationTime().getMonthValue() + "." + member.getUser().getCreationTime().getYear(), true)
                            .addField("Avatar URL", "[Hier](" + member.getUser().getAvatarUrl() + ")", true)
                            .setFooter("SapphireBot for CodersClash © Knight 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                            .setThumbnail(member.getUser().getAvatarUrl())
                            .build()).queue();
        } else if (args.length >= 1){
            channel.sendMessage(
                    new EmbedBuilder().setColor(VariableStoring.COLOR)
                            .setTitle(event.getMessage().getMentionedMembers().get(0).getUser().getName() + "'s Profil | " + event.getMessage().getMentionedMembers().get(0).getUser().getId())
                            .addField("Username", event.getMessage().getMentionedMembers().get(0).getUser().getName() + "#" + event.getMessage().getAuthor().getDiscriminator(), true)
                            .addField("Servername", event.getMessage().getMentionedMembers().get(0).getEffectiveName(), true)
                            .addField("Guild join", event.getMessage().getMentionedMembers().get(0).getJoinDate().getDayOfMonth() + "." + event.getMessage().getMentionedMembers().get(0).getJoinDate().getMonthValue() + "." + event.getMessage().getMentionedMembers().get(0).getJoinDate().getYear(), true)
                            .addField("Discord join", event.getMessage().getMentionedMembers().get(0).getUser().getCreationTime().getDayOfMonth() + "." + event.getMessage().getMentionedMembers().get(0).getUser().getCreationTime().getMonthValue() + "." + event.getMessage().getMentionedMembers().get(0).getUser().getCreationTime().getYear(), true)
                            .addField("Avatar URL", "[Hier](" + member.getUser().getAvatarUrl() + ")", true)
                            .setFooter("SapphireBot for CodersClash © Knight 2018", "https://www2.pic-upload.de/img/35538072/logo_transparent.png")
                            .setThumbnail(event.getMessage().getMentionedMembers().get(0).getUser().getAvatarUrl())
                            .build()).queue();
        } else {
            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help user``", channel);
        }

    }
}
