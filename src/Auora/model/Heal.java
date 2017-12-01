package Auora.model;

/**
 * Represents 'still graphics'.
 *
 * @author Jonathan spawnscape
 */
public class Heal {

    private short healDelay;
    private byte barDelay, healSpeed;

    public Heal(short healdelay, byte bardelay, byte healspeed) {
        healDelay = healdelay;
        barDelay = bardelay;
        healSpeed = healspeed;
    }

    public short getHealDelay() {
        return healDelay;
    }

    public void setHealDelay(short healDelay) {
        this.healDelay = healDelay;
    }

    public byte getBarDelay() {
        return barDelay;
    }

    public void setBarDelay(byte barDelay) {
        this.barDelay = barDelay;
    }

    public byte getHealSpeed() {
        return healSpeed;
    }

    public void setHealSpeed(byte healSpeed) {
        this.healSpeed = healSpeed;
    }

}
