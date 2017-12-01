package Auora.model.player;

public class GpiPlayer {
    Player player;
    boolean inScreen;
    byte status;
    private boolean forceAppearence;
    private int locationHash;
    private boolean needUpdate;

    public GpiPlayer() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getLocation() {
        return locationHash;
    }

    public void setLocation(int location) {
        this.locationHash = location;
    }

    public boolean isForceAppearence() {
        return forceAppearence;
    }

    public void setForceAppearence(boolean forceAppearence) {
        this.forceAppearence = forceAppearence;
    }

    public boolean isInScreen() {
        return inScreen;
    }

    public void setInScreen(boolean inScreen) {
        this.inScreen = inScreen;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }
}