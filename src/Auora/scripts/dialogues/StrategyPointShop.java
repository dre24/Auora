/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class StrategyPointShop extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"Which shop would you like to open?", "Skilling Point Rares Shop", "Skilling Point Pk Shop"};
        this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (this.stage) {
            case 1:
                World.getShopManager().openShop(player, 13);
                this.finish();
                break;
            case 2:
                World.getShopManager().openShop(player, 10);
                this.finish();
                break;
            default:
                this.finish();
                break;
        }
    }
}