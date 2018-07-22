package core;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandSettings;
import commands.fun.*;
import commands.game_tracker.OverwatchCommand;
import commands.game_tracker.PaladinsCommand;
import commands.games.JackCommand;
import commands.general.*;
import commands.moderation.*;
import config.Config;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Icon;
import org.apache.log4j.varia.NullAppender;
//import org.slf4j.Logger;
import utils.VariableStoring;
import utils.sql.MySQL;
import listener.ReadyListener;
import listener.XpListener;
import net.dv8tion.jda.core.entities.Game;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import static config.Config.fileChangeAction;
import static core.CLI.info;
import static core.CLI.input;
import static core.CLI.shutdown;
import static utils.VariableStoring.PREFIX;

public class Main {

    private static JDABuilder builder = new JDABuilder(AccountType.BOT);
    public static JDA jda;
    public static MySQL mySQL;
    public static File config = new File("sapphire.properties");
    private static URL url;
    private static File outputfile = null;



    public static void main(String[] Args) {

        System.out.println("___  ____ ____ ___ _ _  _ ____      ___  _    ____ ____ ____ ____    _ _ _ ____ _ ___    \n" +
                "|__] |  | |  |  |  | |\\ | | __      |__] |    |___ |__| [__  |___    | | | |__| |  |     \n" +
                "|__] |__| |__|  |  | | \\| |__] ..   |    |___ |___ |  | ___] |___    |_|_| |  | |  |  ...\n" +
                "\n");
        org.apache.log4j.BasicConfigurator.configure(new NullAppender());

        Config.init(config);
        //loading();

        if (startScript()){

            builder.setToken(VariableStoring.TOKEN);
            builder.setAutoReconnect(true);

            builder.setStatus(OnlineStatus.ONLINE);
            builder.setGame(Game.playing(VariableStoring.GAME + " | Prefix: " + PREFIX));


            initListeners();
            try {
                url = new URL("https://devcorpstudios.de/SapphireBot/Assets/logo.png"); // Link für das immer aktuelle Profilbild nicht ändern.
                Image image = ImageIO.read(url);
                outputfile = new File("Sapphire.png");
                    ImageIO.write((RenderedImage) image, "png", outputfile);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                CLI.shutdownError(1, "Bild konnte nicht geladen werden!\nBitte lade das Bild manuell herunter: https://www2.pic-upload.de/img/35637103/logo_transparent.png\nLege die Datei mit dem Namen \"Sapphire.png\" in den root Ordner vom Sapphire Bot ab!");
            }

            try {
                jda = builder.buildBlocking();
                jda.getSelfUser().getManager().setAvatar(Icon.from(outputfile)).queue();
            } catch (LoginException | IOException | InterruptedException e) {
                e.printStackTrace();
            }

            //settings =  new CommandSettings(VariableStoring.PREFIX, jda, true, 500);
            CommandSettings settings = new CommandSettings(PREFIX, jda, true);
            settings.put(new HelpCommand(), "help")
                    .put(new BioCommand(), "bio")
                    .put(new ProfileCommand(), "profile", "p")
                    .put(new ClearCommand(), "clear")
                    .put(new EPCommand(), "ep")
                    .put(new VoteCommand(), "vote", "poll", "ask")
                    .put(new WarnCommand(), "warn")
                    .put(new ReportCommand(), "report")
                    .put(new ConfigCommand(), "config", "c")
                    .put(new CoolifyCommand(), "cool", "coolify")
                    .put(new ShutdownCommand(), "shutdown", "shut", "down")
                    .put(new RepCommand(), "rep", "ruf", "reputation")
                    .put(new OverwatchCommand(), "overwatch", "ow")
                    .put(new PaladinsCommand(), "paladins", "pal")
                    .put(new CatCommand(), "cat")
                    .put(new DogCommand(), "dog")
                    .put(new DwdwkCommand(), "das_wars_dann_wieder_komplett", "dwdwk")
                    .put(new CappyCommand(), "cap", "cappy")
                    .put(new MemeCommand(), "meme", "memes")
                    .put(new SayCommand(), "announce", "say")
                    .put(new PaladinsCommand(), "pal")
                    .put(new UserCommand(), "user", "userinfo", "u")
                    .put(new HugCommand(), "hug")
                    .put(new KissCommand(), "kiss")
                    .put(new SaveCommand(), "save")
                    .put(new SmokeCommand(), "smoke")
                    .put(new ShootCommand(), "shoot")
                    .put(new JackCommand(), "jack", "blackjack")
                    .put(new GoogelCommand(), "google")
                    .activate();

    } else {
            shutdown(0);
        }
    }

    private static void initListeners() {
        builder
                .addEventListener(new ReadyListener())
                .addEventListener(new XpListener())
                .addEventListener(new VoteCommand())
        ;

    }

    private static boolean startScript() {
        if (VariableStoring.fastBoot) {
            return true;
        } else {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        boolean returnment;
        input("Mode (config/start/exit): ");
        try {
            switch (br.readLine().toLowerCase()) {
                case "config":
                    input("Do you really want to reset all of your Configurations (sapphire.properties)? [Y/N]");
                    switch (br.readLine().toLowerCase()) {
                        case "y":
                            info("Resetting Config...");
                            fileChangeAction(config);
                            break;
                        case "n":
                            info("Exiting...");
                            break;
                    }
                    returnment = false;
                    break;
                case "start":

                    returnment = true;
                    break;
                case "exit":
                    info("Exiting...");

                    returnment = false;
                    break;

                    default:
                        info("Starting...");
                        returnment = true;
                        break;
            }

            return returnment;

        } catch (IOException e) {
            e.printStackTrace();

            info("\nExiting...");
            return false;
        }

        }
    }
}
