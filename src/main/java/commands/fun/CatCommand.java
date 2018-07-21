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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class CatCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {

        URL url;
        try {
            URLConnection con = new URL("http://thecatapi.com/api/images/get?format=src&type=png").openConnection();
            con.connect();
            InputStream is = con.getInputStream();
            url = con.getURL();
            is.close();
            EmbedBuilder builder = new EmbedBuilder().setTitle("Katzen! Überall Katzen!").setImage(url.toString());
            channel.sendMessage(builder.build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
