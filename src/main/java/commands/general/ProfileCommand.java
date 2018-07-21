package commands.general;
/*
    Created by ConnysSoftware / ConCode on 24.06.2018 at 18:30.
    
    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import utils.MessageCreator;
import utils.sql.ReportSQL;
import utils.sql.UserSQL;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.imageMaking.ProfileCard.img_createProfileImage;

public class ProfileCommand implements ICommand {

    @Override
    public void onCommand(CommandEvent event, Member member_event, TextChannel channel, String[] args) {
        UserSQL userSQL;
        User u;
            try {
                if (args.length >= 1) {
                    Member member;
                    if (event.getMessage().getMentionedUsers().size() == 1) {
                        member = event.getGuild().getMemberById(event.getMessage().getMentionedUsers().get(0).getId());
                    } else if(event.getGuild().getMemberById(args[0]) != null) {
                        member = event.getGuild().getMemberById(args[0]);
                    } else {
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Mitglied mit dieser ID oder Markierung konnte auf diesem Guild nicht gefunden werden! Falsch geschrieben?", "Eingaben überprüfen", channel);
                        return;
                    }
                    u = member.getUser();
                    userSQL = UserSQL.fromUser(u);

                    int Level = userSQL.getLevel();

                    UserSQL.fromUser(member.getUser());


                    URL url;
                    File outputfile;
                    url = new URL("https://www2.pic-upload.de/img/35620093/profile-card-empty-new.png");
                    outputfile = new File("Profile-Cards/" + "template.png");

                    Image image = ImageIO.read(url);
                    Path folderPath = Paths.get("Profile-Cards/");
                    if (Files.exists(folderPath)) {

                        if (!outputfile.exists()) {
                            ImageIO.write((RenderedImage) image, "png", outputfile);
                        }
                    } else {
                        File dir = new File("Profile-Cards");
                        dir.mkdir();
                        if (!outputfile.exists()) {

                            ImageIO.write((RenderedImage) image, "png", outputfile);
                        }
                    }

                    URL goodUrl = new URL("https://www2.pic-upload.de/img/35620084/goodrep.png");
                    URL badUrl = new URL("https://www2.pic-upload.de/img/35620083/bad_rep.png");
                    URL normUrl = new URL("https://www2.pic-upload.de/img/35620082/normal.png");
                    Image goodState = ImageIO.read(goodUrl);
                    Image badState = ImageIO.read(badUrl);
                    Image normState = ImageIO.read(normUrl);
                    File goodOutputfile = new File("State-Cards/" + "goodrep.png");
                    File badOutputfile = new File("State-Cards/" + "bad_rep.png");
                    File normOutputfile = new File("State-Cards/" + "normal.png");
                    Path stateFolderPath = Paths.get("State-Cards/");
                    if (Files.exists(stateFolderPath)) {

                        if (!goodOutputfile.exists()) {
                            ImageIO.write((RenderedImage) goodState, "png", goodOutputfile);
                        }
                        if (!badOutputfile.exists()) {
                            ImageIO.write((RenderedImage) badState, "png", badOutputfile);
                        }
                        if (!normOutputfile.exists()) {
                            ImageIO.write((RenderedImage) normState, "png", normOutputfile);
                        }
                    } else {
                        File dir = new File("State-Cards");
                        dir.mkdir();
                        if (!goodOutputfile.exists()) {
                            ImageIO.write((RenderedImage) goodState, "png", goodOutputfile);
                        }
                        if (!badOutputfile.exists()) {
                            ImageIO.write((RenderedImage) badState, "png", badOutputfile);
                        }
                        if (!normOutputfile.exists()) {
                            ImageIO.write((RenderedImage) normState, "png", normOutputfile);
                        }
                    }

                    File input = outputfile;

                    File output = new File("Profile-Cards/Profile-Card_" + member.getUser().getId() + ".png");

                    String RealName = member.getUser().getName();

                    String NickName = member.getNickname();

                    String Final_Name;

                    if (NickName != null) {
                        Final_Name = NickName;
                    } else {
                        Final_Name = RealName;
                    }

                    Final_Name = Final_Name.replaceAll("[^a-zA-Z0-9]", "");

                    if (Final_Name.equalsIgnoreCase("")) {
                        Final_Name = "Empty Name";
                    }


                    final String urlStr = member.getUser().getAvatarUrl();
                    final URL urll = new URL(urlStr);
                    final HttpURLConnection connection_sec = (HttpURLConnection) urll
                            .openConnection();
                    connection_sec.setRequestProperty(
                            "User-Agent",
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

                    final BufferedImage PB = ImageIO.read(connection_sec.getInputStream());
                    File on_off;

                    String oState;

                    if (member.getOnlineStatus() == OnlineStatus.OFFLINE || member.getOnlineStatus() == OnlineStatus.UNKNOWN) {
                        oState = "Offline";
                    } else if (member.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB) {
                        oState = "Beschäftigt";
                    } else if (member.getOnlineStatus() == OnlineStatus.IDLE) {
                        oState = "Abwesend";
                    } else {
                        oState = "Online";
                    }
                    Color on_off_Color;

                    if (ReportSQL.getReportsByUser(u.getId(), event.getGuild().getId()).size() > 3 || userSQL.getRep() < 0) {
                        on_off = badOutputfile;
                        //on_off_Color = new Color(234, 67, 53, 255);
                        on_off_Color = new Color(109, 207, 246, 255);
                    } else if (ReportSQL.getReportsByUser(u.getId(), event.getGuild().getId()).size() <= 1 && userSQL.getRep() >= 150) {
                        on_off = goodOutputfile;
                        //on_off_Color = new Color(161, 244, 16, 255);
                        on_off_Color = new Color(109, 207, 246, 255);
                    } else {
                        on_off = normOutputfile;
                        on_off_Color = new Color(109, 207, 246, 255);
                    }

                    channel.sendFile(img_createProfileImage(Final_Name, u.getId(), oState, String.valueOf(userSQL.getRep()), on_off_Color, userSQL.getBio(), String.valueOf(ReportSQL.getReportsByUser(u.getId(), event.getGuild().getId()).size()), userSQL.getXp(), Level, PB, on_off, "png", input, output)).queue();

                } else if (args.length < 1){
                    Member member;

                    member = member_event;

                    u = member.getUser();
                    userSQL = UserSQL.fromUser(u);

                    int Level = userSQL.getLevel();

                    UserSQL.fromUser(member.getUser());


                    URL url;
                    File outputfile;
                    url = new URL("https://www2.pic-upload.de/img/35620093/profile-card-empty-new.png");
                    outputfile = new File("Profile-Cards/" + "template.png");

                    Image image = ImageIO.read(url);
                    Path folderPath = Paths.get("Profile-Cards/");
                    if (Files.exists(folderPath)) {

                        if (!outputfile.exists()) {
                            ImageIO.write((RenderedImage) image, "png", outputfile);
                        }
                    } else {
                        File dir = new File("Profile-Cards");
                        dir.mkdir();
                        if (!outputfile.exists()) {

                            ImageIO.write((RenderedImage) image, "png", outputfile);
                        }
                    }

                    URL goodUrl = new URL("https://www2.pic-upload.de/img/35620084/goodrep.png");
                    URL badUrl = new URL("https://www2.pic-upload.de/img/35620083/bad_rep.png");
                    URL normUrl = new URL("https://www2.pic-upload.de/img/35620082/normal.png");
                    Image goodState = ImageIO.read(goodUrl);
                    Image badState = ImageIO.read(badUrl);
                    Image normState = ImageIO.read(normUrl);
                    File goodOutputfile = new File("State-Cards/" + "goodrep.png");
                    File badOutputfile = new File("State-Cards/" + "bad_rep.png");
                    File normOutputfile = new File("State-Cards/" + "normal.png");
                    Path stateFolderPath = Paths.get("State-Cards/");
                    if (Files.exists(stateFolderPath)) {

                        if (!goodOutputfile.exists()) {
                            ImageIO.write((RenderedImage) goodState, "png", goodOutputfile);
                        }
                        if (!badOutputfile.exists()) {
                            ImageIO.write((RenderedImage) badState, "png", badOutputfile);
                        }
                        if (!normOutputfile.exists()) {
                            ImageIO.write((RenderedImage) normState, "png", normOutputfile);
                        }
                    } else {
                        File dir = new File("State-Cards");
                        dir.mkdir();
                        if (!goodOutputfile.exists()) {
                            ImageIO.write((RenderedImage) goodState, "png", goodOutputfile);
                        }
                        if (!badOutputfile.exists()) {
                            ImageIO.write((RenderedImage) badState, "png", badOutputfile);
                        }
                        if (!normOutputfile.exists()) {
                            ImageIO.write((RenderedImage) normState, "png", normOutputfile);
                        }
                    }

                    File input = outputfile;

                    File output = new File("Profile-Cards/Profile-Card_" + member.getUser().getId() + ".png");

                    String RealName = member.getUser().getName();

                    String NickName = member.getNickname();

                    String Final_Name;

                    if (NickName != null) {
                        Final_Name = NickName;
                    } else {
                        Final_Name = RealName;
                    }

                    Final_Name = Final_Name.replaceAll("[^a-zA-Z0-9\\s]", "");

                    if (Final_Name.equalsIgnoreCase("")) {
                        Final_Name = "Empty Name";
                    }


                    final String urlStr = member.getUser().getAvatarUrl();
                    final URL urll = new URL(urlStr);
                    final HttpURLConnection connection_sec = (HttpURLConnection) urll
                            .openConnection();
                    connection_sec.setRequestProperty(
                            "User-Agent",
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

                    final BufferedImage PB = ImageIO.read(connection_sec.getInputStream());
                    File on_off;

                    String oState;

                    if (member.getOnlineStatus() == OnlineStatus.OFFLINE || member.getOnlineStatus() == OnlineStatus.UNKNOWN) {
                        oState = "Offline";
                    } else if (member.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB) {
                        oState = "Beschäftigt";
                    } else if (member.getOnlineStatus() == OnlineStatus.IDLE) {
                        oState = "Abwesend";
                    } else {
                        oState = "Online";
                    }

                    Color on_off_Color;

                    if (ReportSQL.getReportsByUser(u.getId(), event.getGuild().getId()).size() > 3 || userSQL.getRep() < 0) {
                        on_off = badOutputfile;
                        //on_off_Color = new Color(234, 67, 53, 255);
                        on_off_Color = new Color(109, 207, 246, 255);
                    } else if (ReportSQL.getReportsByUser(u.getId(), event.getGuild().getId()).size() <= 1 && userSQL.getRep() >= 150) {
                        on_off = goodOutputfile;
                        //on_off_Color = new Color(161, 244, 16, 255);
                        on_off_Color = new Color(109, 207, 246, 255);
                    } else {
                        on_off = normOutputfile;
                        on_off_Color = new Color(109, 207, 246, 255);
                    }

                    channel.sendFile(img_createProfileImage(Final_Name, u.getId(), oState, String.valueOf(userSQL.getRep()), on_off_Color, userSQL.getBio(), String.valueOf(ReportSQL.getReportsByUser(u.getId(), event.getGuild().getId()).size()), userSQL.getXp(), Level, PB, on_off, "png", input, output)).queue();

                }
            } catch (IOException e) {
                MessageCreator.createError(e.getCause().toString(), "Ein Fehler im Code ist aufgetreten! Das sollte nicht passieren! :thinking:", "Überprüfe die Konsole", channel);
                e.printStackTrace();
            }



    }
}
