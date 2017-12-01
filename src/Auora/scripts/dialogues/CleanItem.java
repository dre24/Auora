/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.rscache.ItemDefinitions;
import Auora.scripts.dialogueScript;

public class CleanItem extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"What would you like to do?", "Clean the " + ItemDefinitions.forID(player.dialogueCustomValue).getName() + "", "No, don't clean this item"};
        this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
                if (player.dialogueCustomValue == 10664 || player.dialogueCustomValue == 10665 || player.dialogueCustomValue == 10666 || player.dialogueCustomValue == 10667 || player.dialogueCustomValue == 10668) {
                    if (player.getInventory().contains(player.dialogueCustomValue, 1) && player.getInventory().contains(3188, 1)) {
                        player.getInventory().deleteItem(player.dialogueCustomValue, 1);
                        player.getInventory().addItem(10663, 1);
                        player.sendMessage("<col=161922>You have cleaned the " + ItemDefinitions.forID(player.dialogueCustomValue) + " to its original style.");
                    }
                } else if (player.dialogueCustomValue == 10670 || player.dialogueCustomValue == 10671 || player.dialogueCustomValue == 10672 || player.dialogueCustomValue == 10673 || player.dialogueCustomValue == 10674) {
                    if (player.getInventory().contains(player.dialogueCustomValue, 1) && player.getInventory().contains(3188, 1)) {
                        player.getInventory().deleteItem(player.dialogueCustomValue, 1);
                        player.getInventory().addItem(10669, 1);
                        player.sendMessage("You have cleaned the " + ItemDefinitions.forID(player.dialogueCustomValue).getName() + " to its original style.");
                    }
                }
                this.finish();
                break;
            case 2:
                this.finish();
                break;
            default:
                this.finish();
                break;
        }
    }
}