package Auora.scripts.dialogues;

import Auora.scripts.dialogueScript;

public class StarterGuide extends dialogueScript {

    @Override
    public void start() {
        String[] talkDefinitions = new String[]{"Auora Guide", "Welcome to Auora!",
                "Would you like me to show you around?"};
        this.sendEntityDialogue(SEND_2_TEXT_CHAT, talkDefinitions, false, (short) 945, TALK_EMOTE);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        if (stage == 1) {
            String[] talkDefinitions = new String[]{"Auora Guide", "Show me around", "No thanks"};
            this.sendDialogue(SEND_2_OPTIONS, talkDefinitions);
            this.stage = 2;
        } else if (stage == 2) {
            switch (child) {
                case 1:
                    player.inStarter = true;
                    String[] talkDefinitions = new String[]{"Auora Guide",
                            "Very well! You're currently located at the home", "you can go north of the bank to fight",
                            "dont worry, you will not lose items!"};
                    this.sendEntityDialogue(SEND_3_TEXT_CHAT, talkDefinitions, false, (short) 945, TALK_EMOTE);
                    this.stage = 3;
                    break;
                case 2:
                    this.finish();
                    break;
                default:
                    break;
            }
        } else if (stage == 3) {
            String[] talkDefinitions = new String[]{"Auora Guide",
                    "Killing players will give you a random reward", "and a random amount of PkP which can be",
                    "spent in the PkP store at home. The more",
                    "players you kill without dying, the higher your bounty!"};
            this.sendEntityDialogue(SEND_4_TEXT_CHAT, talkDefinitions, false, (short) 945, TALK_EMOTE);
            this.stage = 4;
        } else if (stage == 4) {
            String[] talkDefinitions = new String[]{"Auora Guide", "This means that players will target you",
                    "in order to receive the bounty that has", "been placed on you! So be careful!"};
            this.sendEntityDialogue(SEND_3_TEXT_CHAT, talkDefinitions, false, (short) 945, TALK_EMOTE);
            this.stage = 5;
        } else if (stage == 5) {
            player.getMask().getRegion().teleport(3013, 3356, 0, 0);
            String[] talkDefinitions = new String[]{"Auora Guide",
                    "This is Falador PvP! This area is <col=ff0000>Dangerous</col>",
                    "which means that you will lose your items here!",
                    "Killing players here will reward you with more PkP", "per kill as well as receiving their loot!"};
            this.sendEntityDialogue(SEND_4_TEXT_CHAT, talkDefinitions, false, (short) 945, TALK_EMOTE);
            this.stage = 6;
        } else if (stage == 6) {
            player.getMask().getRegion().teleport(3361, 9640, 0, 0);
            String[] talkDefinitions = new String[]{"Auora Guide",
                    "This is the dice zone where you can dice with others",
                    "Dicing is popular so this area is normally quite busy",
                    "Make sure that you record your dice duels!", "Otherwise you are unlikely to be refunded if scammed."};
            this.sendEntityDialogue(SEND_4_TEXT_CHAT, talkDefinitions, false, (short) 945, TALK_EMOTE);
            this.stage = 7;
        } else if (stage == 7) {
            String[] talkDefinitions = new String[]{"Auora Guide",
                    "Thanks for taking the time to tour the server!", "Here's a little booster to get you started."};
            this.sendEntityDialogue(SEND_2_TEXT_CHAT, talkDefinitions, false, (short) 945, TALK_EMOTE);
            player.inStarter = false;
            player.getMask().getRegion().teleport(3185, 3439, 0, 0);
            player.sm("<col=ff0000>Please do ::help to view some useful tips!");
            this.finish();
        }
    }

}