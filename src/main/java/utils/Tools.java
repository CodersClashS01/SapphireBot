package utils;
/*
    Created by ConnysSoftware / ConCode on 07.02.2018 at 02:21.
    
    (c) ConnysSoftware / ConCode 2018
*/

import net.dv8tion.jda.core.entities.Role;
import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tools {

    public static String argsToString(String[] args, int begin, String filler){
        /* Old method to convert Args to Strings
        StringBuilder out = new StringBuilder();
        for (String s : args){
            if (out.length() != 0)
                out.append(filler);
            out.append(s);
        }
        return out.toString();
        */
        return String.join(filler, Arrays.asList(args).subList(begin, args.length));
    }

    public static String listToString(List<String> list){
        return listToString(list, " ");
    }

    public static String listToString(List<String> list, String filler){
        StringBuilder out = new StringBuilder();
        for (String s : list){
            if (out.length() != 0)
                out.append(filler);
            out.append(s);
        }
        return out.toString();
    }

    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }



    public static String[] JsonArrayToStringArray(JSONArray array) {
        if(array==null)
            return null;

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }

    public static boolean isUrl(String url){
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public static String stacktraceToString(StackTraceElement[] stackTrace, int limit, boolean indent){
        StringBuilder out = new StringBuilder();
        int len = (stackTrace.length > limit ? limit : stackTrace.length);
        for (int i = 0; i < len; i++ ){
            if (out.length() != 0)
                out.append("\n");
            out.append(indent ? "    " : "").append("at ").append(stackTrace[i].toString());
        }
        return out.toString();
    }

    public static String coolify(String s){
        StringBuilder out = new StringBuilder();
        for (String str : s.split("")){
            if (str.equals(" "))
                out.append(" :large_blue_diamond:");
            else
                out.append(" :regional_indicator_").append(str.toLowerCase()).append(":");
        }
        return out.toString();
    }

    public static boolean canModify(Role r){
        return r.getPosition() < r.getGuild().getMemberById(r.getJDA().getSelfUser().getId()).getRoles().get(0).getPosition();
    }

    public static boolean isId(String toCheck){
        return toCheck.matches("\\d{18}");
    }

    public static boolean isMention(String toCheck){
        return toCheck.matches("<@(!)?\\d{18}>");
    }

    public static boolean isChannelMention(String toCheck){
        return toCheck.matches("<#(!)?\\d{18}>");
    }

    public static boolean isRoleMention(String toCheck){
        return toCheck.matches("<@&\\d{18}>");
    }

    public static String mentionToId(String mention){
        return mention.replaceAll("<@(!)?|>", "");
    }

    public static String channelMentionToId(String mention){
        return mention.replaceAll("<#(!)?|>", "");
    }

    public static String roleToId(String mention){
        return mention.replaceAll("<#&|>", "");
    }

    public static String getIntName(int i){
        if (i < 0 || i > 9)
            return "invalid";
        String[] intWords = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        return intWords[i];
    }

}
