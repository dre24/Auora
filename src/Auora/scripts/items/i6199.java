package Auora.scripts.items;

import Auora.model.World;
import Auora.model.player.Player;
import Auora.rscache.ItemDefinitions;
import Auora.scripts.itemScript;
import Auora.util.Misc;


public class i6199 extends itemScript {

    public static int max_random = 125;

    public static int[] SUPER_RARE_ITEMS = {1050, 1053, 1055, 1057, 4083, 1419, 1038, 1040, 1042, 1044, 1046, 1048};
    public static int[] UNCOMMON_ITEMS = {6570, 11696, 11724, 11726, 11700, 10551, 17273, 14484, 13263};
    public static int[] RARE_ITEMS = {4708, 14882, 4714, 4716, 4720, 4722, 4736, 14883, 14884, 14885, 4753, 4759, 4151, 11732, 15486, 6889, 2577, 6920, 6585, 6737, 6733, 14880, 14881, 4712, 4738, 4751, 4749};

    public static int random_item(int collection) {
        if (collection == 0) {
            return RARE_ITEMS[(int) (Math.random() * RARE_ITEMS.length)];
        } else if (collection == 1) {
            return SUPER_RARE_ITEMS[(int) (Math.random() * SUPER_RARE_ITEMS.length)];
        } else if (collection == 2) {
            return UNCOMMON_ITEMS[(int) (Math.random() * UNCOMMON_ITEMS.length)];
        }
        return 0;
    }

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p.getInventory().getContainer().get(slot) == null)
            return;
        if (p.getInventory().getContainer().get(slot).getId() != itemId)
            return;
        if (interfaceId != 149)
            return;
        int collection = 0;
        int random_chance = Misc.random(1, max_random);
        if (random_chance == max_random - 1) {
            collection = 1;
        } else if (random_chance > 115) {
            collection = 2;
        }
        int item_id = random_item(collection);
        String name = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
        p.getInventory().deleteItem(6199, 1);
        p.getInventory().addItem(item_id, 1);
        String item_name = ItemDefinitions.forID(item_id).name;
        if (random_chance == max_random - 1) {
            for (Player global_players : World.getPlayers()) {
                if (global_players == null)
                    continue;
                global_players.getFrames().sendMessage("<col=ff0000>" + Misc.formatPlayerNameForDisplay(p.getUsername()) + " has received 1x [" + item_name + "] From A Mystery Box!");
            }
        }
        p.getFrames().sendChatMessage(0, "You open the mysterious box and receive... [1x " + item_name + "]!");
    }
}