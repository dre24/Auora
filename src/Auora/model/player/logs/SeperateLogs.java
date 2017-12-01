package Auora.model.player.logs;

import Auora.model.player.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Writes data to the log files for seperate logs
 * stored at ./logs/seperate/type/player name.txt
 *
 * @Author Jonny
 */
public class SeperateLogs {

    /**
     * Fetches system time and formats it appropriately
     *
     * @return Formatted time
     */
    private static String getTime() {
        Date getDate = new Date();
        String timeFormat = "M/d/yy hh:mma";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        return "[" + sdf.format(getDate) + "]";
    }

    /**
     * Logs data into a certain log
     */
    public static void log(Player player, String type, String[] data) {
        //if (!GameSettings.PLAYER_LOGGING) {
        //return;
        //}
        try {
            File directory = new File("./logs/seperate/" + type + "");
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File("./logs/seperate/" + type + "/" + player.getFormattedName() + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(getTime() + " ");
            for (String logData : data) {
                if (data == null) {
                    continue;
                }
                bw.write(logData + ", ");
            }
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}