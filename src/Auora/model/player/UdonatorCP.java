package Auora.model.player;

import java.io.Serializable;

    /*
    * Ultra Donator CP
    * By Jet Kai
    * 28/11/2011
     */

public class UdonatorCP implements Serializable {
//Ultra Donator Control Panel - Find a interface.
//Ultra snow - Interface 481.

    public static void Open(Player p) {
        p.getFrames().sendChatMessage(0, "Opening test.");
    }

    public static void Options(Player p) {
        p.getFrames().sendCInterface(458);
        p.getFrames().sendString("Open Console", 458, 1);
        p.getFrames().sendString("Extras", 458, 2);
        p.getFrames().sendString("Exit", 458, 3);
        p.getFrames().sendString("<col=ff0000>Test4[Testing]", 458, 4);
    }

    public static void Extras(Player p) {
//Extras Menu
    }

    public static void Exit(Player p) {
        p.getFrames().CloseCInterface();
    }

}