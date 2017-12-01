/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.rsobjects.RSObjectsRegion;
import Auora.scripts.dialogueScript;
import Auora.util.RSObject;
import Auora.util.RSTile;

public class Plants extends dialogueScript {


    @Override
    public void start() {
        String[] talkDefinitions = new String[]{"What would you like to do?", "Take Plant", "Leave Plant"};
        this.sendEntityDialogue(SEND_2_OPTIONS, talkDefinitions, false, (short) 1, TALK_EMOTE);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        /*System.out.println(this.stage+"");
    	System.out.println(child + " " +inter);*/
        int giveID = 0;
        switch (this.stage) {
            case 1:
                if (child == 1) {
                    if (!player.getInventory().hasRoomFor(player.plantId, 1)) {
                        this.finish();
                        return;
                    }
                    switch (player.plantId) {
                        case 2980:
                            giveID = 2460;
                            break;
                        case 2981:
                            giveID = 2462;
                            break;
                        case 2982:
                            giveID = 2464;
                            break;
                        case 2983:
                            giveID = 2466;
                            break;
                        case 2984:
                            giveID = 2468;
                            break;
                        case 2985:
                            giveID = 2470;
                            break;
                        case 2986:
                            giveID = 2472;
                            break;
                        case 2987:
                            giveID = 2474;
                            break;
                        case 2988:
                            giveID = 2476;
                            break;

                    }
                    player.getInventory().addItem(giveID, 1);
                    RSTile newPos = RSTile.createRSTile(player.plantX, player.plantY, player.plantZ);
                    RSObjectsRegion.removeObject(new RSObject(player.plantId, newPos, 10, 1));
                    this.finish();
                } else {
                    this.finish();
                }
                break;

            case 6:
                this.finish();
                break;
            default:

                this.finish();
                break;
        }
    }
}