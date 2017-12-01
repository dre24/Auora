/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;

public class FireMaxCape extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"What would you like to do?", "Combine Fire cape into Max cape", "No, don't combine these items"};
        this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
                if (player.getInventory().contains(10663, 1) && player.getInventory().contains(6570, 1) && player.getInventory().contains(10669, 1)) {
                    player.getInventory().deleteItem(10663, 1);
                    player.getInventory().deleteItem(6570, 1);
                    player.getInventory().deleteItem(10669, 1);
                    player.getInventory().addItem(10670, 1);
                    player.getInventory().addItem(10664, 1);
                    player.sendMessage("<col=FF9100>You have combined your Max cape & Fire cape into a Fire max cape.");
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