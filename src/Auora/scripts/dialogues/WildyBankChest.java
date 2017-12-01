/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;

public class WildyBankChest extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"What would you like to do?", "Change Name. (Coming Soon)",
                "Change Title. (Donator)", "Change Title. (Kills)"};
        this.sendDialogue(SEND_3_LARGE_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
                player.sm("This feature is coming soon.");
                player.sm("This feature is coming soon.");
                player.sm("This feature is coming soon.");
                //this.finish();
                break;
            case 2:
                player.sm("This feature is coming soon.");
                player.sm("This feature is coming soon.");
                player.sm("This feature is coming soon.");
                //int Spaces = player.getInventory().getFreeSlots();
                //player.getInventory().addItem(15272, Spaces);
                this.finish();
                break;
            case 3:
                //int Spaces = player.getInventory().getFreeSlots();
                //player.getInventory().addItem(15272, Spaces);
                player.getDialogue().startDialogue("Optiontwo");
                //this.finish();
                break;
            case 4:
                //int Spaces = player.getInventory().getFreeSlots();
                //player.getInventory().addItem(15272, Spaces);
                player.getDialogue().startDialogue("Optionone");
                //this.finish();
                break;

            default:
                this.finish();
                break;
        }
    }
}