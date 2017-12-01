package Auora.model.player;

import Auora.model.World;
import Auora.util.RSTile;

public class Region {

    private transient boolean didTeleport;
    private transient boolean DidMapRegionChange;
    private transient boolean UsingStaticRegion;
    private transient boolean NeedReload;
    private transient boolean needLoadObjects;
    private transient RSTile lastMapRegion;
    private transient int[][][][] Palletes = new int[4][4][13][13];
    private transient RSTile lastLocation;
    private transient Player player;

    public Region(Player player) {
        this.player = player;
        this.setLastLocation(RSTile.createRSTile(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getStaticLocation()));
    }

    public void teleportNoCheck(int coordX, int coordY, int height, int staticLocation) {
        this.player.getWalk().reset(true);
        RSTile futurelocation = RSTile.createRSTile((short) (coordX), (short) (coordY), (byte) height, staticLocation);
        if ((this.getLastMapRegion().getRegionX() - futurelocation.getRegionX()) >= 4 || (this.getLastMapRegion().getRegionX() - futurelocation.getRegionX()) <= -4) {
            this.setDidMapRegionChange(true);
        } else if ((this.getLastMapRegion().getRegionY() - futurelocation.getRegionY()) >= 4 || (this.getLastMapRegion().getRegionY() - futurelocation.getRegionY()) <= -4) {
            this.setDidMapRegionChange(true);
        }
        if (!this.isDidMapRegionChange()) {
            if (this.getLastMapRegion().getStaticLocation() != futurelocation.getStaticLocation()) {
                this.setDidMapRegionChange(true);
                this.setNeedReload(true);
            }
            this.player.getFrames().teleOnMapRegion(futurelocation.getLocalX(), futurelocation.getLocalY(), height);
        }
        this.setLastLocation(RSTile.createRSTile(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getStaticLocation()));
        player.getLocation().set((short) (coordX), (short) (coordY), (byte) height, staticLocation);
        World.getGlobalItemsManager().showAllGlobalItems(player);
        this.setDidTeleport(true);
        this.setUsingStaticRegion(false);
    }

    public void teleport(int coordX, int coordY, int height, int staticLocation) {
        this.player.getWalk().reset(true);
        RSTile futurelocation = RSTile.createRSTile((short) (coordX), (short) (coordY), (byte) height, staticLocation);
        if ((this.getLastMapRegion().getRegionX() - futurelocation.getRegionX()) >= 4 || (this.getLastMapRegion().getRegionX() - futurelocation.getRegionX()) <= -4) {
            this.setDidMapRegionChange(true);
        } else if ((this.getLastMapRegion().getRegionY() - futurelocation.getRegionY()) >= 4 || (this.getLastMapRegion().getRegionY() - futurelocation.getRegionY()) <= -4) {
            this.setDidMapRegionChange(true);
        }
        if (!this.isDidMapRegionChange()) {
            if (this.getLastMapRegion().getStaticLocation() != futurelocation.getStaticLocation()) {
                this.setDidMapRegionChange(true);
                this.setNeedReload(true);
            }
            this.player.getFrames().teleOnMapRegion(futurelocation.getLocalX(), futurelocation.getLocalY(), height);
        }
        this.setLastLocation(RSTile.createRSTile(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getStaticLocation()));
        player.getLocation().set((short) (coordX), (short) (coordY), (byte) height, staticLocation);
        World.getGlobalItemsManager().showAllGlobalItems(player);
        this.setDidTeleport(true);
        this.setUsingStaticRegion(false);
    }

    public void teleport(RSTile tile) {
        this.player.getWalk().reset(false);
        RSTile futurelocation = RSTile.createRSTile(tile.getX(), tile.getY(), tile.getZ(), 0);
        if ((this.getLastMapRegion().getRegionX() - futurelocation.getRegionX()) >= 4 || (this.getLastMapRegion().getRegionX() - futurelocation.getRegionX()) <= -4) {
            this.setDidMapRegionChange(true);
        } else if ((this.getLastMapRegion().getRegionY() - futurelocation.getRegionY()) >= 4 || (this.getLastMapRegion().getRegionY() - futurelocation.getRegionY()) <= -4) {
            this.setDidMapRegionChange(true);
        }
        if (!this.isDidMapRegionChange()) {
            if (this.getLastMapRegion().getStaticLocation() != futurelocation.getStaticLocation()) {
                this.setDidMapRegionChange(true);
                this.setNeedReload(true);
            }
            this.player.getFrames().teleOnMapRegion(futurelocation.getLocalX(), futurelocation.getLocalY(), tile.getZ());
        }
        player.setLocation(RSTile.createRSTile(tile.getX(), tile.getY(), tile.getZ()));
        this.setLastLocation(RSTile.createRSTile(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getStaticLocation()));
        player.getLocation().set(tile.getX(), tile.getY(), tile.getZ(), 0);
        World.getGlobalItemsManager().showAllGlobalItems(player);
        this.setDidTeleport(true);
        this.setUsingStaticRegion(false);
    }

    public void teleport(int coordX, int coordY, int height, int staticLocation, int[][][][] palletes) {
    /*if(this.player.isDead()) {
    return;
	}*/
        this.player.getWalk().reset(true);
        RSTile futurelocation = RSTile.createRSTile((short) (coordX), (short) (coordY), (byte) height, staticLocation);
        if ((this.getLastMapRegion().getRegionX() - futurelocation.getRegionX()) >= 4 || (this.getLastMapRegion().getRegionX() - futurelocation.getRegionX()) <= -4) {
            this.setDidMapRegionChange(true);
        } else if ((this.getLastMapRegion().getRegionY() - futurelocation.getRegionY()) >= 4 || (this.getLastMapRegion().getRegionY() - futurelocation.getRegionY()) <= -4) {
            this.setDidMapRegionChange(true);
        }
        if (!this.isDidMapRegionChange()) {
            if (this.getLastMapRegion().getStaticLocation() != futurelocation.getStaticLocation()) {
                this.setDidMapRegionChange(true);
                this.setNeedReload(true);
            } else {
                if (palletes != null)
                    for (int realX = 0; realX < 13; realX++) {
                        for (int realY = 0; realY < 13; realY++) {
                            for (int realZ = 0; realZ < 4; realZ++) {
                                for (int slot = 0; slot < 4; slot++) {
                                    if (this.getPalletes()[slot][realZ][realX][realY] != palletes[slot][realZ][realX][realY]) {
                                        this.setDidMapRegionChange(true);
                                        this.setNeedReload(true);
                                        break;
                                    }

                                }
                            }
                        }
                    }
            }


            // this.player.getFrames().teleOnMapRegion(futurelocation.getLocalX(), futurelocation.getLocalY(), height);
        }
        this.setLastLocation(RSTile.createRSTile(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getStaticLocation()));
        player.getLocation().set((short) (coordX), (short) (coordY), (byte) height, staticLocation);
        World.getGlobalItemsManager().showAllGlobalItems(player);
        this.setDidTeleport(true);
        this.setUsingStaticRegion(true);
        if (palletes != null)
            this.setPalletes(palletes);
    }


    public void reset() {
        if (player.getWalk().isDidTele())
            this.setDidTeleport(false);
        this.setDidMapRegionChange(false);
        this.setNeedReload(false);
    }

    public boolean isDidMapRegionChange() {
        return DidMapRegionChange;
    }

    public void setDidMapRegionChange(boolean didMapRegionChange) {
        DidMapRegionChange = didMapRegionChange;
    }

    public int[][][][] getPalletes() {
        return Palletes;
    }

    public void setPalletes(int[][][][] palletes) {
        Palletes = palletes;
    }

    public RSTile getLastMapRegion() {
        return lastMapRegion;
    }

    public void setLastMapRegion(RSTile lastMapRegion) {
        this.lastMapRegion = lastMapRegion;
    }

    public boolean isDidTeleport() {
        return didTeleport;
    }

    public void setDidTeleport(boolean didTeleport) {
        this.didTeleport = didTeleport;
    }

    public boolean isUsingStaticRegion() {
        return UsingStaticRegion;
    }

    public void setUsingStaticRegion(boolean usingStaticRegion) {
        UsingStaticRegion = usingStaticRegion;
    }

    public boolean isNeedReload() {
        return NeedReload;
    }

    public void setNeedReload(boolean needReload) {
        NeedReload = needReload;
    }

    public RSTile getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(RSTile lastLocation) {
        this.lastLocation = lastLocation;
    }

    public boolean isNeedLoadObjects() {
        return needLoadObjects;
    }

    public void setNeedLoadObjects(boolean needLoadObjects) {
        this.needLoadObjects = needLoadObjects;
    }

}
