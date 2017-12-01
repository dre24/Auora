package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;

public class Optionone extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"What would you like to do?", "<col=AA44AA>Junior Cadet (250 kills)",
                "<col=AA44AA>Serjeant (750 kills)", "<col=AA44AA>Commander (1500 kills)", "<col=AA44AA>War-Chief (2500 kills)", "Go back"};
        this.sendDialogue(SEND_5_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
                if ((player.dangerousKills + player.safeKills) < 250) {
                    player.sm("You need atleast 250 overall kills before you can enable this title.");
                } else {
                    player.getTitles().setTitle("Junior Cadet");
                    player.getTitles().setColor("<col=AA44AA>");
                    player.getTitles().setShad("<shad=0>");
                }
                this.finish();
                break;
            case 2:
                if ((player.dangerousKills + player.safeKills) < 750) {
                    player.sm("You need atleast 750 overall kills before you can enable this title.");
                } else {
                    player.getTitles().setTitle("Serjeant");
                    player.getTitles().setColor("<col=AA44AA>");
                    player.getTitles().setShad("<shad=0>");
                }
                this.finish();
                break;
            case 3:
                if ((player.dangerousKills + player.safeKills) < 1500) {
                    player.sm("You need atleast 1500 overall kills before you can enable this title.");
                } else {
                    player.getTitles().setTitle("Commander");
                    player.getTitles().setColor("<col=AA44AA>");
                    player.getTitles().setShad("<shad=0>");
                }

                this.finish();
                break;
            case 4:
                if ((player.dangerousKills + player.safeKills) < 2500) {
                    player.sm("You need atleast 2500 overall kills before you can enable this title.");
                } else {
                    player.getTitles().setTitle("War-Chief");
                    player.getTitles().setColor("<col=AA44AA>");
                    player.getTitles().setShad("<shad=0>");
                }

                this.finish();
                break;
            case 5:

                player.getDialogue().startDialogue("WildyBankChest");
                break;

            default:
                this.finish();
                break;
        }
    }
}
