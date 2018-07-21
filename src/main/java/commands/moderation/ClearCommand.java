package commands.moderation;
/*
    Created by ConnysSoftware / ConCode on 25.06.2018 at 18:55.
    
    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.MessageCreator;
import utils.VariableStoring;

import java.util.List;

import static handler.PermissionsHandler.hasPermission;

public class ClearCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {

        if (hasPermission(false, new Permission[]{Permission.MESSAGE_MANAGE}, channel, member))

        if (args.length == 1){
            try {
                if (Integer.parseInt(args[0]) >= 2 && Integer.parseInt(args[0]) <= 100){
                    MessageHistory msg_history = new MessageHistory(channel);
                    List<Message> msg_list;
                    msg_list = msg_history.retrievePast(Integer.parseInt(args[0])).complete();
                    channel.deleteMessages(msg_list).queue();
                    msg_list.clear();
                } else {
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Die angegebene Zahl befindet sich außerhalb des erlaubten Bereiches! Sie muss zwischen 2 und 100 liegen.", "``" + VariableStoring.PREFIX +  "help clear``", channel);
                }

            } catch (NumberFormatException e) {
                MessageCreator.createError(event.getMessage().getContentRaw(), "Das benötigte erste Argument ist kein Integer.", "``" + VariableStoring.PREFIX +  "help clear``", channel);
            } catch (IllegalArgumentException e) {
                MessageCreator.createError(event.getMessage().getContentRaw(), "Einige Nachrichten die du zu löschen versuchst, existieren nicht mehr, oder sind älter als 2 Wochen.", channel);
            }
        } else {
            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help clear``", channel);
        }
    }
}
