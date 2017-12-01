/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.rscache.ItemDefinitions;
import Auora.scripts.dialogueScript;
import Auora.scripts.items.i10944;

public class VoteTokens extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"What would you like to do?", "Randomize Rewards", "View Rewards List"};
        this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
                player.getFrames().requestIntegerInput(21001, "How many vote token rewards would you like to generate?<br>(Max: 200)");
                this.finish();
                break;
            case 2:
                int number = 0;
                int super_rare_amount = i10944.SUPER_RARE_ITEMS.length;
                int rare_amount = i10944.RARE_ITEMS.length;
                int common = i10944.COMMON_ITEMS.length;
                for (int i = 0; i < 316; i++) {
                    player.getFrames().sendString("", 275, i);
                }
                player.getFrames().sendString("<col=00A1B3><shad=002896>Vote Tokens</col>", 275, 14);
                player.getFrames().sendString("<col=00A1B3><shad=002896>Vote Token Rewards</col>", 275, 16);
                player.getFrames().sendString("<col=00A1B3><shad=002896>Vote Token Rewards</col>", 275, 2);
                player.getFrames().sendString("<col=ff0000><shad=0>Super Rare</col>", 275, 18);
                player.getFrames().sendString("<col=ff0000><shad=0>Rare</col>", 275, 20 + super_rare_amount);
                player.getFrames().sendString("<col=ff0000><shad=0>Common</col>", 275, 22 + super_rare_amount + rare_amount);
                player.getFrames().sendInterface(275);
                for (int i = 0; i < super_rare_amount; i++) {
                    player.getFrames().sendString(ItemDefinitions.forID(i10944.SUPER_RARE_ITEMS[i]).name, 275, 19 + i);
                }
                for (int i = 21 + super_rare_amount; i < 21 + super_rare_amount + rare_amount; i++) {
                    player.getFrames().sendString(ItemDefinitions.forID(i10944.RARE_ITEMS[i - super_rare_amount - 21]).name, 275, i);
                }
                for (int i = 23 + super_rare_amount + rare_amount; i < 23 + super_rare_amount + rare_amount + common; i++) {
                    player.getFrames().sendString(ItemDefinitions.forID(i10944.COMMON_ITEMS[i - super_rare_amount - rare_amount - 23]).name, 275, i);
                }
                player.lastRandomizationName = "";
                this.finish();
                break;
            default:
                this.finish();
                break;
        }
    }
}