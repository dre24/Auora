package Auora.model.player.content;

import Auora.io.OutStream;
import Auora.model.CoordinateLocations;
import Auora.model.GlobalItem;
import Auora.model.World;
import Auora.model.player.Player;
import Auora.util.HintIcon;
import Auora.util.Misc;

import java.io.Serializable;
import java.util.Random;


/**
 * Created by Dre on 22.04.2017 11:03.
 */
public class BountyHunter implements Serializable {

    private static final long serialVersionUID = 2011932556974180375L;
    /**
     * Check if the killed player was the target. You can add the rogue rewards here as well if you want.
     *
     * @param killed the killed player
     */
    public static int[] bh1 = {13911, 13917, 13923, 13908, 13914};
    public static int[] bh2 = {13929, 13920, 13926, 13932, 13935, 13938, 13941, 13944, 13947, 13950};
    public static int[] bh3 = {14892, 14891, 14890, 14889, 14888, 14887, 14886, 14885, 14884,
            14883, 14882, 14881, 14880};
    public transient Player target;
    private transient Player player;
    private int likelihood = 0;
    private float ep = 0;
    private long lastTarget = 0;
    private long leaveTime = 0;
    private Player p;
    private HintIcon[] loadedIcons;
    /**
     * Number of bounty hunter ticks (about 10secs) in wilderness
     */
    private int NORMAL_EP_TICKS = 0;
    private int HOT_ZONE_EP_TICKS = 0;

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Does this player have a target?
     *
     * @return target == null
     */
    public boolean hasTarget() {
        return target != null;
    }

    /**
     * Set a target for this player
     *
     * @param target the target
     */
    void assignTarget(Player target) {
        this.target = target;

        //(target, 0, -1, false);
        updateInterfaces();
    }

    /**
     * Check whether a player is suitable for a target for this player
     *
     * @param target the target candidate
     * @return suitability
     */
    boolean isSuitableTarget(Player target) {
        if (likelihood > 60) {
            if ((player.getSkills().getCombatLevel() - target.getSkills().getCombatLevel())
                    <= (CoordinateLocations.getWildernessLevel(player, (int) player.getLocation().getX(), (int) player.getLocation().getY()))) {
                return true;
            }
        } else {
            if (target.getBountyHunter().likelihood > 30) return true;
        }
        return false;
    }

    /**
     * Handle the player logging out
     */
    public void logout() {
        if (target != null) {
            target.getBountyHunter().removeTarget(true);
            removeTarget(false);
        }
        lastTarget = System.currentTimeMillis();
        likelihood = 0;
        BountyHunterManager.removeHandledPlayer(player);
    }

    /**
     * Remove the players target
     *
     * @param loggedOut did the target logout?
     */
    public void removeTarget(boolean loggedOut) {
        if (loggedOut) {
            player.sendMessage("Your target has logged out. You will be assigned a new one shortly.");
            lastTarget = System.currentTimeMillis() - (long) (BountyHunterManager.TARGET_COOLDOWN * 0.3
                    * 1000); // 30% of the target delay
            likelihood = 50;
            //  target.getFrames().resetHintIcon(10, player, 1, 40497);
            //player.getFrames().resetHintIcon(10, target, 1, 40497);
        } else {
            lastTarget = System.currentTimeMillis() - BountyHunterManager.TARGET_COOLDOWN * 1000;
            //likelihood = 30;
        }
        updateInterfaces();
        // player.getIntermanager().removeUnsavedHintIcon();
        target = null;

    }

    public void removeUnsavedHintIcon(Player p) {
        p.getFrames().sendHintIcon(new HintIcon());
    }

    public void removeAll(Player p) {
        for (int index = 0; index < loadedIcons.length; index++) {
            loadedIcons[index].setTargetType(0);
            p.getFrames().sendHintIcon(loadedIcons[index]);
            loadedIcons[index] = null;
        }
    }

    /*
     * You may want to change the tab value of the skull (the number 27 in the two below methods) in Wilderness.java
     */

    public void sendHintIcon(HintIcon icon) {
        OutStream stream = new OutStream();
        stream.writePacket(10);
        stream.writeByte(icon.getTargetType() & 0x1f | icon.getIndex() << 5);
        if (icon.getTargetType() == 0) {
            stream.skip(13);
        } else {
            stream.writeByte(icon.getArrowType());
            if (icon.getTargetType() == 1 || icon.getTargetType() == 10) {
                stream.writeShort(icon.getTargetIndex());
                stream.writeShort(0); // how often the arrow flashes, 2500
                // ideal, 0 never
                stream.skip(4);
            } else if (icon.getTargetType() >= 2 && icon.getTargetType() <= 6) { // directions
                stream.writeByte(icon.getHeight()); // unknown
                stream.writeShort(icon.getCoordX());
                stream.writeShort(icon.getCoordY());
                stream.writeByte(icon.getDistanceFromFloor() * 4 >> 2);
                stream.writeShort(-1); // distance to start showing on minimap,
                // 0 doesnt show, -1 infinite
            }
            stream.writeShort(icon.getModelId());
        }
        player.getConnection().write(stream);

    }

    public void checkKill(Player killed) {
        if (target == null) return;
        if (killed.getUsername().equalsIgnoreCase(target.getUsername()) && player.getCombat().inWild(player)) {

            int bhchance = Misc.random(0, 100);

            if (bhchance > 96) {
                Random random = new Random();
                World.getGlobalItemsManager()
                        .addGlobalItem(new GlobalItem(player.getUsername(), bh1[random.nextInt(bh1.length)], 1,
                                target.getLocation().getX(), target.getLocation().getY(),
                                target.getLocation().getZ(), 1), false);
            } else if (96 >= bhchance && bhchance > 87) {
                Random random2 = new Random();
                World.getGlobalItemsManager()
                        .addGlobalItem(new GlobalItem(player.getUsername(), bh2[random2.nextInt(bh2.length)], 1,
                                target.getLocation().getX(), target.getLocation().getY(),
                                target.getLocation().getZ(), 1), false);
            } else {
                Random random3 = new Random();
                World.getGlobalItemsManager()
                        .addGlobalItem(new GlobalItem(player.getUsername(), bh3[random3.nextInt(bh3.length)], 1,
                                target.getLocation().getX(), target.getLocation().getY(),
                                target.getLocation().getZ(), 1), false);
            }


            player.sendMessage("You have killed your target and received an extra 5 pkp");
            player.pkPoints += 5;
            player.getBountyHunter().likelihood = 30;
            target.getBountyHunter().likelihood = 0;
            //target.getFrames().resetHintIcon(10, player, 1, 40497);
            // player.getFrames().resetHintIcon(10, target, 1, 40497);
            target.getBountyHunter().removeTarget(false);
            player.getBountyHunter().removeTarget(false);


        }
    }

    /**
     * Send the bh interfaces
     */
    public void sendInterfaces() {
        updateInterfaces();
        player.getFrames().sendClickableInterface(591);


    }

    /**
     * Update the bh interfaces
     */
    void updateInterfaces() {
        if (target != null) {
            player.getFrames().sendString(target.getDisplayName(), 591, 8);


        } else {
            player.getFrames().sendString("None", 591, 8);


        }

        //player.getPackets().sendHideIComponent(745, 6, inHotZone());

       /* if (player.getConnection().getDisplayMode() == 2) {
             player.getFrames().sendString(getCombatDiff() + "<br> EP: <col=" + getEpColor() + ">" + getEpPercentage() + "%", 591, 17
                    );
        } else {
            player.getFrames().sendString(getCombatDiff(),591, 12);
            player.getFrames().sendString("EP: <col=" + getEpColor() + ">" + getEpPercentage() + "%", 591, 13
                    );//EP
        }*/
        player.getFrames().sendConfig(1410, likelihood > 60 ? 60 : likelihood);
    }

    /**
     * Checks if player is in hot zone to gain more ep
     **/
    private boolean inHotZone() {
        return player.getLocation().getY() > 3700;
    }

    /**
     * Get the color that the ep percentage is displayed in
     *
     * @return the ep color
     */
    public String getEpColor() {
        if (ep < 25) return "red";
        if (ep < 50) return "or1";
        if (ep < 75) return "yel";
        else return "gre";
    }

    /**
     * Used just for the display
     *
     * @return combat level diff in format [min]-[max]
     */
    private String getCombatDiff() {
        int wildLevel;
        if (player.getLocation().getY() > 9900) wildLevel = (player.getLocation().getY() - 9912) / 8 + 1;
        else wildLevel = (player.getLocation().getY() - 3520) / 8 + 1;
        int min = player.getSkills().getCombatLevel() - wildLevel;
        int max = player.getSkills().getCombatLevel() + wildLevel;

        return min + " - " + max;
    }

    /**
     * Fetch the ep percentage in full percents
     *
     * @return formatted percentage
     */
    public int getEpPercentage() {
        return Math.round(ep * 100);
    }

    /**
     * Increase the players earning potential
     * Add 25% for each 30 minutes in hot zone and 10% for 30 minutes in normal area
     */
    void increaseEP() {

        if (player.getCombat().inWild(player)) {
            if (inHotZone()) HOT_ZONE_EP_TICKS++;
            else NORMAL_EP_TICKS++;
        }
        if (HOT_ZONE_EP_TICKS >= 1) {
            ep += BountyHunterManager.EPH * 10 / 60.0 / 100;
            HOT_ZONE_EP_TICKS = 0;
        }
        if (NORMAL_EP_TICKS >= 2) {
            ep += BountyHunterManager.EPH * 5 / 60.0 / 100;
            NORMAL_EP_TICKS = 0;
        }
        if (ep > 1) ep = 1;
    }


    /**
     * Close the bh interfaces
     */
    public void closeInterfaces() {
        if (player.getConnection().getDisplayMode() == 2) {
            player.getFrames().sendString("", 591, 17);
        } else {
            player.getFrames().sendString("", 591, 12);
            player.getFrames().sendString("", 591, 13);//EP
        }

        player.getFrames().closeInterface(BountyHunterManager.RESIZABLE_TAB_ID);
        player.getFrames().closeInterface(BountyHunterManager.FIXED_TAB_ID);
    }

    /**
     * A player can only get a target every BountyHunterManager.TARGET_COOLDOWN seconds.
     */
    boolean isOnTargetCooldown() {
        return (System.currentTimeMillis() - lastTarget) < BountyHunterManager.TARGET_COOLDOWN * 1000;
    }

    /**
     * Increase the players likelihood of getting a target
     */
    void increaseLikelihood() {
        likelihood += BountyHunterManager.TARGET_LIKELIHOOD_INCREASE;
    }

    /**
     * Resets the target likelihood, called upon entering wilderness etc
     */
    private void resetLikelihood() {
        likelihood = 0;
    }

    /**
     * Called upon entering bh
     */
    void enterBountyHunter() {
        if (System.currentTimeMillis() - leaveTime > 10 * 60 * 1000) {
            //If been out for more than 10 minutes
        }

        // resetLikelihood();
    }

    /**
     * Called upon leaving bh by logging out or teleporting etc.
     */
    void leaveBountyHunter() {
        leaveTime = System.currentTimeMillis();
    }

}