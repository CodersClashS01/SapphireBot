package utils.sql;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
    (c) strukteon
*/

import utils.VariableStoring;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportSQL {
    private static MySQL mySQL;
    private static String table = "reports";

    private String guild, victim, executor, reason;
    private Date date;

    public static void init(MySQL mySQL){
        ReportSQL.mySQL = mySQL;
    }


    private ReportSQL(String guild, String victim, String executor, String reason){
        this(guild, victim, executor, reason, new Date());
        create();
    }

    private ReportSQL(String guild, String victim, String executor, String reason, Date date){
        this.guild = guild;
        this.victim = victim;
        this.executor = executor;
        this.reason = reason;
        this.date = date;
    }


    public static ReportSQL createNew(String guild, String victim, String executor, String reason){
        return new ReportSQL(guild, victim, executor, reason);
    }

    public static List<ReportSQL> getReportsByUser(String userid, String guildID){
        List<ReportSQL> reportSQLs = new ArrayList<>();

        try {
            PreparedStatement ps = mySQL.connection.prepareStatement(String.format("select * from `%s` where `victim`='%s' and `guild`='%s'", table, userid, guildID));

            ResultSet res = ps.executeQuery();

            while (res.next()){
                Date d;
                try {
                    d = VariableStoring.DATE_FORMAT.parse(res.getString("date"));
                } catch (ParseException e) {
                    d = new Date();
                }
                reportSQLs.add(new ReportSQL(res.getString("guild"), res.getString("victim"), res.getString("executor"), res.getString("reason"), d));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportSQLs;
    }

    public static List<ReportSQL> getReportsByAll(String guildID){
        List<ReportSQL> reportSQLs = new ArrayList<>();

        try {
            PreparedStatement ps = mySQL.connection.prepareStatement(String.format("select * from `%s` where `guild`='%s'", table, guildID));

            ResultSet res = ps.executeQuery();
            ResultSetMetaData meta = res.getMetaData();

            while (res.next()){
                Date d;
                try {
                    d = VariableStoring.DATE_FORMAT.parse(res.getString("date"));
                } catch (ParseException e) {
                    d = new Date();
                }
                reportSQLs.add(new ReportSQL(res.getString("guild"), res.getString("victim"), res.getString("executor"), res.getString("reason"), d));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportSQLs;
    }


    private void create(){
        mySQL.INSERT(table, "`guild`, `victim`, `executor`, `reason`, `date`", String.format("'%s', '%s', '%s', '%s', '%s'", guild, victim, executor, reason, VariableStoring.DATE_FORMAT.format(date)));
    }

    public String getGuild() {
        return guild;
    }

    public String getVictim() {
        return victim;
    }

    public String getExecutor() {
        return executor;
    }

    public String getReason() {
        return reason;
    }

    public Date getDate() {
        return date;
    }
}
