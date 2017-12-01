package Auora.scripts.dialogues;

import Auora.scripts.dialogueScript;

/**
 * @author Leon
 */

public class SwitchMagic extends dialogueScript {

    @Override
    public void start() {
        String[] talkDefinitions = new String[]{"Select a spellbook to switch to", "Lunars", "Ancients"};
        this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
                player.getFrames().sendInterface(1, 548, 205, 430);
                player.getFrames().sendInterface(1, 746, 93, 430);
                player.spellbook = 2;
                this.finish();
                break;
            case 2:
                player.getFrames().sendInterface(1, 548, 205, 193);
                player.getFrames().sendInterface(1, 746, 93, 193);
                player.spellbook = 1;
                this.finish();
                break;
            default:
                this.finish();
                break;
        }
    }
}