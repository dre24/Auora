package Auora.model.npc;

import Auora.model.Animation;
import Auora.model.ForceText;
import Auora.model.Graphics;
import Auora.model.Hits;


public class Mask {
    private transient Npc npc;


    private transient boolean Teleport;
    private transient boolean HitUpdate;
    private transient boolean graphicUpdate;
    private transient boolean graphic2Update;
    private transient boolean animUpdate;
    private transient boolean forceTextUpdate;
    private transient boolean HitUpdate2;
    private transient boolean turnToUpdate;
    private transient boolean switchUpdate;
    private transient int turnToIndex;

    private transient Graphics graphics;
    private transient Animation animation;
    private transient ForceText forceText;
    private transient Hits hit;
    //private transient NpcSwitch npcSwitch;

    private boolean forceMovementUpdate;


    public Mask(Npc npc) {
        this.setNpc(npc);
        this.setTeleport(true);
        this.setHitUpdate(false);
        this.setHit2Update(false);
        this.setGraphicUpdate(false);
        this.setGraphic2Update(false);
        this.setAnimationUpdate(false);
        this.setForceTextUpdate(false);
        this.setTurnToUpdate(false);
        this.setSwitchUpdate(false);
    }

    public void reset() {
        this.setTeleport(false);
        this.setHitUpdate(false);
        this.setHit2Update(false);
        this.setGraphicUpdate(false);
        this.setGraphic2Update(false);
        this.setAnimationUpdate(false);
        this.setForceTextUpdate(false);
        this.setTurnToUpdate(false);
        this.setSwitchUpdate(false);
    }

    public Npc getNpc() {
        return npc;
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
    }

    public boolean isUpdateNeeded() {
        return HitUpdate | forceMovementUpdate | HitUpdate2 | graphicUpdate | animUpdate | forceTextUpdate | turnToUpdate | switchUpdate;
    }

    public boolean isTeleport() {
        return Teleport;
    }

    public void setTeleport(boolean teleport) {
        Teleport = teleport;
    }

    public boolean isHitUpdate() {
        return HitUpdate;
    }

    public void setHitUpdate(boolean hitUpdate) {
        HitUpdate = hitUpdate;
    }

    public void setLastHits(Hits hit2) {
        hit = hit2;
    }

    public Graphics getLastGraphics() {
        return graphics;
    }

    public void setLastGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public boolean isGraphicUpdateNeeded() {
        return graphicUpdate;
    }

    public void setGraphicUpdate(boolean graphics) {
        graphicUpdate = graphics;
    }

    public boolean isGraphic2UpdateNeeded() {
        return graphic2Update;
    }

    public void setGraphic2Update(boolean g2) {
        graphic2Update = g2;
    }

    public boolean isAnimationUpdateNeeded() {
        return animUpdate;
    }

    public void setAnimationUpdate(boolean needed) {
        animUpdate = needed;
    }

    public Animation getLastAnimation() {
        return animation;
    }

    public void setLastAnimation(Animation anim) {
        animation = anim;
    }

    public boolean isForceTextUpdate() {
        return forceTextUpdate;
    }

    public void setForceTextUpdate(boolean b) {
        forceTextUpdate = b;
    }

    public ForceText getLastForceText() {
        return forceText;
    }

    public void setLastForceText(ForceText text) {
        forceText = text;
    }

    public Hits getLastHit() {
        return hit;
    }

    public boolean isHit2Update() {
        return HitUpdate2;
    }

    public void setHit2Update(boolean b) {
        HitUpdate2 = b;
    }

    public int getTurnToIndex() {
        return turnToIndex;
    }

    public void setTurnToIndex(int index) {
        turnToIndex = index;
    }

    public boolean isTurnToUpdate() {
        return turnToUpdate;
    }

    public void setTurnToUpdate(boolean turn) {
        turnToUpdate = turn;
    }

    public void setTurnToReset(boolean b) {
        this.setTurnToUpdate(false);
    }

    public boolean isNpcSwitchUpdate() {
        return switchUpdate;
    }

    public void setSwitchUpdate(boolean b) {
        switchUpdate = b;
    }

	/*public NpcSwitch getLastSwitch() {
		return npcSwitch;
	}
	
	public void setLastSwitch(NpcSwitch npcs) {
		npcSwitch = npcs;
	}*/

    public boolean isForceMovementUpdate() {
        return forceMovementUpdate;
    }

    public void setForceMovementUpdate(boolean forceMovementUpdate) {
        this.forceMovementUpdate = forceMovementUpdate;
    }
}
