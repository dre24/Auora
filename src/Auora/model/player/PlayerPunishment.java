package Auora.model.player;

import java.io.File;
import java.io.IOException;

/**
 * This file manages player punishments, such as mutes, bans, etc.
 *
 * @author Jonathan spawnscape
 */
public final class PlayerPunishment {

    /**
     * The general punishment folder directory.
     */
    public static final String PUNISHMENT_DIRECTORY = "./data/punishments";
    /**
     * Leads to directory where banned account files are stored.
     */
    public static final String PLAYER_BAN_DIRECTORY = PUNISHMENT_DIRECTORY + "/bans/";
    /**
     * Leads to directory where muted account files are stored.
     */
    public static final String MUTE_DIRECTORY = PUNISHMENT_DIRECTORY + "/mutes/";
    /**
     * Leads to directory where banned account files are stored.
     */
    public static final String IP_BAN_DIRECTORY = PUNISHMENT_DIRECTORY + "/ip_bans/";
    /**
     * Leads to directory where muted account files are stored.
     */
    public static final String IP_MUTE_DIRECTORY = PUNISHMENT_DIRECTORY + "/ip_mutes/";
    /**
     * Leads to directory where banned account files are stored.
     */
    public static final String JBAN_DIRECTORY = PUNISHMENT_DIRECTORY + "/jbans/";

    public static boolean isPlayerBanned(String username) {
        return new File(PLAYER_BAN_DIRECTORY + username.toLowerCase()).exists();
    }

    public static boolean isIpBanned(String ip) {
        return new File(IP_BAN_DIRECTORY + ip.toLowerCase()).exists();
    }

    public static boolean isJBanned(String mac) {
        return new File(JBAN_DIRECTORY + mac.toLowerCase()).exists();
    }

    public static boolean isIpMuted(String ip) {
        return new File(IP_MUTE_DIRECTORY + ip.toLowerCase()).exists();
    }

    public static boolean isMuted(String name) {
        return new File(MUTE_DIRECTORY + name.toLowerCase()).exists();
    }

    public static void ipBan(String ip) {
        try {
            new File(IP_BAN_DIRECTORY + ip).createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void playerBan(String name) {
        try {
            new File(PLAYER_BAN_DIRECTORY + name).createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void unPlayerBan(String name) {
        new File(PLAYER_BAN_DIRECTORY + name).delete();
    }

    public static void jBan(String serial) {
        if(serial.equalsIgnoreCase("invalid_serial")) {
            System.out.println("A serial that was invalid was attempted to be banned.");
            return;
        }
        try {
            new File(JBAN_DIRECTORY + serial).createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void ipMute(String ip) {
        try {
            new File(IP_MUTE_DIRECTORY + ip).createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void mute(String name) {
        try {
            new File(MUTE_DIRECTORY + name).createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void unIpBan(String ip) {
        new File(IP_BAN_DIRECTORY + ip).delete();
    }

    public static void unIpMute(String ip) {
        new File(IP_MUTE_DIRECTORY + ip).delete();
    }

    public static void unMute(String ip) {
        new File(MUTE_DIRECTORY + ip).delete();
    }

    public static void unJBan(String serial) {
        new File(JBAN_DIRECTORY + serial).delete();
    }

}
