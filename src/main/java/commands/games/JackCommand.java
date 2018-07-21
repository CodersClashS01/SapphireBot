package commands.games;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import utils.MessageCreator;
import utils.VariableStoring;
import utils.chatgame.JackPlayersSQL;
import utils.chatgame.JackSessionSQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JackCommand implements ICommand {

    private String[] card_Names = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10", "König", "Dame", "Bube", "Ass", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {

        if (args.length < 1) {
            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX + "help jack``", channel);
            return;
        }

        switch (args[0]) {

            case "s":

                if (args.length < 2) {
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX + "help jack``", channel);
                    return;
                }

                switch (args[1]) {
                    case "create":
                        List jackPlayerList = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                        if (jackPlayerList.isEmpty()) {
                            JackPlayersSQL.createNew(event.getGuild().getId(), member.getUser().getId(), "null", 0, null, null);
                            jackPlayerList = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                        }
                        JackPlayersSQL jackPlayersSQL = (JackPlayersSQL) jackPlayerList.get(0);
                        System.out.println("session: " + jackPlayersSQL.getSession_id());
                        if (jackPlayersSQL.getSession_id().contains("null")) {
                            System.out.println("1");
                            java.util.Random generator = new java.util.Random();
                            generator.setSeed(System.currentTimeMillis());
                            int i = generator.nextInt(1000000) % 1000000;
                            java.text.DecimalFormat f = new java.text.DecimalFormat("000000");

                            System.out.println("2");
                            List jackSessionList_c = JackSessionSQL.getJackSessionByUser(event.getGuild().getId(), member.getUser().getId());

                            System.out.println("3");

                            List<String> playersList = new ArrayList<>();
                            playersList.add(member.getUser().getId());

                            System.out.println("4");
                            if (jackSessionList_c.size() < 1) {
                                System.out.println("5");
                                JackSessionSQL.createNew(event.getGuild().getId(), member.getUser().getId(), f.format(i), playersList);
                                System.out.println("6");
                                JackPlayersSQL.updateOld(event.getGuild().getId(), member.getUser().getId(), f.format(i), 0, null, null);
                                System.out.println("7");

                                EmbedBuilder firstBuilder = new EmbedBuilder().setTitle("Blackjack").setColor(VariableStoring.COLOR).setFooter("Blackjack Game System", "https://www2.pic-upload.de/img/35647927/JAKE_bjt_icon1024.png")
                                        .setAuthor(member.getEffectiveName(), null, member.getUser().getAvatarUrl())
                                        .setDescription("Session wurde erfolgreich erstellt!\nSpieler können durch folgende SessionID, deiner Session beitreten.")
                                        .addBlankField(false)
                                        .addField("Session ID", f.format(i), true)
                                        .addField("Session beitreten", "``" + VariableStoring.PREFIX + "jack s join " + f.format(i) + "``", true);

                                System.out.println("8");
                                channel.sendMessage(firstBuilder.build()).queue();
                            } else {
                                MessageCreator.createError("Du hast bereits eine Session erstellt! Beende diese zuerst.", channel);
                            }

                        } else {
                            MessageCreator.createError("Du bist bereits in einer Session! Bitte verlasse diese zuerst.", channel);
                        }
                        break;

                    case "join":
                        if (args.length < 3) {
                            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX + "help jack``", channel);
                            return;
                        }

                        System.out.println(JackSessionSQL.getJackSessionBySessionID(event.getGuild().getId(), args[2]).size());

                        if (JackSessionSQL.getJackSessionBySessionID(event.getGuild().getId(), args[2]).size() != 1) {
                            MessageCreator.createError(event.getMessage().getContentRaw(), "Fehler beim auslesen der Datenbank.", channel);
                            return;
                        }

                        List jackPlayerList_join = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                        if (jackPlayerList_join.isEmpty()) {
                            JackPlayersSQL.createNew(event.getGuild().getId(), member.getUser().getId(), null, 0, null, null);
                        }
                        JackPlayersSQL jackPlayersSQL_list = (JackPlayersSQL) jackPlayerList_join.get(0);

                        List jackSessionList = JackSessionSQL.getJackSessionBySessionID(event.getGuild().getId(), args[2]);
                        JackSessionSQL jackSessionSQL = (JackSessionSQL) jackSessionList.get(0);

                        if (Boolean.parseBoolean(jackSessionSQL.getStarted())) {
                            MessageCreator.createError("Session wurde bereits gestartet, du kannst ihr nicht beitreten sobald sie gestartet wurde.", channel);
                            return;
                        }

                        if (jackSessionSQL.getCreator().equals(member.getUser().getId())) {
                            MessageCreator.createError("Du kannst deiner eigenen Session nicht beitreten.", channel);
                            return;
                        }

                        if (jackSessionSQL.getPlayers().contains(member.getUser().getId())) {
                            MessageCreator.createError("Du bist bereits in dieser Session.", channel);
                            return;
                        }

                        if (jackSessionSQL.getPlayers().size() >= 20) {
                            MessageCreator.createError("Die Session ist bereits voll.", channel);
                            return;
                        }

                        if (!jackPlayersSQL_list.getSession_id().contains("null")) {
                            MessageCreator.createError("Du bist bereits in einer Session.", channel);
                            return;
                        }

                        System.out.println("1");

                        List<String> playersList = new ArrayList<>(jackSessionSQL.getPlayers());
                        System.out.println("2");
                        playersList.add(member.getUser().getId());
                        System.out.println("3");

                        JackSessionSQL.updateOld(event.getGuild().getId(), jackSessionSQL.getCreator(), jackSessionSQL.getSession_id(), jackSessionSQL.getStarted(), null, playersList);
                        JackPlayersSQL.updateOld(event.getGuild().getId(), member.getUser().getId(), jackSessionSQL.getSession_id(), 0, null, null);

                        EmbedBuilder builder = new EmbedBuilder().setTitle("Blackjack").setColor(VariableStoring.COLOR).setFooter("Blackjack Game System", "https://www2.pic-upload.de/img/35647927/JAKE_bjt_icon1024.png")
                                .setAuthor(member.getEffectiveName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl())
                                .setDescription("Blackjack Session beigetreten!")
                                .addField("Session ID", jackSessionSQL.getSession_id(), true)
                                .addField("Session Creator", event.getJDA().getUserById(jackSessionSQL.getCreator()).getAsMention(), true)
                                .addBlankField(false);

                        StringBuilder mentions = new StringBuilder();

                        for (int j = 0; j < jackSessionSQL.getPlayers().size(); j++) {
                            mentions.append(event.getJDA().getUserById(jackSessionSQL.getPlayers().get(j)).getAsMention()).append(" ");
                        }

                        builder.addField("Spieler", mentions + " " + member.getUser().getAsMention(), false);


                        channel.sendMessage(builder.build()).queue();

                        break;

                    case "start":
                        List jackPlayerList_start = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                        JackPlayersSQL jackPlayersSQL_start = (JackPlayersSQL) jackPlayerList_start.get(0);

                        if (jackPlayersSQL_start.equals("null")) {MessageCreator.createError("Du bist in keiner Session.", channel);return;}

                        List jackSessionListTwo = JackSessionSQL.getJackSessionBySessionID(event.getGuild().getId(), jackPlayersSQL_start.getSession_id());
                        JackSessionSQL jackSessionSQLTwo = (JackSessionSQL) jackSessionListTwo.get(0);
                        if (member.getUser().getId().equals(jackSessionSQLTwo.getCreator())) {
                            if (Boolean.parseBoolean(jackSessionSQLTwo.getStarted())) {
                                MessageCreator.createError("Session wurde bereits gestartet.", channel);
                                return;
                            }

                            System.out.println("Player - " + jackSessionSQLTwo.getPlayers().size());

                            if (jackSessionSQLTwo.getPlayers().size() <= 1) {
                                MessageCreator.createError("Du Brauchst mindestens einen Mitspieler.", channel);
                                return;
                            }

                            JackSessionSQL.updateOld(event.getGuild().getId(), member.getUser().getId(), jackSessionSQLTwo.getSession_id(), "true", jackSessionSQLTwo.getPlayers().get(0), jackSessionSQLTwo.getPlayers());

                            EmbedBuilder builder2 = new EmbedBuilder().setTitle("Blackjack").setColor(VariableStoring.COLOR).setFooter("Blackjack Game System", "https://www2.pic-upload.de/img/35647927/JAKE_bjt_icon1024.png")
                                    .setAuthor(member.getEffectiveName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl())
                                    .setDescription("Blackjack Session gestartet!")
                                    .addField("Session ID", jackSessionSQLTwo.getSession_id(), true)
                                    .addField("Spieler an der Reihe", event.getJDA().getUserById(jackSessionSQLTwo.getPlayers().get(0)).getAsMention(), true)
                                    .addBlankField(false);

                            channel.sendMessage(builder2.build()).queue();

                        } else {
                            MessageCreator.createError("Du bist nicht der Ersteller der Session.", channel);
                        }

                        break;
                }

                break;
                //ENDE VOM INNEREN


            case "hit":
                List jackPlayerList = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                if (jackPlayerList.isEmpty()) {
                    JackPlayersSQL.createNew(event.getGuild().getId(), member.getUser().getId(), "null", 0, null, null);
                    jackPlayerList = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                }
                JackPlayersSQL jackPlayersSQL = (JackPlayersSQL) jackPlayerList.get(0);
                if (jackPlayersSQL.getSession_id().contains("null")) {
                    MessageCreator.createError("Du bist in keiner Session.", channel);
                    return;
                }

                List jackSessionList = JackSessionSQL.getJackSessionBySessionID(event.getGuild().getId(), jackPlayersSQL.getSession_id());
                JackSessionSQL jackSessionSQL = (JackSessionSQL) jackSessionList.get(0);


                if (jackSessionSQL.getStarted().contains("false")) {
                    MessageCreator.createError("Session wurde noch nicht gestartet.", channel);
                    return;
                }

                System.out.println("test");


                System.out.println("tes1");
                if (!jackSessionSQL.getGaming().equals(member.getUser().getId())) {
                    MessageCreator.createError("Du bist nicht an der Reihe!\n" + event.getJDA().getUserById(jackSessionSQL.getGaming()).getAsMention() + " ist an der Reihe!", channel);
                    return;
                }

                System.out.println("tes2");

                int number = jackPlayersSQL.getNumber();
                int randomNumber = (new Random()).nextInt(card_Names.length);


                System.out.println("tes3");
                if (card_Names[randomNumber].equalsIgnoreCase("Bube") || card_Names[randomNumber].equalsIgnoreCase("Dame") || card_Names[randomNumber].equalsIgnoreCase("König")) {
                    number = number + 10;
                } else if (card_Names[randomNumber].equalsIgnoreCase("ass")) {
                    number = number + 1;
                } else {
                    number = number + Integer.parseInt(card_Names[randomNumber]);
                }

                System.out.println("tes4");

                EmbedBuilder builder;
                System.out.println("1");
                if (number == 21) {
                    System.out.println("2");
                    builder = new EmbedBuilder().setColor(VariableStoring.COLOR).setTitle("Gratulation!")
                            .setAuthor(member.getEffectiveName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl())
                            .addField("Gewinner", member.getEffectiveName(), true)
                            .addField("Grund", member.getEffectiveName() + " hat die Zahl '21' exakt erreicht!", true);

                    StringBuilder playerNumbers = new StringBuilder();

                    for (int j = 0; j < jackSessionSQL.getPlayers().size(); j++) {

                        List jackPlayerList_each = JackPlayersSQL.getJackPlayersByUser(jackSessionSQL.getPlayers().get(j), event.getGuild().getId());
                        JackPlayersSQL jackPlayersSQL_each = (JackPlayersSQL) jackPlayerList_each.get(0);

                        playerNumbers.append(event.getJDA().getUserById(jackPlayersSQL_each.getUser_id()).getName()).append(" -> ").append(jackPlayersSQL_each.getNumber()).append("\n");

                    }

                    builder.addField("Andere Spieler", playerNumbers.toString(), false);
                    System.out.println("3");
                    for (int i = 0; i < jackSessionSQL.getPlayers().size(); i++) {
                        JackSessionSQL.removeOld(jackSessionSQL.getSession_id());
                        JackPlayersSQL.updateOld(event.getGuild().getId(), jackSessionSQL.getPlayers().get(i), null, 0, null, null);
                        System.out.println("4");
                    }
                    System.out.println("5");
                } else if (number > 21) {
                    System.out.println("6");
                    builder = new EmbedBuilder().setColor(VariableStoring.COLOR).setTitle("Verloren!")
                            .setAuthor(member.getEffectiveName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl())
                            .addField("Spieler", member.getEffectiveName(), true)
                            .addField("Zahl", String.valueOf(number), true)
                            .addField("Grund", "Der Spieler hatte eine Zahl über 21", false);


                    System.out.println("7");

                    int i_int;
                    for (int i = 0; i < jackSessionSQL.getPlayers().size(); i++) {
                        System.out.println("8");
                        System.out.println("i: " + i);
                        System.out.println("list: " + jackSessionSQL.getPlayers());
                        System.out.println("size: " + jackSessionSQL.getPlayers().size());
                        System.out.println(jackSessionSQL.getPlayers().get(i));

                        //hier

                        if (jackSessionSQL.getPlayers().get(i).contains(member.getUser().getId())){
                            System.out.println("9");
                            System.out.println("9 - " + i + " || " + jackSessionSQL.getPlayers().get(i));
                            System.out.println("9 - Size " + jackSessionSQL.getPlayers().size());
                            if (i+1 == jackSessionSQL.getPlayers().size()) {
                                System.out.println("10");

                                String[] numbersArray = new String[jackSessionSQL.getPlayers().size()];
                                String[] namesArray = new String[jackSessionSQL.getPlayers().size()];

                                System.out.println("11");

                                for (int j = 0; j < jackSessionSQL.getPlayers().size(); j++) {
                                    List jackPlayerList_each = JackPlayersSQL.getJackPlayersByUser(jackSessionSQL.getPlayers().get(j), event.getGuild().getId());
                                    JackPlayersSQL jackPlayersSQL_each = (JackPlayersSQL) jackPlayerList_each.get(0);
                                    numbersArray[j] = String.valueOf(jackPlayersSQL_each.getNumber());
                                    namesArray[j] = jackPlayersSQL_each.getUser_id();
                                }
                                System.out.println("16");

                                System.out.println(Arrays.toString(numbersArray));

                                int max = 0;
                                int name = 0;

                                for (int counter = 1; counter < numbersArray.length; counter++)
                                {
                                    if (Integer.parseInt(numbersArray[counter]) > max)
                                    {
                                        max = Integer.parseInt(numbersArray[counter]);
                                        name = counter -1;
                                    }
                                }
                                System.out.println("17");
                                System.out.println("17 - " + max);
                                System.out.println("17 - " + name);

                                List jackPlayerList_last = JackPlayersSQL.getJackPlayersByUser(jackSessionSQL.getPlayers().get(name), event.getGuild().getId());
                                System.out.println("18");
                                JackPlayersSQL jackPlayersSQL_last = (JackPlayersSQL) jackPlayerList_last.get(0);
                                System.out.println("19");

                                builder = new EmbedBuilder().setColor(VariableStoring.COLOR).setTitle("Spiel beendet!")
                                        .setAuthor(event.getJDA().getUserById(jackSessionSQL.getCreator()).getName(), null, event.getJDA().getUserById(jackSessionSQL.getCreator()).getAvatarUrl())
                                        .addField("Gewinner", event.getJDA().getUserById(jackPlayersSQL_last.getUser_id()).getAsMention(), true);


                                StringBuilder playerNumbers = new StringBuilder();

                                for (int j = 0; j < jackSessionSQL.getPlayers().size(); j++) {

                                    List jackPlayerList_each = JackPlayersSQL.getJackPlayersByUser(jackSessionSQL.getPlayers().get(j), event.getGuild().getId());
                                    JackPlayersSQL jackPlayersSQL_each = (JackPlayersSQL) jackPlayerList_each.get(0);

                                    playerNumbers.append(event.getJDA().getUserById(jackPlayersSQL_each.getUser_id()).getName()).append(" -> ").append(jackPlayersSQL_each.getNumber()).append("\n");

                                }

                                builder.addField("Andere Spieler", playerNumbers.toString(), false);

                                for (int j = 0; j < jackSessionSQL.getPlayers().size(); j++) {
                                    JackSessionSQL.removeOld(jackSessionSQL.getSession_id());
                                    JackPlayersSQL.updateOld(event.getGuild().getId(), jackSessionSQL.getPlayers().get(j), null, 0, null, null);
                                }

                            } else {
                                JackSessionSQL.updateOld(event.getGuild().getId(), jackSessionSQL.getCreator(), jackSessionSQL.getSession_id(), "true", jackSessionSQL.getPlayers().get(i+1), jackSessionSQL.getPlayers());
                                JackPlayersSQL.updateOld(event.getGuild().getId(), member.getUser().getId(), jackSessionSQL.getSession_id(), jackPlayersSQL.getNumber(), null, null);
                                builder.addField("Neuer Spieler", event.getJDA().getUserById(jackSessionSQL.getPlayers().get(i+1)).getAsMention(), false);
                            }
                        } else {
                            System.out.println("got - " + jackSessionSQL.getPlayers().get(i));
                        }

                    }

                } else {

                    builder = new EmbedBuilder().setColor(VariableStoring.COLOR).setTitle(member.getEffectiveName() + " hat \'hit\' gewählt.")
                            .setAuthor(member.getEffectiveName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl())
                            .addField("Neue Karte", "Karte: ``" + card_Names[randomNumber] + "``", true)
                            .addField("Aktuelle Zahl", String.valueOf(number), true);
                    //.addField("Alle Karten bisher", cards, false)
                    //.addBlankField(false)
                    //.addField("Mögliche Aktionen", VariableStoring.PREFIX + "jack hit\n" + VariableStoring.PREFIX + "jack stand", true)
                    //.addField("", "Fordere eine weitere Karte\nBehalte deine Karten und beende", true);


                    JackPlayersSQL.updateOld(event.getGuild().getId(), member.getUser().getId(), jackSessionSQL.getSession_id(), number, null, null);
                }

                channel.sendMessage(builder.build()).queue();
                break;

            case "stand":
                List jackPlayerList_stand = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                if (jackPlayerList_stand.isEmpty()) {
                    JackPlayersSQL.createNew(event.getGuild().getId(), member.getUser().getId(), null, 0, null, null);
                    jackPlayerList_stand = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                }
                JackPlayersSQL jackPlayersSQL_stand = (JackPlayersSQL) jackPlayerList_stand.get(0);

                if (jackPlayersSQL_stand.getSession_id().contains("null")) {
                    MessageCreator.createError("Du bist in keiner Session.", channel);
                    return;
                }

                List jackSessionList_stand = JackSessionSQL.getJackSessionBySessionID(event.getGuild().getId(), jackPlayersSQL_stand.getSession_id());
                JackSessionSQL jackSessionSQL_stand = (JackSessionSQL) jackSessionList_stand.get(0);

                if (jackSessionSQL_stand.getStarted().contains("false")) {
                    MessageCreator.createError("Session wurde noch nicht gestartet.", channel);
                    return;
                }

                if (jackPlayersSQL_stand.getNumber() == 0) {
                    MessageCreator.createError("Du musst zuerst eine Katen nehmen.", channel);
                    return;
                }


                EmbedBuilder builder_two = new EmbedBuilder().setColor(VariableStoring.COLOR).setTitle("Nächster Spieler!")
                        .setAuthor(member.getEffectiveName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl())
                        .addField("Nächster Spieler", member.getEffectiveName(), true)
                        .addField("Letzte Zahl", String.valueOf(jackPlayersSQL_stand.getNumber()), true);

                int i_int;
                for (int i = 0; i < jackSessionSQL_stand.getPlayers().size(); i++) {
                    System.out.println("8");
                    System.out.println("i: " + i);
                    System.out.println("list: " + jackSessionSQL_stand.getPlayers());
                    System.out.println("size: " + jackSessionSQL_stand.getPlayers().size());
                    System.out.println(jackSessionSQL_stand.getPlayers().get(i));

                    //hier

                    if (jackSessionSQL_stand.getPlayers().get(i).contains(member.getUser().getId())){
                        System.out.println("9");
                        System.out.println("9 - " + i + " || " + jackSessionSQL_stand.getPlayers().get(i));
                        System.out.println("9 - Size " + jackSessionSQL_stand.getPlayers().size());
                        if (i+1 == jackSessionSQL_stand.getPlayers().size()) {
                            System.out.println("10");

                            String[] numbersArray = new String[jackSessionSQL_stand.getPlayers().size()];
                            String[] namesArray = new String[jackSessionSQL_stand.getPlayers().size()];

                            System.out.println("11");

                            for (int j = 0; j < jackSessionSQL_stand.getPlayers().size(); j++) {
                                List jackPlayerList_each = JackPlayersSQL.getJackPlayersByUser(jackSessionSQL_stand.getPlayers().get(j), event.getGuild().getId());
                                JackPlayersSQL jackPlayersSQL_each = (JackPlayersSQL) jackPlayerList_each.get(0);
                                numbersArray[j] = String.valueOf(jackPlayersSQL_each.getNumber());
                                namesArray[j] = jackPlayersSQL_each.getUser_id();
                            }
                            System.out.println("16");

                            System.out.println(Arrays.toString(numbersArray));

                            int max = 0;
                            int name = 0;

                            System.out.println(Arrays.toString(numbersArray));

                            for (int counter = 1; counter < numbersArray.length; counter++)
                            {
                                if (Integer.parseInt(numbersArray[counter]) > max)
                                {
                                    max = Integer.parseInt(numbersArray[counter]);
                                    name = counter -1;
                                }
                            }
                            List jackPlayerList_last = JackPlayersSQL.getJackPlayersByUser(jackSessionSQL_stand.getPlayers().get(name), event.getGuild().getId());
                            JackPlayersSQL jackPlayersSQL_last = (JackPlayersSQL) jackPlayerList_last.get(0);

                            builder_two = new EmbedBuilder().setColor(VariableStoring.COLOR).setTitle("Spiel beendet!")
                                    .setAuthor(event.getJDA().getUserById(jackSessionSQL_stand.getCreator()).getName(), null, event.getJDA().getUserById(jackSessionSQL_stand.getCreator()).getAvatarUrl())
                                    .addField("Gewinner", event.getJDA().getUserById(jackPlayersSQL_last.getUser_id()).getAsMention(), true);

                            StringBuilder playerNumbers = new StringBuilder();

                            for (int j = 0; j < jackSessionSQL_stand.getPlayers().size(); j++) {

                                List jackPlayerList_each = JackPlayersSQL.getJackPlayersByUser(jackSessionSQL_stand.getPlayers().get(j), event.getGuild().getId());
                                JackPlayersSQL jackPlayersSQL_each = (JackPlayersSQL) jackPlayerList_each.get(0);

                                playerNumbers.append(event.getJDA().getUserById(jackPlayersSQL_each.getUser_id()).getName()).append(" -> ").append(jackPlayersSQL_each.getNumber()).append("\n");

                            }

                            builder_two.addField("Andere Spieler", playerNumbers.toString(), false);



                            for (int j = 0; j < jackSessionSQL_stand.getPlayers().size(); j++) {
                                JackSessionSQL.removeOld(jackSessionSQL_stand.getSession_id());
                                JackPlayersSQL.updateOld(event.getGuild().getId(), jackSessionSQL_stand.getPlayers().get(j), null, 0, null, null);
                            }

                        } else {
                            JackSessionSQL.updateOld(event.getGuild().getId(), jackSessionSQL_stand.getCreator(), jackSessionSQL_stand.getSession_id(), "true", jackSessionSQL_stand.getPlayers().get(i+1), jackSessionSQL_stand.getPlayers());
                            JackPlayersSQL.updateOld(event.getGuild().getId(), member.getUser().getId(), jackSessionSQL_stand.getSession_id(), jackPlayersSQL_stand.getNumber(), null, null);
                            builder_two.addField("Neuer Spieler", event.getJDA().getUserById(jackSessionSQL_stand.getPlayers().get(i+1)).getAsMention(), false);
                        }
                    } else {
                        System.out.println("got - " + jackSessionSQL_stand.getPlayers().get(i));
                    }
                }
                channel.sendMessage(builder_two.build()).queue();
                break;

            case "leave":
                System.out.println("-5");
                List jackPlayerList_leave = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                System.out.println("-4");
                if (jackPlayerList_leave.isEmpty()) {
                    JackPlayersSQL.createNew(event.getGuild().getId(), member.getUser().getId(), null, 0, null, null);
                    jackPlayerList_leave = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                }
                System.out.println("-3");
                JackPlayersSQL jackPlayersSQL_leave = (JackPlayersSQL) jackPlayerList_leave.get(0);

                System.out.println("-2");
                List jackSessionList_leave = JackSessionSQL.getJackSessionBySessionID(event.getGuild().getId(), jackPlayersSQL_leave.getSession_id());
                System.out.println("-1");
                JackSessionSQL jackSessionSQL_leave = (JackSessionSQL) jackSessionList_leave.get(0);

                System.out.println("0");
                if (jackSessionSQL_leave.getCreator().contains(member.getUser().getId())) {
                    MessageCreator.createError("Du kannst als Creator die Runde nicht verlassen, du kannst die Runde nur beenden.\n\n``s!jack end``", channel);
                    return;
                }
                System.out.println("1");

                if (jackPlayersSQL_leave.getSession_id().contains("null")) {
                    MessageCreator.createError("Du bist in keiner Session.", channel);
                    return;
                }
                System.out.println("2");

                if (jackSessionSQL_leave.getGaming().contains(member.getUser().getId())){
                    MessageCreator.createError("Du kann nicht verlassen, während du an der Reihe bist.", channel);
                    return;
                }
                System.out.println("3");

                EmbedBuilder msg_builder_leave = new EmbedBuilder().setColor(VariableStoring.COLOR).setTitle("Blackjack Session verlassen")
                        .setDescription(member.getEffectiveName() + " hat die Session verlassen.")
                        .setAuthor(member.getEffectiveName(), null, member.getUser().getAvatarUrl());


                System.out.println("4");
                int id = 0;
                List<String> arrayList = jackSessionSQL_leave.getPlayers();
                List<String> arrayListNew = new ArrayList<>();
                System.out.println("5");
                for (int i = 0; i < jackSessionSQL_leave.getPlayers().size(); i++) {
                    if (jackSessionSQL_leave.getPlayers().get(i).contains(member.getUser().getId())) {
                    } else {
                        arrayListNew.add(jackSessionSQL_leave.getPlayers().get(i));
                    }
                }

                JackPlayersSQL.updateOld(event.getGuild().getId(), member.getUser().getId(), "null", 0, null, null);
                JackSessionSQL.updateOld(event.getGuild().getId(), jackSessionSQL_leave.getCreator(), jackSessionSQL_leave.getSession_id(), jackSessionSQL_leave.getStarted(), jackSessionSQL_leave.getGaming(), arrayListNew);


                channel.sendMessage(msg_builder_leave.build()).queue();

                break;

            case "end":
                List jackPlayerList_end = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                if (jackPlayerList_end.isEmpty()) {
                    JackPlayersSQL.createNew(event.getGuild().getId(), member.getUser().getId(), null, 0, null, null);
                    jackPlayerList_end = JackPlayersSQL.getJackPlayersByUser(member.getUser().getId(), event.getGuild().getId());
                }
                JackPlayersSQL jackPlayersSQL_end   = (JackPlayersSQL) jackPlayerList_end.get(0);

                List jackSessionList_end = JackSessionSQL.getJackSessionBySessionID(event.getGuild().getId(), jackPlayersSQL_end.getSession_id());
                JackSessionSQL jackSessionSQL_end = (JackSessionSQL) jackSessionList_end.get(0);

                if (jackPlayersSQL_end.getSession_id().contains("null")) {
                    MessageCreator.createError("Du bist in keiner Session.", channel);
                    return;
                }

                EmbedBuilder msg_builder_end = new EmbedBuilder().setColor(VariableStoring.COLOR).setTitle("Blackjack Session aufgelößt")
                        .setDescription("Die Session wurde aufgelößt, weil der Ersteller die Runde verlassen hat.")
                        .addField("SessionID", jackSessionSQL_end.getSession_id(), true)
                        .addField("Creator", event.getJDA().getUserById(jackSessionSQL_end.getCreator()).getAsMention(), true)
                        .setFooter("Blackjack Game System", "https://www2.pic-upload.de/img/35647927/JAKE_bjt_icon1024.png")
                        .setAuthor(member.getEffectiveName(), null, member.getUser().getAvatarUrl());


                for (int i = 0; i < jackSessionSQL_end.getPlayers().size(); i++) {
                    JackSessionSQL.removeOld(jackSessionSQL_end.getSession_id());
                    JackPlayersSQL.updateOld(event.getGuild().getId(), jackSessionSQL_end.getPlayers().get(i), "null", 0, null, null);
                }

                channel.sendMessage(msg_builder_end.build()).queue();

                break;

        }



        }




    }


