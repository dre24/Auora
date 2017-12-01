package Auora.net.packethandlers;

import Auora.model.player.Player;

/**
 * Click items
 *
 * @Author Jonny
 */
public class ItemActionPacketListener {

    /**
     * Clicking the item
     *
     * @param player
     * @param itemId
     */
    public static void clickItem(Player player, int itemId) {
        switch (itemId) {
            case 11846:
            case 11848:
            case 11850:
            case 11852:
            case 11854:
            case 11856:
                if (!player.getClickDelay().elapsed(2000) || !player.getInventory().contains(itemId))
                    return;

                int[] items = itemId == 11858 ? new int[]{10350, 10348, 10346, 10352}
                        : itemId == 11860 ? new int[]{10334, 10330, 10332, 10336}
                        : itemId == 11862 ? new int[]{10342, 10338, 10340, 10344}
                        : itemId == 11848 ? new int[]{4716, 4720, 4722, 4718}
                        : itemId == 11856 ? new int[]{4753, 4757, 4759, 4755}
                        : itemId == 11850 ? new int[]{4724, 4728, 4730, 4726}
                        : itemId == 11854 ? new int[]{4745, 4749, 4751, 4747}
                        : itemId == 11852
                        ? new int[]{4732, 4734, 4736, 4738}
                        : itemId == 11846
                        ? new int[]{4708, 4712, 4714,
                        4710}
                        : new int[]{itemId};

                if (player.getInventory().getFreeSlots() < items.length) {
                    player.sendMessage("You do not have enough space in your inventory.");
                    return;
                }
                player.getInventory().deleteItem(itemId, 1);
                for (int i : items) {
                    player.getInventory().addItem(i, 1);
                }
                player.sendMessage("You open the set and find items inside.");
                player.getClickDelay().reset();
                break;
        }
    }
}
