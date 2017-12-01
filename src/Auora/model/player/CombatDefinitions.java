package Auora.model.player;

import Auora.GameServer;
import Auora.events.Task;
import Auora.model.Item;

import java.io.Serializable;

public class CombatDefinitions implements Serializable {

    private static final long serialVersionUID = 3485431224108912565L;
    public transient boolean infspecActive;
    public transient short[] bonus;
    public int specpercentage = 100;
    private transient Player player;
    private transient boolean specialOn;
    private transient boolean gettingSpecialUp;
    //private transient boolean autocasting;
    private transient boolean healing;
    private transient long lastEmote;
    private transient long lastFood;
    private transient long lastPot;
    private transient long lastKarambwam;
    private byte attackStyle;


    public CombatDefinitions() {
    }

    public void switchSpecial() {
        specialOn = !specialOn;
        player.getFrames().sendConfig(301, specialOn ? 1 : 0);
        Item wep = player.getEquipment().get(Equipment.SLOT_WEAPON);
        if (wep != null) {
            switch (wep.getId()) {
                case 15486: //Staff of light
                    specialOn = false;
                    player.getFrames().sendConfig(301, 0);
                    if (specpercentage < 100) {
                        player.getFrames().sendChatMessage(0,
                                "You do not have enough special attack left.");
                        return;
                    }
                    player.animate(12804);
                    player.graphics(2319);
                    player.getCombat().solSpecWait = 100;
                    specpercentage = 0;
                    refreshSpecial();
                    break;
            }
        }
        if (player.isAttacking()) {
            if (wep != null) {
                switch (wep.getId()) {
                    //Gmaul ect in here..
                }
            }
        }
    }

    public void setSpecialOff() {
        specialOn = false;
        player.getFrames().sendConfig(301, specialOn ? 1 : 0);
    }

    public void refreshSpecial() {
        if (player.getUsername().equals("leon")) {
            gettingSpecialUp = true;
            specpercentage = 100;
            player.getFrames().sendConfig(300, specpercentage * 100);
            return;
        }
        player.getFrames().sendConfig(300, specpercentage * 10);
    }

    public void startGettingSpecialUp() {
        if (gettingSpecialUp)
            return;
        gettingSpecialUp = true;
        GameServer.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                if (player.getUsername().equals("leon")) {
                    gettingSpecialUp = true;
                    specpercentage = 100;
                    return;
                }
                if (!player.isOnline() || specpercentage == 100) {
                    gettingSpecialUp = false;
                    stop();
                    return;
                }
                specpercentage += (byte) ((100 - specpercentage) > 9 ? 10 : 100 - specpercentage);
                refreshSpecial();
                if (specpercentage == 100) {
                    gettingSpecialUp = false;
                    stop();
                }
            }
        }, 30000, 30000);
    }

    public void doEmote(int animId, int gfxId, int milliSecondDelay) {
        if (System.currentTimeMillis() < lastEmote) {
            player.getFrames().sendChatMessage(0,
                    "You're already doing an emote!");
            return;
        }
        if (player.getCombat().hasTarget()) {
            player.getFrames().sendChatMessage(0,
                    "You can't make an emote while attacking!");
            return;
        }
        if (!player.getCombat().isSafe(player)) {
            player.sm("You cannot perform an emote outside a safezone.");
        }
        if (animId > -1)
            player.animate(animId);
        if (gfxId > -1)
            player.graphics(gfxId);
        lastEmote = System.currentTimeMillis() + milliSecondDelay;
    }

    public void startHealing() {
        if (healing)
            return;
        healing = true;
        GameServer.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                if (!player.isOnline() || player.getSkills().getHitPoints() <= 0 || player.getSkills().getHitPoints() >= (player.getSkills().getXPForLevel(3) * 10)) {
                    healing = false;
                    stop();
                    return;
                }
                short max = (short) ((player.getSkills().getLevel(3) * 10) * 1.15);
                if (player.getSkills().getHitPoints() < max) {
                    player.getSkills().heal(1);
                }
                if (player.getSkills().getHitPoints() >= (player.getSkills()
                        .getXPForLevel(3) * 10)) {
                    healing = false;
                    stop();
                }
            }
        }, 6000, 6000);
    }

    public void refreshBonuses() {
        short[] bonuses = new short[15];
        for (int i = 0; i < 14; i++) {
            Item item = player.getEquipment().get(i);
            if (item == null)
                continue;
            for (int j = 0; j < 15; j++) {
                bonuses[j] += item.getDefinition().bonus[j];
            }
        }
        bonus = bonuses;
        // TODO
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isSpecialOn() {
        return specialOn;
    }

    public void setSpecialOn(boolean specialOn) {
        this.specialOn = specialOn;
    }

    public byte getAttackStyle() {
        return attackStyle;
    }

    public void setAttackStyle(byte attackStyle) {
        this.attackStyle = attackStyle;
    }

    public short[] getBonus() {
        return bonus;
    }

    public void setBonus(short[] bonus) {
        this.bonus = bonus;
    }

    /*public boolean isAutocasting() {
        return autocasting;
    }

    public void setAutocasting(boolean autocasting) {
        this.autocasting = autocasting;
    }*/

    public int getSpecpercentage() {
        return specpercentage;
    }

    public void setSpecpercentage(byte specpercentage) {
        this.specpercentage = specpercentage;
    }

    public boolean isHealing() {
        return healing;
    }

    public void setHealing(boolean healing) {
        this.healing = healing;
    }

    public long getLastEmote() {
        return lastEmote;
    }

    public void setLastEmote(long lastEmote) {
        this.lastEmote = lastEmote;
    }

    public long getLastFood() {
        return lastFood;
    }

    public void setLastFood(long lastFood) {
        this.lastFood = lastFood;
    }

    public long getLastKarambwam() {//not here
        return lastKarambwam;
    }

    public void setLastKarambwam(long kam) {
        this.lastKarambwam = kam;
    }

    public long getLastPot() {
        return lastPot;
    }

    public void setLastPot(long lastPot) {
        this.lastPot = lastPot;
    }
}
