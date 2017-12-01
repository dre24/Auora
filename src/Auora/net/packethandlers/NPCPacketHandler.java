package Auora.net.packethandlers;

import Auora.io.InStream;
import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.model.player.Player;

/**
 * @developer Jonathan
 */
public class NPCPacketHandler {

    public static void handleOption1(Player player, Npc npc, int id, InStream packet) {
        int index = packet.readLEShort();
        player.getWalk().reset(true);
        player.turnTemporarilyTo(npc.getLocation());
        player.getMask().setApperanceUpdate(true);
        if (player.getUsername().equals("jonny")) {
            player.getFrames().sendMessage("Npc id: " + id + "");
        }
        switch (id) {
            case 252: // tramp well
                player.getDialogue().startDialogue("Well");
                break;

            case 6390: //Pk Shop
                //player.getDialogue().startDialogue("PkpShop");
                World.getShopManager().openShop(player, 1);               
                break;
            case 605: //Pk Shop
                //player.getDialogue().startDialogue("PkpShop");
                World.getShopManager().openShop(player, 16);
                player.sm("<col=ff0000><shad=0>WARNING: You lose 50% of the price selling items to this store!");
                break;
            case 8608: //Pk Shop
                //player.getDialogue().startDialogue("DonatorShop");
                World.getShopManager().openShop(player, 12);
                break;
            case 599:
                player.getFrames().sendInterface(2676);
                break;
            case 2580: //Supplies Shop
                World.getShopManager().openShop(player, 2);
                break;
            case 659: //Vote Shop
                World.getShopManager().openShop(player, 3);
                break;
            case 400: //Strategy Shop
                World.getShopManager().openShop(player, 4);
                break;
            case 534:
                player.getDialogue().startDialogue("WildyBankChest");
                break;
            case 2253: //Skillcape Shop
                player.getDialogue().startDialogue("SkillcapeShop");
                break;
            case 520: //Dungeoneering shop
                World.getShopManager().openShop(player, 5);
                break;
            case 581: //Melee Shop
                World.getShopManager().openShop(player, 6);
                break;
            case 583: //Magic Shop
                World.getShopManager().openShop(player, 7);
                break;
            case 8725: //Range Shop
                World.getShopManager().openShop(player, 8);
                break;

            case 494:
                player.getBank().openBank();
                break;
            case 2999: //gambler
                player.getDialogue().startDialogue("n2998");
                break;
        }

    }

    public static void handleOption2(Player p, Npc npc, int id, InStream packet) {
        Player player = p;
        int index = packet.readLEShort();
        player.getWalk().reset(true);
        player.turnTemporarilyTo(npc.getLocation());
        player.getMask().setApperanceUpdate(true);
        switch (id) {
            case 2253: //Skillcape Shop
                World.getShopManager().openShop(player, 9);
                break;
            case 520: //Dungeoneering shop
                World.getShopManager().openShop(player, 5);
                break;
            case 6390: //Pk Shop
                player.getDialogue().startDialogue("PkpShop");
                break;
            case 2538: //Supplies Shop
                World.getShopManager().openShop(player, 2);
                break;
            case 659: //Vote Shop
                World.getShopManager().openShop(player, 3);
                break;
            case 400: //Strategy Shop
                World.getShopManager().openShop(player, 4);
                break;
            case 541: //Melee Shop
                World.getShopManager().openShop(player, 6);
                break;
            case 546: //Magic Shop
                World.getShopManager().openShop(player, 7);
                break;
            case 550: //Range Shop
                World.getShopManager().openShop(player, 8);
                break;
            case 494:
                player.getBank().openBank();
                break;
            case 599:
                player.getFrames().sendInterface(900);
                break;
            default:
                player.getFrames().sendChatMessage(0, "Unhandled NPC: " + id);
        }

    }

}
