/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;

public class Reports extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"What would you like to Report?", "Report Player", "Report A Bug"};
        this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (this.stage) {
            case 1:
                player.getFrames().sendChatMessage(0, "Scenario 1");
                this.finish();
                break;
            case 2:
                player.getFrames().sendChatMessage(0, "Scenario 2");
                this.finish();
                break;
            default:
                this.finish();
                break;
        }
    }
}