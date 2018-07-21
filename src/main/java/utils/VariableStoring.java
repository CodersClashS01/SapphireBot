package utils;
/*
    Created by ConnysSoftware / ConCode on 24.06.2018 at 17:29.

    (c) ConnysSoftware / ConCode 2018
*/

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VariableStoring {

    public static String TOKEN = "";

    public static String GAME = "";

    public static String MYSQL_HOST = "";
    public static int MYSQL_PORT = 3306;
    public static String MYSQL_USER = "";
    public static String MYSQL_PASSWORD = "";
    public static String MYSQL_DATABASE = "";
    public static boolean DEBUG = false;

    public static String LOGO_TEXT = "https://www2.pic-upload.de/img/35618314/text_small.png";

    public static String PREFIX = "s!";
    public static Color COLOR = Color.decode("#7289da");

    public static boolean fastBoot = false;

    public static List<String> BOT_ADMINS = Collections.singletonList("");

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");

    public static String pal_authkey = "off";
    public static String pal_devid = "off";


}
