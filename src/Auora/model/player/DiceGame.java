package Auora.model.player;

import Auora.model.player.logs.Logs;
import Auora.util.Misc;

/**
 * Handles SpawnScape Dicing
 *
 * @Author Jonathan spawnscape
 **/

public class DiceGame {

    public static void rollDice(Player p) {
        int numberRolled = Misc.random(0, 101);       
        if (p.lastDice + 1000 > System.currentTimeMillis()) {
            p.getFrames().sendChatMessage(0, "<col=ff0000>You must wait 1 second between each dice roll.");
            return;
        }

       /* int x = p.getLocation().getX();
        int y = p.getLocation().getY();
        int maxX = 3387;
        int maxY = 9665;
        int minX = 3335;
        int minY = 9615;
        if (!(x >= minX && x <= maxX && y >= minY && y <= maxY)){
        	p.sendMessage("You can't use the dice bag here, please go to ::dicezone!");
        	return;
        }*/
        Logs.log(p, "dicing",
                new String[]{
                        "Rolled: " + numberRolled,
                });

        p.getMask().setLastChatMessage(new ChatMessage(0, 0, "[DICE ROLL] " + Misc.formatPlayerNameForDisplay(p.getUsername()) + " rolled " + numberRolled + " on the dice!"));
        p.getFrames().sendChatMessage(0, "<col=FF0000>You</col> rolled <col=FF00FF>" + numberRolled + "</col> on the dice");

        p.animate(11900);
        p.graphics(2075);
        p.getMask().setChatUpdate(true);
        p.lastDice = System.currentTimeMillis();
    }
}
