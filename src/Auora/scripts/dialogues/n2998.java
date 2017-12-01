/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;
import Auora.util.Misc;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class n2998 extends dialogueScript {

    private short npcclientid = 2998;

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
        String[] talkDefinitions = new String[]{"Gambler", "Hey " + Misc.formatPlayerNameForDisplay(player.getUsername()) + ", I can gamble your money for you."};
        this.sendEntityDialogue(SEND_1_TEXT_CHAT, talkDefinitions, false, (short) 2998, TALK_EMOTE);
        this.stage = 0;
    }

    @Override
    public void run(short inter, byte child) {
        int test = 0;
        int gamblestage = 999999;

			/*
            if(gamblestage == 15) {
                String[] talkDefinitions  = new String[]{SEND_DEFAULT_OPTION_DIALOGUE, "Buy 1 Lottery Ticket (100M)", "Buy 2 Lottery Tickets (200M)", "Buy 3 Lottery Tickets (300M)"};
                this.sendDialogue(SEND_3_LARGE_OPTIONS, talkDefinitions);
                this.stage = 1;	
				gamblestage = 20;
			}	
			*/


        switch (this.stage) {
            case 0:
                if (child == 5 && inter == 241) {
                    String[] talkDefinitions = new String[]{SEND_DEFAULT_OPTION_DIALOGUE, "60x2", "Lottery", "How do I get Dice Rank?", "What are the Dicing Rules?"};
                    this.sendDialogue(SEND_4_OPTIONS, talkDefinitions);
                    this.stage = 1;
                }
                break;

            case 2:
                if (test == 0) {
                    String[] talkDefinitions = new String[]{SEND_DEFAULT_OPTION_DIALOGUE, "Buy 1 Lottery Ticket <col=ff0000>(100M)", "No Thanks"};
                    this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
                    this.stage = 3;
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