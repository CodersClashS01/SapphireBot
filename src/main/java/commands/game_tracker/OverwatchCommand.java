package commands.game_tracker;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import org.json.JSONObject;
import utils.MessageCreator;
import utils.VariableStoring;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class OverwatchCommand implements ICommand {
    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        switch (args[0].toLowerCase()) {
            case "public":
                member.getUser().openPrivateChannel().queue(pc -> pc.sendMessage("So änderst du dein privates Overwatch-Profil zu einem öffentlichen Overwatch-Profil: https://i.imgur.com/8PqBe20.jpg\nÄndere einfach die Einstellung `Karrieresichtbarkeit` auf `Öffentlich`!").queue());

                break;

            case "profile":

                if (args.length < 3) {
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help overwatch``", channel);
                } else {

                    try {
                        // Merken https://hastebin.com/hediqosute.cs

                        String aName = args[1];
                        String aDone = aName.replace("#", "-");
                        String aPLAT = args[2].toLowerCase();

                        String profileURL = "https://playoverwatch.com/en-us/career/" + aPLAT + "/" + aDone;
                        String iconURL;
                        String levelIconURL;
                        String nameString;
                        String ratingNameString = "";
                        String ratingString;
                        int levelInt = 0;
                        int prestigeInt = 0;
                        int gamesWonInt = 0;

                        URL url_pre = new URL("https://ow-api.com/v1/stats/" + aPLAT + "/eu/" + aDone + "/complete");
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url_pre.openStream(), "UTF-8"))) {
                            String line;
                            StringBuilder firstBuilder = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                firstBuilder.append(line);
                            }
                            JSONObject firstJsonObject = new JSONObject(firstBuilder.toString());
                            //Neue Objects
                            JSONObject quickPlayStatsObject;
                            JSONObject careerStatsObject;
                            JSONObject allHeroesObject;
                            JSONObject assistsObject;
                            JSONObject bestObject;
                            JSONObject combatObject;
                            JSONObject gameObject;
                            JSONObject matchAwardsObject;

                            try {
                                //Strings
                                iconURL = firstJsonObject.getString("icon");
                                levelIconURL = firstJsonObject.getString("levelIcon");
                                nameString = firstJsonObject.getString("name");
                                ratingNameString = firstJsonObject.getString("ratingName");
                                //Ints
                                ratingString = firstJsonObject.getString("rating");
                                levelInt = firstJsonObject.getInt("level");
                                prestigeInt = firstJsonObject.getInt("prestige");
                                gamesWonInt = firstJsonObject.getInt("gamesWon");

                                //Neue Objects
                                quickPlayStatsObject = new JSONObject(firstJsonObject.get("quickPlayStats").toString());
                                careerStatsObject = new JSONObject(quickPlayStatsObject.get("careerStats").toString());
                                allHeroesObject = new JSONObject(careerStatsObject.get("allHeroes").toString());
                                assistsObject = new JSONObject(allHeroesObject.get("assists").toString());
                                bestObject = new JSONObject(allHeroesObject.get("best").toString());
                                combatObject = new JSONObject(allHeroesObject.get("combat").toString());
                                gameObject = new JSONObject(allHeroesObject.get("game").toString());
                                matchAwardsObject = new JSONObject(allHeroesObject.get("matchAwards").toString());
                            } catch (Exception ex) {
                                try { MessageCreator.createError("Entweder dieser Spieler existiert nicht, oder es gab einen Fehler mit der API! Versuche eventuell einen anderen Spieler.\n\nEs kann vorkommen, dass vereinzelte Spieler zu verschiedenen Zeiten nicht mehr abrufbar sind.\n\nBekannter Fehler: `" + firstJsonObject.get("error").toString() + "`\n\nFehler-Guide:\n`The requested player was not found` -> Entweder der Spieler existiert nicht, oder die API hat zur Zeit Probleme disen Spieler auszulesen.\nManuell überprüfen: [Overwatch Profil](" + profileURL + ")", channel); } catch (Exception exc) {
                                    MessageCreator.createError("Es gab einen Fehler, als wir die Daten laden wollten! \n\nWarscheinlich ist das Profil privat!\nManuell überprüfen: [Overwatch Profil](" + profileURL + ")\n\nWie ändere ich das? -> " + VariableStoring.PREFIX + "overwatch public", channel);
                                }
                                return;
                            }
                            EmbedBuilder builder = new EmbedBuilder();
                            builder
                                    .setThumbnail(iconURL)
                                    .setFooter("Falls die Informationen inkorrekt sind, ist das Profil eventuell Privat! -> " + VariableStoring.PREFIX + "ow public", "https://www2.pic-upload.de/img/35597749/Overwatch_circle_logo.png");

                            //try { builder.addField("", "**Karriere-Statistiken (Generell):**", false); } catch (Exception e) {}
                            try { builder.addField("Spielzeit (Ingame)", "" + gameObject.getString("timePlayed"), true); } catch (Exception ignored) {}
                            try { builder.addField("Spiele gewonnen", "" + gameObject.getInt("gamesWon"), true); } catch (Exception ignored) {}
                            try { builder.addField("Auszeichnungen", "Karten: " + matchAwardsObject.getInt("cards") + "\n" +
                                    "Medallien: " + matchAwardsObject.getInt("cards") + "\n" +
                                    "Bronze-Medallien: " + matchAwardsObject.getInt("medalsBronze") + "\n" +
                                    "Silber-Medallien: " + matchAwardsObject.getInt("medalsGold") + "\n" +
                                    "Gold-Medallien: " + matchAwardsObject.getInt("medalsSilver"), false); } catch (Exception ignored) {}

                            try { builder.addField("", "**Karriere-Statistiken (Behilflich sein):**", false); } catch (Exception ignored) {}
                            try { builder.addField("Defensive Assists", "" + assistsObject.getInt("defensiveAssists"), true); } catch (Exception ignored) {}
                            try { builder.addField("Insgesamt geheilt", "" + assistsObject.getInt("healingDone"), true); } catch (Exception ignored) {}
                            try { builder.addField("Offensive Assists", "" + assistsObject.getInt("offensiveAssists"), true); } catch (Exception ignored) {}
                            try { builder.addField("Recon Assist", "" + assistsObject.getInt("reconAssists"), true); } catch (Exception ignored) {}
                            try { builder.addField("Teleporter zerstört", "" + assistsObject.getInt("teleporterPadsDestroyed"), true); } catch (Exception ignored) {}

                            try { builder.addField("", "**Karriere-Statistiken (Die meisten...in einer Runde):**", false); } catch (Exception ignored) {}
                            try { builder.addField("Schaden (An allem)", "" + bestObject.getInt("allDamageDoneMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Schaden gegen Helden", "" + bestObject.getInt("heroDamageDoneMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Schaden an Deckungen", "" + bestObject.getInt("barrierDamageDoneMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Verursachte Heilung", "" + bestObject.getInt("healingDoneMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Offensiven Assists", "" + bestObject.getInt("offensiveAssistsMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Defensiven Assists", "" + bestObject.getInt("defensiveAssistsMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Eliminierungen", "" + bestObject.getInt("eliminationsMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Solokills", "" + bestObject.getInt("soloKillsMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Umgebungs-Kills", "" + bestObject.getInt("environmentalKillsMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Final Blows", "" + bestObject.getInt("finalBlowsMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Final Blows im Nahkampf", "" + bestObject.getInt("meleeFinalBlowsMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Killstreaks", "" + bestObject.getInt("killsStreakBest"), true); } catch (Exception ignored) {}
                            try { builder.addField("Multikills", "" + bestObject.getInt("multikillsBest"), true); } catch (Exception ignored) {}
                            try { builder.addField("Zielfortschritt-Kills", "" + bestObject.getInt("objectiveKillsMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Zielfortschritt-Zeit", bestObject.getString("objectiveTimeMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Recon-Assists", "" + bestObject.getInt("reconAssistsMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Schildgeneratoren zerstört", "" + bestObject.getInt("shieldGeneratorsDestroyedMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Teleporter zerstört", "" + bestObject.getInt("teleporterPadsDestroyedMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Turrets zerstört", "" + bestObject.getInt("turretsDestroyedMostInGame"), true); } catch (Exception ignored) {}
                            try { builder.addField("Zeit geschossen", "" + bestObject.getInt("timeSpentOnFireMostInGame"), true); } catch (Exception ignored) {}

                            try { builder.addField("", "**Karriere-Statistiken (Im Kampf insgesamt):**", false); } catch (Exception ignored) {}
                            try { builder.addField("Schaden (An allem)", "" + combatObject.getInt("damageDone"), true); } catch (Exception ignored) {}
                            try { builder.addField("Schaden an Schilden", "" + combatObject.getInt("barrierDamageDone"), true); } catch (Exception ignored) {}
                            try { builder.addField("Schaden an anderen Helden", "" + combatObject.getInt("heroDamageDone"), true); } catch (Exception ignored) {}
                            try { builder.addField("Tode", "" + combatObject.getInt("deaths"), true); } catch (Exception ignored) {}
                            try { builder.addField("Kills", "" + combatObject.getInt("eliminations"), true); } catch (Exception ignored) {}
                            try { builder.addField("Umgebungs-Kills", "" + combatObject.getInt("environmentalKills"), true); } catch (Exception ignored) {}
                            try { builder.addField("Final Blows", "" + combatObject.getInt("finalBlows"), true); } catch (Exception ignored) {}
                            try { builder.addField("Final Blows im Nahkampf", "" + combatObject.getInt("meleeFinalBlows"), true); } catch (Exception ignored) {}
                            try { builder.addField("Solokills", "" + combatObject.getInt("soloKills"), true); } catch (Exception ignored) {}
                            try { builder.addField("Multikills", "" + combatObject.getInt("multikills"), true); } catch (Exception ignored) {}
                            try { builder.addField("Zielfortschritt-Zeit", combatObject.getString("objectiveTime"), true); } catch (Exception ignored) {}
                            try { builder.addField("Zielfortschritt-Kills", "" + combatObject.getInt("objectiveKills"), true); } catch (Exception ignored) {}
                            try { builder.addField("Zeit gebrannt", combatObject.getString("timeSpentOnFire"), true); } catch (Exception ignored) {}


                            if (ratingString.equals("")) {
                                builder.setAuthor(nameString + ", Unranked - Platform: " + aPLAT, profileURL, levelIconURL);
                            } else {
                                builder.setAuthor(nameString + ", Rating: " + ratingString + " - Platform: " + aPLAT, profileURL, levelIconURL);
                            }

                            channel.sendMessage(builder.build()).queue();

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
                break;

            case "hero":
                if (args.length < 4) {
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "Verwende die korrekte Schreibweise: `" + VariableStoring.PREFIX + "overwatch hero [BattleTag#BattleID] [pc/...] [dVa, ana, ...]`", channel);
                } else {
                    String[] heros = new String[]{"ana", "bastion", "brigitte", "dVa", "doomfist", "genji", "hanzo", "junkrat", "lucio", "mccree", "mei", "mercy", "moira", "orisa", "pharah", "reaper", "reinhardt", "roadhog", "soldier76", "sombra", "symmetra", "torbjorn", "tracer", "widowmaker", "winston", "zarya", "zenyatta"};


                    String aName = args[1];
                    String aDone = aName.replace("#", "-");
                    String aPLAT = args[2].toLowerCase();
                    String aHero = args[3];

                    if (aHero.equalsIgnoreCase("dva")) {
                        aHero = "dVa";
                    } else {
                        aHero = aHero.toLowerCase();
                    }

                    List<String> contains = Arrays.asList(heros);

                    if (contains.contains(aHero)) {

                        try {
                            String profileURL = "https://playoverwatch.com/en-us/career/" + aPLAT + "/" + aDone;
                            String iconURL;
                            String levelIconURL;
                            String nameString;
                            String ratingString;

                            URL url_pre = new URL("https://ow-api.com/v1/stats/" + aPLAT + "/eu/" + aDone + "/complete");
                            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url_pre.openStream(), "UTF-8"))) {
                                String line;
                                StringBuilder firstBuilder = new StringBuilder();
                                while ((line = reader.readLine()) != null) {
                                    firstBuilder.append(line);
                                }

                                JSONObject quickPlayStatsObject;
                                JSONObject firstJsonObject = new JSONObject(firstBuilder.toString());
                                try {
                                //Strings
                                iconURL = firstJsonObject.getString("icon");
                                levelIconURL = firstJsonObject.getString("levelIcon");
                                nameString = firstJsonObject.getString("name");
                                ratingString = firstJsonObject.getString("rating");

                                //Neue Objects
                                    quickPlayStatsObject = new JSONObject(firstJsonObject.get("quickPlayStats").toString());

                                }catch (Exception ex) {
                                    try { MessageCreator.createError("Entweder dieser Spieler existiert nicht, oder es gab einen Fehler mit der API! Versuche eventuell einen anderen Spieler.\n\nEs kann vorkommen, dass vereinzelte Spieler zu verschiedenen Zeiten nicht mehr abrufbar sind.\n\nBekannter Fehler: `" + firstJsonObject.get("error").toString() + "`\n\nFehler-Guide:\n`The requested player was not found` -> Entweder der Spieler existiert nicht, oder die API hat zur Zeit Probleme disen Spieler auszulesen.\nManuell überprüfen: [Overwatch Profil](" + profileURL + ")", channel); } catch (Exception exc) {
                                        MessageCreator.createError("Es gab einen Fehler, als wir die Daten laden wollten! \n\nWarscheinlich ist das Profil privat!\nManuell überprüfen: [Overwatch Profil](" + profileURL + ")\n\nWie ändere ich das? -> " + VariableStoring.PREFIX + "overwatch public", channel);
                                    }

                                    return;
                                }
                                try {
                                    quickPlayStatsObject.get("topHeroes").toString();
                                    JSONObject topHeroesObject = new JSONObject(quickPlayStatsObject.get("topHeroes").toString());
                                    JSONObject heroObject = new JSONObject(topHeroesObject.get(aHero).toString());

                                    JSONObject compQuickPlayStatsObject = new JSONObject(firstJsonObject.get("competitiveStats").toString());
                                    JSONObject compTopHeroesObject = new JSONObject(compQuickPlayStatsObject.get("topHeroes").toString());
                                    JSONObject compHeroObject = new JSONObject(compTopHeroesObject.get(aHero).toString());

                                    EmbedBuilder builder = new EmbedBuilder();
                                    builder
                                            .setThumbnail(iconURL)
                                            .setFooter("Falls die Informationen inkorrekt sind, ist das Profil eventuell Privat! -> " + VariableStoring.PREFIX + "ow public", "https://www2.pic-upload.de/img/35597749/Overwatch_circle_logo.png");


                                    //QuickPlay
                                    builder.addField("", "**" + aHero + " Statistiken (QuickPlay):**", false)
                                            .addField("Spielzeit mit dem Helden", heroObject.getString("timePlayed"), true)
                                            .addField("Spiele gewonnen", "" + heroObject.getInt("gamesWon"), true)
                                            //.addField("Wins in %", "" + heroObject.getInt("winPercentage"), true)
                                            .addField("Waffen-Genauigkeit in %", "" + heroObject.getInt("weaponAccuracy"), true)
                                            .addField("Kills pro Leben (K/D)", "" + heroObject.getInt("eliminationsPerLife"), true)
                                            .addField("Bester Multikill", "" + heroObject.getInt("multiKillBest"), true);
                                    //.addField("Kill Ø", "" + heroObject.getInt("eliminationsAvg"), true);

                                    //Competitive
                                    builder.addField("", "**" + aHero + " Statistiken (Competitive):**", false)
                                            .addField("Spielzeit mit dem Helden", compHeroObject.getString("timePlayed"), true)
                                            .addField("Spiele gewonnen", "" + compHeroObject.getInt("gamesWon"), true)
                                            .addField("Wins in %", "" + compHeroObject.getInt("winPercentage"), true)
                                            .addField("Waffen-Genauigkeit in %", "" + compHeroObject.getInt("weaponAccuracy"), true)
                                            .addField("Kills pro Leben (K/D)", "" + compHeroObject.getInt("eliminationsPerLife"), true)
                                            .addField("Bester Multikill", "" + compHeroObject.getInt("multiKillBest"), true);
                                    //.addField("Kill Ø", "" + compHeroObject.getInt("eliminationsAvg"), true);



                                    if (ratingString.equals("")) {
                                        builder.setAuthor(nameString + ", Unranked - Plattform: " + aPLAT, profileURL, levelIconURL);
                                    } else {
                                        builder.setAuthor(nameString + ", Rating: " + ratingString + " - Plattform: " + aPLAT, profileURL, levelIconURL);
                                    }

                                    channel.sendMessage(builder.build()).queue();
                                } catch (Exception e){
                                    MessageCreator.createError(event.getMessage().getContentRaw(), "Zu diesem Spieler liegen uns keine QuickPlay oder Competitive daten vor.", channel);
                                }


                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (FileNotFoundException e) {
                                MessageCreator.createError("Entweder dieser Spieler existiert nicht, oder es gab einen Fehler mit der API! Versuche eventuell einen anderen Spieler.", channel);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } catch(MalformedURLException e){
                            e.printStackTrace();
                        }

                    } else {
                        MessageCreator.createError(event.getMessage().getContentRaw(), "Dieser Held ist unbekannt oder falsch geschrieben!", "Stelle sicher, dass der Held korrekt geschrieben wurde!\nHelden: ``ana``, ``bastion``, ``brigitte``, ``dVa``, ``doomfist``, ``genji``, ``hanzo``, ``junkrat``, ``lucio``, ``mccree``, ``mei``, ``mercy``, ``moira``, ``orisa``, ``pharah``, ``reaper``, ``reinhardt``, ``roadhog``, ``soldier76``, ``sombra``, ``symmetra``, ``torbjorn``, ``tracer``, ``widowmaker``, ``winston``, ``zarya``, ``zenyatta``", channel);
                    }

                }
                break;
        }




/*
        try {
            URL url = new URL("https://ow-api.com/v1/stats/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Content-Type", "application/json");
            String contentType = con.getHeaderField("Content-Type");

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }
}
