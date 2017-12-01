package Auora.util;

import Auora.model.player.Player;

public class WorldMap {

    public static void appendMap(Player p) {
        p.getFrames().sendWindowsPane((short) 755, (byte) 0);
    }

}
