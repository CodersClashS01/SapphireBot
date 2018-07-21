package commands.game_tracker;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import core.CLI;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.heyzeer0.papi.PaladinsAPI;
import net.heyzeer0.papi.enums.Platform;
import net.heyzeer0.papi.exceptions.SessionException;
import net.heyzeer0.papi.exceptions.UnknowPlayerException;
import net.heyzeer0.papi.profiles.requests.PaladinsPlayer;
import utils.MessageCreator;
import utils.PrivateMessageCreator;
import utils.VariableStoring;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PaladinsCommand implements ICommand {

    private static PaladinsAPI api = new PaladinsAPI(VariableStoring.pal_devid, VariableStoring.pal_authkey, Platform.PC);
    //                                                               ^ dev_id       ^ dev_key               ^ Platform#PC/XBOX/PS4


    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {
        if (args[0].equals("")) {
            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "``" + VariableStoring.PREFIX +  "help paladins``", channel);
        } else {
        if (!VariableStoring.pal_devid.equals("off") || !VariableStoring.pal_authkey.equals("off")) {
            try {
                PaladinsPlayer p = api.getRequestManager().requestPlayer(args[0]);

                EmbedBuilder embed = new EmbedBuilder().setColor(VariableStoring.COLOR).setThumbnail("https://img12.androidappsapk.co/300/b/6/7/net.bitsized.forgepaladins.png").setTitle("Paladins: " + p.getName() + " | ID: " + p.getID()).setFooter("Alle Rechte gehen an HiRez und an das Team von Paladins", "https://i.pinimg.com/originals/e4/df/a2/e4dfa24b1b0c57472ffec4a0a99ef59b.jpg");



                embed.addField("Level", String.valueOf(p.getLevel()), true)
                        .addField("Mastery-Level", String.valueOf(p.getMasteryLevel()), true)
                        .addField("Siege", String.valueOf(p.getWins()), true)
                        .addField("Niederlagen", "Verloren: " + p.getLosses() + "\nLeaves: " + p.getLeaves(), true);

                double result = (double)p.getWins() / (double)p.getLosses();

                NumberFormat numberFormat = new DecimalFormat("0.00");
                numberFormat.setRoundingMode(RoundingMode.DOWN);

                embed.addField("Wins/Losses (W/L)", String.valueOf(numberFormat.format(result)), true)
                        .addField("Region", p.getRegion(), true)
                        .addField("Beigetreten", p.getJoinDate(), true)
                        .addField("Letzter Login", p.getLastLogin(), true)
                        .addField("Alle Errungenschaften", String.valueOf(p.getTotalArchievements()), true)
                        .addField("Alle Anbeter", String.valueOf(p.getTotalWorshippers()), true);

                channel.sendMessage(embed.build()).queue();

            } catch (UnknowPlayerException e) {
                MessageCreator.createError(event.getMessage().getContentRaw(), "Dieser Paladins Spielername existiert nicht!", "Überprüfe deine Schreibweise!", channel);
            } catch (SessionException e) {

                if (e.getLocalizedMessage().contains("Invalid Developer Id") || e.getLocalizedMessage().contains("Invalid signature.")) {
                    CLI.error("HiRez Developer Account falsch aufgesetzt, Befehle nicht möglich!");
                    int count = 0;
                    try {
                        for (int i = 0; i < VariableStoring.BOT_ADMINS.size(); i++) {
                            PrivateMessageCreator.createError(event.getMessage().getContentRaw(), "HiRez-Dev Account inkorrekt eingestellt!", "Falls du die Funktionen 'Paladins Gametracker', 'SMITE Gametracker' nicht verwenden möchtest, setze die Einstellungen ``pal_devId`` und ``pal_authKey`` auf ``off``.\n\nVerwende dazu die Befehle `s!c set pal_devid off` und `s!c set pal_authkey off` auf einem Server auf dem ich laufe.", event.getGuild(), event.getJDA().getUserById(VariableStoring.BOT_ADMINS.get(i)));
                            count++;
                        }
                    } catch (Exception ignored) {

                    }

                    long plus = Integer.parseInt(args[1]);

                    long ping = Integer.parseInt(String.valueOf(event.getJDA().getPing())) + plus;

                    MessageCreator.createError(event.getMessage().getContentRaw(), "HiRez-Dev Account inkorrekt eingestellt!", "Es wurden ``" + count + "`` der ``" + VariableStoring.BOT_ADMINS.size() + "`` Bot-Admins kontaktiert!", channel);
                } else {
                    CLI.error("An error ocurred while trying to create the session: " + e.getLocalizedMessage());
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Es gab ein unbekanntes Problem mit dem HiRez-Dev Account!\n\n Fehlercode: " + e.getLocalizedMessage(), "Kontaktiere einen Bot-Admin!", channel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MessageCreator.createError(event.getMessage().getContentRaw(), "HiRez Befehle, wie zb. dieser Paladis Befehl, wurden von den Bot-Admins deaktiviert!", channel);
        }
        }

    }
}
