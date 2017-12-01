package Auora.model.player.logs;

import Auora.model.player.Player;

/**
 * Distributes logs to seperate & individual logs
 *
 * @Author Jonny
 */
public class Logs {

    public static void log(Player player, String type, String[] data) {
        SeperateLogs.log(player, type, data);
        IndividualLogs.log(player, type, data);
    }
}
