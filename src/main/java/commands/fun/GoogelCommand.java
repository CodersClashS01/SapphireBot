package commands.fun;
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
import utils.Tools;

public class GoogelCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        EmbedBuilder builder = new EmbedBuilder().setAuthor("Google Link", "http://lmgtfy.com/?q=" + Tools.argsToString(args, 0, "+"), "https://www2.pic-upload.de/img/35656954/google-logo-icon-PNG-Transparent-Background.png");
        event.getMessage().getTextChannel().sendMessage(builder.build()).queue();
    }
}
