package commands.moderation;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.MessageCreator;
import utils.Tools;

import static handler.PermissionsHandler.hasPermission;

public class SayCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        String[] splitted = new String[0];
        if (hasPermission(false, new Permission[]{Permission.MESSAGE_MANAGE}, channel, member))
            splitted = Tools.argsToString(args, 0, " ").split("~");
        String message = splitted[1];
        MessageCreator.sayMessage(member, splitted[0], message, channel);

    }
}
