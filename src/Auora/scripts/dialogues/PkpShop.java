/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;

public class PkpShop extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"What shop do you want to open?", "Open Armour Pkp Shop", "Open Weapons/Misc Pkp Shop"};
        this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
                World.getShopManager().openShop(player, 11);
                this.finish();
                break;
            case 2:
                World.getShopManager().openShop(player, 1);
                this.finish();
                break;
            default:
                this.finish();
                break;
        }
    }
}