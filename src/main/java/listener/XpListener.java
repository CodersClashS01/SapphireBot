package listener;
/*
    Created by ConnysSoftware / ConCode on 24.06.2018 at 17:43.
    
    (c) ConnysSoftware / ConCode 2018
    (c) strukteon
*/

import utils.sql.UserSQL;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class XpListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT) && !event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId()) && !event.getMember().getUser().isBot()){
            UserSQL userSQL = UserSQL.fromUser(event.getAuthor());
            userSQL.addXp(event.getMessage().getContentRaw().length() / 5);
        }
    }
}
