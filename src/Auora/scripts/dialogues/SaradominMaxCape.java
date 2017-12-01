/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;

public class SaradominMaxCape extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"What would you like to do?", "Combine Saradomin cape into Max cape", "No, don't combine these items"};
        this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
                if (player.getInventory().contains(10663, 1) && player.getInventory().contains(2412, 1) && player.getInventory().contains(10669, 1)) {
                    player.getInventory().deleteItem(10663, 1);
                    player.getInventory().deleteItem(2412, 1);
                    player.getInventory().deleteItem(10669, 1);
                    player.getInventory().addItem(10672, 1);
                    player.getInventory().addItem(10666, 1);
                    player.sendMessage("<col=00288D>You have combined your Max cape & Saradomin cape into a Saradomin max cape.");
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