package Auora.net.packethandlers;

import Auora.io.InStream;
import Auora.model.player.CrystalChest;
import Auora.model.player.Player;


/**
 * @developer Charlie Salter <charliesalter@hotmail.com>
 */
public class ItemOnItemPacketHandler {
    //can you find item on object handler? errr idk wtf that is LOL like when using item on object or just want when click an crystal chest?  packets.java
    public static void handleItemOnItem(Player player, InStream packet, int itemUsed, int usedWith) {
        packet.readInt();// i need item on object
        int fromSlot = packet.readShortA();
        int toSlot = packet.readLEShortA();
        packet.readInt();
        Player p = player;
        if (!player.getInventory().contains(itemUsed))
            return;
        if (!player.getInventory().contains(usedWith))
            return;
        //looking for chest id..?
        // PotionDecanting.decantPotion(p, itemUsed, usedWith);

        if (itemUsed == 985 && usedWith == 987)
            CrystalChest.tryCombine(player);
        if (itemUsed == 987 && usedWith == 985)
            CrystalChest.tryCombine(player);
        if (itemUsed == 10663 || usedWith == 10663) {
            if (itemUsed == 6570 || usedWith == 6570) {
                if (!p.getInventory().contains(10669, 1)) {
                    p.sendMessage("You must have the Max cape & Max hood in your inventory to do this.");
                    return;
                }
                p.getDialogue().startDialogue("FireMaxCape");
            }
        }
        if (itemUsed == 10663 || usedWith == 10663) {
            if (itemUsed == 2412 || usedWith == 2412) {
                if (!p.getInventory().contains(10669, 1)) {
                    p.sendMessage("You must have the Max cape & Max hood in your inventory to do this.");
                    return;
                }
                p.getDialogue().startDialogue("SaradominMaxCape");
            }
        }
        if (itemUsed == 10663 || usedWith == 10663) {
            if (itemUsed == 10499 || usedWith == 10499) {
                if (!p.getInventory().contains(10669, 1)) {
                    p.sendMessage("You must have the Max cape & Max hood in your inventory to do this.");
                    return;
                }
                p.getDialogue().startDialogue("AvaMaxCape");
            }
        }

        if (itemUsed == 13263 && usedWith == 15488) {
            if (!p.getInventory().contains(15490, 1)) {
                p.sendMessage("You must have a Focus Sight in your inventory to assemble this.");
                return;
            } else {
                p.getInventory().addItem(15492, 1);
                p.getInventory().deleteItem(13263, 1);
                p.getInventory().deleteItem(15488, 1);
                p.getInventory().deleteItem(15490, 1);
            }

        }
        if (itemUsed == 13263 && usedWith == 15490) {

            if (!p.getInventory().contains(15488, 1)) {
                p.sendMessage("You must have a Hexcrest in your inventory to assemble this.");
                return;
            } else {
                p.getInventory().addItem(15492, 1);
                p.getInventory().deleteItem(13263, 1);
                p.getInventory().deleteItem(15488, 1);
                p.getInventory().deleteItem(15490, 1);
            }

        }


        if (itemUsed == 10663 || usedWith == 10663) {
            if (itemUsed == 2413 || usedWith == 2413) {
                if (!p.getInventory().contains(10669, 1)) {
                    p.sendMessage("You must have the Max cape & Max hood in your inventory to do this.");
                    return;
                }
                p.getDialogue().startDialogue("GuthixMaxCape");
            }
        }
        if (itemUsed == 10663 || usedWith == 10663) {
            if (itemUsed == 2414 || usedWith == 2414) {
                if (!p.getInventory().contains(10669, 1)) {
                    p.sendMessage("You must have the Max cape & Max hood in your inventory to do this.");
                    return;
                }
                p.getDialogue().startDialogue("ZamorakMaxCape");
            }
        }
        if (itemUsed == 3188 || usedWith == 3188) {
            if (itemUsed == 10664 || usedWith == 10664) {
                p.dialogueCustomValue = 10664;
                p.getDialogue().startDialogue("CleanItem");
            } else if (itemUsed == 10665 || usedWith == 10665) {
                p.dialogueCustomValue = 10665;
                p.getDialogue().startDialogue("CleanItem");
            } else if (itemUsed == 10666 || usedWith == 10666) {
                p.dialogueCustomValue = 10666;
                p.getDialogue().startDialogue("CleanItem");
            } else if (itemUsed == 10667 || usedWith == 10667) {
                p.dialogueCustomValue = 10667;
                p.getDialogue().startDialogue("CleanItem");
            } else if (itemUsed == 10668 || usedWith == 10668) {
                p.dialogueCustomValue = 10668;
                p.getDialogue().startDialogue("CleanItem");
            } else if (itemUsed == 10670 || usedWith == 10670) {
                p.dialogueCustomValue = 10670;
                p.getDialogue().startDialogue("CleanItem");
            } else if (itemUsed == 10671 || usedWith == 10671) {
                p.dialogueCustomValue = 10671;
                p.getDialogue().startDialogue("CleanItem");
            } else if (itemUsed == 10672 || usedWith == 10672) {
                p.dialogueCustomValue = 10672;
                p.getDialogue().startDialogue("CleanItem");
            } else if (itemUsed == 10673 || usedWith == 10673) {
                p.dialogueCustomValue = 10673;
                p.getDialogue().startDialogue("CleanItem");
            } else if (itemUsed == 10674 || usedWith == 10674) {
                p.dialogueCustomValue = 10674;
                p.getDialogue().startDialogue("CleanItem");
            } else {
                p.sendMessage("This item is already sparkling clean.");
            }
        }
    }
}
