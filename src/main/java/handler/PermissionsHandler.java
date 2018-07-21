package handler;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/


import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.MessageCreator;
import utils.VariableStoring;

public class PermissionsHandler {

    public static boolean hasPermission(boolean requiresBotAdmin, Permission[] requiredPermissions, TextChannel channel, Member member) {

        boolean hasPermissions = member.hasPermission(requiredPermissions) &&
                (!requiresBotAdmin || VariableStoring.BOT_ADMINS.contains(member.getUser().getId())) || VariableStoring.BOT_ADMINS.contains(member.getUser().getId());


        if (hasPermissions){
            return true;
        } else if (!hasPermissions) {
            if (requiresBotAdmin && !VariableStoring.BOT_ADMINS.contains(member.getUser().getId())) {
                MessageCreator.createError("Du must ein Admin des Bots sein um diesen Befehl auszuführen!", channel);
            } else {
                StringBuilder requiredPerms = new StringBuilder();
                for (Permission p : requiredPermissions)
                    requiredPerms.append(p.getName()).append(", ");
                requiredPerms.delete(requiredPerms.length() - 2, requiredPerms.length());
                MessageCreator.createError("Um diesen Befehl ausführen zu können benötigst du folgende Berechtigungen: `" + requiredPerms.toString() + "`", channel);
            }
            return false;
        } else return requiresBotAdmin && VariableStoring.BOT_ADMINS.contains(member.getUser().getId());

    }
}