package Auora.scripts.dialogues;

import Auora.scripts.dialogueScript;

public class HelpBot extends dialogueScript {

    @Override
    public void start() {
        String[] talkDefinitions = new String[]{"Hello! I'm the Help Bot!", "Yes", "No"};
        this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        if (stage == 1) {
            switch (child) {
                case 1:
                    String[] talkDefinitions = new String[]{"What would you like to know?", "How can I make money on Rune-Unity?", "Where are the teleports for pking?"};
                    this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
                    stage = 2;
                    break;
                case 2:
                    this.finish();
                    break;
                default:
                    break;
            }
        } else if (stage == 2) {
            switch (child) {
                case 1:
                    String[] talkDefinitions = new String[]{"Help Bot", "Go Thieving at home punk bitch!"};
                    this.sendEntityDialogue(SEND_1_TEXT_CHAT, talkDefinitions, false, (short) 2998, TALK_EMOTE);
                    this.finish();
                    break;
                case 2:
                    talkDefinitions = new String[]{"Help Bot", "Answer 2!"};
                    this.sendEntityDialogue(SEND_1_TEXT_CHAT, talkDefinitions, false, (short) 2998, TALK_EMOTE);
                    this.finish();
                    break;
                default:
                    break;
            }
        }
    }

}