package Auora.model.player;

import java.io.Serializable;

    /*
    * OpenCB
    * By Jonathan Sirens
     */

public class OpenCB implements Serializable {

    public static void open(Player p) {
        p.getFrames().sendInterface(496);
        if (p.page == 0) {
            p.getFrames().sendString("<col=01DFD7><shad=01DFD7>Auora Teleports | Commands", 496, 3);
            p.getFrames().sendString("<col=00ff00><shad=00ff00>Home (Grand Exchange) |" + "<col=000000> ::Home", 496, 4);
            p.getFrames().sendString("<col=00ff00><shad=00ff00>Duel Arena (Lobby) |" + "<col=000000> ::Duel", 496, 5);
            p.getFrames().sendString("<col=00ff00><shad=00ff00>DiceZone (Gambling) |" + "<col=000000> ::Dicezone", 496, 6);
            p.getFrames().sendString("<col=00ff00><shad=00ff00>Chillzone (Lobby) |" + "<col=000000> ::Chillzone", 496, 7);
            p.getFrames().sendString("<col=00ff00><shad=00ff00>Mage Bank (Lobby) |" + "<col=000000> ::Mb", 496, 8);
            p.getFrames().sendString("<col=00ffff><shad=00ffff>Mage Arena <col=ff0000>(PvP) |" + "<col=000000> ::Arena", 496, 9);
            p.getFrames().sendString("<col=00ffff><shad=00ffff>Ice Plateau <col=ff0000>(PvP) |" + "<col=000000> ::Home", 496, 10);
            p.getFrames().sendString("<col=00ffff><shad=00ffff>Falador <col=ff0000>(PvP) |" + "<col=000000> ::Pvp", 496, 11);
            p.getFrames().sendString("<col=00ffff><shad=00ffff>Shilo Village <col=ff0000>(PvP) |" + "<col=000000> ::Shilo", 496, 12);
            p.getFrames().sendString("", 496, 13);
//fk u
        } else if (p.page == 1) {
            p.getFrames().sendString("<col=ffffff><shad=ff0000>Teleports", 496, 3);
            p.getFrames().sendString("<col=33ff00>DiceZone", 496, 4);
            p.getFrames().sendString("<col=33ff00>Duel Arena", 496, 5);
            p.getFrames().sendString("<col=33ff00>Market", 496, 6);
            p.getFrames().sendString("<col=33ff00>Canifis Skilling", 496, 7);
            p.getFrames().sendString("<col=33ff00>Donator Area", 496, 8);
            p.getFrames().sendString("<col=AD6B77>Auora", 496, 9);
            p.getFrames().sendString("", 496, 10);
            p.getFrames().sendString("", 496, 10);
            p.getFrames().sendString("", 496, 11);
            p.getFrames().sendString("", 496, 12);
            p.getFrames().sendString("<col=ffffff><shad=ff0000>Back", 496, 13);
        }
    }

    public static void close(Player p) {
        p.getFrames().sendClickableInterface(778);
    }

}
