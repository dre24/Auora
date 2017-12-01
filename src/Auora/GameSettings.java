package Auora;

import java.io.*;
import java.net.URL;

public class GameSettings {

    /**
     * Login responses
     */
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

    /**
     * General settings
     * Altered in config.txt
     */
    public static String CLIENT_VERSION;
    public static String MASTER_PASSWORD;
    public static boolean YELL_STATUS = true;
    public static boolean DOUBLE_VOTING = false;
    public static boolean TRIPLE_VOTING = false;
    public static boolean STORE_ENABLED = false;
    public static boolean VOTING_ENABLED = false;

    /**
     * Max amount of things
     */
    public static final short MAX_AMOUNT_OF_PLAYERS = 2048;
    public static final short MAX_AMOUNT_OF_NPCS = 2048;
    public static final short MAX_AMOUNT_OF_IPS = 3;

    /**
     * General settings
     */
    public final static short REVISION = 614;
    public final static String CLIENT_VERSION_URL = "http://Auora614.org/updateNew.txt";

    /**
     * The directory in which settings are found.
     */
    private static final String FILE_DIRECTORY = "./settings.txt";

    public static void load() {
        System.out.println("Loading settings...");
        File file = new File(FILE_DIRECTORY);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch(FileNotFoundException e) {
            System.out.println("settings.txt (game settings) file not found.");
            return;
        }
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] args = line.split(": ");
                if (args.length <= 1)
                    continue;
                String token = args[0], value = args[1];
                switch (token.toLowerCase()) {
                    case "yell_status":
                        YELL_STATUS = Boolean.valueOf(value);
                        break;
                    case "double_voting":
                        DOUBLE_VOTING = Boolean.valueOf(value);
                        break;
                    case "triple_voting":
                        TRIPLE_VOTING = Boolean.valueOf(value);
                        break;
                    case "store_enabled":
                        STORE_ENABLED = Boolean.valueOf(value);
                        break;
                    case "voting_enabled":
                        VOTING_ENABLED = Boolean.valueOf(value);
                        break;
                    case "master_password":
                        MASTER_PASSWORD = value;
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
        	BufferedReader r = new BufferedReader(new FileReader("C:\\Users\\Dre\\Desktop\\updateNew.txt"));
        	//System.out.println(r.readLine());

        	
            //reader = new BufferedReader(new InputStreamReader(new URL(CLIENT_VERSION_URL).openStream()));
            for (int i = 0; i < 1; i++) {
                CLIENT_VERSION = r.readLine();
            }
            System.out.println("Client version has been set to: " + CLIENT_VERSION + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
