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
import org.json.JSONArray;
import org.json.JSONObject;
import utils.MessageCreator;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class MemeCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        URL url_pre;
        try {
            url_pre = new URL("https://api.imgflip.com/get_memes");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url_pre.openStream(), "UTF-8"))) {
            String line;
            StringBuilder firstBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                firstBuilder.append(line);
            }
            JSONObject firstJsonObject = new JSONObject(firstBuilder.toString());

            if (firstJsonObject.getBoolean("success")) {
                JSONObject dataJsonObject = new JSONObject(firstJsonObject.get("data").toString());
                //JSONObject memesJsonObject = new JSONObject(dataJsonObject.toString());

                JSONArray jarray = dataJsonObject.getJSONArray("memes");
                JSONObject jArrayObject = jarray.getJSONObject((int) (Math.random() * 99));

            EmbedBuilder builder = new EmbedBuilder();
            builder
                    .setImage(jArrayObject.getString("url"))
                    .setTitle("Random Memes")
                    .setDescription("[" + jArrayObject.getString("name") + "](" + jArrayObject.getString("url") + ")");
                    //.setFooter("Falls die Informationen inkorrekt sind, ist das Profil eventuell Privat! -> " + VariableStoring.PREFIX + "ow public", "https://www2.pic-upload.de/img/35597749/Overwatch_circle_logo.png");

            channel.sendMessage(builder.build()).queue();

            } else {
                MessageCreator.createError("Es gab einen Fehler mit der API", channel);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            MessageCreator.createError("Entweder dieser Spieler existiert nicht, oder es gab einen Fehler mit der API! Versuche eventuell einen anderen Spieler oder versuche es in ein Paar Minuten erneut.\n\nEs kann vorkommen, dass vereinzelte Spieler zu verschiedenen Zeiten nicht mehr abrufbar sind.", channel);
        } catch (IOException e) {
            e.printStackTrace();
        }

    } catch(MalformedURLException e){
        e.printStackTrace();
    }
    }
}
