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

public class JackSessionSQL {
    private static MySQL mySQL;
    private static String table = "jack_sessions";

    private String guild, creator, session_id, gaming;
    private List<String> players;
    private String started;

    public static void init(MySQL mySQL){
        JackSessionSQL.mySQL = mySQL;
    }


    private JackSessionSQL(String guild, String creator, String session_id, List<String> players, String gaming, Date date){ //Create
        this(guild, creator, session_id, players, "false", gaming, false);
        create();
    }
    private JackSessionSQL(String session_id){ //delete
        this(session_id, false);
        delete();
    }

    private JackSessionSQL(String guild, String creator, String session_id, List<String> players, String started, String gaming,  Color color){ //Update
        this(guild, creator, session_id, players, started, gaming, true);
                update();
    }

    private JackSessionSQL(String guild, String creator, String session_id, List<String> players, String started, String gaming,  boolean nan){
        this.guild = guild;
        this.creator = creator;
        this.session_id = session_id;
        this.players = players;
        this.started = started;
        this.gaming = gaming;
    }

    private JackSessionSQL(String session_id, boolean nan){
        this.session_id = session_id;
    }


    public static JackSessionSQL createNew(String guild, String creator, String session_id, List<String> players){
        return new JackSessionSQL(guild, creator, session_id, players, null, new Date());
    }

    public static JackSessionSQL updateOld(String guild, String creator, String session_id, String started, String gaming, List<String> players){
        return new JackSessionSQL(guild, creator, session_id, players, started, gaming, new Color(1 , 2, 3));
    }

    public static JackSessionSQL removeOld(String session_id){
        return new JackSessionSQL(session_id);
    }

    public static List<JackSessionSQL> getJackSessionBySessionID(String guildID, String sessionID){
        List<JackSessionSQL> jackSessionSQLs = new ArrayList<>();

        try {
            PreparedStatement ps = mySQL.connection.prepareStatement(String.format("select * from `%s` where `guild`='%s' and `session_id`='%s'", table, guildID, sessionID));

            ResultSet res = ps.executeQuery();

            while (res.next()){

                jackSessionSQLs.add(new JackSessionSQL(res.getString("guild"), res.getString("creator"), res.getString("session_id"), Arrays.asList(res.getString("players").split(",")), res.getString("started"), res.getString("gaming"), false));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jackSessionSQLs;
    }

    public static List<JackSessionSQL> getJackSessionByUser(String guild, String userID){
        List<JackSessionSQL> jackSessionSQLs = new ArrayList<>();

        try {
            PreparedStatement ps = mySQL.connection.prepareStatement(String.format("select * from `%s` where `guild`='%s' and `creator`='%s'", table, guild, userID));

            ResultSet res = ps.executeQuery();

            while (res.next()){

                jackSessionSQLs.add(new JackSessionSQL(res.getString("guild"), res.getString("creator"), res.getString("session_id"), Arrays.asList(res.getString("players").split(",")), res.getString("started"), res.getString("gaming"), false));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jackSessionSQLs;
    }

    public static List<JackSessionSQL> getJackSessionByGuild(String guildID){
        List<JackSessionSQL> jackSessionSQLs = new ArrayList<>();

        try {
            PreparedStatement ps = mySQL.connection.prepareStatement(String.format("select * from `%s` where `guild`='%s'", table, guildID));

            ResultSet res = ps.executeQuery();
            ResultSetMetaData meta = res.getMetaData();

            while (res.next()){
                jackSessionSQLs.add(new JackSessionSQL(res.getString("guild"), res.getString("creator"), res.getString("session_id"), Arrays.asList(res.getString("players").split(",")), res.getString("started"), res.getString("gaming"), false));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jackSessionSQLs;
    }


    public void create(){

        mySQL.INSERT(table, "`guild`, `creator`, `session_id`, `players`, `started`", String.format("'%s', '%s', '%s', '%s', '%s'", guild, creator, session_id, String.join(",", players), started));
    }

    public void update(){
        mySQL.UPDATE(table, "`players`='" + String.join(",", players) + "', `started`='" + started + "', `gaming`='" + gaming + "'", "`guild`='" + guild + "' and `session_id`='" + session_id + "'");
    }

    public void delete(){
        mySQL.DELETE(table, "`session_id`='" + session_id + "'");
    }

    public String getGuild() {
        return guild;
    }

    public String getCreator() {
        return creator;
    }

    public String getSession_id() {
        return session_id;
    }

    public List<String> getPlayers() {
        return players;
    }

    public String getStarted() {
        return started;
    }

    public String getGaming() {
        return gaming;
    }
}
