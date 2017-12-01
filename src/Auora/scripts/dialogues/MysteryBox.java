/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.rscache.ItemDefinitions;
import Auora.scripts.dialogueScript;
import Auora.scripts.items.i6199;

import java.util.ArrayList;

public class MysteryBox extends dialogueScript {

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
                player.getFrames().requestIntegerInput(21000, "How many mystery box rewards would you like to generate?<br>(Max: 200)");
                this.finish();
                break;
            case 2:
                int number = 0;
                int super_rare_amount = i6199.SUPER_RARE_ITEMS.length;
                int rare_amount = i6199.RARE_ITEMS.length;
                for (int i = 0; i < 316; i++) {
                    player.getFrames().sendString("", 275, i);
                }
                player.getFrames().sendString("<col=ff0000><shad=FFCE0F>Mystery Box</col>", 275, 14);
                player.getFrames().sendString("<col=ff0000><shad=FFCE0F>Mystery Box Rewards</col>", 275, 16);
                player.getFrames().sendString("<col=ff0000><shad=FFCE0F>Mystery Box Rewards</col>", 275, 2);
                player.getFrames().sendString("<col=ff0000><shad=0>Super Rare</col>", 275, 18);
                player.getFrames().sendString("<col=ff0000><shad=0>Common</col>", 275, 20 + super_rare_amount);
                player.getFrames().sendInterface(275);
                for (int i = 0; i < super_rare_amount; i++) {
                    player.getFrames().sendString(ItemDefinitions.forID(i6199.SUPER_RARE_ITEMS[i]).name, 275, 19 + i);
                }
                for (int i = 21 + super_rare_amount; i < 21 + super_rare_amount + rare_amount; i++) {
                    player.getFrames().sendString(ItemDefinitions.forID(i6199.RARE_ITEMS[i - super_rare_amount - 21]).name, 275, i);
                }
                player.lastRandomizationName = "";
                this.finish();
                break;
            default:
                this.finish();
                break;
        }
    }

    public static class Empty extends dialogueScript {

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
            String[] talkDefinitions = new String[]{"What would you like to do?", "Empty my inventory.", "No, don't empty it."};
            this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
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
}