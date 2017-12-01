package Auora.model.player;

import java.util.Calendar;

/**
 * This file manages player punishments, such as mutes, bans, etc.
 *
 * @author Jonathan spawnscape
 */
public final class TimedInfractions {

    public static Calendar calendar = Calendar.getInstance();

    public static int YEAR = calendar.get(Calendar.YEAR);
    public static int MONTH = calendar.get(Calendar.MONTH) + 1;
    public static int DAY = calendar.get(Calendar.DAY_OF_MONTH);
    public static int HOUR = calendar.get(Calendar.HOUR_OF_DAY);

    public static void process_timed_infractions(Player p) {
        //	System.out.println(""+p.mute_year+"");
        //System.out.println(""+p.mute_month+"");
        //System.out.println(""+p.mute_day+"");
        //System.out.println(""+p.mute_hour+"");
    }

}
