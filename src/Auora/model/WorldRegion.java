package Auora.model;

/**
 * @author Dalton Harriman (Palidino/Palidino76)
 */

public class WorldRegion {
  /*  public static List<WorldRegion> regions = new ArrayList<WorldRegion>();
    private ArrayList<RSObject> customObjects = new ArrayList<RSObject>();
    private int index;
    private int id;
    private int[][][] clips;
    private RSObject[][][] rsObjects;
    private ArrayList<Player> players;

    public WorldRegion(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public ArrayList<Player> players() {
        return players;
    }

    public void addPlayer(Player p) {
        if (players == null) {
            players = new ArrayList<Player>();
        }
        if (!players.contains(p)) {
            players.add(p);
        }
    }

    public void removePlayer(Player p) {
        if (players == null) {
            return;
        }
        players.remove(p);
        if (players.isEmpty()) {
            players = null;
        }
    }

    private void addClip(int x, int y, int height, int shift) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (clips == null) {
            clips = new int[4][][];
        }
        if (clips[height] == null) {
            clips[height] = new int[64][64];
        }
        clips[height][x - regionAbsX][y - regionAbsY] |= shift;
    }

    private void removeClip(int x, int y, int height, int shift) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (clips == null) {
            return;
        }
        if (clips[height] == null) {
            return;
        }
        clips[height][x - regionAbsX][y - regionAbsY] &= 16777215 - shift;
    }

    private void addRSObject2(int x, int y, int height, RSObject object) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (rsObjects == null) {
            rsObjects = new RSObject[4][][];
        }
        if (rsObjects[height] == null) {
            rsObjects[height] = new RSObject[64][64];
        }
        RSObject oldObject = rsObjects[height][x - regionAbsX][y - regionAbsY];
        if (oldObject != null && object != null && !ObjectDefinitions.forID(object.getId()).hasActions()) {
            return;
        }
        rsObjects[height][x - regionAbsX][y - regionAbsY] = object;
    }

    private int getClip(int x, int y, int height) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (clips == null || clips[height] == null) {
            return 0;
        }
        return clips[height][x - regionAbsX][y - regionAbsY];
    }

    public RSObject getRSObject2(int x, int y, int height) {

        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (rsObjects == null || rsObjects[height] == null) {
            return null;
        }
        return rsObjects[height][x - regionAbsX][y - regionAbsY];
    }

    private void addClipping(int x, int y, int height, int shift) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = ((regionX / 8) << 8) + (regionY / 8);
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                r.addClip(x, y, height, shift);
                break;
            }
        }
    }

    private void removeClipping(int x, int y, int height, int shift) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = ((regionX / 8) << 8) + (regionY / 8);
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                r.removeClip(x, y, height, shift);
                break;
            }
        }
    }

    private void addRSObject(int x, int y, int height, RSObject object) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = ((regionX / 8) << 8) + (regionY / 8);
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                r.addRSObject2(x, y, height, object);
                break;
            }
        }
    }

    private void addClippingForVariableObject(int x, int y, int height, int type, int direction, boolean flag) {
        if (type == 0) {
            if (direction == 0) {
                addClipping(x, y, height, 128);
                addClipping(x - 1, y, height, 8);
            } else if (direction == 1) {
                addClipping(x, y, height, 2);
                addClipping(x, y + 1, height, 32);
            } else if (direction == 2) {
                addClipping(x, y, height, 8);
                addClipping(x + 1, y, height, 128);
            } else if (direction == 3) {
                addClipping(x, y, height, 32);
                addClipping(x, y - 1, height, 2);
            }
        } else if (type == 1 || type == 3) {
            if (direction == 0) {
                addClipping(x, y, height, 1);
                addClipping(x - 1, y, height, 16);
            } else if (direction == 1) {
                addClipping(x, y, height, 4);
                addClipping(x + 1, y + 1, height, 64);
            } else if (direction == 2) {
                addClipping(x, y, height, 16);
                addClipping(x + 1, y - 1, height, 1);
            } else if (direction == 3) {
                addClipping(x, y, height, 64);
                addClipping(x - 1, y - 1, height, 4);
            }
        } else if (type == 2) {
            if (direction == 0) {
                addClipping(x, y, height, 130);
                addClipping(x - 1, y, height, 8);
                addClipping(x, y + 1, height, 32);
            } else if (direction == 1) {
                addClipping(x, y, height, 10);
                addClipping(x, y + 1, height, 32);
                addClipping(x + 1, y, height, 128);
            } else if (direction == 2) {
                addClipping(x, y, height, 40);
                addClipping(x + 1, y, height, 128);
                addClipping(x, y - 1, height, 2);
            } else if (direction == 3) {
                addClipping(x, y, height, 160);
                addClipping(x, y - 1, height, 2);
                addClipping(x - 1, y, height, 8);
            }
        }
        if (flag) {
            if (type == 0) {
                if (direction == 0) {
                    addClipping(x, y, height, 65536);
                    addClipping(x - 1, y, height, 4096);
                } else if (direction == 1) {
                    addClipping(x, y, height, 1024);
                    addClipping(x, y + 1, height, 16384);
                } else if (direction == 2) {
                    addClipping(x, y, height, 4096);
                    addClipping(x + 1, y, height, 65536);
                } else if (direction == 3) {
                    addClipping(x, y, height, 16384);
                    addClipping(x, y - 1, height, 1024);
                }
            }
            if (type == 1 || type == 3) {
                if (direction == 0) {
                    addClipping(x, y, height, 512);
                    addClipping(x - 1, y + 1, height, 8192);
                } else if (direction == 1) {
                    addClipping(x, y, height, 2048);
                    addClipping(x + 1, y + 1, height, 32768);
                } else if (direction == 2) {
                    addClipping(x, y, height, 8192);
                    addClipping(x + 1, y + 1, height, 512);
                } else if (direction == 3) {
                    addClipping(x, y, height, 32768);
                    addClipping(x - 1, y - 1, height, 2048);
                }
            } else if (type == 2) {
                if (direction == 0) {
                    addClipping(x, y, height, 66560);
                    addClipping(x - 1, y, height, 4096);
                    addClipping(x, y + 1, height, 16384);
                } else if (direction == 1) {
                    addClipping(x, y, height, 5120);
                    addClipping(x, y + 1, height, 16384);
                    addClipping(x + 1, y, height, 65536);
                } else if (direction == 2) {
                    addClipping(x, y, height, 20480);
                    addClipping(x + 1, y, height, 65536);
                    addClipping(x, y - 1, height, 1024);
                } else if (direction == 3) {
                    addClipping(x, y, height, 81920);
                    addClipping(x, y - 1, height, 1024);
                    addClipping(x - 1, y, height, 4096);
                }
            }
        }
    }

    private void removeClippingForVariableObject(int x, int y, int height, int type, int direction, boolean flag) {
        if (type == 0) {
            if (direction == 0) {
                removeClipping(x, y, height, 128);
                removeClipping(x - 1, y, height, 8);
            } else if (direction == 1) {
                removeClipping(x, y, height, 2);
                removeClipping(x, y + 1, height, 32);
            } else if (direction == 2) {
                removeClipping(x, y, height, 8);
                removeClipping(x + 1, y, height, 128);
            } else if (direction == 3) {
                removeClipping(x, y, height, 32);
                removeClipping(x, y - 1, height, 2);
            }
        } else if (type == 1 || type == 3) {
            if (direction == 0) {
                removeClipping(x, y, height, 1);
                removeClipping(x - 1, y, height, 16);
            } else if (direction == 1) {
                removeClipping(x, y, height, 4);
                removeClipping(x + 1, y + 1, height, 64);
            } else if (direction == 2) {
                removeClipping(x, y, height, 16);
                removeClipping(x + 1, y - 1, height, 1);
            } else if (direction == 3) {
                removeClipping(x, y, height, 64);
                removeClipping(x - 1, y - 1, height, 4);
            }
        } else if (type == 2) {
            if (direction == 0) {
                removeClipping(x, y, height, 130);
                removeClipping(x - 1, y, height, 8);
                removeClipping(x, y + 1, height, 32);
            } else if (direction == 1) {
                removeClipping(x, y, height, 10);
                removeClipping(x, y + 1, height, 32);
                removeClipping(x + 1, y, height, 128);
            } else if (direction == 2) {
                removeClipping(x, y, height, 40);
                removeClipping(x + 1, y, height, 128);
                removeClipping(x, y - 1, height, 2);
            } else if (direction == 3) {
                removeClipping(x, y, height, 160);
                removeClipping(x, y - 1, height, 2);
                removeClipping(x - 1, y, height, 8);
            }
        }
        if (flag) {
            if (type == 0) {
                if (direction == 0) {
                    removeClipping(x, y, height, 65536);
                    removeClipping(x - 1, y, height, 4096);
                } else if (direction == 1) {
                    removeClipping(x, y, height, 1024);
                    removeClipping(x, y + 1, height, 16384);
                } else if (direction == 2) {
                    removeClipping(x, y, height, 4096);
                    removeClipping(x + 1, y, height, 65536);
                } else if (direction == 3) {
                    removeClipping(x, y, height, 16384);
                    removeClipping(x, y - 1, height, 1024);
                }
            }
            if (type == 1 || type == 3) {
                if (direction == 0) {
                    removeClipping(x, y, height, 512);
                    removeClipping(x - 1, y + 1, height, 8192);
                } else if (direction == 1) {
                    removeClipping(x, y, height, 2048);
                    removeClipping(x + 1, y + 1, height, 32768);
                } else if (direction == 2) {
                    removeClipping(x, y, height, 8192);
                    removeClipping(x + 1, y + 1, height, 512);
                } else if (direction == 3) {
                    removeClipping(x, y, height, 32768);
                    removeClipping(x - 1, y - 1, height, 2048);
                }
            } else if (type == 2) {
                if (direction == 0) {
                    removeClipping(x, y, height, 66560);
                    removeClipping(x - 1, y, height, 4096);
                    removeClipping(x, y + 1, height, 16384);
                } else if (direction == 1) {
                    removeClipping(x, y, height, 5120);
                    removeClipping(x, y + 1, height, 16384);
                    removeClipping(x + 1, y, height, 65536);
                } else if (direction == 2) {
                    removeClipping(x, y, height, 20480);
                    removeClipping(x + 1, y, height, 65536);
                    removeClipping(x, y - 1, height, 1024);
                } else if (direction == 3) {
                    removeClipping(x, y, height, 81920);
                    removeClipping(x, y - 1, height, 1024);
                    removeClipping(x - 1, y, height, 4096);
                }
            }
        }
    }

    private void addClippingForSolidObject(int x, int y, int height, int xLength, int yLength, boolean flag) {
        int clipping = 256;
        if (flag) {
            clipping += 0x20000;
        }
        for (int i = x; i < x + xLength; i++) {
            for (int i2 = y; i2 < y + yLength; i2++) {
                addClipping(i, i2, height, clipping);
            }
        }
    }

    private void removeClippingForSolidObject(int x, int y, int height, int xLength, int yLength, boolean flag) {
        int clipping = 256;
        if (flag) {
            clipping += 0x20000;
        }
        for (int i = x; i < x + xLength; i++) {
            for (int i2 = y; i2 < y + yLength; i2++) {
                removeClipping(i, i2, height, clipping);
            }
        }
    }

    private void addObject(int objectId, int x, int y, int height, int type, int direction, boolean ignoreObjects) {
        if (!ignoreObjects && objectId == -1) {
            removeObject(x, y, height);
        }
        if (objectId == -1) {
            return;
        }
        ObjectDefinitions def = ObjectDefinitions.forID(objectId);
        if (def == null) {
            return;
        }
        int xLength;
        int yLength;
        if (direction != 1 && direction != 3) {
            xLength = def.sizeX;
            yLength = def.sizeY;
        } else {
            xLength = def.sizeY;
            yLength = def.sizeX;
        }
        boolean objectAdded = false;
        if (type == 22) {
            if (def.getNumberOfActions() == 1) {
                if (!ignoreObjects) {
                    removeObject(x, y, height);
                }
                addClipping(x, y, height, 0x200000);
                addRSObject(x, y, height, new RSObject(objectId, x, y, height, type, direction));
                objectAdded = true;
            }
        } else if (type >= 9) {
            if (def.getNumberOfActions() != 0) {
                if (!ignoreObjects) {
                    removeObject(x, y, height);
                }
                addClippingForSolidObject(x, y, height, xLength, yLength, false);// def.solid());
                addRSObject(x, y, height, new RSObject(objectId, x, y, height, type, direction));
                objectAdded = true;
            }
        } else if (type >= 0 && type <= 3) {
            if (def.getNumberOfActions() != 0) {
                if (!ignoreObjects) {
                    removeObject(x, y, height);
                }
                addClippingForVariableObject(x, y, height, type, direction, false);// def.solid());
                addRSObject(x, y, height, new RSObject(objectId, x, y, height, type, direction));
                objectAdded = true;
            }
        }
        if (!objectAdded && def.hasActions()) {
            addRSObject(x, y, height, new RSObject(objectId, x, y, height, type, direction));
        }
    }

    private void removeObject(int x, int y, int height) {
        RSObject oldObj = getRSObject(x, y, height);
        addRSObject(x, y, height, null);
        if (oldObj != null) {
            ObjectDefinitions def = ObjectDefinitions.forID(oldObj.getId());
            int xLength;
            int yLength;
            if (oldObj.getRotation() != 1 && oldObj.getRotation() != 3) {
                xLength = def.sizeX;
                yLength = def.sizeY;
            } else {
                xLength = def.sizeY;
                yLength = def.sizeX;
            }
            if (oldObj.getType() == 22) {
                if (def.getNumberOfActions() == 1) {
                    removeClipping(x, y, height, 0x200000);
                }
            } else if (oldObj.getType() >= 9) {
                if (def.getNumberOfActions() != 0) {
                    removeClippingForSolidObject(x, y, height, xLength, yLength, false);// def.solid());
                }
            } else if (oldObj.getType() >= 0 && oldObj.getType() <= 3) {
                if (def.getNumberOfActions() != 0) {
                    removeClippingForVariableObject(x, y, height, oldObj.getType(), oldObj.getRotation(), false);// true);//,
                    // def.solid());
                }
            }
        }
    }

	
     * private static void loadMap(byte[][] is, int []RegionIds, int P_RegionX,
	 * int P_RegionY) { int i_0_ = is.length; for (int i_1_ = 0; i_1_ < i_0_;
	 * i_1_++) { int[] is_2_ = null; byte[] is_3_ = is[i_1_]; //information of
	 * region1 int i_4_ = RegionIds[i_1_] >> -1670060632; int i_5_ = 0xff &
	 * RegionIds[i_1_]; int i_6_ = -P_RegionX + i_4_ * 64; int i_7_ = -P_RegionY
	 * + 64 * i_5_;
	 * 
	 * if (is_3_ != null) { //TODO
	 * 
	 * }
	 * 
	 * for (int i_9_ = 0; i_9_ < i_0_; i_9_++) { //TODO int i_10_ = (-P_RegionX
	 * + 64 * (RegionIds[i_9_] >> -2023849624)); int i_11_ = (-P_RegionY + 64 *
	 * (0xff & RegionIds[i_9_])); byte[] is_12_ = is[i_9_]; /*if (is_12_ == null
	 * && (Class173.anInt2372 ^ 0xffffffff) > -801) { Class43.method522(-87);
	 * for (int i_13_ = 0; i_13_ < i; i_13_++) Class209.method2754(64, i_11_,
	 * 64, -22467, i_13_, i_10_); }
	 * 
	 * } }
	 

    public void loadRegionObjects(short RegionId, boolean arg0) {
        int absX = (RegionId >> 8) * 64;
        int absY = (RegionId & 0xff) * 64;
        ByteInputStream objectmap = null;
        RSInputStream landscape = null;
        byte[] landscapebytes;
        byte[] objectmapbytes;
        if (!arg0) {
            landscapebytes = CacheManager.getByName(5, "m" + ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));
            objectmapbytes = CacheManager.getByName(5, "l" + ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));
        } else {
            landscapebytes = CacheManager.getByName(5, "um" + ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));
            objectmapbytes = CacheManager.getByName(5, "ul" + ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));
        }
        try {
            int[] xtea_keys = MapData.getMapData().get(RegionId);
            byte[] raw_data_objectmap = null;
            if (xtea_keys != null) {
                if (objectmapbytes != null)
                    if (!arg0)
                        raw_data_objectmap = XTEADecrypter.decryptXTEA(xtea_keys, objectmapbytes, 5,
                                objectmapbytes.length);
                    else
                        raw_data_objectmap = objectmapbytes;
            } else {
                raw_data_objectmap = objectmapbytes;
            }
            if (raw_data_objectmap != null)
                objectmap = new ByteInputStream(new CacheContainer(raw_data_objectmap).decompress());
            if (landscapebytes != null)
                landscape = new RSInputStream(
                        new ByteArrayInputStream(new CacheContainer(landscapebytes).decompress()));
        } catch (OutOfMemoryError e) {
            return;
        } catch (Exception e) {
            return;
        }

        try {
            byte[][][] someArray = new byte[4][64][64];
            if (landscape != null) {
                for (int z = 0; z < (arg0 ? 1 : 4); z++) {
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
                                    someArray[z][localX][localY] = (byte) (v - 49);
                                }
                            }
                        }
                    }
                }
            }
            for (int z = 0; z < (arg0 ? 1 : 4); z++) {
                for (int localX = 0; localX < 64; localX++) {
                    for (int localY = 0; localY < 64; localY++) {
                        if ((someArray[z][localX][localY] & 1) == 1) {
                            int height = z;
                            if ((someArray[1][localX][localY] & 2) == 2) {
                                height--;
                            }
                            if (height >= 0 && height <= 3) {
                                addClipping(absX + localX, absY + localY, height, 0x200000);
                            }
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
                        int z = location >> 12;
                        int objectData = objectmap.readUByte();
                        int type = objectData >> 2;
                        int rotation = objectData & 0x3;
                        if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
                            continue;
                        }
                        if (!arg0) {
                            if ((someArray[1][localX][localY] & 2) == 2) {
                                z--;
                            }
                        }
                        if (z >= 0 && z <= 3) {
                            // addObject(objectId, localX + absX, localY + absY,
                            // z, type, rotation, true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getClipping(int x, int y, int height) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = ((regionX / 8) << 8) + (regionY / 8);
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                return r.getClip(x, y, height);
            }
        }
        return 0x200000;
    }

    public RSObject getRSObject(int x, int y, int height) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = ((regionX / 8) << 8) + (regionY / 8);
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                return r.getRSObject2(x, y, height);
            }
        }
        return null;
    }

    public Player[] getPlayers(int regionX, int regionY) {
        int count = 0;
        for (int x = regionX / 8; x <= (regionX + 12) / 8; x++) {
            for (int y = regionY / 8; y <= (regionY + 12) / 8; y++) {
                WorldRegion r = get((x << 8) + y);
                if (r != null && r.players() != null) {
                    count += r.players().size();
                }
            }
        }
        Player[] players = new Player[count];
        count = 0;
        for (int x = regionX / 8; x <= (regionX + 12) / 8; x++) {
            for (int y = regionY / 8; y <= (regionY + 12) / 8; y++) {
                WorldRegion r = get((x << 8) + y);
                if (r != null && r.players() != null) {
                    for (Player p : r.players()) {
                        players[count++] = p;
                    }
                }
            }
        }
        return players;
    }

    public int[] getNextStep(int baseX, int baseY, int toX, int toY, int height, int xLength, int yLength) {
        int moveX = 0;
        int moveY = 0;
        if (baseX - toX > 0) {
            moveX--;
        } else if (baseX - toX < 0) {
            moveX++;
        }
        if (baseY - toY > 0) {
            moveY--;
        } else if (baseY - toY < 0) {
            moveY++;
        }
        if (canMove(baseX, baseY, baseX + moveX, baseY + moveY, height, xLength, yLength)) {
            return new int[]{baseX + moveX, baseY + moveY};
        } else if (moveX != 0 && canMove(baseX, baseY, baseX + moveX, baseY, height, xLength, yLength)) {
            return new int[]{baseX + moveX, baseY};
        } else if (moveY != 0 && canMove(baseX, baseY, baseX, baseY + moveY, height, xLength, yLength)) {
            return new int[]{baseX, baseY + moveY};
        }
        return new int[]{baseX, baseY};
    }

    public boolean canMove(int startX, int startY, int endX, int endY, int height, int xLength, int yLength) {
        int diffX = endX - startX;
        int diffY = endY - startY;
        int max = Math.max(Math.abs(diffX), Math.abs(diffY));
        for (int ii = 0; ii < max; ii++) {
            int currentX = endX - diffX;
            int currentY = endY - diffY;
            for (int i = 0; i < xLength; i++) {
                for (int i2 = 0; i2 < yLength; i2++) {
                    if (diffX < 0 && diffY < 0) {
                        if ((getClipping(currentX + i - 1, currentY + i2 - 1, height) & 0x128010e) != 0
                                || (getClipping(currentX + i - 1, currentY + i2, height) & 0x1280108) != 0
                                || (getClipping(currentX + i, currentY + i2 - 1, height) & 0x1280102) != 0) {
                            return false;
                        }
                    } else if (diffX > 0 && diffY > 0) {
                        if ((getClipping(currentX + i + 1, currentY + i2 + 1, height) & 0x12801e0) != 0
                                || (getClipping(currentX + i + 1, currentY + i2, height) & 0x1280180) != 0
                                || (getClipping(currentX + i, currentY + i2 + 1, height) & 0x1280120) != 0) {
                            return false;
                        }
                    } else if (diffX < 0 && diffY > 0) {
                        if ((getClipping(currentX + i - 1, currentY + i2 + 1, height) & 0x1280138) != 0
                                || (getClipping(currentX + i - 1, currentY + i2, height) & 0x1280108) != 0
                                || (getClipping(currentX + i, currentY + i2 + 1, height) & 0x1280120) != 0) {
                            return false;
                        }
                    } else if (diffX > 0 && diffY < 0) {
                        if ((getClipping(currentX + i + 1, currentY + i2 - 1, height) & 0x1280183) != 0
                                || (getClipping(currentX + i + 1, currentY + i2, height) & 0x1280180) != 0
                                || (getClipping(currentX + i, currentY + i2 - 1, height) & 0x1280102) != 0) {
                            return false;
                        }
                    } else if (diffX > 0 && diffY == 0) {
                        if ((getClipping(currentX + i + 1, currentY + i2, height) & 0x1280180) != 0) {
                            return false;
                        }
                    } else if (diffX < 0 && diffY == 0) {
                        if ((getClipping(currentX + i - 1, currentY + i2, height) & 0x1280108) != 0) {
                            return false;
                        }
                    } else if (diffX == 0 && diffY > 0) {
                        if ((getClipping(currentX + i, currentY + i2 + 1, height) & 0x1280120) != 0) {
                            return false;
                        }
                    } else if (diffX == 0 && diffY < 0) {
                        if ((getClipping(currentX + i, currentY + i2 - 1, height) & 0x1280102) != 0) {
                            return false;
                        }
                    }
                }
            }
            if (diffX < 0) {
                diffX++;
            } else if (diffX > 0) {
                diffX--;
            }
            if (diffY < 0) {
                diffY++;
            } else if (diffY > 0) {
                diffY--;
            }
        }
        return true;
    }

    public WorldRegion get(int regionId) {
        return regions.get(regionId);
    }

    public boolean withinDistanceIgnoreHeight(Player p, RSObject object, int distance) {
        return Misc.getDistance(p.getLocation().getLocalX(), p.getLocation().getLocalY(), object.getX(),
                object.getY()) <= 104;
    }

    public boolean withinDistance(Player p, int newX, int newY, int distance) {
        return Misc.getDistance(p.getLocation().getLocalX(), p.getLocation().getLocalY(), newX, newY) <= 104;
    }

    public void displayObjects(Player p) {
        for (RSObject object : customObjects) {
            if (withinDistanceIgnoreHeight(p, object, 104)) {
                // player.getActionSender().sendObject(object.id(), object.x(),
                // object.y(), object.type(), object.direction());
            }
        }
    }

    *//**
     * Returns the contents of the file in a byte array
     *
     * @param file File this method should read
     * @return byte[] Returns a byte[] array of the contents of the file
     *//*
    private byte[] getBytesFromFile(File file) throws IOException {

        InputStream is = new FileInputStream(file);
        // System.out.println("\nDEBUG: FileInputStream is " + file);

        // Get the size of the file
        long length = file.length();
        // System.out.println("DEBUG: Length of " + file + " is " + length +
        // "\n");

		
         * You cannot create an array using a long type. It needs to be an int
		 * type. Before converting to an int type, check to ensure that file is
		 * not loarger than Integer.MAX_VALUE;
		 
        if (length > Integer.MAX_VALUE) {
            System.out.println("File is too large to process");
            return null;
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while ((offset < bytes.length) && ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {

            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;

    }*/
}