/**
 *
 */
package Auora.scripts;

import Auora.model.player.Player;

/**
 * @author Alex
 */
public abstract class dialogueScript {

    protected static final String SEND_DEFAULT_OPTION_DIALOGUE = "What would you like to say?";
    protected static final String SEND_DEFAULT_OPTIONS_TITLE = "Select an Option";
    protected static final short SEND_2_OPTIONS = 236;
    protected static final short SEND_4_OPTIONS = 237;
    protected static final short SEND_5_OPTIONS = 238;
    protected static final short SEND_2_LARGE_OPTIONS = 229;
    protected static final short SEND_3_LARGE_OPTIONS = 231;
    protected static final short SEND_1_TEXT_CHAT = 241;
    protected static final short SEND_2_TEXT_CHAT = 242;
    protected static final short SEND_3_TEXT_CHAT = 243;
    protected static final short SEND_4_TEXT_CHAT = 244;
    protected static final short SEND_NO_CONTINUE_1_TEXT_CHAT = 245;
    protected static final short SEND_NO_CONTINUE_2_TEXT_CHAT = 246;
    protected static final short SEND_NO_CONTINUE_3_TEXT_CHAT = 247;
    protected static final short SEND_NO_CONTINUE_4_TEXT_CHAT = 248;
    protected static final short SEND_NO_EMOTE = -1;
    protected static final short TALK_EMOTE = 9827; // not normal
    protected static final short DRUNK = 9835; // drunk
    protected static final short IDK = 9811;// im not sure emote
    protected static final short EVILEYED = 9800;
    protected static final short RAGE = 9789;
    protected static final short SAD = 9765, EVIL = 9795;
    protected static final short WHAT_THE_CRAP = 9800;
    protected Player player;
    protected byte stage = -1;

    private static int[] getChildIds(short interId) {
        int[] childOptions;
        switch (interId) {
            case SEND_2_LARGE_OPTIONS:
                childOptions = new int[3];
                childOptions[0] = 1;
                childOptions[1] = 2;
                childOptions[2] = 3;
                break;
            case SEND_3_LARGE_OPTIONS:
                childOptions = new int[4];
                childOptions[0] = 1;
                childOptions[1] = 2;
                childOptions[2] = 3;
                childOptions[3] = 4;
                break;
            case SEND_2_OPTIONS:
                childOptions = new int[3];
                childOptions[0] = 0;
                childOptions[1] = 1;
                childOptions[2] = 2;
                break;
            case SEND_4_OPTIONS:
                childOptions = new int[5];
                childOptions[0] = 0;
                childOptions[1] = 1;
                childOptions[2] = 2;
                childOptions[3] = 3;
                childOptions[4] = 4;
                break;
            case SEND_5_OPTIONS:
                childOptions = new int[6];
                childOptions[0] = 0;
                childOptions[1] = 1;
                childOptions[2] = 2;
                childOptions[3] = 3;
                childOptions[4] = 4;
                childOptions[5] = 5;
                break;
            case SEND_1_TEXT_CHAT:
            case SEND_NO_CONTINUE_1_TEXT_CHAT:
                childOptions = new int[2];
                childOptions[0] = 3;
                childOptions[1] = 4;
                break;
            case SEND_2_TEXT_CHAT:
            case SEND_NO_CONTINUE_2_TEXT_CHAT:
                childOptions = new int[3];
                childOptions[0] = 3;
                childOptions[1] = 4;
                childOptions[2] = 5;
                break;
            case SEND_3_TEXT_CHAT:
            case SEND_NO_CONTINUE_3_TEXT_CHAT:
                childOptions = new int[4];
                childOptions[0] = 3;
                childOptions[1] = 4;
                childOptions[2] = 5;
                childOptions[3] = 6;
                break;
            case SEND_4_TEXT_CHAT:
            case SEND_NO_CONTINUE_4_TEXT_CHAT:
                childOptions = new int[5];
                childOptions[0] = 3;
                childOptions[1] = 4;
                childOptions[2] = 5;
                childOptions[3] = 6;
                childOptions[4] = 7;
                break;
            default:
                return null;
        }
        return childOptions;
    }

    public abstract void start();

    public abstract void run(short inter, byte child);

    public void finish() {
        this.player.getDialogue().reset();
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public boolean sendDialogue(short interId, String[] talkDefinitons) {
        int[] childOptions = getChildIds(interId);
        if (childOptions == null)
            return false;
        player.getFrames().sendCInterface(interId);
        if (talkDefinitons.length != childOptions.length)
            return false;
        for (int childOptionId = 0; childOptionId < childOptions.length; childOptionId++)
            player.getFrames().sendString(talkDefinitons[childOptionId], interId, childOptions[childOptionId]);
        return true;
    }

    public boolean sendEntityDialogue(short interId, String[] talkDefinitons, boolean isPlayer, short entityId, short animationId) {
        int[] childOptions = getChildIds(interId);
        if (childOptions == null)
            return false;
        player.getFrames().sendCInterface(interId);
        if (talkDefinitons.length != childOptions.length)
            return false;
        for (int childOptionId = 0; childOptionId < childOptions.length; childOptionId++)
            player.getFrames().sendString(talkDefinitons[childOptionId], interId, childOptions[childOptionId]);
        if (isPlayer) //TODO fix this if npc
            player.getFrames().sendEntityOnInterface(isPlayer, entityId, interId, 2);
        if (!isPlayer)
            player.getFrames().sendEntityOnInterface(isPlayer, entityId, interId, 2);
        if (animationId != -1)
            player.getFrames().sendInterAnimation(animationId, interId, 2);
        return true;
    }

}
