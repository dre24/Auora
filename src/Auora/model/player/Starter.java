package Auora.model.player;

import Auora.model.Item;
import Auora.model.World;

import java.io.File;
import java.io.IOException;

/**
 * This file manages SpawnScape's starter setup, it also includes catching data from a .txt file.
 *
 * @author Jonathan spawnscape
 */
public final class Starter {

    /**
     * The general starter folder directory.
     */
    public static final String MAIN_DIRECTORY = "./data/starters";
    /**
     * Leads to directory where starter mac address files are stored.
     */
    public static final String STARTER_DIRECTORY_1 = MAIN_DIRECTORY + "/1/";
    public static final String STARTER_DIRECTORY_2 = MAIN_DIRECTORY + "/2/";
    public static final String STARTER_DIRECTORY_3 = MAIN_DIRECTORY + "/3/";

    public static int starter_amounts(String mac) {
        int starters = 0;
        if (new File(STARTER_DIRECTORY_1 + mac).exists()) {
            starters += 1;
        }
        if (new File(STARTER_DIRECTORY_2 + mac).exists()) {
            starters += 1;
        }
        if (new File(STARTER_DIRECTORY_3 + mac).exists()) {
            starters += 1;
        }
        return starters;
    }

    public static void add_starter(String mac) {
        try {
            if (starter_amounts(mac) == 0) {
                new File(STARTER_DIRECTORY_1 + mac).createNewFile();
            } else if (starter_amounts(mac) == 1) {
                new File(STARTER_DIRECTORY_2 + mac).createNewFile();
            } else if (starter_amounts(mac) == 2) {
                new File(STARTER_DIRECTORY_3 + mac).createNewFile();
            }
        } catch (IOException e) {

        }
    }

    public static void initiate_starter(Player p) {
        boolean maximum_starters_exceeded = false;
        int money_amount = 30000000;
        boolean starter_message = true;
        if (starter_amounts(p.getSerialAddress()) == 3) {
            // p.getFrames().sendMessage("<col=ff0000>You have used all of your starters on your computer.");
            maximum_starters_exceeded = true;
            starter_message = false;
            money_amount = 2000000;
        }
        add_starter(p.getSerialAddress());
        if (starter_message) {
            // p.getFrames().sendMessage("You have used <col=ffff00><shad=ffffff>" + starter_amounts(p.getMacAddress()) + "</col></shad> out of <col=ffff00><shad=ffffff>3</col></shad> starters on your computer.");
        }
        //p.getFrames().sendMessage("<col=0000ff><shad=ffffff>Make sure you check your bank for extra items!");
        // p.getFrames().sendMessage("<col=0000ff><shad=ffffff>You received 75pkp to spend in the pkp shop!");
        // p.getDialogue().startDialogue("StarterGuide");
        if (maximum_starters_exceeded) {
           /* p.getEquipment().set(1, new Item(1052));//Legends Cape
            p.getEquipment().set(10, new Item(3105)); // Climbing Boots
            p.getEquipment().set(0, new Item(10828)); // helm
            p.getEquipment().set(2, new Item(1725)); // fury
            p.getEquipment().set(4, new Item(1127));// dh body
            p.getEquipment().set(12, new Item(6735)); // Ring
            p.getEquipment().set(7, new Item(1079)); // legs
            p.getEquipment().set(9, new Item(7462)); // gloves
            p.getEquipment().set(3, new Item(15441)); // Whip
            p.getEquipment().set(5, new Item(8850)); // Defender*/
            /* p.getEquipment().set(1, new Item(1052));//Legends Cape
             p.getEquipment().set(10, new Item(11732)); // Climbing Boots
             p.getEquipment().set(0, new Item(4716)); // helm
             p.getEquipment().set(2, new Item(1725)); // fury
             p.getEquipment().set(4, new Item(4720));// dh body
             p.getEquipment().set(12, new Item(6737)); // Ring
             p.getEquipment().set(7, new Item(4722)); // legs
             p.getEquipment().set(9, new Item(7462)); // gloves
             p.getEquipment().set(3, new Item(4151)); // Whip
             p.getEquipment().set(5, new Item(8850)); // Defender
             p.getInventory().addItem(4718, 1);*/
        } else {
            p.getEquipment().set(1, new Item(1052));//Legends Cape
            p.getEquipment().set(10, new Item(11732)); // Climbing Boots
            p.getEquipment().set(0, new Item(4716)); // helm
            p.getEquipment().set(2, new Item(1725)); // fury
            p.getEquipment().set(4, new Item(4720));// dh body
            p.getEquipment().set(12, new Item(6737)); // Ring
            p.getEquipment().set(7, new Item(4722)); // legs
            p.getEquipment().set(9, new Item(7462)); // gloves
            p.getEquipment().set(3, new Item(4151)); // Whip
            p.getEquipment().set(5, new Item(8850)); // Defender
            p.getInventory().addItem(4718, 1);

        }

        //First Bank Tab
        p.getBank().addItem(995, money_amount, 0);
        if (!maximum_starters_exceeded) {

            p.getBank().addItem(995, 5000000, 0);

        }

        //Second Bank Tab
        p.getBank().addItem(2440, 10000, 0);
        p.getBank().addItem(2436, 10000, 0);
        p.getBank().addItem(2442, 10000, 0);
        p.getBank().addItem(2444, 10000, 0);
        p.getBank().addItem(3040, 10000, 0);
        p.getBank().addItem(3040, 10000, 0);
        p.getBank().addItem(3024, 10000, 0);
        p.getBank().addItem(6685, 10000, 0);
        p.getBank().addItem(3144, 10000, 0);
        p.getBank().addItem(15272, 100000, 0);
        p.getBank().addItem(385, 100000, 0);

        p.getBank().addItem(391, 100000, 0);
        p.getBank().addItem(373, 100000, 0);
        p.getBank().addItem(379, 100000, 0);

        p.getBank().addItem(563, 100000, 0);
        p.getBank().addItem(561, 100000, 0);
        p.getBank().addItem(555, 100000, 0);
        p.getBank().addItem(565, 100000, 0);
        p.getBank().addItem(9075, 100000, 0);
        p.getBank().addItem(566, 100000, 0);
        p.getBank().addItem(557, 100000, 0);

        p.getBank().addItem(3144, 100000, 0);
        p.getBank().addItem(4417, 100000, 0);
        p.getBank().addItem(2301, 100000, 0);


        // tweede tab

        p.getBank().addItem(4675, 10000, 2);
        p.getBank().addItem(10828, 10000, 2);
        p.getBank().addItem(3755, 10000, 2);
        p.getBank().addItem(6109, 10000, 2);
        p.getBank().addItem(3749, 10000, 2);
        p.getBank().addItem(3751, 10000, 2);
        p.getBank().addItem(7462, 10000, 2);
        p.getBank().addItem(3842, 10000, 2);
        p.getBank().addItem(9185, 10000, 2);
        p.getBank().addItem(9244, 1000000, 2);

        p.getBank().addItem(2413, 10000, 2);
        p.getBank().addItem(7399, 10000, 2);
        p.getBank().addItem(4091, 10000, 2);
        p.getBank().addItem(6107, 10000, 2);
        p.getBank().addItem(2503, 10000, 2);
        p.getBank().addItem(1127, 10000, 2);
        p.getBank().addItem(2491, 10000, 2);
        p.getBank().addItem(10499, 10000, 2);
        p.getBank().addItem(861, 10000, 2);
        p.getBank().addItem(892, 1000000, 2);

        p.getBank().addItem(2414, 10000, 2);
        p.getBank().addItem(7398, 10000, 2);
        p.getBank().addItem(4093, 10000, 2);
        p.getBank().addItem(6108, 10000, 2);
        p.getBank().addItem(2497, 10000, 2);
        p.getBank().addItem(1079, 10000, 2);
        p.getBank().addItem(2550, 10000, 2);
        p.getBank().addItem(1704, 10000, 2);
        p.getBank().addItem(5698, 10000, 2);
        p.getBank().addItem(11212, 1000000, 2);

        p.getBank().addItem(2412, 10000, 2);
        p.getBank().addItem(3105, 10000, 2);
        p.getBank().addItem(4097, 10000, 2);
        p.getBank().addItem(13734, 10000, 2);
        p.getBank().addItem(6328, 10000, 2);
        p.getBank().addItem(4131, 10000, 2);
        p.getBank().addItem(8850, 10000, 2);
        p.getBank().addItem(1434, 10000, 2);
        p.getBank().addItem(1305, 10000, 2);
        p.getBank().addItem(4587, 10000, 2);

        

        /*p.animate(4410);
        p.graphics2(726);
        p.getSkills().addXp(6, 112);
        p.getCombat().vengeance = true;
        p.getCombat().vengDelay = 60;*/

        p.getPrayer().switchPrayBook(Boolean.parseBoolean(String.valueOf(true)));
        p.getFrames().sendInterface(1, 548, 205, 430);
        p.getFrames().sendInterface(1, 746, 93, 430);
        p.spellbook = 2;
        World.clanManager.joinClan(p, "Auora", false);

    }
}