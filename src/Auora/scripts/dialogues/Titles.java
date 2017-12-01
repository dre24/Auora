package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.model.player.DonatorRights;
import Auora.scripts.dialogueScript;

public class Titles extends dialogueScript {

    private short npcclientid = 300;

    private short getEntityId() {
        for (Npc n : World.getNpcs()) {
            if (n.getId() == npcclientid) {
                return (short) n.getIndex();
            }
        }
        return -1;
    }

    @Override
    public void start() {
        String[] talkDefinitions = null;
        if (player.getDonatorRights() == DonatorRights.LEGENDARY) {
            talkDefinitions = new String[]{"What would you like to do?", "Change Title (Free)", "Change Color (Free)", "Change Shade (Free)"};
        }
        this.sendDialogue(SEND_3_LARGE_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 2:
               /* if(!player.getInventory().contains(995, player.getDonatorRights().getTitlePrice()) && player.getDonatorRights() != DonatorRights.GODLIKE) {
                    player.sendMessage("You need to have "+Misc.formatAmount(player.getDonatorRights().getTitlePrice())+" coins to set a title.");
                    this.finish();
                    return;
                }*/
                player.getFrames().requestStringInput(10000, "Enter a new name for your title:");
                this.finish();
                //player.getInventory().deleteItem(19670, 1);
                break;
            case 3:
               /* if(!player.getInventory().contains(995, player.getDonatorRights().getTitlePrice() / 2) && player.getDonatorRights() != DonatorRights.GODLIKE) {
                    player.sendMessage("You need to have "+Misc.formatAmount(player.getDonatorRights().getTitlePrice() / 2)+" coins to set a color on your title.");
                    this.finish();
                    return;
                }*/
                player.getFrames().requestStringInput(10001, "Enter a new color for your title (hexadecimal):");
                this.finish();
                // player.getInventory().deleteItem(19670, 1);
                break;
            case 4:
               /* if(!player.getInventory().contains(995, player.getDonatorRights().getTitlePrice() / 2) && player.getDonatorRights() != DonatorRights.GODLIKE) {
                    player.sendMessage("You need to have "+Misc.formatAmount(player.getDonatorRights().getTitlePrice() / 2)+" coins to set a shade on your title.");
                    this.finish();
                    return;
                }*/
                player.getFrames().requestStringInput(10002, "Enter a new shade for your title (hexadecimal):");
                this.finish();
                //player.getInventory().deleteItem(19670, 1);
                break;
            default:
                this.finish();
                break;
        }
    }
}