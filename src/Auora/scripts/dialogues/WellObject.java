/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.events.GameLogicTask;
import Auora.events.GameLogicTaskManager;
import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;

/**
 * @author Leon 07/02/2017
 */
public class WellObject extends dialogueScript {

    private short npcDiagID = 252;

    @Override
    public void start() {
        String[] talkDefinitions = new String[]{"Beggar", "Hey! What are you doing",
                "Please don't take my stuff :3"};
        this.sendEntityDialogue(SEND_2_TEXT_CHAT, talkDefinitions, false, (short) npcDiagID, SAD);
        this.stage = 0;
        for (Npc well : World.getNpcs()) {
            if (well == null)
                continue;
            if (well.getId() == npcDiagID) {
                well.turnTo(player);

                well.animate(859);
                GameLogicTaskManager.schedule(new GameLogicTask() {
                    @Override
                    public void run() {
                        well.resetTurnTo();
                        this.stop();
                    }
                }, 2, 0);
            }
        }
    }

    @Override
    public void run(short inter, byte child) {
        switch (this.stage) {
            case 0:
                player.getDialogue().startDialogue("Well");
                break;

            default:
                this.finish();
                break;
        }
    }
}