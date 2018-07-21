package commands.fun;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.MessageCreator;
import utils.VariableStoring;

import static utils.Tools.coolify;

public class CoolifyCommand implements ICommand {


    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {


        event.getMessage().delete().queue();

        if (event.getMessage().getMentionedUsers().size() == 0) {


        String out = "";

            for (String arg : args) {
                out += arg;
            }

        out = out.replaceAll("[^a-zA-Z]","");

            if (!out.equals("")) {

            channel.sendMessage(coolify(out)).queue();

            } else {
                MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help coolify``", channel);
            }
        } else {
            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help coolify``", channel);
        }
    }
}
