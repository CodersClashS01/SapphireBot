package commands.fun;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class DwdwkCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent commandEvent, Member member, TextChannel channel, String[] args) {

        commandEvent.getMessage().delete().queue();

        channel.sendMessage(new EmbedBuilder().setImage("https://www2.pic-upload.de/img/35614701/IMG-20180626-WA0004.jpg").setTitle("Das wars dann wieder komplett...").build()).queue();
    }
}
