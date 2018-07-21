package commands.fun;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.Tools;
import utils.VariableStoring;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

class OverwhelmCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        EmbedBuilder builder = new EmbedBuilder().setColor(VariableStoring.COLOR);

        if (args.length >= 1 && Tools.isMention(args[0])) {

            if (event.getMessage().getMentionedMembers().get(0) == member) {
                //channel.sendMessage(builder.setTitle(member.getEffectiveName() + " hat leider niemanden zum Küssen...").setImage("https://www2.pic-upload.de/img/35633191/tumblr_ou4xdh96jS1w9q1uyo2_500.gif").build()).queue();
            } else {

                EmbedBuilder warning = new EmbedBuilder().setTitle("WARNING!").setDescription("Spoiler Warning for Life is Strange 1, Eposode 4!\nShowing Content in 10 Seconds!").setColor(Color.red);
                Message warning_msg = channel.sendMessage(warning.build()).complete();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        warning_msg.delete().queue();
                        channel.sendMessage(builder.setTitle(member.getEffectiveName() + " überwältigt " + event.getMessage().getMentionedMembers().get(0).getEffectiveName() + "!").setImage("https://www2.pic-upload.de/img/35633492/jefferson.gif").build()).queue();
                    }
                }, 10000);

            }

        } else {
            //channel.sendMessage(builder.setTitle(member.getEffectiveName() + " hat leider niemanden zum Küssen...").setImage("https://www2.pic-upload.de/img/35633191/tumblr_ou4xdh96jS1w9q1uyo2_500.gif").build()).queue();
            //MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help hug``", channel);
        }
    }
}
