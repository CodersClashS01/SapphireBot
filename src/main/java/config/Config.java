package config;

/*
    Created by ConnysSoftware / ConCode on 24.06.2018 at 17:22.
    
    (c) ConnysSoftware / ConCode 2018
*/

import core.CLI;
import core.Main;
import utils.VariableStoring;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

public class Config {

    private static Properties properties;

    public static void init(File file){
        properties = new Properties();
        try {
            InputStream in = new FileInputStream(file);
            properties.load(in);
            in.close();
            load();
        } catch (IOException e) {
            CLI.error("Settings File '" + file.getName() + "' (" + file.getAbsolutePath() + ") not found");
            fileNotFoundAction(file);
        }
    }

    private static void load(){
        try {
        VariableStoring.TOKEN = properties.getProperty("token");
            VariableStoring.MYSQL_HOST = properties.getProperty("mysql_host");
            VariableStoring.MYSQL_PORT = Integer.parseInt(properties.getProperty("mysql_port"));
        VariableStoring.MYSQL_USER = properties.getProperty("mysql_user");
        VariableStoring.MYSQL_PASSWORD = properties.getProperty("mysql_password");
            VariableStoring.MYSQL_DATABASE = properties.getProperty("mysql_database");

            VariableStoring.GAME = properties.getProperty("game");

            VariableStoring.pal_authkey = properties.getProperty("pal_authkey");
            VariableStoring.pal_devid = properties.getProperty("pal_devid");

        VariableStoring.fastBoot = Boolean.parseBoolean(properties.getProperty("fastboot").toLowerCase());

        VariableStoring.BOT_ADMINS = Arrays.asList(properties.getProperty("bot_admins").split(","));

        CLI.info("Bot admins are: " + Arrays.asList(properties.getProperty("bot_admins").split(",")));

        VariableStoring.COLOR = Color.decode("#" + properties.getProperty("color").replaceAll("#", ""));
        VariableStoring.PREFIX = properties.getProperty("prefix");

        CLI.success("Successfully loaded Config");
        } catch (NullPointerException | NumberFormatException e) {
            CLI.error("Fehler in der Konfiguration! Konfigurationsassistent wird gestartet...");
            fileNotFoundAction(Main.config);
        }
    }

    public static String get(String property) {
        return properties.getProperty(property);
    }

    public static String[] get_list() {
        String[] all_Props;

        StringBuilder all_Props_builder = new StringBuilder();

        Enumeration em = properties.keys();
        while(em.hasMoreElements()){
            String str = (String)em.nextElement();
            all_Props_builder.append(str).append("|");
            all_Props_builder.append(properties.get(str) + "|");
        }

        all_Props = all_Props_builder.toString().split("\\|", -1);

        return all_Props;
    }

    private static void fileNotFoundAction(File f){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("\n\n");
            CLI.info("sapphireBot Konfigurationsassistent wird gestartet...");
            CLI.customInfo(CLI.PURPLE, "Config", "Konfigurationsassistent wurde erfolgreich gestartet!\n" +
                    "Bitte folge den nun folgenden Anweisungen, um deine Kopie von dem sapphireBot korrekt einzustellen.\n");

            CLI.input("Bitte gib den Token deiner Bot-APP ein: ");
            properties.put("token", br.readLine());

            CLI.input("Was soll der Bot spielen?: ");
            properties.put("game", br.readLine());

            CLI.input("Bitte gib die Adresse zu deiner MySQL Datenbank an (localhost wenn auf gleichem Server): ");
            properties.put("mysql_host", br.readLine());

            CLI.input("Bitte gib den Port zu deiner MySQL Datenbank an (Wenn unbekannt 3306 eingeben): ");
            properties.put("mysql_port", br.readLine());

            CLI.input("Bitte gib den Namen deiner MySQL Datenbank ein: ");
            properties.put("mysql_database", br.readLine());

            CLI.input("Bitte gib den Benutzer mit Rechten zu deiner MySQL Datenbank an: ");
            properties.put("mysql_user", br.readLine());

            CLI.input("Bitte gib das Passwort zu dem Benutzer deiner MySQL Datenbank ein: ");
            properties.put("mysql_password", br.readLine());

            CLI.input("Bitte gib alle BotAdmins getrennt durch ein Komma und ein Leerzeichen ein (Die IDs! zb. 289077956976967680, 289077956976967680): ");
            properties.put("bot_admins", br.readLine());


            CLI.input("Möchtest du das Startmenü bei jedem Start in der Konsole sehen? [Y/N]: ");
            switch (br.readLine().toLowerCase()) {
                case "y":
                    properties.put("fastboot", "false");
                    break;

                case "n":
                    properties.put("fastboot", "true");
                    break;

                default:
                    properties.put("fastboot", "false");
                    break;
            }

            CLI.input("Welchen Prefix möchtest du für die Befehle verwenden? (Standart ist \"s!\"): ");

            String prefix = br.readLine();
            // prefix.replaceAll(" ", ""); unnötig af
            prefix = prefix.replaceAll(" ", "");
            if (!prefix.equals("") && !prefix.equals(null)) {
                properties.put("prefix", prefix);
            } else {
                CLI.error_SameLN("Prefix konnte nicht gesetzt werden! Setzte auf Standart: \"s!\"\n");
                properties.put("prefix", "s!");
            }

            properties.put("color", "7289da");

            System.out.println("\n");
            CLI.customInfo(CLI.PURPLE, "Config", "Konfigurationsassistent für den Paladins und SMITE Game-Tracker...\nFordere hier einen Developer Zugang an: https://fs12.formsite.com/HiRez/form48/secure_index.html\nSolltest du keinen Developer Zugang haben, fülle die folgenden Felder mit 'off'!\nFolgende Funktionen werden nicht zur verfügung stehen: 'Paladins Gametracker', 'SMITE Gametracker'");

            CLI.input("Bitte gib deine HiRez 'devId' ein: ");
            properties.put("pal_devid", br.readLine());

            CLI.input("Bitte gib deinen HiRez 'authKey' ein: ");
            properties.put("pal_authkey", br.readLine());

            //properties.put("sapphire_cat", br.readLine());
            //properties.put("sapphire_moderation", br.readLine());

            try {
                properties.store(new FileOutputStream(f), "Sapphire config");
                CLI.success("Konfigurationsdatei erfolgreich erstellt!");
                CLI.info("Bot wird nun beendet, bei dem nächsten Start wird die Konfiguration automatisch geladen!");
                CLI.shutdown(0);
            } catch (IOException e) {
                CLI.error("Es gab einen Fehler, als ich die Konfiguration abspeichern wollte!");
                e.printStackTrace();
            }

        } catch (IOException e) {
            CLI.error("Es gab einen Fehler, als ich deine eingaben überprüfen wollte!");
            e.printStackTrace();
        }
    }

    public static void fileChangeAction(File f){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            CLI.info("\n\nsapphireBot Konfigurationsassistent wird gestartet...");
            CLI.customInfo(CLI.PURPLE, "Config", "Konfigurationsassistent wurde erfolgreich gestartet!\n" +
                    "Bitte folge den nun folgenden Anweisungen, um deine Kopie von dem sapphireBot korrekt einzustellen.\n");

            CLI.input("Bitte gib den Token deiner Bot-APP ein (Derzeit: " + properties.getProperty("token") + " - Enter für unverändert): ");
            String token = br.readLine();
            token = token.replaceAll(" ", "");
            if (!token.equals("") && !token.equals(null)) {
                properties.put("token", token);
            } else {
            }

            CLI.input("Was soll der Bot spielen? (Derzeit: " + properties.getProperty("game") + " - Enter für unverändert): ");
            String game = br.readLine();
            game = game.replaceAll(" ", "");
            if (!game.equals("") && !game.equals(null)) {
                properties.put("game", game);
            } else {
            }

            CLI.input("Bitte gib die Adresse zu deiner MySQL Datenbank an (Derzeit: " + properties.getProperty("mysql_host") + " - Enter für unverändert): ");
            String mysql_host = br.readLine();
            mysql_host = mysql_host.replaceAll(" ", "");
            if (!mysql_host.equals("") && !mysql_host.equals(null)) {
                properties.put("mysql_host", mysql_host);
            } else {
            }

            CLI.input("Bitte gib den Port zu deiner MySQL Datenbank an (Derzeit: " + properties.getProperty("mysql_port") + " - Enter für unverändert): ");
            String mysql_port = br.readLine();
            mysql_port = mysql_port.replaceAll(" ", "");
            if (!mysql_port.equals("") && !mysql_port.equals(null)) {
                properties.put("mysql_port", mysql_port);
            } else {
            }

            CLI.input("Bitte gib den Namen deiner MySQL Datenbank ein (Derzeit: " + properties.getProperty("mysql_database") + " - Enter für unverändert): ");
            String mysql_database = br.readLine();
            mysql_database = mysql_database.replaceAll(" ", "");
            if (!mysql_database.equals("") && !mysql_database.equals(null)) {
                properties.put("mysql_database", mysql_database);
            } else {
            }

            CLI.input("Bitte gib den Benutzer mit Rechten zu deiner MySQL Datenbank an (Derzeit: " + properties.getProperty("mysql_user") + " - Enter für unverändert): ");
            String mysql_user = br.readLine();
            mysql_user = mysql_user.replaceAll(" ", "");
            if (!mysql_user.equals("") && !mysql_user.equals(null)) {
                properties.put("mysql_user", mysql_user);
            } else {
            }

            CLI.input("Bitte gib das Passwort zu dem Benutzer deiner MySQL Datenbank ein (Derzeit: " + properties.getProperty("mysql_password") + " - Enter für unverändert): ");
            String mysql_password = br.readLine();
            mysql_password = mysql_password.replaceAll(" ", "");
            if (!mysql_password.equals("") && !mysql_password.equals(null)) {
                properties.put("mysql_password", mysql_password);
            } else {
            }

            CLI.input("Bitte gib alle BotAdmins getrennt durch ein Komma und ein Leerzeichen ein (Derzeit: " + properties.getProperty("bot_admins") + " - Enter für unverändert): ");
            String bot_admins = br.readLine();
            bot_admins = bot_admins.replaceAll(" ", "");
            if (!bot_admins.equals("") && !bot_admins.equals(null)) {
                properties.put("bot_admins", bot_admins);
            } else {
            }


            CLI.input("Möchtest du das Startmenü bei jedem Start in der Konsole sehen? [Y/N]: ");
            switch (br.readLine().toLowerCase()) {
                case "y":
                    properties.put("fastboot", "false");
                    break;

                case "n":
                    properties.put("fastboot", "true");
                    break;

                default:
                    properties.put("fastboot", "false");
                    break;
            }

            CLI.input("Welchen Prefix möchtest du für die Befehle verwenden? (Derzeit: " + properties.getProperty("prefix") + " - Enter für unverändert): ");
            String prefix = br.readLine();
            prefix = prefix.replaceAll(" ", "");
            if (!prefix.equals("") && !prefix.equals(null)) {
                properties.put("prefix", prefix);
            } else {
            }

            properties.put("color", "7289da");

            //properties.put("sapphire_cat", br.readLine());
            //properties.put("sapphire_moderation", br.readLine());

            System.out.println("\n");
            CLI.customInfo(CLI.PURPLE, "Config", "Konfigurationsassistent für den Paladins und SMITE Game-Tracker...\nFordere hier einen Developer Zugang an: https://fs12.formsite.com/HiRez/form48/secure_index.html\nSolltest du keinen Developer Zugang haben, fülle die folgenden Felder mit 'off'!\nFolgende Funktionen werden nicht zur verfügung stehen: 'Paladins Gametracker', 'SMITE Gametracker'");

            CLI.input("Bitte gib deine HiRez 'devId' ein: ");
            properties.put("pal_devid", br.readLine());

            CLI.input("Bitte gib deinen HiRez 'authKey' ein: ");
            properties.put("pal_authkey", br.readLine());

            try {
                properties.store(new FileOutputStream(f), "Sapphire config");
                CLI.success("Konfigurationsdatei erfolgreich erstellt!");
                CLI.info("Bot wird nun beendet, bei dem nächsten Start wird die Konfiguration automatisch geladen!");
                CLI.shutdown(0);
            } catch (IOException e) {
                CLI.error("Es gab einen Fehler, als ich die Konfiguration abspeichern wollte!");
                e.printStackTrace();
            }

        } catch (IOException e) {
            CLI.error("Es gab einen Fehler, als ich deine eingaben überprüfen wollte!");
            e.printStackTrace();
        }
    }

    public static void overrideProperties(String stringName, String stringValue){
        properties.put(stringName, stringValue);

        try {
            properties.store(new FileOutputStream(Main.config), "Sapphire config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
