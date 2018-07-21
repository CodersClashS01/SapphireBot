package core;
/*
    Created by ConnysSoftware / ConCode on 28.04.2018 at 22:26.

    (c) ConnysSoftware / ConCode 2018
    (c) strukteon
*/

public class CLI {

    public static final String RESET = "\u001B[0m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    public static void error(Exception e){
        e.printStackTrace();
        /*error(e.getMessage());
        for (StackTraceElement el :e.getStackTrace())*/
    }

    public static void customInfo(String color, String title, String text){
        System.out.println(color + "[" + title + "] " + text + RESET);
    }

    public static void error(String text){
        System.out.println(RED + "[ERROR] " + text + RESET);
    }

    public static void error_SameLN(String text){
        System.out.print(RED + "[ERROR] " + text + RESET);
    }

    public static void info(String text){
        System.out.println(BLUE + "[INFO] " + text + RESET);
    }

    public static void input(String text){
        System.out.print(YELLOW + "[INPUT] " + text + RESET);
    }

    public static void success(String text){
        System.out.println(GREEN + "[SUCCESS] " + text + RESET);
    }

    public static void shutdown(int status){
        info("Shutting down...");
        Runtime.getRuntime().exit(status);
    }

    public static void shutdownError(int status, String message){

        error(message);

        info("Shutting down...");
        Runtime.getRuntime().exit(status);
    }

}