package Auora.util;

import java.util.ArrayList;

public class Constants {

    public static final short MAX_AMT_OF_IPS = 3;
    public final static boolean ISMEMBERWORLD = true;
    public final static short REVISION = 614;
    public final static byte DISCONNECT = -1;
    public final static byte GET_CONNECTION_ID = 0;
    public final static byte LOGIN_START = 1;
    public final static byte LOGIN_CYPTION = 2;
    public final static byte CHECK_ACC_NAME = 3;
    public final static byte CHECK_ACC_COUNTRY = 4;
    public final static byte MAKE_ACC = 5;
    public final static byte REMOVE_ID = 100;
    public final static byte UPDATESERVER_PART1 = 5;
    public final static byte UPDATESERVER_PART2 = 6;
    public final static byte UPDATESERVER_CACHE = 8;
    public static final byte LOGIN_OK = 2;
    public static final byte INVALID_PASSWORD = 3;
    public static final byte BANNED = 4;
    public static final byte ALREADY_ONLINE = 5;
    public static final byte WORLD_FULL = 7;
    public static final byte TRY_AGAIN = 11;
    public static final byte LOCKED = 18;
    public static final byte UPDATE = 14;
    public static final byte IPED = 26;
    public static final byte LOGINLIMIT = 9;
    public static final short MAX_AMT_OF_PLAYERS = 2048;
    public static final short MAX_AMT_OF_NPCS = 2048;
    public static final byte LOBBY_PM_CHAT_MESSAGE = 0;
    public static final byte LOBBY_CLAN_CHAT_MESSAGE = 11;
    public static final byte COMMANDS_MESSAGE = 99;
    public static byte[] idx255_File255 = {};
    public static boolean PLAYERS_ONLINE;
    public static boolean DATABASE_LOGGING;
    public static boolean VOTING_CONNECTIONS;
    public static boolean STORE_CONNECTIONS;
    public static boolean YELL_STATUS;
    public static boolean ITEM_SPAWN_TACTICAL;

    public static int DATABASE_LOGGING_TIME = 60;

    public static ArrayList<String> PROTECTED_MAC_ADDRESS = new ArrayList<String>();
    public static ArrayList<String> PROTECTED_COMPUTER_ADDRESS = new ArrayList<String>();
    public static ArrayList<String> PROTECTED_IP_ADDRESS = new ArrayList<String>();

    public static int GLOBAL_TELEPORT_START_GFX = 2617;
    public static int GLOBAL_TELEPORT_END_GFX = 2618;

    public static int GLOBAL_TELEPORT_START_ANIMATION = 8939;
    public static int GLOBAL_TELEPORT_END_ANIMATION = 8941;

}
