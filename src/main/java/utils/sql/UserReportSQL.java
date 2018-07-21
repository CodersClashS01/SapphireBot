package utils.sql;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
    (c) strukteon
*/

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserReportSQL {
    private static MySQL mySQL;
    private static String table = "user_reports";

    private String guild, text_channel_id, use_text, no_notify;

    public static void init(MySQL mySQL){
        UserReportSQL.mySQL = mySQL;
    }


    private UserReportSQL(String guild, String text_channel_id, String use_text, String no_notify, Date date){ //Create
        this(guild, text_channel_id, use_text, no_notify);
        create();
    }

    private UserReportSQL(String guild, String text_channel_id, String use_text, String no_notify, Color color){ //Update
        this(guild, text_channel_id, use_text, no_notify);
        update();
    }

    private UserReportSQL(String guild, String text_channel_id, String use_text, String no_notify){
        this.guild = guild;
        this.text_channel_id = text_channel_id;
        this.use_text = use_text;
        this.no_notify = no_notify;
    }


    public static UserReportSQL createNew(String guild, String use_text, String text_channel_id, String no_notify){
        return new UserReportSQL(guild, text_channel_id, use_text, no_notify, new Date());
    }

    public static UserReportSQL updateOld(String guild, String use_text, String text_channel_id, String no_notify){
        return new UserReportSQL(guild, text_channel_id, use_text, no_notify, new Color(1, 2 ,3));
    }

    public static List<UserReportSQL> getReportsByGuild(String guild){
        List<UserReportSQL> UserReportSQLs = new ArrayList<>();

        try {
            PreparedStatement ps = mySQL.connection.prepareStatement(String.format("select * from `%s` where `guild`='%s'", table, guild));

            ResultSet res = ps.executeQuery();

            while (res.next()){
                UserReportSQLs.add(new UserReportSQL(res.getString("guild"), res.getString("text_channel_id"), res.getString("use_text"), res.getString("no_notify")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserReportSQLs;
    }

    public static List<UserReportSQL> getReportsByAll(){
        List<UserReportSQL> UserReportSQLs = new ArrayList<>();

        try {
            PreparedStatement ps = mySQL.connection.prepareStatement(String.format("select * from `%s`", table));

            ResultSet res = ps.executeQuery();

            while (res.next()){
                UserReportSQLs.add(new UserReportSQL(res.getString("guild"), res.getString("victim"), res.getString("executor"), res.getString("reason")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserReportSQLs;
    }


    private void create(){
        mySQL.INSERT(table, "`guild`, `use_text`, `text_channel_id`, `no_notify`", String.format("'%s', '%s', '%s', '%s'", guild, text_channel_id, use_text, no_notify));
    }

    private void update(){
        mySQL.UPDATE(table, "`use_text`='" + use_text + "', `text_channel_id`='" + text_channel_id + "', `no_notify`='" + no_notify + "'", "`guild`='" + guild + "'");
    }

    public String getGuild() {
        return guild;
    }

    public String getText_channel_id() {
        return text_channel_id;
    }

    public String getUse_text() {
        return use_text;
    }

    public String getNo_notify() {
        return no_notify;
    }
}
