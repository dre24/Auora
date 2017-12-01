package Auora.model.player;

import Auora.scripts.Scripts;
import Auora.scripts.dialogueScript;

public class Dialogue {


    private dialogueScript lastDialogue;
    private Player player;

    public Dialogue(Player player) {
        this.player = player;
    }

    public void startDialogue(String key) {
        if (this.getLastDialogue() != null)
            this.getLastDialogue().finish();
        this.setLastDialogue(Scripts.invokeDialogueScript(key));
        if (this.getLastDialogue() == null)
            return;
        this.getLastDialogue().setPlayer(this.player);
        this.getLastDialogue().start();
    }

    public void continueDialogue(short inter, byte child) {
        if (this.getLastDialogue() == null)
            return;
        this.getLastDialogue().run(inter, child);
    }

    public void finishDialogue() {
        if (this.getLastDialogue() == null)
            return;
        this.getLastDialogue().finish();
    }

    public void reset() {
        this.setLastDialogue(null);
        this.player.getFrames().CloseCInterface();
    }

    public dialogueScript getLastDialogue() {
        return lastDialogue;
    }

    public void setLastDialogue(dialogueScript lastDialogue) {
        this.lastDialogue = lastDialogue;
    }
}
