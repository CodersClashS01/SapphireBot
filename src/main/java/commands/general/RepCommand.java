package commands.general;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import utils.MessageCreator;
import utils.VariableStoring;
import utils.Tools;
import utils.sql.UserSQL;

import static handler.PermissionsHandler.hasPermission;

public class RepCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        UserSQL userSQL;
        User u;
        if (args.length >= 1 && Tools.isMention(args[1])) {

            u = event.getMessage().getMentionedUsers().get(0);
            userSQL = UserSQL.fromUser(u);

            UserSQL.fromUser(event.getMessage().getMentionedUsers().get(0));

            String MemberName;

            if (member.getNickname() != null) {
                MemberName = member.getNickname();
            } else {
                MemberName = member.getEffectiveName();
            }

            switch (args[0]) {
                case "+":
                    if (event.getMember() ==  event.getGuild().getMemberById(event.getMessage().getMentionedUsers().get(0).getId())) {
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Na na na! Du kannst dir nicht selber einen guten Ruf verschaffen.", channel);
                    } else {
                        userSQL.addRep(1);
                    MessageCreator.normalMessage("Guter Ruf", MemberName + " hat " + event.getMessage().getMentionedUsers().get(0).getName() + " einen besseren Ruf verschafft!", channel);
                    }
                    break;
                case "-":
                    if (event.getMember() ==  event.getGuild().getMemberById(event.getMessage().getMentionedUsers().get(0).getId())) {
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Na na na! Du kannst dir nicht selber einen guten Ruf verschaffen.", channel);
                    } else {
                        userSQL.takeRep(1);
                        MessageCreator.normalMessage("Schlechter Ruf", MemberName + " hat " + event.getMessage().getMentionedUsers().get(0).getName() + " einen schlechteren Ruf verschafft!", channel);
                    }
                    break;
                case "set":

                    if (hasPermission(true, new Permission[]{}, channel, member))

                    if (args.length >= 3) {

                    userSQL.setRep(Integer.parseInt(args[2]));
                        MessageCreator.normalMessage("Reputations System", MemberName + " hat den Ruf von " + event.getMessage().getMentionedUsers().get(0).getName() + " auf `" + args[2] + "` gesetzt!", channel);
                    } else {
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help rep``", channel);
                    }
                    break;

                    default:
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help rep``", channel);
                        break;
            }

        } else {
            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help rep``", channel);
        }

    }
}
