package commands.moderation;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import core.CLI;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import static handler.PermissionsHandler.hasPermission;

public class ShutdownCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        if (hasPermission(true, new Permission[]{}, channel, member)) {

            channel.sendMessage("Ich fahre mich herunter!").queue();
            CLI.shutdown(0);
        }

    }
}
