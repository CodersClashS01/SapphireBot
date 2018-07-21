package commands.general;
/*
    Created by ConnysSoftware / ConCode on 26.06.2018 at 20:59.
    
    (c) ConnysSoftware / ConCode
*/

import com.github.johnnyjayjay.discord.commandapi.CommandEvent;
import com.github.johnnyjayjay.discord.commandapi.ICommand;
import core.CLI;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.MessageCreator;
import utils.VariableStoring;
import utils.Tools;
import utils.sql.VoteSQL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class VoteCommand extends ListenerAdapter implements ICommand {

    private static final String[] digits = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private static final String[] digitsReaction = {"\u0031\u20E3", "\u0032\u20E3", "\u0033\u20E3", "\u0034\u20E3", "\u0035\u20E3", "\u0036\u20E3", "\u0037\u20E3", "\u0038\u20E3", "\u0039\u20E3"};

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        try {


            String emote;

            emote = event.getReactionEmote().getName();

            if (emote.equals("\u0031\u20E3")){
            }

            if (!(event.getUser().getId().equals(event.getJDA().getSelfUser().getId()) || event.getUser().isFake() || event.getUser().isBot())) {
                VoteSQL voteSQL = VoteSQL.fromMessageId(event.getMessageId());
                if (voteSQL.exists()) {
                    User author = voteSQL.getAuthor();
                    List<String> options = voteSQL.getOptions();
                    String question = voteSQL.getQuestion();
                    List<String> digits = Arrays.asList(digitsReaction);



                    if (digits.contains(event.getReactionEmote().getName()) && !voteSQL.hasVoted(event.getUser().getId())) {

                        voteSQL.addVote(digits.indexOf(event.getReactionEmote().getName()), event.getUser().getId());
                        HashMap<Integer, List<String>> votes = voteSQL.getVotes();

                        StringBuilder optionsStr = new StringBuilder();
                        for (int i = 0; i < options.size(); i++) {
                            if (optionsStr.length() != 0)
                                optionsStr.append("\n");
                            optionsStr.append(":").append(VoteCommand.digits[i]).append(": - ").append(options.get(i)).append(" **Votes: ``").append(votes.get(i) != null ? votes.get(i).size() : 0).append("``**");
                        }

                        MessageEmbed.Footer footer = event.getTextChannel().getMessageById(event.getMessageId()).complete().getEmbeds().get(0).getFooter();

                        EmbedBuilder builder = new EmbedBuilder();
                        builder
                                .setAuthor(author.getName() + "s Umfrage", event.getUser().getAvatarUrl(), author.getEffectiveAvatarUrl())
                                .setDescription(question)
                                .setFooter(footer.getText(), footer.getIconUrl())
                                .addField("Options", optionsStr.toString(), false);
                        event.getTextChannel().getMessageById(event.getMessageId()).complete().editMessage(builder.build()).queue();
                    }
                    event.getReaction().removeReaction(event.getUser()).queue();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {

        event.getMessage().delete().queue();
        if (args.length >= 2 && args[0].equals("-c") && !args[1].equals("-id")){
            if (VoteSQL.fromUser(event.getAuthor()).exists()){
                MessageCreator.normalMessage("Abstimmungssystem", "Du hast bereits eine offene Abstimmung! Schließe diese bevor du eine neue eröffnest. `" + VariableStoring.PREFIX + "vote -q`", channel);
                return;
            }

            String[] splitted = Tools.argsToString(args, 1, " ").split("~");

            if (splitted.length == 0){
                MessageCreator.createError(event.getMessage().getContentRaw(), "Ungültige Syntax", "``" + VariableStoring.PREFIX +  "help vote``", channel);
                return;
            } else if (splitted.length < 3){
                MessageCreator.createError(event.getMessage().getContentRaw(), "Du musst ein minimum von 2 Antwortmöglichkeiten definieren!", channel);
                return;
            } else if (splitted.length > 10){
                MessageCreator.createError(event.getMessage().getContentRaw(), "Du darfst maximal 9 Antworten angeben!", channel);
                return;
            }
            String question = splitted[0];
            List<String> options = Arrays.asList(Arrays.copyOfRange(splitted, 1, splitted.length));

            options.forEach(CLI::info);

            StringBuilder optionsStr = new StringBuilder();
            for (int i = 0; i < options.size(); i++){
                if (optionsStr.length() != 0)
                    optionsStr.append("\n");
                optionsStr.append(":").append(digits[i]).append(": - ").append(options.get(i)).append(" **Votes: ``0``**");
            }
            EmbedBuilder builder = new EmbedBuilder();
            builder
                    .setAuthor(event.getAuthor().getName() + "s Umfrage", member.getUser().getAvatarUrl(), event.getAuthor().getAvatarUrl())
                    .setDescription(question)
                    .addField("Options", optionsStr.toString(), false);

            net.dv8tion.jda.core.entities.Message msg = channel.sendMessage(builder.build()).complete();

            VoteSQL.createNew(event.getAuthor().getId(), msg.getId(), question, options);

            for (int i = 0; i < options.size(); i++) {
                msg.addReaction(digitsReaction[i]).queue();
            }
        } else if (args.length >= 1 && args[0].equals("-c") && args[1].equals("-id")) {

            int id;

            System.out.println("id before - " + args[2]);

            try {
                id = Integer.parseInt(args[2]);
            } catch (Exception e) {
                id = 0;
            }

            System.out.println("id after - " + id);

            if (event.getGuild().getTextChannelById(args[2]) != null) {

                if (VoteSQL.fromUser(event.getAuthor()).exists()) {
                    MessageCreator.normalMessage("Abstimmungssystem", "Du hast bereits eine offene Abstimmung! Schließe diese bevor du eine neue eröffnest. `" + VariableStoring.PREFIX + "vote -q`", channel);
                    return;
                }

                String[] splitted = Tools.argsToString(args, 3, " ").split("~");

                if (splitted.length == 0) {
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Ungültige Syntax", "``" + VariableStoring.PREFIX +  "help vote``", channel);
                    return;
                } else if (splitted.length < 3) {
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Du musst ein minimum von 2 Antwortmöglichkeiten definieren!", channel);
                    return;
                } else if (splitted.length > 10) {
                    MessageCreator.createError(event.getMessage().getContentRaw(), "Du darfst maximal 9 Antworten angeben!", channel);
                    return;
                }
                String question = splitted[0];
                List<String> options = Arrays.asList(Arrays.copyOfRange(splitted, 1, splitted.length));

                options.forEach(CLI::info);

                StringBuilder optionsStr = new StringBuilder();
                for (int i = 0; i < options.size(); i++) {
                    if (optionsStr.length() != 0)
                        optionsStr.append("\n");
                    optionsStr.append(":").append(digits[i]).append(": - ").append(options.get(i)).append(" **Votes: ``0``**");
                }
                EmbedBuilder builder = new EmbedBuilder();
                builder
                        .setAuthor(event.getAuthor().getName() + "s Umfrage", member.getUser().getAvatarUrl(), event.getAuthor().getAvatarUrl())
                        .setDescription(question)
                        .setFooter("Vote erstellt in: " + event.getGuild().getTextChannelById(args[2]).getName(), event.getGuild().getIconUrl())
                        .addField("Options", optionsStr.toString(), false);

                net.dv8tion.jda.core.entities.Message msg = event.getGuild().getTextChannelById(args[2]).sendMessage(builder.build()).complete();

                VoteSQL.createNew(event.getAuthor().getId(), msg.getId(), question, options);

                for (int i = 0; i < options.size(); i++) {
                    msg.addReaction(digitsReaction[i]).queue();
                }

            System.out.println(question);
            System.out.println(msg.getId());

            } else {
                MessageCreator.createError(event.getMessage().getContentRaw(), "Diese Channel ID wurde nicht gefunden!", "Überprüfe ob du die ChannelID richtig geschrieben hast!", channel);
            }

        } else if (args.length >= 1 && args[0].equals("-q")){
                if (!VoteSQL.fromUser(event.getAuthor()).exists()){
                    MessageCreator.normalMessage("Abstimmungssystem", "Du hast noch keine offene Abstimmung die du schließen könntest! Du kannst eine neue Abstimmung mit folgendem Befehl eröffnen: `" + "``" + VariableStoring.PREFIX +  "help vote``", channel);
                    return;
                }
                VoteSQL.fromUser(event.getAuthor()).close();
                MessageCreator.normalMessage("Abstimmungssystem", "Abstimmung wurde geschlossen.", channel);

        } else

            MessageCreator.createError(event.getMessage().getContentRaw(), "Ungültige Syntax", VariableStoring.PREFIX + "vote [-c/-c -id/-q] <Channel-ID> [frage~antwort1~antwort2~antwort3~..]\n" + "``" + VariableStoring.PREFIX +  "help vote``", channel);

        }
    }
