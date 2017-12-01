package Auora.scripts.items;

import Auora.model.World;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.rscache.ItemDefinitions;
import Auora.scripts.itemScript;
import Auora.util.Misc;

public class i10944 extends itemScript {

    /**
     * Rune-Unity Vote Tokens
     *
     * @Author Jonathan spawnscape
     **/

    public static int super_rare_chance = 1150;

    public static String global_color = "2EA73D";

    public static String global_shad = "0";

    public static String website = "www.Auora614.org/vote";

    public static int[] SUPER_RARE_ITEMS = {17173, 17175, 17177, 17179, 17181, 17183, 17185, 17187, 16359, 12610, 12612, 5607, 1419, 4084, 12614, 12616, 17161, 17163, 17165, 17167, 17169, 17171, 1038, 1040, 1042, 1044, 1046, 1048, 1050, 1053, 1055, 1057};

    public static int[] RARE_ITEMS = {9177, 1191, 5680, 15069, 15071, 18786, 6570, 19669, 11694, 14484, 11724, 11726, 13887, 13893, 13899, 13905, 15220, 15017, 11696, 6199, 11698, 11700, 18349, 18351, 18353, 18355, 18357, 13738, 13740, 13742, 13744, 15825, 17273};

    public static int[] COMMON_ITEMS = {4151, 6585, 11732, 15018, 15019, 15020, 6920, 15486, 6889, 11235, 11718, 11720, 11722, 6733, 6735, 6737, 6731, 6914, 1052, 18335, 4716, 4718, 4720, 4722, 4708, 4710, 4712, 4714, 4724, 4726, 4728, 4730, 4745, 4747, 4749, 4751, 4732, 4734, 4736, 4738, 4753, 4755, 4757, 4759, 989};

    public static int random_item(int collection) {
        if (collection == 0) {
            return COMMON_ITEMS[(int) (Math.random() * COMMON_ITEMS.length)];
        } else if (collection == 1) {
            return RARE_ITEMS[(int) (Math.random() * RARE_ITEMS.length)];
        } else if (collection == 2) {
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
        int random_chance = Misc.random(1, super_rare_chance);
        if (random_chance == super_rare_chance - 1) {
            collection = 2;
        } else if (random_chance >= 1 && random_chance <= 12) {
            collection = 1;
        } else {
            collection = 0;
        }
        int item_id = random_item(collection);
        String name = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
        p.getInventory().deleteItem(10944, 1);
        p.getInventory().addItem(item_id, 1);
        p.getInventory().addItem(995, 25000000);
        int yell_chance = Misc.random(1, 10);
        String item_name = ItemDefinitions.forID(item_id).name;
        int points = 1;
        if (p.getDonatorRights().ordinal() == 1) {
            points += 1;
        } else if (p.getDonatorRights().ordinal() == 2) {
            points += 1;
        } else if (p.getDonatorRights().ordinal() == 3) {
            points += 2;

        } else if (p.getDonatorRights() == DonatorRights.YOUTUBER || p.getDonatorRights() == DonatorRights.TRUSTED) {
            points += 2;
        }
        p.votePoints += points;
        p.unlimitedPrayer += 3600;
        if (collection == 2) {
            yell_chance = 1;
        }
        if (yell_chance == 1) {
            for (Player global_players : World.getPlayers()) {
                if (global_players == null)
                    continue;
                global_players.getFrames().sendMessage("" + name + "<col=" + global_color + "><shad=" + global_shad + "> has voted at <col=ffff00><shad=ffffff>" + website + " <col=" + global_color + "><shad=" + global_shad + ">and recieved (<col=ff0000><shad=0>" + item_name + "<col=" + global_color + "><shad=" + global_shad + ">)!");
            }
        }
        p.getFrames().sendMessage("You have recieved (<col=ff0000><shad=0>" + item_name + "</col></shad>), 30 minutes of unlimited prayer,");
        p.getFrames().sendMessage("and <col=ffff00><shad=ffffff>" + points + "</col></shad> vote points.");
    }
}