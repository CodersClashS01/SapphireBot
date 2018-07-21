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
import utils.VariableStoring;

public class CappyCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {

        event.getMessage().delete().queue();

        channel.sendMessage(new EmbedBuilder().setImage("https://www2.pic-upload.de/img/35641247/cappy.png").setColor(VariableStoring.COLOR).build()).queue();
    }
}
