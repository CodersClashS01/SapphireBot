package listener;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
*/

import core.CLI;
import core.Main;
import utils.chatgame.JackPlayersSQL;
import utils.chatgame.JackSessionSQL;
import utils.sql.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.VariableStoring;

import java.util.Timer;
import java.util.TimerTask;

public class ReadyListener extends ListenerAdapter {

    public void onReady(ReadyEvent event) {

        Main.mySQL = new MySQL(VariableStoring.MYSQL_HOST, VariableStoring.MYSQL_PORT, VariableStoring.MYSQL_USER, VariableStoring.MYSQL_PASSWORD, VariableStoring.MYSQL_DATABASE);
        Main.mySQL.connect();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Main.mySQL.disconnect();
                Main.mySQL.connect();
            }
        }, 60 * 60 * 1000, 60 * 60 * 1000);

        UserSQL.init(Main.mySQL);
        VoteSQL.init(Main.mySQL);
        ReportSQL.init(Main.mySQL);
        UserReportSQL.init(Main.mySQL);
        JackPlayersSQL.init(Main.mySQL);
        JackSessionSQL.init(Main.mySQL);

        System.out.print(CLI.BLUE);
        System.out.println("" +
                "   _____                   __    _                     ___\n" +
                "  / ___/____ _____  ____  / /_  (_)_______     _   __ <  /\n" +
                "  \\__ \\/ __ `/ __ \\/ __ \\/ __ \\/ / ___/ _ \\   | | / / / / \n" +
                " ___/ / /_/ / /_/ / /_/ / / / / / /  /  __/   | |/ / / /  \n" +
                "/____/\\__,_/ .___/ .___/_/ /_/_/_/   \\___/    |___(_)_/   \n" +
                "          /_/   /_/                                       " + CLI.RESET);

    }


}
