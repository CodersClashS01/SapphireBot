package commands.general;
/*
    Created by ConnysSoftware / ConCode on 24.06.2018 at 18:30.
    
    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.MessageCreator;
import utils.VariableStoring;
import utils.Tools;
import utils.sql.UserSQL;

public class BioCommand implements ICommand {

    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        if (args.length > 1 && args[0].equals("set")){
            UserSQL userSQL = UserSQL.fromUser(event.getAuthor());
            String bio = Tools.argsToString(args, 1, " ");

            if (bio.length() <= 30) {
                userSQL.setBio(bio);
                MessageCreator.normalMessage("Bio geändert", "Bio wurde auf `" + bio + "` gesetzt", channel);
            } else {
                MessageCreator.createError(event.getMessage().getContentRaw(), "Der angegebene Text überschreitet die erlaubte maximale Länge! Die erlaubte maximale Länge beträgt 30 Zeichen.", "``" + VariableStoring.PREFIX +  "help bio``", channel);
            }
        } else if (args.length == 1 && args[0].equals("reset")) {
            MessageCreator.normalMessage("Bio zurückgesetzt", "Bio wurde auf die Standardwerte zurückgesetzt", channel);

            UserSQL userSQL = UserSQL.fromUser(event.getAuthor());
            String bio = "Keine BIO gesetzt... :/";
            userSQL.setBio(bio);
        } else {
            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help bio``", channel);
        }
    }
}
