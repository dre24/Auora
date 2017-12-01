package Auora.model.player;

import Auora.model.Animation;
import Auora.model.ForceText;
import Auora.model.Graphics;
import Auora.model.Heal;
import Auora.util.RSTile;

public class Mask {
    private transient Player player;
    private ForceText forceText;
    private boolean forceTextUpdate;
    private transient ChatMessage lastChatMessage;
    private transient Graphics lastGraphics;
    private transient Graphics lastGraphics2;
    private transient Animation lastAnimation;
    private transient Heal lastHeal;
    private transient boolean ApperanceUpdate;
    private transient boolean HitUpdate;
    private transient boolean Hit2Update;
    private transient boolean HealUpdate;
    private transient boolean ChatUpdate;
    private transient boolean GraphicUpdate;
    private transient boolean Graphic2Update;
    private transient boolean AnimationUpdate;
    private transient boolean ForceMovementUpdateRequired;
    private transient boolean turnToUpdate;
    private transient boolean turnToUpdate1;
    private transient boolean turnToReset;
    private transient RSTile turnToLocation;
    private transient int turnToIndex;
    private transient Region region;

    public Mask(Player player) {
        this.setwPlayer(player);
        this.setRegion(new Region(this.player));
        this.setApperanceUpdate(true);
    }

    public void reset() {
        this.getRegion().reset();
        this.setApperanceUpdate(false);
        this.setHitUpdate(false);
        this.setForceMovementUpdateRequired(false);
        this.setHit2Update(false);
        this.setHealUpdate(false);
        this.setForceTextUpdate(false);
        this.setChatUpdate(false);
        this.setGraphicUpdate(false);
        this.setGraphic2Update(false);
        this.setAnimationUpdate(false);
        forceText = null;
        if (this.turnToReset)
            this.setTurnToUpdate(false);
        this.setTurnToUpdate1(false);
    }

    public void setwPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isUpdateNeeded() {
        return ApperanceUpdate || HitUpdate || Hit2Update || HealUpdate
                || GraphicUpdate || Graphic2Update || AnimationUpdate || ForceMovementUpdateRequired || turnToUpdate || player.getWalk().getWalkDir() != -1 || player.getWalk().getRunDir() != -1 || player.getWalk().isDidTele() || forceText != null;
    }

    public boolean isApperanceUpdate() {
        return ApperanceUpdate;
    }

    public void setApperanceUpdate(boolean apperanceUpdate) {
        ApperanceUpdate = apperanceUpdate;
    }

    public boolean isHitUpdate() {
        return HitUpdate;
    }

    public void setHitUpdate(boolean hitUpdate) {
        HitUpdate = hitUpdate;
    }

    public boolean isChatUpdate() {
        return ChatUpdate;
    }

    public void setChatUpdate(boolean chatUpdate) {
        ChatUpdate = chatUpdate;
    }

    public ChatMessage getLastChatMessage() {
        return lastChatMessage;
    }

    public void setLastChatMessage(ChatMessage lastChatMessage) {
        this.lastChatMessage = lastChatMessage;
    }

    public boolean isGraphicUpdate() {
        return GraphicUpdate;
    }

    public void setGraphicUpdate(boolean graphicUpdate) {
        GraphicUpdate = graphicUpdate;
    }

    public Graphics getLastGraphics() {
        return lastGraphics;
    }

    public void setLastGraphics(Graphics lastGraphics) {
        this.lastGraphics = lastGraphics;
    }

    public Animation getLastAnimation() {
        return lastAnimation;
    }

    public void setLastAnimation(Animation lastAnimation) {
        this.lastAnimation = lastAnimation;
    }

    public boolean isAnimationUpdate() {
        return AnimationUpdate;
    }

    public void setAnimationUpdate(boolean animationUpdate) {
        AnimationUpdate = animationUpdate;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public boolean isHit2Update() {
        return Hit2Update;
    }

    public void setHit2Update(boolean hit2Update) {
        Hit2Update = hit2Update;
    }

    public boolean isTurnToUpdate() {
        return turnToUpdate;
    }

    public void setTurnToUpdate(boolean turnToUpdate) {
        this.turnToUpdate = turnToUpdate;
    }

    public boolean isTurnToReset() {
        return turnToReset;
    }

    public void setTurnToReset(boolean turnToReset) {
        this.turnToReset = turnToReset;
    }

    public boolean isGraphic2Update() {
        return Graphic2Update;
    }

    public void setGraphic2Update(boolean Graphic2Update) {
        this.Graphic2Update = Graphic2Update;
    }

    public Graphics getLastGraphics2() {
        return lastGraphics2;
    }

    public void setLastGraphics2(Graphics lastGraphics2) {
        this.lastGraphics2 = lastGraphics2;
    }

    public RSTile getTurnToLocation() {
        return turnToLocation;
    }

    public void setTurnToLocation(RSTile turnToLocation) {
        this.turnToLocation = turnToLocation;
    }

    public boolean isHealUpdate() {
        return HealUpdate;
    }

    public void setHealUpdate(boolean hit3Update) {
        HealUpdate = hit3Update;
    }

    public void setLastForceText(ForceText text) {
        setForceText(text);
    }

    public boolean isForceTextUpdate() {
        return forceTextUpdate;
    }

    public void setForceTextUpdate(boolean forceText) {
        this.forceTextUpdate = forceText;
    }

    public ForceText getForceText() {
        return forceText;
    }

    public void setForceText(ForceText forceText) {
        this.forceText = forceText;
        forceTextUpdate = true;
    }

    public Heal getLastHeal() {
        return lastHeal;
    }

    public void setLastHeal(Heal lastHeal) {
        this.lastHeal = lastHeal;
    }

    public int getTurnToIndex() {
        return turnToIndex;
    }

    public void setTurnToIndex(int turnToIndex) {
        this.turnToIndex = turnToIndex;
    }

    public boolean isNextForceMovement() {
        return ForceMovementUpdateRequired;
    }

    public void setForceMovementUpdateRequired(boolean b) {
        this.ForceMovementUpdateRequired = b;
    }

    public boolean isTurnToUpdate1() {
        return turnToUpdate1;
    }

    public void setTurnToUpdate1(boolean turnToUpdate1) {
        this.turnToUpdate1 = turnToUpdate1;
    }
}
