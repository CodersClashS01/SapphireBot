package commands.general;
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

public class HelpCommand implements ICommand {

    @Override
    public void onCommand(CommandEvent event, Member member, TextChannel channel, String[] args) {

        EmbedBuilder buiderMSG = new EmbedBuilder().setColor(VariableStoring.COLOR).setTitle("Befehle");

        if (args.length > 1) {
            MessageCreator.createError(event.getMessage().getContentRaw(), "Du verwendest eine inkorrekte Schreibweise.", "Verwende die korrekte Schreibweise: `" + VariableStoring.PREFIX + "help <Befehl>`", channel);
        } else  if (args.length < 1) {
            channel.sendMessage(
                    buiderMSG.setColor(VariableStoring.COLOR)
                            .setDescription("**__Bot-Administrator__**\n" +
                                    "**shutdown** - Schaltet den Bot aus\n" +
                                    "**config** - Konfiguriert den Bot\n\n" +
                                    "**__Moderation__**\n" +
                                    "**ep** - Verwaltet die EP von Nutzern\n" +
                                    "**clear** - Säubert TextChannel\n" +
                                    "**warn** - Verwarnt Nutzer\n" +
                                    "**announce** - Schreibe Ankündigungen\n\n" +
                                    "**__User Moderation__**\n" +
                                    "**report** - Reporte andere Nutzer\n\n" +
                                    "**__Generell__**\n" +
                                    "**bio** - Verwalte deine Bio\n" +
                                    "**rep** - Verteile den anderen Usern einen Ruf\n" +
                                    "**profile** - Öffne deine Profile-Card\n" +
                                    "**vote** - Erstelle Abstimmungen\n\n" +
                                    "**__Spaß/Misc__**\n" +
                                    "**shoot** - Erschieße Miglieder\n" +
                                    "**smoke** - Rauche eine Zigarette\n" +
                                    "**kiss** - Küsse Miglieder\n" +
                                    "**hug** - Umarme Miglieder\n" +
                                    "**save** - Rette Miglieder\n" +
                                    "**cat** - Random Katzenbilder\n" +
                                    "**dog** - Random Hundebilder\n" +
                                    "**memes** - Random Memes\n" +
                                    "**dwdwk** - Das wars dann wieder komplett...\n" +
                                    "**cappy** - Beef...\n" +
                                    "**coolify** - Lässt text cool aussehen\n\n" +
                                    "**__Game-Tracker__**\n" +
                                    "**overwatch** - Checke deine Overwatch-Stats\n" +
                                    "**paladins** - Checke deine Paladins-Stats\n" +
                                    "**vote** - Erstelle Abstimmungen\n\n" +
                                    "**__Chatgames__**\n" +
                                    "**backjack** - Spiele Blackjack\n").build()
            ).queue();

        } else {
            EmbedBuilder helpMSG = new EmbedBuilder().setColor(VariableStoring.COLOR);
            switch (args[0].toLowerCase()){
                case "help":

                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du dir weitere Infos über die Benutzung des jeweiligen Befehls anzeigen lassen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "help [Befehl]``", false)
                            .addField("Alias", "help", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true);
                    break;

                case "shutdown":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du den SapphireBot herunter Fahren.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "shutdown``", false)
                            .addField("Alias", "shutdown", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "`BOT ADMIN`", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Shutdown)", false);
                    break;

                case "rep":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du Ruf verteilen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "rep [+/-/set] [@Mitglied] <Anzahl>``", false)
                            .addField("Alias", "rep", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Rep)", false);
                    break;

                case "smoke":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du eine Zigarette rauchen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "smoke``", false)
                            .addField("Alias", "smoke", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Smoke)", false);
                    break;

                case "shoot":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du andere Mitglieder erschießen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "shoot <@Mitglied>``", false)
                            .addField("Alias", "shoot", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Shoot)", false);
                    break;

                case "save":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du versuchen andere Mitglieder vor dem Zug zur retten.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "save <@Mitglied>``", false)
                            .addField("Alias", "save", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Save)", false);
                    break;

                case "hug":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du andere Mitglieder umarmen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "hug <@Mitglied>``", false)
                            .addField("Alias", "hug", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Hug)", false);
                    break;

                case "kiss":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du andere Mitglieder romantisch küssen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "kiss <@Mitglied>``", false)
                            .addField("Alias", "kiss", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Kiss)", false);
                    break;

                case "bio":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du deine Bio verändern.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "bio [set/reset] <Text>``", false)
                            .addField("Alias", "bio", false)

                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Bio)", false);
                    break;

                case "p":
                case "profile":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du die \"ProfileCard\" von dir, oder von anderen Mitgliedern aufrufen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "profile <@Mitglied/ID>``", false)
                            .addField("Alias", "profile, p", false)

                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Profile)", false);
                    break;

                case "clear":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du einen Text channel säubern.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "clear [2-100]``", false)
                            .addField("Alias", "clear", false)

                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "`Permission.MESSAGE_MANAGE`", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Clear)", false);
                    break;

                case "ep":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du die EP der Mitglieder kontrollieren.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "ep [set/add/take/reset] [@member] <Anzahl>``", false)
                            .addField("Alias", "ep", false)

                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "`BOT ADMIN`", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#EP)", false);
                    break;

                case "poll":
                case "ask":
                case "vote":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du Umfragen erstellen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "vote [-c/-c -id/-q] <Channel-ID> [frage~antwort1~antwort2~antwort3~..]``", false)
                            .addField("Argumente", "`-c` Erstelle eine Umfrage\n`-c -id [Channel-ID]` Erstelle eine Umfrage in einem anderem Channel\n`-q` Beende deine Umfrage", false)
                            .addField("Alias", "vote, poll, ask", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Vote)", false);
                    break;

                case "warn":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du Mitglieder als Reaktion auf Regelverstöße verwarnen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "warn <-l/-c> <@Mitglied/ID> <Regelverstoß>``", false)
                            .addField("Argumente", "`-l` Listet alle Warns auf diesem Guild auf\n`-l [@Mitglied/ID]` Listet alle Warns von diesem User auf\n`-c [@Mitglied/ID] [Grund]` Verwarnt einen Nutzer", false)
                            .addField("Alias", "warn", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "`Permission.MANAGE_SERVER`", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Warn)", false);
                    break;

                case "report":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du andere Mitglieder Reporten.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "report [@Mitglied] [Regelverstoß]``\n``" + VariableStoring.PREFIX + "report set [true/false] [Team-TextChannel ID] [Role ID]``", false)
                            .addField("Alias", "report", false)
                            .addField("Informiert werden", "``Permission.KICK_MEMBERS``, ``Permission.BAN_MEMBERS``", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Report)", false);
                    MessageCreator.normalUnbeddedMessage("```md\n[Argumente zur Konfiguration erklärt]\n*" + VariableStoring.PREFIX + "report set [true/false] [Team-TextChannel ID] [Role ID]*\n[true/false]\n    Sollen Report Benachrichtigungen per Teamchat statt PN an die Moderatoren gesendet werden?\n[Team-TextChannel ID]\n    Hier werden die Report Hinweise gesendet, falls die vorherige Option mit true beschrieben wurde?\n[Role ID]\n    Moderatoren mit dieser Rolle werden nicht mehr Informiert.\n```", channel);
                    break;

                case "c":
                case "config":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du die in der Config-Datei stehenden Einstellungen abändern.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "config [set/check/list/reset/reload] <Einstellung> <Neuer Wert>``", false)
                            .addField("Alias", "config, c", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "`BOT ADMIN`", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Config)", false);
                    break;

                case "pal":
                case "paladins":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du Stats aus Paladins auslesen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "paladins [Spielername]``", false)
                            .addField("Alias", "paladins, pal", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Paladins)", false);
                    break;

                case "ow":
                case "overwatch":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du, die Stats von Overwatch-Spielern checken.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "overwatch profile [BattleTag#BattleID] [pc/...]``\n" +
                                    "``" + VariableStoring.PREFIX + "overwatch hero [BattleTag#BattleID] [pc/...] [dva/ana/...]``", false)
                            .addField("Beispiele", "``" + VariableStoring.PREFIX + "overwatch profile ConGaming#21333 pc``\n" +
                                    "``" + VariableStoring.PREFIX + "overwatch hero ConGaming#21333 pc dva``", false)
                            .addField("Alias", "overwatch, ow", false)
                            .addField("Helden", "``ana``, ``bastion``, ``brigitte``, ``dva``, ``doomfist``, ``genji``, ``hanzo``, ``junkrat``, ``lucio``, ``mccree``, ``mei``, ``mercy``, ``moira``, ``orisa``, ``pharah``, ``reaper``, ``reinhardt``, ``roadhog``, ``soldier76``, ``sombra``, ``symmetra``, ``torbjorn``, ``tracer``, ``widowmaker``, ``winston``, ``zarya``, ``zenyatta``", false)
                            .addBlankField(false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Overwatch)", false);
                    break;

                case "cat":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl erhällst du random Bilder von Katzen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "cat``", false)
                            .addField("Alias", "cat", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Cat)", false);
                    break;

                case "dog":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl erhällst du random Bilder von Hunden.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "dog``", false)
                            .addField("Alias", "dog", false)

                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Dog)", false);
                    break;

                case "dwdwk":
                case "das_wars_dann_wieder_komplett":
                    helpMSG.addField("Erklärung", "Das wars dann wieder komplett...", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "das_wars_dann_wieder_komplett``", false)
                            .addField("Alias", "das_wars_dann_wieder_komplett, dwdwk", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#dwdwk)", false);
                    break;

                case "cap":
                case "cappy":
                    helpMSG.addField("Erklärung", "Cap, wenn er wieder Beef sieht...", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "cappy``", false)
                            .addField("Alias", "cappy, cap", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true);
                    break;

                case "memes":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl erhällst du random Memes.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "memes``", false)
                            .addField("Alias", "memes", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Memes)", false);
                    break;

                case "announce":
                case "say":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du Ankündigungen oder Ähnliches mit einer Embedded Message schreiben.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "announce [Title~Content Text]``", false)
                            .addField("Alias", "say, announce", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Announce)", false);
                    break;

                case "coolify":
                case "cool":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst deinen Text cool machen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "coolify [Text]``", false)
                            .addField("Alias", "cool, coolify", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Coolify)", false);
                    break;

                case "jack":
                case "blackjack":
                    helpMSG.addField("Erklärung", "Mit diesem Befehl kannst du Blackjack spielen.", false)
                            .addField("Verwendung", "``" + VariableStoring.PREFIX + "blackjack [-s/leave/end/hit/stand] <create/join/start> <Angabe>``", false)
                                    .addField("Beispiele", "``" + VariableStoring.PREFIX + "blackjack s create``\n" +
                                    "``" + VariableStoring.PREFIX + "blackjack s join [SessionID]``\n" +
                                    "``" + VariableStoring.PREFIX + "blackjack s start``\n" +
                                    "``" + VariableStoring.PREFIX + "blackjack hit``\n" +
                                    "``" + VariableStoring.PREFIX + "blackjack stand``\n" +
                                    "``" + VariableStoring.PREFIX + "blackjack leave``\n" +
                                    "``" + VariableStoring.PREFIX + "blackjack end``\n", false)
                            .addField("Alias", "blackjack, jack", false)
                            .addField("Verwendungsort", "TextChannel", true)
                            .addField("Permissions", "/", true)
                            .addField("Weitere Infos", "[Sapphire Wiki-Eintrag](https://github.com/CodersClashS01/SapphireBot/wiki/Befehle#Blackjack)", false);
                    break;
            }

            channel.sendMessage(helpMSG.build()).queue();

        }
    }
}
