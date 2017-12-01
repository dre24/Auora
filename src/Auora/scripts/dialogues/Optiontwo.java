package Auora.scripts.dialogues;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.scripts.dialogueScript;

public class Optiontwo extends dialogueScript {

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
        String[] talkDefinitions = new String[]{"What would you like to do?", "<col=C61515>Donator",
                "<col=0688c8>Extreme Donator", "<col=12b203>Legendary Donator", "<col=2ECCFA>Dicer", "Go back"};
        this.sendDialogue(SEND_5_OPTIONS, talkDefinitions);
        this.stage = 1;
    }

    @Override
    public void run(short inter, byte child) {
        switch (child) {
            case 1:
                if (player.getDonatorRights().ordinal() < 2) {
                    player.sm("You have to be Donator+ before you can enable this title.");
                } else {
                    player.getTitles().setTitle("Donator");
                    player.getTitles().setColor("<col=C61515>");
                    player.getTitles().setShad("<shad=0>");
                }
                this.finish();
                break;
            case 2:
                if (player.getDonatorRights().ordinal() < 3) {
                    player.sm("You have to be Extreme+ before you can enable this title.");
                } else {
                    player.getTitles().setTitle("Extreme Donator");
                    player.getTitles().setColor("<col=0688c8>");
                    player.getTitles().setShad("<shad=0>");
                }
                //int Spaces = player.getInventory().getFreeSlots();
                //player.getInventory().addItem(15272, Spaces);
                this.finish();
                break;
            case 3:
                if (player.getDonatorRights().ordinal() < 4) {
                    player.sm("You have to be Legendary+ before you can enable this title.");
                } else {
                    player.getTitles().setTitle("Legendary Donator");
                    player.getTitles().setColor("<col=12b203>");
                    player.getTitles().setShad("<shad=0>");

                }

                this.finish();
                break;
            case 4:
                if (player.dicerRank == 0) {
                    player.sm("You have to be a Dicer before you can enable this title.");
                } else {
                    player.getTitles().setTitle("Dicer");
                    player.getTitles().setColor("<col=2ECCFA>");
                    player.getTitles().setShad("<shad=0>");
                }

                this.finish();
                break;
            case 5:

                player.getDialogue().startDialogue("WildyBankChest");
                //this.finish();
                break;

            default:
                this.finish();
                break;
        }
    }
}
