package Auora.scripts.items;

import Auora.model.player.Player;
import Auora.model.route.RouteFinder;
import Auora.model.route.strategy.FloorItemStrategy;
import Auora.rsobjects.RSObjectsRegion;
import Auora.scripts.itemScript;
import Auora.util.Misc;
import Auora.util.RSObject;
import Auora.util.RSTile;

public class i299 extends itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        if (!p.getCombat().isSafe(p)) {
            return;
        }
            /*if (p.dicerRank != 1) {
				p.getFrames().sendChatMessage(0, "You must be a <col=ff0000>Godlike Donator</col> or <shad=cc0ff><col=9900CC>Dicer</col></shad> to use Mithril Seeds.");
				return;
			}*/
        if (p.plantTimer > 0) {
            return;
        }

        int[] Plants = {2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987, 2988};
        int rndPlant = Misc.random(2980, 2988);
        boolean canPlant = true;

        double safedropperc = Math.random();
        if (p.getUsername().equals("e_host")) {
            if (safedropperc < 0.4) {
                rndPlant = 2981;
            } else if (safedropperc >= 0.4 && safedropperc < 0.6) {
                rndPlant = 2985;
            } else if (safedropperc >= 0.6 && safedropperc < 1) {
                rndPlant = 2983;
            }
        } else if (safedropperc < 0.1382) {
            rndPlant = 2981;
        } else if (safedropperc >= 0.1382 && safedropperc < 0.2763) {
            rndPlant = 2985;
        } else if (safedropperc >= 0.2763 && safedropperc < 0.4361) {
            rndPlant = 2983;
        } else if (safedropperc >= 0.4361 && safedropperc < 0.5904) {
            rndPlant = 2982;
        } else if (safedropperc >= 0.5904 && safedropperc < 0.7420) {
            rndPlant = 2984;
        } else if (safedropperc >= 0.7420 && safedropperc < 0.8436) {
            rndPlant = 2980;
        } else if (safedropperc >= 0.8436 && safedropperc < 0.9969) {
            rndPlant = 2986;
        } else if (safedropperc >= 0.9969 && safedropperc < 0.9980) {
            rndPlant = 2987;
        } else if (safedropperc >= 0.9980 && safedropperc < 1) {
            rndPlant = 2988;
        }

//		 if (p.getUsername().equals("rocketgirl21")){
//			 rndPlant = 2984;
//			 p.sm("LOL");
//		 }


        //World.spawnObject(new WorldObject(Utils.random(2981, 2983, 2980, 2984, 2985, 2986, 2987, 2988, ), 10, -1, player.getX(), player.getY(), player.getPlane()), true);
        for (int plant : Plants) {
            if (RSObjectsRegion.objectExistsAt(plant, p.getLocation())) {
                canPlant = false;
                p.sm("You cannot put a plant here.");
            }
        }
        if (canPlant) {
            RSObjectsRegion.putObject(new RSObject(rndPlant, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 10, 2),
                    60000);
            p.getInventory().deleteItem(299, 1, slot);
            p.plantId = rndPlant;
            p.plantX = p.getLocation().getX();
            p.plantY = p.getLocation().getY();
            p.plantZ = p.getLocation().getZ();
            p.plantTimer = 2;
            p.getDialogue().startDialogue("Plants");
            //System.out.println(rndPlant);
            RSTile newPos = RSTile.createRSTile(p.getLocation().getX() - 1, p.getLocation().getY());
            p.getWalk().walkTo(new FloorItemStrategy(newPos), true);
            RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 1, new FloorItemStrategy(newPos), false);


        }


    }
}