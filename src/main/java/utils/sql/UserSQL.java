package utils.sql;
/*
    Created by ConnysSoftware / ConCode on 30.04.2018 at 12:09.
    
    (c) ConnysSoftware / ConCode 2018
    (c) strukteon
*/

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

public class UserSQL {
    private static MySQL mySQL;
    private static String table = "users";

    private String userid;

    public static void init(MySQL mySQL){
        UserSQL.mySQL = mySQL;
    }


    private UserSQL(String userid){
        this.userid = userid;
        if (!exists())
            create();
    }


    private static UserSQL fromUserId(String userid){
        return new UserSQL(userid);
    }


    public static UserSQL fromUser(User user){
        return fromUserId(user.getId());
    }


    public static UserSQL fromMember(Member member){
        return fromUser(member.getUser());
    }


    private boolean exists(){
        return mySQL.SELECT("*", table, "id="+userid).size() != 0;
    }

    private void create(){
        mySQL.INSERT(table, "`id`, `bio`, `xp`, `rep`", String.format("'%s', 'Ziemlich leer hier...', 0, 0", userid));
    }

    public String getBio(){
        return mySQL.SELECT("*", table, "id="+userid).get("bio");
    }

    public int getRep(){
        return Integer.parseInt(mySQL.SELECT("*", table, "id="+userid).get("rep"));
    }

    public void setBio(String bio){
        mySQL.UPDATE(table, "bio='"+bio.replace("'", "\\'")+"'", "id="+userid);
    }

    public int getXp(){
        try {
            return Integer.parseInt(mySQL.SELECT("*", table, "id='" + userid + "'").get("xp"));
        } catch (NumberFormatException e){
            return 0;
        }
    }

    public int getLevel(){
        int level = 1;
        int xp = getXp();
        while (xp > 50 * level){
            xp -= 50 * level;
            level++;
        }
        return level < 100 ? level : 99;
    }

    public void addXp(int amount){
        mySQL.UPDATE(table, "xp=xp+"+amount, "id="+userid);
    }

    public void setXp(int amount){
        mySQL.UPDATE(table, "xp="+amount, "id="+userid);
    }

    public void takeXp(int amount){
        mySQL.UPDATE(table, "xp=xp-"+amount, "id="+userid);
    }

    public void addRep(int amount){
        mySQL.UPDATE(table, "rep=rep+"+amount, "id="+userid);
    }

    public void setRep(int amount){
        mySQL.UPDATE(table, "rep="+amount, "id="+userid);
    }

    public void takeRep(int amount){
        mySQL.UPDATE(table, "rep=rep-"+amount, "id="+userid);
    }

}
