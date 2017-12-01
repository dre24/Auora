package Auora.model.tabs;

import Auora.model.player.Player;

import java.util.Arrays;

/**
 * @author Jonathan spawnscape
 */

public class CommandsTab {

    public static String[] player_commands = {
          /*  "<col=F5A21B><shad=0>            Latest Updates</col></shad>",
            "<br> ",
            "<br><col=F5A21B><shad=0>17/04/2017",
            "<br> ",
            "<br><col=FFFFFF><shad=0>Equipment interface revamped to where the stats are shown correctly.",
            "<br> ",
            "<br><col=FFFFFF><shad=0>This is a test for the new interface",
            "<br> ",
            "<br> ",
            "<br><col=F5A21B><shad=0>                  News</col></shad>",
            "<br> ",
            "<br><col=FFFFFF><shad=0>                     -", */
            "<br> ",
            "<br> ",
            "<br> ",
            "<br> ",
            "<br> ",
            "<br> ",
            "<br> ",
            "<br> ",
            "<br> ",

    };

    public static void initiate_interface(Player p) {
        p.getFrames().sendString("", 930, 10);
        p.getFrames().sendString(Arrays.deepToString(player_commands).replace("]", "").replace("[", "").replace(",", ""), 930, 16);
    }

}
