package Auora.scripts.items;

import Auora.model.World;
import Auora.model.player.Player;
import Auora.rscache.ItemDefinitions;
import Auora.scripts.itemScript;
import Auora.util.Misc;


public class i6183 extends itemScript {

    public static int max_random = 8;

    public static int[] SUPER_RARE_ITEMS = {12618, 12620, 17161, 17163, 17165, 17167, 17169, 17171};

    public static int[] RARE_ITEMS = {1050};

    public static int random_item(int collection) {
        if (collection == 0) {
            return RARE_ITEMS[(int) (Math.random() * RARE_ITEMS.length)];
        } else if (collection == 1) {
            return SUPER_RARE_ITEMS[(int) (Math.random() * SUPER_RARE_ITEMS.length)];
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
        }
        int item_id = random_item(collection);
        String name = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
        p.getInventory().deleteItem(6183, 1);
        p.getInventory().addItem(item_id, 1);
        String item_name = ItemDefinitions.forID(item_id).name;
        if (random_chance == max_random - 1) {
            for (Player global_players : World.getPlayers()) {
                if (global_players == null)
                    continue;
                global_players.getFrames().sendMessage("<col=ff0000><shad=FFCE0F><img=2>[" + Misc.formatPlayerNameForDisplay(p.getUsername()) + "] has received 1x [" + item_name + "] From Random Santa Box!");
            }
        }
        p.getFrames().sendChatMessage(0, "You open the mysterious box and receive... [1x " + item_name + "]!");
    }
}