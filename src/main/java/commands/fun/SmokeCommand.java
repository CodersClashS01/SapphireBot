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

public class SmokeCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        EmbedBuilder builder = new EmbedBuilder().setColor(VariableStoring.COLOR);

                channel.sendMessage(builder.setTitle(member.getEffectiveName() + " raucht eine Zigarette!").setImage("https://www2.pic-upload.de/img/35634882/chloe_04.gif").build()).queue();

    }
}
