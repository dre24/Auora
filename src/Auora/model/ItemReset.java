package Auora.model;

import Auora.model.player.Player;

/**
 * Handles SpawnScape Item Resets
 *
 * @Jonny
 **/

public class ItemReset {


    public static int[] item_reset_items_1 = {};   

    public static void initiate_item_reset(Player p) {
        if (p.item_reset_1 == 0) {
            item_reset_1(p);
        }
        
    }

    public static void item_reset_1(Player p) {
        for (int i = 0; i < item_reset_items_1.length; i++) {
            dispose_of_item(p, item_reset_items_1[i]);
        }
        p.item_reset_1 = 1;
    }

   
    public static void dispose_of_item(Player p, int item_id) {
        p.getInventory().deleteItem(item_id, Integer.MAX_VALUE);
        p.getInventory().refresh();
        p.getBank().bank.remove(new Item(item_id, Integer.MAX_VALUE));
        p.getBank().refresh();
        p.getBank().bank.shift();
        p.getEquipment().deleteItem(item_id, Integer.MAX_VALUE);
        p.getEquipment().refresh();
    }

    public static void all_resets_give(Player p) {
    	p.item_reset_1 = 1;
        
    }

}	

