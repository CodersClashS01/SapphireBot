package utils.sql;
/*
    Created by ConnysSoftware / ConCode.

    (c) ConnysSoftware / ConCode 2018
    (c) strukteon
*/

import config.Config;
import core.CLI;
import core.Main;
import utils.VariableStoring;

import java.sql.*;
import java.util.*;

public class MySQL {

    public Connection connection = null;
    private String
            host,
            user,
            password,
            database;
    private int port;


    public MySQL(String host, int port, String user, String password, String database){
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    public MySQL connect(){

        String[] parts = new String[]{this.host, "" + this.port, this.database, this.user, this.password};
        try {
            if (connection != null)
                connection.close();
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + parts[0] + ":" + parts[1] + "/" + parts[2] + "?allowMultiQueries=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true", parts[3], parts[4]);
            CLI.success("MySQL Connected Successfully");
        } catch (Exception e) {
            //CLI.error(e.getCause().toString());
            CLI.error("Could not establish MySQL connection to " + parts[0] + ":" + parts[1] + " with username: " + parts[3] + " and password: " + parts[4]);
            Config.fileChangeAction(Main.config);
        }
        return this;
    }

    public MySQL disconnect(){
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            CLI.error("Could not disconnect from " + this.host);
            CLI.error(e);
        }
        return this;
    }


    public HashMap<String, String> SELECT(String selection, String table, String where){
        HashMap<String, String> hashMap = new HashMap<>();

        try {
            PreparedStatement ps = connection.prepareStatement(String.format("select %s from `%s` where %s", selection, table, where));

            ResultSet res = ps.executeQuery();
            ResultSetMetaData meta = res.getMetaData();

            if (res.first())
                for (int i = 1; i <= meta.getColumnCount(); i++)
                    hashMap.put(meta.getColumnName(i), res.getString(i));
            else
                return hashMap;

        } catch (SQLException e) {
            CLI.error("Error in Database Connection.. reconnecting...");
            CLI.info("Reconnecting to Database...");
            disconnect();
            connect();
        }
        return hashMap;
    }


    public boolean MATCH_COLUMNS(String table, String[] columns){
        List<String> sourceColumns = new ArrayList<>();
        List<String> endColumns = Arrays.asList(columns);
        StringBuilder finalSql = new StringBuilder();
        try {
            PreparedStatement ps = connection.prepareStatement(String.format("select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME='%s' AND NOT COLUMN_NAME='USER' AND NOT COLUMN_NAME='CURRENT_CONNECTIONS' AND NOT COLUMN_NAME='TOTAL_CONNECTIONS'", table));

            ResultSet res = ps.executeQuery();

            while (res.next())
                sourceColumns.add(res.getString("COLUMN_NAME"));

            for (String s : sourceColumns)
                if (!endColumns.contains(s))
                    finalSql.append(String.format("alter table `%s` drop column `%s`; ", table, s));

            for (String s : endColumns)
                if (!sourceColumns.contains(s))
                    finalSql.append(String.format("alter table `%s` add column `%s` text; ", table, s));

            if (finalSql.length() != 0)
                connection.createStatement().execute(finalSql.toString());
        } catch (SQLException e) {
            CLI.error("Error in Database Connection.. reconnecting...");
            CLI.info("Reconnecting to Database...");
            disconnect();
            connect();
        }
        return finalSql.length() != 0;
    }


    public boolean MATCH_COLUMNS(String table, String[] columns, String[] types){
        List<String> sourceColumns = new ArrayList<>();
        List<String> endColumns = Arrays.asList(columns);
        StringBuilder finalSql = new StringBuilder();
        try {
            PreparedStatement ps = connection.prepareStatement(String.format("select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME='%s' AND NOT COLUMN_NAME='USER' AND NOT COLUMN_NAME='CURRENT_CONNECTIONS' AND NOT COLUMN_NAME='TOTAL_CONNECTIONS'", table));

            ResultSet res = ps.executeQuery();

            while (res.next())
                sourceColumns.add(res.getString("COLUMN_NAME"));

            for (String s : sourceColumns)
                if (!endColumns.contains(s))
                    finalSql.append(String.format("alter table `%s` drop column `%s`; ", table, s));

            for (String s : endColumns)
                if (!sourceColumns.contains(s))
                    finalSql.append(String.format("alter table `%s` add column `%s` " + types[endColumns.indexOf(s)] + "; ", table, s));

            if (finalSql.length() != 0)
                connection.createStatement().execute(finalSql.toString());
        } catch (SQLException e) {
            CLI.error("Error in Database Connection.. reconnecting...");
            CLI.info("Reconnecting to Database...");
            disconnect();
            connect();
        }
        return finalSql.length() != 0;
    }


    public void INSERT(String table, String columns, String values){
        try {
            if (VariableStoring.DEBUG) {
                System.out.println("INSERT -> " + String.format("insert into `%s` (%s) values (%s)", table, columns, values));
            }
            connection.createStatement().execute(String.format("insert into `%s` (%s) values (%s)", table, columns, values));
        } catch (SQLException e) {
            CLI.error("Error in Database Connection.. reconnecting...");
            CLI.info("Reconnecting to Database...");
            disconnect();
            connect();
            //e.printStackTrace();
        }
    }


    public void UPDATE(String table, String values, String where){
        try {
            if (VariableStoring.DEBUG){
                System.out.println("UPDATE -> " + String.format("update `%s` set %s where %s", table, values, where));
            }
            connection.createStatement().execute(String.format("update `%s` set %s where %s", table, values, where));
        } catch (SQLException e) {
            CLI.error("Error in Database Connection.. reconnecting...");
            CLI.info("Reconnecting to Database...");
            disconnect();
            connect();
            // e.printStackTrace();
        }
    }


    public void DELETE(String table, String where){
        try {
            connection.createStatement().execute(String.format("delete from `%s` where %s", table, where));
        } catch (SQLException e) {
            CLI.error("Error in Database Connection.. reconnecting...");
            CLI.info("Reconnecting to Database...");
            disconnect();
            connect();
        }
    }


    public void CREATE_TABLE(String table, String[] columns){
        try {
            StringBuilder b = new StringBuilder();
            for (String s : columns)
                b.append(s).append(" text, ");
            b.delete(b.length()-2, b.length());
            connection.createStatement().execute(String.format("create table `%s` (%s)", table, b.toString()));
        } catch (SQLException e) {
            CLI.error("Error in Database Connection.. reconnecting...");
            CLI.info("Reconnecting to Database...");
            disconnect();
            connect();
        }
    }


    public void DROP_TABLE(String table){
        try {
            connection.createStatement().execute(String.format("drop table `%s`", table));
        } catch (SQLException e) {
            CLI.error("Error in Database Connection.. reconnecting...");
            CLI.info("Reconnecting to Database...");
            disconnect();
            connect();
        }
    }


    public void ALTER_TABLE_ADD(String table, String column){
        try {
            connection.createStatement().execute(String.format("alter table `%s` add %s text", table, column));
        } catch (SQLException e) {
            CLI.error("Error in Database Connection.. reconnecting...");
            CLI.info("Reconnecting to Database...");
            disconnect();
            connect();
        }
    }


    public void ALTER_TABLE_DROP(String table, String column){
        try {
            connection.createStatement().execute(String.format("alter table `%s` drop column `%s`", table, column));
        } catch (SQLException e) {
            CLI.error("Error in Database Connection.. reconnecting...");
            CLI.info("Reconnecting to Database...");
            disconnect();
            connect();
        }
    }


    public boolean TABLE_EXISTS(String table){
        try {
            connection.createStatement().execute(String.format("select * from `%s` where 0", table));
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public boolean COLUMN_EXISTS(String table, String column){
        try {
            connection.createStatement().execute(String.format("select `%s` from `%s` where 0", column, table));
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
