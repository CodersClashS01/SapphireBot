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
import utils.Tools;
import utils.VariableStoring;

public class ShootCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        EmbedBuilder builder = new EmbedBuilder().setColor(VariableStoring.COLOR);

        if (args.length >= 1 && Tools.isMention(args[0])) {

            if (event.getMessage().getMentionedMembers().get(0) == member) {
                channel.sendMessage(builder.setTitle(member.getEffectiveName() + " musste zusehen, wie eine Freundin erschossen wurde...").setImage("https://www2.pic-upload.de/img/35634909/chloe_shot.gif").build()).queue();
            } else {
                channel.sendMessage(builder.setTitle(member.getEffectiveName() + " hat " + event.getMessage().getMentionedMembers().get(0).getEffectiveName() + " selber erschossen!").setImage("https://www2.pic-upload.de/img/35634880/chloe_03.gif").build()).queue();
            }

        } else {
            channel.sendMessage(builder.setTitle(member.getEffectiveName() + " musste zusehen, wie eine Freundin erschossen wurde...").setImage("https://www2.pic-upload.de/img/35634909/chloe_shot.gif").build()).queue();
            //MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help hug``", channel);
        }
    }
}
