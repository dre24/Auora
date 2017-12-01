/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.rscache.ItemDefinitions;
import Auora.scripts.dialogueScript;
import Auora.util.Misc;

/**
 * @author Hadyn Fitzgerald
 * @version 1.0
 */
public class ItemGamble extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"Gambler", "Are you sure that you want to gamble " + ItemDefinitions.forID(player.itemGambled).name + "?"};
        this.sendEntityDialogue(SEND_1_TEXT_CHAT, talkDefinitions, false, (short) 2998, TALK_EMOTE);
        this.stage = 0;
    }

    @Override
    public void run(short inter, byte child) {
        switch (this.stage) {
            case 0:
                if (inter == 241) {
                    String[] talkDefinitions = new String[]{SEND_DEFAULT_OPTION_DIALOGUE, "Yeah", "No Thanks"};
                    this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
                    this.stage = 1;
                }
                break;
            case 1:
                this.stage = 2;
                if (!player.getInventory().contains(player.itemGambled, 1)) {
                    player.itemGambled = 1;
                    this.finish();
                    return;
                }
                if (!player.getInventory().hasRoomFor(player.itemGambled, 2)) {
                    player.getFrames().sendChatMessage(0, "<col=ff0000>You must have at least 2 free inventory spaces to use the gambler.");
                    this.finish();
                    return;
                }
                player.getInventory().deleteItem(player.itemGambled, 1);
                int ran = Misc.random(100);
                if (ran == 0) {
                    ran = Misc.random(100);
                }
                if (ran > 60) {
                    String[] talkDefinitions = new String[]{"Gambler", "You rolled " + ran + ", You have won 2x your item!"};
                    this.sendEntityDialogue(SEND_1_TEXT_CHAT, talkDefinitions, false, (short) 2998, TALK_EMOTE);
                    player.getInventory().addItem(player.itemGambled, 2);
                }
                if (ran < 60) {
                    String[] talkDefinitions = new String[]{"Gambler", "You rolled " + ran + ", Sorry you lost your item!"};
                    this.sendEntityDialogue(SEND_1_TEXT_CHAT, talkDefinitions, false, (short) 2998, TALK_EMOTE);
                }
                if (ran == 60) {
                    String[] talkDefinitions = new String[]{"Gambler", "You rolled " + ran + ", You have been given back your item!"};
                    this.sendEntityDialogue(SEND_1_TEXT_CHAT, talkDefinitions, false, (short) 2998, TALK_EMOTE);
                    player.getInventory().addItem(player.itemGambled, 1);
                }
                break;
            case 2:
                this.stage = 3;
                this.finish();
                break;
            default:
                this.finish();
                break;
        }
    }
}