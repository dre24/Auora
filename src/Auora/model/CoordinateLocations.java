package Auora.model;

import Auora.model.player.Player;

/**
 * @author Jonathan spawnscape
 */

public class CoordinateLocations {

    public static boolean in_pvp(Player p, int absX, int absY) {
        return absX >= 2449 && absX <= 2478 && absY >= 3068 && absY <= 3140;
    }

    public static boolean in_multi(Player p, int absX, int absY) {
        return false;
    }

    public static boolean in_safe_pvp(Player p, int absX, int absY) {
        return false;
    }

    public static int getWildernessLevel(Player p, int absX, int absY) {
        if (absY > 3520 && absY < 4000) {
            return (((int) (Math.ceil((absY) - 3520D) / 8D) + 1));
        }
        return 0;
    }

    public boolean in_wilderness(Player p, int absX, int absY) {
        return getWildernessLevel(p, absX, absY) > 0;
    }
}
