package utils.chatgame;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
    (c) strukteon
*/

import utils.sql.MySQL;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JackPlayersSQL {
    private static MySQL mySQL;
    private static String table = "jack_players";

    private String guild, user_id, session_id;
    private int number;
    private List<String> cards, ever_cards;

    public static void init(MySQL mySQL){
        JackPlayersSQL.mySQL = mySQL;
    }

    private JackPlayersSQL(String guild, String user_id, String session_id, int number, List<String> cards, List<String> ever_cards, Date date){ //Create
        this(guild, user_id, session_id, number, cards, ever_cards, false);
        create();

    }

    private JackPlayersSQL(String guild, String user_id, String session_id, int number, List<String> cards, List<String> ever_cards, Color color){ //Update
        this(guild, user_id, session_id, number, cards, ever_cards, true);
        update();
    }

    private JackPlayersSQL(String guild, String user_id, String session_id, int number, List<String> cards, List<String> ever_cards, boolean nan){
        this.guild = guild;
        this.user_id = user_id;
        this.session_id = session_id;
        this.number = number;
        this.cards = cards;
        this.ever_cards = ever_cards;
        System.out.println("NUMMER INT" + number);
    }


    public static JackPlayersSQL createNew(String guild, String user_id, String session_id, int number, List<String> cards, List<String> ever_cards){
        return new JackPlayersSQL(guild, user_id, session_id, number, cards, ever_cards, new Date());
    }



    public static JackPlayersSQL updateOld(String guild, String user_id, String session_id, int number, List<String> cards, List<String> ever_cards){
        return new JackPlayersSQL(guild, user_id, session_id, number, cards, ever_cards, new Color(1, 2, 3));
    }



    public static List<JackPlayersSQL> getJackPlayersBySession(String sessionID, String guildID){
        List<JackPlayersSQL> jackPlayersSQLs = new ArrayList<>();

        try {
            PreparedStatement ps = mySQL.connection.prepareStatement(String.format("select * from `%s` where `session_id`='%s' and `guild`='%s'", table, sessionID, guildID));

            ResultSet res = ps.executeQuery();

            while (res.next()){
                jackPlayersSQLs.add(new JackPlayersSQL(res.getString("guild"), res.getString("userid"), res.getString("session_id"), res.getInt("number"), Arrays.asList(res.getString("cards").split(",")),  Arrays.asList(res.getString("ever_cards").split(",")), true));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jackPlayersSQLs;
    }

    public static List<JackPlayersSQL> getJackPlayersByUser(String user_id, String guildID){
        List<JackPlayersSQL> jackPlayersSQLs = new ArrayList<>();

        try {
            PreparedStatement ps = mySQL.connection.prepareStatement(String.format("select * from `%s` where `userid`='%s' and `guild`='%s'", table, user_id, guildID));

            ResultSet res = ps.executeQuery();

            while (res.next()){
                jackPlayersSQLs.add(new JackPlayersSQL(res.getString("guild"), res.getString("userid"), res.getString("session_id"), res.getInt("number"), Arrays.asList(res.getString("cards").split(",")),  Arrays.asList(res.getString("ever_cards").split(",")), true));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jackPlayersSQLs;
    }

    public static List<JackPlayersSQL> getReportsByAll(String guildID){
        List<JackPlayersSQL> jackPlayersSQLs = new ArrayList<>();

        try {
            PreparedStatement ps = mySQL.connection.prepareStatement(String.format("select * from `%s` where `guild`='%s'", table, guildID));

            ResultSet res = ps.executeQuery();
            ResultSetMetaData meta = res.getMetaData();

            while (res.next()){
                jackPlayersSQLs.add(new JackPlayersSQL(res.getString("guild"), res.getString("userid"), res.getString("session_id"), res.getInt("number"), Arrays.asList(res.getString("cards").split(",")),  Arrays.asList(res.getString("ever_cards").split(",")), true));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jackPlayersSQLs;
    }

    public boolean exists(){
        return mySQL.SELECT("*", table, "`guild`='" + guild + "' AND `userid`='" + user_id + "'").size() != 0;
    }

    public void create(){
        mySQL.INSERT(table, "`guild`, `userid`, `session_id`, `number`, `cards`, `ever_cards`", String.format("'%s', '%s', '%s', '%s', '%s', '%s'", guild, user_id, session_id, number, cards, ever_cards));
    }

    private void update(){
        mySQL.UPDATE(table, "`session_id`='" + session_id + "', `number`='" + number + "'", "`guild`='" + guild + "' and `userid`='" + user_id + "'");
    }

    public void addNumbers(int amount){
        mySQL.UPDATE(table, "number=number+"+amount, "`guild`='" + guild + "' and `userid`='" + user_id + "'");
    }

    public String getGuild() {
        return guild;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public int getNumber() {
        return number;
    }

    public List<String> getCards() {
        return cards;
    }

    public List<String> getEver_cards() {
        return ever_cards;
    }
}
