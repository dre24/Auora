package Auora.rsobjects;

import Auora.GameServer;
import Auora.events.Task;
import Auora.model.RegionMap;
import Auora.model.World;
import Auora.model.player.Player;
import Auora.rscache.*;
import Auora.util.MapData;
import Auora.util.RSObject;
import Auora.util.RSTile;

import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * @author Dragonkk
 */
public class RSObjectsRegion {

    private static Map<Short, RSObjectsRegion> loadedRegionObjects = new HashMap<Short, RSObjectsRegion>();
    protected RegionMap map;
    protected RegionMap clipedOnlyMap;
    protected int loadMapStage;
    private List<RSObject> objects;
    private List<RSObject> spawnobjects;
    private List<RSObject> removeobjects;
    private short regionId;

    public RSObjectsRegion(short regionId) {
        this.regionId = regionId;
        objects = new ArrayList<RSObject>();
        spawnobjects = new ArrayList<RSObject>();
        removeobjects = new ArrayList<RSObject>();
    }

    public static RSObjectsRegion getRegion(short regionId, boolean load) {
        RSObjectsRegion region = loadedRegionObjects.get(regionId);
        if (region == null)
            loadedRegionObjects.put(regionId, region = new RSObjectsRegion(regionId));
        if (load)
            region.loadRegion();
        return region;

    }

    public static RSObjectsRegion getRegion(short regionId) {
        return getRegion(regionId, true);
    }

    public static RSObject objectAt(int objectId, RSTile location) {
        return getRegion((short) (((location.getRegionX() / 8) << 8) + (location.getRegionY() / 8))).getObjectAt(objectId, location);
    }

    public static boolean objectExistsAt(int objectId, RSTile location) {
        return getRegion((short) (((location.getRegionX() / 8) << 8) + (location.getRegionY() / 8))).containsObjectAt(objectId, location);
    }

    public static void removeObjectAt(RSTile tile) {
        RSObjectsRegion region = getRegion((short) (((tile.getRegionX() / 8) << 8) + (tile.getRegionY() / 8)));
        final RSObject lastObject = region.getRealObjectAt(tile);
        if (lastObject == null)
            return;
        region.deleteObject(lastObject);
    }

    public static void removeObject(RSObject object) {
        RSObjectsRegion region = getRegion((short) (((object.getLocation().getRegionX() / 8) << 8) + (object.getLocation().getRegionY() / 8)));
    /*	final RSObject lastObject = region.getRealObjectByObject(object);
		if(lastObject == null)
			return;*/
        region.deleteObject(object);
    }

    public static void putObject(RSObject object) {
        putObject(object, -1);
    }

    public static void putObject(RSObject object, long expireTime) {
        getRegion((short) (((object.getLocation().getRegionX() / 8) << 8) + (object.getLocation().getRegionY() / 8))).addObject(object, expireTime);
    }

    public static void loadMapObjects(Player player) {
        boolean forceSend = true;
        if ((((player.getLocation().getRegionX() / 8) == 48) || ((player.getLocation().getRegionX() / 8) == 49)) && ((player.getLocation().getRegionY() / 8) == 48)) {
            forceSend = false;
        }
        if (((player.getLocation().getRegionX() / 8) == 48) && ((player.getLocation().getRegionY() / 8) == 148)) {
            forceSend = false;
        }
        for (int xCalc = (player.getLocation().getRegionX() - 6) / 8; xCalc <= ((player.getLocation().getRegionX() + 6) / 8); xCalc++) {
            for (int yCalc = (player.getLocation().getRegionY() - 6) / 8; yCalc <= ((player.getLocation().getRegionY() + 6) / 8); yCalc++) {
                short regionId = (short) (yCalc + (xCalc << 8));
                if (forceSend || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147) && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47)))) {
                    getRegion(regionId).loadSpawnObjects(player);
                    getRegion(regionId).loadRemoveObjects(player);
                }
            }
        }
    }

    public int getLoadMapStage() {
        return loadMapStage;
    }

    public void setLoadMapStage(int loadMapStage) {
        this.loadMapStage = loadMapStage;
    }

    public RegionMap forceGetRegionMapClipedOnly() {
        if (clipedOnlyMap == null)
            clipedOnlyMap = new RegionMap(regionId, true);
        return clipedOnlyMap;
    }

    public RegionMap forceGetRegionMap() {
        if (map == null)
            map = new RegionMap(regionId, false);
        return map;
    }

    public RegionMap getRegionMap() {
        return map;
    }

    public int getMask(int plane, int localX, int localY) {
        if (map == null || getLoadMapStage() != 2)
            return -1; // cliped tile
        return map.getMasks()[plane][localX][localY];
    }

    public int getMaskClipedOnly(int plane, int localX, int localY) {
        if (clipedOnlyMap == null || getLoadMapStage() != 2)
            return -1; // cliped tile
        return clipedOnlyMap.getMasks()[plane][localX][localY];
    }

    public void loadSpawnObjects(Player player) {
        for (RSObject object : spawnobjects) {
            player.getFrames().addMapObject(object);
            RSObject lastObject = getRealObjectAt(object.getLocation());
            if (lastObject != null && lastObject.getType() != object.getType())
                player.getFrames().removeMapObject(lastObject);
        }
    }

    public void loadRemoveObjects(Player player) {
        for (RSObject object : removeobjects) {
            player.getFrames().removeMapObject(object);
        }
    }


    public void deleteObject(RSObject lastObject) {
        removeobjects.add(lastObject);
        unclip(lastObject, lastObject.getLocation().getXInRegion(), lastObject.getLocation().getYInRegion());
        for (Player player : World.getPlayers()) {

            boolean forceSend = true;
            if ((((player.getLocation().getRegionX() / 8) == 48) || ((player.getLocation().getRegionX() / 8) == 49)) && ((player.getLocation().getRegionY() / 8) == 48)) {
                forceSend = false;
            }
            if (((player.getLocation().getRegionX() / 8) == 48) && ((player.getLocation().getRegionY() / 8) == 148)) {
                forceSend = false;
            }
            for (int xCalc = (player.getLocation().getRegionX() - 6) / 8; xCalc <= ((player.getLocation().getRegionX() + 6) / 8); xCalc++) {
                for (int yCalc = (player.getLocation().getRegionY() - 6) / 8; yCalc <= ((player.getLocation().getRegionY() + 6) / 8); yCalc++) {
                    short regionId = (short) (yCalc + (xCalc << 8));
                    if (forceSend || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147) && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47)))) {
                        if (regionId == this.regionId) {
                            player.getFrames().removeMapObject(lastObject);
                            break;
                        }
                    }
                }
            }
        }
    }

    //permanent
    public void addObject(final RSObject object) {
        addObject(object, -1);
    }

    public void addObject(final RSObject object, long expireTime) {
        final RSObject lastObject = getObjectAt(object.getLocation()); //getRealObjectAt

        spawnobjects.add(object);
        if (lastObject != null && object.getType() == lastObject.getType())
            unclip(lastObject, lastObject.getLocation().getXInRegion(), lastObject.getLocation().getYInRegion());

        clip(object, object.getLocation().getXInRegion(), object.getLocation().getYInRegion());
        for (Player player : World.getPlayers()) {

            boolean forceSend = true;
            if ((((player.getLocation().getRegionX() / 8) == 48) || ((player.getLocation().getRegionX() / 8) == 49)) && ((player.getLocation().getRegionY() / 8) == 48)) {
                forceSend = false;
            }
            if (((player.getLocation().getRegionX() / 8) == 48) && ((player.getLocation().getRegionY() / 8) == 148)) {
                forceSend = false;
            }
            for (int xCalc = (player.getLocation().getRegionX() - 6) / 8; xCalc <= ((player.getLocation().getRegionX() + 6) / 8); xCalc++) {
                for (int yCalc = (player.getLocation().getRegionY() - 6) / 8; yCalc <= ((player.getLocation().getRegionY() + 6) / 8); yCalc++) {
                    short regionId = (short) (yCalc + (xCalc << 8));
                    if (forceSend || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147) && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47)))) {
                        if (regionId == this.regionId) {
                            player.getFrames().addMapObject(object);
                            break;
                        }
                    }
                }
            }
        }
        if (expireTime >= 0) {
            GameServer.getWorldExecutor().schedule(new Task() {
                @Override
                public void run() {
                    spawnobjects.remove(object);
                    unclip(object, object.getLocation().getXInRegion(), object.getLocation().getYInRegion());
                    if (lastObject != null && object.getType() == lastObject.getType())
                        clip(lastObject, lastObject.getLocation().getXInRegion(), lastObject.getLocation().getYInRegion());
                    for (Player player : World.getPlayers()) {

                        boolean forceSend = true;
                        if ((((player.getLocation().getRegionX() / 8) == 48) || ((player.getLocation().getRegionX() / 8) == 49)) && ((player.getLocation().getRegionY() / 8) == 48)) {
                            forceSend = false;
                        }
                        if (((player.getLocation().getRegionX() / 8) == 48) && ((player.getLocation().getRegionY() / 8) == 148)) {
                            forceSend = false;
                        }
                        for (int xCalc = (player.getLocation().getRegionX() - 6) / 8; xCalc <= ((player.getLocation().getRegionX() + 6) / 8); xCalc++) {
                            for (int yCalc = (player.getLocation().getRegionY() - 6) / 8; yCalc <= ((player.getLocation().getRegionY() + 6) / 8); yCalc++) {
                                short thisregionId = (short) (yCalc + (xCalc << 8));
                                if (forceSend || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147) && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47)))) {
                                    if (thisregionId == regionId) {
                                        //if it was replaced, just readds when adding.
                                        if (lastObject != null && lastObject.getType() == object.getType())
                                            player.getFrames().addMapObject(lastObject);
                                        else
                                            player.getFrames().removeMapObject(object);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }, expireTime);
        }
    }

    public boolean containsObjectAt(int objectId, RSTile location) {
        for (RSObject object : spawnobjects) {
            if (object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ()) {
                if (objectId == object.getId())
                    return true;
                return false;
            }
        }
        for (RSObject object : objects) {
            if (objectId == object.getId() && object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ())
                return true;
        }
        return false;
    }

    public RSObject getObjectAt(int objectId, RSTile location) {
        for (RSObject object : spawnobjects) {
            if (object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ() && objectId == object.getId())
                return object;
        }
        for (RSObject object : objects) {
            if (object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ() && objectId == object.getId())
                return object;
        }
        return null;
    }


    public RSObject getRealObjectByObject(RSObject realobject) {
        for (RSObject object : objects) {
            if (object.getX() == realobject.getX() && object.getY() == realobject.getY() && object.getZ() == realobject.getZ() && (object.getType() == realobject.getType() || object.getId() == realobject.getId()))
                return object;
        }
        return null;
    }

    public RSObject getRealObjectAt(RSTile location) {
        for (RSObject object : objects) {
            if (object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ())
                return object;
        }
        return null;
    }

    public RSObject getObjectAt(RSTile location) {
        for (RSObject object : spawnobjects) {
            if (object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ())
                return object;
        }
        for (RSObject object : objects) {
            if (object.getX() == location.getX() && object.getY() == location.getY() && object.getZ() == location.getZ())
                return object;
        }
        return null;
    }


    private void loadRegion() {
        if (getLoadMapStage() != 0)
            return;
        this.setLoadMapStage(1);
        try {
            int absX = (regionId >> 8) * 64;
            int absY = (regionId & 0xff) * 64;
            ByteInputStream objectmap = null;
            RSInputStream landscape = null;
            byte[] landscapebytes = CacheManager.getByName(5, "m" + ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));
            byte[] objectmapbytes = CacheManager.getByName(5, "l" + ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));

            int[] xtea_keys = MapData.getMapData().get(regionId);
            byte[] raw_data_objectmap = null;

            //xteas 0,0,0,0 lol shouldnt exist, thats same as no xtea.
            if (xtea_keys != null && !Arrays.equals(xtea_keys, new int[4])) {
                if (objectmapbytes != null)
                    raw_data_objectmap = XTEADecrypter.decryptXTEA(xtea_keys, objectmapbytes, 5, objectmapbytes.length);
                else
                    raw_data_objectmap = objectmapbytes;
            } else
                raw_data_objectmap = objectmapbytes;
            if (raw_data_objectmap != null) //makes sense. since wrong xteas for this area anyway
                objectmap = new ByteInputStream(new CacheContainer(raw_data_objectmap).decompress());
            if (landscapebytes != null)
                landscape = new RSInputStream(
                        new ByteArrayInputStream(new CacheContainer(landscapebytes).decompress()));
            byte[][][] mapSettings = new byte[4][64][64];
            if (landscape != null) {
                for (int z = 0; z < 4; z++) {
                    for (int localX = 0; localX < 64; localX++) {
                        for (int localY = 0; localY < 64; localY++) {
                            while (true) {
                                int v = landscape.readByte() & 0xff;
                                if (v == 0) {
                                    break;
                                } else if (v == 1) {
                                    landscape.readByte();
                                    break;
                                } else if (v <= 49) {
                                    landscape.readByte();

                                } else if (v <= 81) {
                                    mapSettings[z][localX][localY] = (byte) (v - 49);
                                }
                            }
                        }
                    }
                }
                // clips non walkable tiles
                for (int plane = 0; plane < 4; plane++) {
                    for (int x = 0; x < 64; x++) {
                        for (int y = 0; y < 64; y++) {
                            if ((mapSettings[plane][x][y] & 0x1) == 1) {
                                int realPlane = plane;
                                if ((mapSettings[1][x][y] & 2) == 2)
                                    realPlane--;
                                if (realPlane >= 0)
                                    forceGetRegionMap().addUnwalkable(realPlane, x, y);
                            }
                        }
                    }
                }
            } else { // no landscape so make it not clipable
                for (int plane = 0; plane < 4; plane++) {
                    for (int x = 0; x < 64; x++) {
                        for (int y = 0; y < 64; y++) {
                            forceGetRegionMap().addUnwalkable(plane, x, y);
                        }
                    }
                }
            }
            if (objectmap != null) {
                int objectId = -1;
                int incr;
                while ((incr = objectmap.readSmart2()) != 0) {
                    objectId += incr;
                    int location = 0;
                    int incr2;
                    while ((incr2 = objectmap.readSmart()) != 0) {
                        location += incr2 - 1;
                        int localX = (location >> 6 & 0x3f);
                        int localY = (location & 0x3f);
                        int plane = location >> 12;
                        int objectData = objectmap.readUByte();
                        int type = objectData >> 2;
                        int rotation = objectData & 0x3;
                        if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
                            continue;
                        }
                        int objectPlane = plane;
                        if ((mapSettings[1][localX][localY] & 2) == 2) {
                            objectPlane--;
                        }
                        if (objectPlane < 0 || objectPlane >= 4 || plane < 0 || plane >= 4)
                            continue;
                        if (objectPlane >= 0 && objectPlane <= 3) {
                            RSObject object = new RSObject(objectId, localX + absX, localY + absY, objectPlane, type,
                                    rotation);
                            objects.add(object);
                            clip(object, localX, localY);
                        }
                    }
                }
            }
        } catch (Throwable e) {
            //System.out.println("failed to load region: "+this.regionId+", "+Arrays.toString(MapData.getMapData().get(regionId)));
            //	e.printStackTrace();
        }
        this.setLoadMapStage(2);
    }


    public void clip(RSObject object, int x, int y) {
        if (object.getId() == -1) //dont clip or noclip with id -1
            return;
        if (map == null)
            map = new RegionMap(regionId, false);
        if (clipedOnlyMap == null)
            clipedOnlyMap = new RegionMap(regionId, true);
        int plane = object.getLocation().getZ();
        int type = object.getType();
        int rotation = object.getRotation();
        if (x < 0 || y < 0 || x >= map.getMasks()[plane].length || y >= map.getMasks()[plane][x].length)
            return;
        ObjectDefinitions objectDefinition = ObjectDefinitions.forID(object.getId()); // load
        // here

        if (type == 22 ? objectDefinition.getClipType() != 1 : objectDefinition.getClipType() == 0)
            return;
        if (type >= 0 && type <= 3) {
            if (!objectDefinition.isIgnoreClipOnAlternativeRoute()) //disabled those walls for now since theyre guard corners, temporary fix
                map.addWall(plane, x, y, type, rotation, objectDefinition.isProjectileCliped(), !objectDefinition.isIgnoreClipOnAlternativeRoute());
            if (objectDefinition.isProjectileCliped())
                clipedOnlyMap.addWall(plane, x, y, type, rotation, objectDefinition.isProjectileCliped(), !objectDefinition.isIgnoreClipOnAlternativeRoute());
        } else if (type >= 9 && type <= 21) {
            int sizeX;
            int sizeY;
            if (rotation != 1 && rotation != 3) {
                sizeX = objectDefinition.getSizeX();
                sizeY = objectDefinition.getSizeY();
            } else {
                sizeX = objectDefinition.getSizeY();
                sizeY = objectDefinition.getSizeX();
            }
            map.addObject(plane, x, y, sizeX, sizeY, objectDefinition.isProjectileCliped(), !objectDefinition.isIgnoreClipOnAlternativeRoute());
            if (objectDefinition.isProjectileCliped())
                clipedOnlyMap.addObject(plane, x, y, sizeX, sizeY, objectDefinition.isProjectileCliped(), !objectDefinition.isIgnoreClipOnAlternativeRoute());
        } else if (type == 22) {
            map.addFloor(plane, x, y); // dont ever fucking think about removing it..., some floor deco objects DOES BLOCK WALKING
        }
    }

    public void unclip(int plane, int x, int y) {
        if (map == null)
            map = new RegionMap(regionId, false);
        if (clipedOnlyMap == null)
            clipedOnlyMap = new RegionMap(regionId, true);
        map.setMask(plane, x, y, 0);
    }

    public void unclip(RSObject object, int x, int y) {
        if (object.getId() == -1) //dont clip or noclip with id -1
            return;
        if (map == null)
            map = new RegionMap(regionId, false);
        if (clipedOnlyMap == null)
            clipedOnlyMap = new RegionMap(regionId, true);
        int plane = object.getLocation().getZ();
        int type = object.getType();
        int rotation = object.getRotation();
        if (x < 0 || y < 0 || x >= map.getMasks()[plane].length || y >= map.getMasks()[plane][x].length)
            return;
        ObjectDefinitions objectDefinition = ObjectDefinitions.forID(object.getId()); // load
        // here
        if (type == 22 ? objectDefinition.getClipType() != 1 : objectDefinition.getClipType() == 0)
            return;
        if (type >= 0 && type <= 3) {
            map.removeWall(plane, x, y, type, rotation, objectDefinition.isProjectileCliped(), !objectDefinition.isIgnoreClipOnAlternativeRoute());
            if (objectDefinition.isProjectileCliped())
                clipedOnlyMap.removeWall(plane, x, y, type, rotation, objectDefinition.isProjectileCliped(), !objectDefinition.isIgnoreClipOnAlternativeRoute());
        } else if (type >= 9 && type <= 21) {
            int sizeX;
            int sizeY;
            if (rotation != 1 && rotation != 3) {
                sizeX = objectDefinition.getSizeX();
                sizeY = objectDefinition.getSizeY();
            } else {
                sizeX = objectDefinition.getSizeY();
                sizeY = objectDefinition.getSizeX();
            }
            map.removeObject(plane, x, y, sizeX, sizeY, objectDefinition.isProjectileCliped(), !objectDefinition.isIgnoreClipOnAlternativeRoute());
            if (objectDefinition.isProjectileCliped())
                clipedOnlyMap.removeObject(plane, x, y, sizeX, sizeY, objectDefinition.isProjectileCliped(), !objectDefinition.isIgnoreClipOnAlternativeRoute());
        } else if (type == 22) {
            map.removeFloor(plane, x, y);
        }
    }


}
