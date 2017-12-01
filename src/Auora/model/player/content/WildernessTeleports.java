package Auora.model.player.content;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.player.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WildernessTeleports {

    public static List<Player> playersInObelisk = new CopyOnWriteArrayList<Player>();
    public int ObeliskTimer = 0;

    public static boolean inLobby(Player p) {
        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        return false;
    }

    public static void preTeleport(Player p, final Object object) {
        p.sm("You activate the obelisk and hear a faint rumbling sound.");
        GameLogicTaskManager.schedule(new GameLogicTask() {


            int timer = 0;

            @Override
            public void run() {


                if (timer == 4) {
                    //handleObelisk(object);
                    p.getMask().getRegion().teleport(3156, 3616, 0, 0);
                    stop();
                }
                timer++;
            }
        }, 0, 1);
    }


}