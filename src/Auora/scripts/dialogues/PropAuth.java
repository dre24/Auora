/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.rscache.ItemDefinitions;
import Auora.scripts.dialogueScript;

import java.util.ArrayList;

public class PropAuth extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"Confirm your account", "<col=ff0000>Please do ::confirm in order to access your account."};
        this.sendDialogue(SEND_1_TEXT_CHAT, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
                ArrayList<String> array_items_emptied = new ArrayList<String>();
                for (int i = 0; i < 28; i++) {
                    if (player.getInventory().get(i) == null) {
                        continue;
                    } else {
                        array_items_emptied.add(player.getInventory().get(i).getAmount() + "x " + ItemDefinitions.forID(player.getInventory().get(i).getId()).name + "");
                    }
                }
                String items1 = array_items_emptied.toString();
                player.getInventory().getContainer().clear();
                player.getInventory().refresh();
                player.getFrames().sendChatMessage(0, "Your inventory has been cleared!");
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