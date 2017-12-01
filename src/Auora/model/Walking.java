package Auora.model;

import Auora.model.npc.Npc;
import Auora.model.player.Player;
import Auora.model.route.Flags;
import Auora.model.route.RouteFinder;
import Auora.model.route.RouteStrategy;
import Auora.rsobjects.RSObjectsRegion;
import Auora.util.Misc;
import Auora.util.RSTile;

/**
 * Handles walking.
 *
 * @author Graham
 */
public class Walking {

    static final int SIZE = 50;
    public boolean isFollowing;
    public int wQueueReadPtr = 0;
    public int wQueueWritePtr = 0;
    public Point[] walkingQueue = new Point[SIZE];
    private byte runEnergy = 100;
    private byte WalkDir = -1;
    private byte RunDir = -1;
    private RSTile lastWalkFinalPosition;
    private Entity entity;
    private boolean didTele;
    private boolean isRunning = false, isRunToggled = false;

    public Walking(Entity entity) {
        this.entity = entity;
        setWalkDir((byte) -1);
        setRunDir((byte) -1);
        for (int i = 0; i < SIZE; i++) {
            walkingQueue[i] = new Point();
            walkingQueue[i].x = 0;
            walkingQueue[i].y = 0;
            walkingQueue[i].dir = -1;
        }
        reset(true);
    }

    public static final boolean checkProjectileStep(int plane, int x, int y, int dir, int size) {
        int xOffset = Misc.DIRECTION_DELTA_X[dir];
        int yOffset = Misc.DIRECTION_DELTA_Y[dir];
        /*
		 *
		 * int rotation = getRotation(plane,x+xOffset,y+yOffset); if(rotation !=
		 * 0) { dir += rotation; if(dir >= Utils.DIRECTION_DELTA_X.length) dir =
		 * dir - (Utils.DIRECTION_DELTA_X.length-1); xOffset =
		 * Utils.DIRECTION_DELTA_X[dir]; yOffset = Utils.DIRECTION_DELTA_Y[dir];
		 * }
		 */
        if (size == 1) {
            int mask = getClipedOnlyMask(plane, x + Misc.DIRECTION_DELTA_X[dir], y + Misc.DIRECTION_DELTA_Y[dir]);
            if (xOffset == -1 && yOffset == 0)
                return (mask & 0x42240000) == 0;
            if (xOffset == 1 && yOffset == 0)
                return (mask & 0x60240000) == 0;
            if (xOffset == 0 && yOffset == -1)
                return (mask & 0x40a40000) == 0;
            if (xOffset == 0 && yOffset == 1)
                return (mask & 0x48240000) == 0;
            if (xOffset == -1 && yOffset == -1) {
                return (mask & 0x43a40000) == 0 && (getClipedOnlyMask(plane, x - 1, y) & 0x42240000) == 0 && (getClipedOnlyMask(plane, x, y - 1) & 0x40a40000) == 0;
            }
            if (xOffset == 1 && yOffset == -1) {
                return (mask & 0x60e40000) == 0 && (getClipedOnlyMask(plane, x + 1, y) & 0x60240000) == 0 && (getClipedOnlyMask(plane, x, y - 1) & 0x40a40000) == 0;
            }
            if (xOffset == -1 && yOffset == 1) {
                return (mask & 0x4e240000) == 0 && (getClipedOnlyMask(plane, x - 1, y) & 0x42240000) == 0 && (getClipedOnlyMask(plane, x, y + 1) & 0x48240000) == 0;
            }
            if (xOffset == 1 && yOffset == 1) {
                return (mask & 0x78240000) == 0 && (getClipedOnlyMask(plane, x + 1, y) & 0x60240000) == 0 && (getClipedOnlyMask(plane, x, y + 1) & 0x48240000) == 0;
            }
        } else if (size == 2) {
            if (xOffset == -1 && yOffset == 0)
                return (getClipedOnlyMask(plane, x - 1, y) & 0x43a40000) == 0 && (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4e240000) == 0;
            if (xOffset == 1 && yOffset == 0)
                return (getClipedOnlyMask(plane, x + 2, y) & 0x60e40000) == 0 && (getClipedOnlyMask(plane, x + 2, y + 1) & 0x78240000) == 0;
            if (xOffset == 0 && yOffset == -1)
                return (getClipedOnlyMask(plane, x, y - 1) & 0x43a40000) == 0 && (getClipedOnlyMask(plane, x + 1, y - 1) & 0x60e40000) == 0;
            if (xOffset == 0 && yOffset == 1)
                return (getClipedOnlyMask(plane, x, y + 2) & 0x4e240000) == 0 && (getClipedOnlyMask(plane, x + 1, y + 2) & 0x78240000) == 0;
            if (xOffset == -1 && yOffset == -1)
                return (getClipedOnlyMask(plane, x - 1, y) & 0x4fa40000) == 0 && (getClipedOnlyMask(plane, x - 1, y - 1) & 0x43a40000) == 0 && (getClipedOnlyMask(plane, x, y - 1) & 0x63e40000) == 0;
            if (xOffset == 1 && yOffset == -1)
                return (getClipedOnlyMask(plane, x + 1, y - 1) & 0x63e40000) == 0 && (getClipedOnlyMask(plane, x + 2, y - 1) & 0x60e40000) == 0 && (getClipedOnlyMask(plane, x + 2, y) & 0x78e40000) == 0;
            if (xOffset == -1 && yOffset == 1)
                return (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4fa40000) == 0 && (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4e240000) == 0 && (getClipedOnlyMask(plane, x, y + 2) & 0x7e240000) == 0;
            if (xOffset == 1 && yOffset == 1)
                return (getClipedOnlyMask(plane, x + 1, y + 2) & 0x7e240000) == 0 && (getClipedOnlyMask(plane, x + 2, y + 2) & 0x78240000) == 0 && (getClipedOnlyMask(plane, x + 1, y + 1) & 0x78e40000) == 0;
        } else {
            if (xOffset == -1 && yOffset == 0) {
                if ((getClipedOnlyMask(plane, x - 1, y) & 0x43a40000) != 0 || (getClipedOnlyMask(plane, x - 1, -1 + (y + size)) & 0x4e240000) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
                    if ((getClipedOnlyMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0)
                        return false;
            } else if (xOffset == 1 && yOffset == 0) {
                if ((getClipedOnlyMask(plane, x + size, y) & 0x60e40000) != 0 || (getClipedOnlyMask(plane, x + size, y - (-size + 1)) & 0x78240000) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
                    if ((getClipedOnlyMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
                        return false;
            } else if (xOffset == 0 && yOffset == -1) {
                if ((getClipedOnlyMask(plane, x, y - 1) & 0x43a40000) != 0 || (getClipedOnlyMask(plane, x + size - 1, y - 1) & 0x60e40000) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
                    if ((getClipedOnlyMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
                        return false;
            } else if (xOffset == 0 && yOffset == 1) {
                if ((getClipedOnlyMask(plane, x, y + size) & 0x4e240000) != 0 || (getClipedOnlyMask(plane, x + (size - 1), y + size) & 0x78240000) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
                    if ((getClipedOnlyMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0)
                        return false;
            } else if (xOffset == -1 && yOffset == -1) {
                if ((getClipedOnlyMask(plane, x - 1, y - 1) & 0x43a40000) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
                    if ((getClipedOnlyMask(plane, x - 1, y + (-1 + sizeOffset)) & 0x4fa40000) != 0 || (getClipedOnlyMask(plane, sizeOffset - 1 + x, y - 1) & 0x63e40000) != 0)
                        return false;
            } else if (xOffset == 1 && yOffset == -1) {
                if ((getClipedOnlyMask(plane, x + size, y - 1) & 0x60e40000) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
                    if ((getClipedOnlyMask(plane, x + size, sizeOffset + (-1 + y)) & 0x78e40000) != 0 || (getClipedOnlyMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
                        return false;
            } else if (xOffset == -1 && yOffset == 1) {
                if ((getClipedOnlyMask(plane, x - 1, y + size) & 0x4e240000) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
                    if ((getClipedOnlyMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0 || (getClipedOnlyMask(plane, -1 + (x + sizeOffset), y + size) & 0x7e240000) != 0)
                        return false;
            } else if (xOffset == 1 && yOffset == 1) {
                if ((getClipedOnlyMask(plane, x + size, y + size) & 0x78240000) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
                    if ((getClipedOnlyMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0 || (getClipedOnlyMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
                        return false;
            }
        }
        return true;
    }

    //well decided to place it at walking to make it more organizated i guess
    public static final boolean checkWalkStep(int plane, int x, int y, int dir, int size) {
        return checkWalkStep(plane, x, y, Misc.DIRECTION_DELTA_X[dir], Misc.DIRECTION_DELTA_Y[dir], size);
    }

    public static final boolean checkWalkStep(int plane, int x, int y, int xOffset, int yOffset, int size) {
        if (size == 1) {
            int mask = getMask(plane, x + xOffset, y + yOffset);
            if (xOffset == -1 && yOffset == 0)
                return (mask & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST)) == 0;
            if (xOffset == 1 && yOffset == 0)
                return (mask & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_WEST)) == 0;
            if (xOffset == 0 && yOffset == -1)
                return (mask & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH)) == 0;
            if (xOffset == 0 && yOffset == 1)
                return (mask & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_SOUTH)) == 0;
            if (xOffset == -1 && yOffset == -1)
                return (mask & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.CORNEROBJ_NORTHEAST)) == 0 && (getMask(plane, x - 1, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST)) == 0 && (getMask(plane, x, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH)) == 0;
            if (xOffset == 1 && yOffset == -1)
                return (mask & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST)) == 0 && (getMask(plane, x + 1, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_WEST)) == 0 && (getMask(plane, x, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH)) == 0;
            if (xOffset == -1 && yOffset == 1)
                return (mask & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_SOUTHEAST)) == 0 && (getMask(plane, x - 1, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST)) == 0 && (getMask(plane, x, y + 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_SOUTH)) == 0;
            if (xOffset == 1 && yOffset == 1)
                return (mask & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHWEST)) == 0 && (getMask(plane, x + 1, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_WEST)) == 0 && (getMask(plane, x, y + 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_SOUTH)) == 0;
        } else if (size == 2) {
            if (xOffset == -1 && yOffset == 0)
                return (getMask(plane, x - 1, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.CORNEROBJ_NORTHEAST)) == 0 && (getMask(plane, x - 1, y + 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_SOUTHEAST)) == 0;
            if (xOffset == 1 && yOffset == 0)
                return (getMask(plane, x + 2, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST)) == 0 && (getMask(plane, x + 2, y + 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHWEST)) == 0;
            if (xOffset == 0 && yOffset == -1)
                return (getMask(plane, x, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.CORNEROBJ_NORTHEAST)) == 0 && (getMask(plane, x + 1, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST)) == 0;
            if (xOffset == 0 && yOffset == 1)
                return (getMask(plane, x, y + 2) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_SOUTHEAST)) == 0 && (getMask(plane, x + 1, y + 2) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHWEST)) == 0;
            if (xOffset == -1 && yOffset == -1)
                return (getMask(plane, x - 1, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_NORTHEAST | Flags.CORNEROBJ_SOUTHEAST)) == 0 && (getMask(plane, x - 1, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.CORNEROBJ_NORTHEAST)) == 0 && (getMask(plane, x, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_NORTHEAST)) == 0;
            if (xOffset == 1 && yOffset == -1)
                return (getMask(plane, x + 1, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_NORTHEAST)) == 0 && (getMask(plane, x + 2, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST)) == 0 && (getMask(plane, x + 2, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_SOUTHWEST)) == 0;
            if (xOffset == -1 && yOffset == 1)
                return (getMask(plane, x - 1, y + 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_NORTHEAST | Flags.CORNEROBJ_SOUTHEAST)) == 0 && (getMask(plane, x - 1, y + 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_SOUTHEAST)) == 0 && (getMask(plane, x, y + 2) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHEAST | Flags.CORNEROBJ_SOUTHWEST)) == 0;
            if (xOffset == 1 && yOffset == 1)
                return (getMask(plane, x + 1, y + 2) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHEAST | Flags.CORNEROBJ_SOUTHWEST)) == 0 && (getMask(plane, x + 2, y + 2) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHWEST)) == 0 && (getMask(plane, x + 1, y + 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_SOUTHWEST)) == 0;
        } else {
            if (xOffset == -1 && yOffset == 0) {
                if ((getMask(plane, x - 1, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.CORNEROBJ_NORTHEAST)) != 0 || (getMask(plane, x - 1, -1 + (y + size)) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_SOUTHEAST)) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
                    if ((getMask(plane, x - 1, y + sizeOffset) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_NORTHEAST | Flags.CORNEROBJ_SOUTHEAST)) != 0)
                        return false;
            } else if (xOffset == 1 && yOffset == 0) {
                if ((getMask(plane, x + size, y) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST)) != 0 || (getMask(plane, x + size, y - (-size + 1)) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHWEST)) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
                    if ((getMask(plane, x + size, y + sizeOffset) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_SOUTHWEST)) != 0)
                        return false;
            } else if (xOffset == 0 && yOffset == -1) {
                if ((getMask(plane, x, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.CORNEROBJ_NORTHEAST)) != 0 || (getMask(plane, x + size - 1, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST)) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
                    if ((getMask(plane, x + sizeOffset, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_NORTHEAST)) != 0)
                        return false;
            } else if (xOffset == 0 && yOffset == 1) {
                if ((getMask(plane, x, y + size) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_SOUTHEAST)) != 0 || (getMask(plane, x + (size - 1), y + size) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHWEST)) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
                    if ((getMask(plane, x + sizeOffset, y + size) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHEAST | Flags.CORNEROBJ_SOUTHWEST)) != 0)
                        return false;
            } else if (xOffset == -1 && yOffset == -1) {
                if ((getMask(plane, x - 1, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.CORNEROBJ_NORTHEAST)) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
                    if ((getMask(plane, x - 1, y + (-1 + sizeOffset)) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_NORTHEAST | Flags.CORNEROBJ_SOUTHEAST)) != 0 || (getMask(plane, sizeOffset - 1 + x, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_NORTHEAST)) != 0)
                        return false;
            } else if (xOffset == 1 && yOffset == -1) {
                if ((getMask(plane, x + size, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST)) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
                    if ((getMask(plane, x + size, sizeOffset + (-1 + y)) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_SOUTHWEST)) != 0 || (getMask(plane, x + sizeOffset, y - 1) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_NORTHEAST)) != 0)
                        return false;
            } else if (xOffset == -1 && yOffset == 1) {
                if ((getMask(plane, x - 1, y + size) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_SOUTHEAST)) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
                    if ((getMask(plane, x - 1, y + sizeOffset) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.CORNEROBJ_NORTHEAST | Flags.CORNEROBJ_SOUTHEAST)) != 0 || (getMask(plane, -1 + (x + sizeOffset), y + size) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHEAST | Flags.CORNEROBJ_SOUTHWEST)) != 0)
                        return false;
            } else if (xOffset == 1 && yOffset == 1) {
                if ((getMask(plane, x + size, y + size) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHWEST)) != 0)
                    return false;
                for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
                    if ((getMask(plane, x + sizeOffset, y + size) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_EAST | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_SOUTHEAST | Flags.CORNEROBJ_SOUTHWEST)) != 0 || (getMask(plane, x + size, y + sizeOffset) & (Flags.FLOOR_BLOCKSWALK | Flags.FLOORDECO_BLOCKSWALK | Flags.OBJ | Flags.WALLOBJ_NORTH | Flags.WALLOBJ_SOUTH | Flags.WALLOBJ_WEST | Flags.CORNEROBJ_NORTHWEST | Flags.CORNEROBJ_SOUTHWEST)) != 0)
                        return false;
            }
        }
        return true;
    }

    public static int getMask(int plane, int x, int y) {
        RSTile tile = RSTile.createRSTile(x, y, plane);
        RSObjectsRegion region = RSObjectsRegion.getRegion((short) tile.getRegionId());
        if (region == null) {
            return -1;
        }
        return region.getMask(tile.getZ(), tile.getXInRegion(), tile.getYInRegion());
    }

    private static int getClipedOnlyMask(int plane, int x, int y) {
        RSTile tile = RSTile.createRSTile(x, y, plane);
        RSObjectsRegion region = RSObjectsRegion.getRegion((short) tile.getRegionId());
        if (region == null) {
            return -1;
        }
        return region.getMaskClipedOnly(tile.getZ(), tile.getXInRegion(), tile.getYInRegion());
    }

    public boolean isRunToggled() {
        return isRunToggled;
    }

    public void setRunToggled(boolean isRunToggled) {
        this.isRunToggled = isRunToggled;
    }

    public boolean isMoving() {
        return this.hasWalkingDirection() || this.WalkDir != -1 || this.RunDir != -1;
    }

    public boolean isWalkingMoving() {
        return (this.hasWalkingDirection() && !this.isRunning) || this.WalkDir != -1;
    }

    public boolean isRunningMoving() {
        return (this.hasWalkingDirection() && this.isRunning) || this.RunDir != -1;
    }

    private boolean hasWalkingDirection() {
        return wQueueReadPtr != wQueueWritePtr;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void reset(boolean removeFollow) {
        if (removeFollow) {
            isFollowing = false;
//			if(entity.getPlayer() != null) {
//				Player player = (Player)entity;
//				player.getMask().setTurnToIndex(-1);
//				player.getMask().setTurnToReset(true);
//				player.getMask().setTurnToUpdate(true);
//			}
        }
        walkingQueue[0].x = entity.getLocation().getLocalX();
        walkingQueue[0].y = entity.getLocation().getLocalY();
        walkingQueue[0].dir = -1;
        wQueueReadPtr = wQueueWritePtr = 1;
        lastWalkFinalPosition = RSTile.createRSTile(0, 0, 0);
    }

    /**
     * Walks to the entity/location/object/item assigned in strategy or closest route in case it can't find exact path
     *
     * @param strategy The assigned strategy to walk to (view EntityStrategy/FixedTileStrategy/FloorItemStrategy/ObjectStrategy)
     * @return steps
     * @author dragonkk(Alex)  15/12/2016
     * @see RouteFinder
     */
    public int walkTo(RouteStrategy strategy) {
        return walkTo(strategy, true);
    }

    /**
     * Walks to the entity/location/object/item assigned in strategy
     *
     * @param strategy         The assigned strategy to walk to (view EntityStrategy/FixedTileStrategy/FloorItemStrategy/ObjectStrategy)
     * @param alternativeRoute finds closest route to assigned strategy in case it can't find exact path
     * @return steps
     * @author dragonkk(Alex)  15/12/2016
     * @see RouteFinder
     */
    public int walkTo(RouteStrategy strategy, boolean alternativeRoute) {
        if (entity.getPlayer().inStarter) {//npc change dre
            return 0;
        }
        int steps = RouteFinder.findRoute(RouteFinder.WALK_ROUTEFINDER, entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), entity.getSize(), strategy, alternativeRoute);
        if (steps >= 0) {
            reset(false);
            int[] bufferX = RouteFinder.getLastPathBufferX();
            int[] bufferY = RouteFinder.getLastPathBufferY();
            int baseX = (entity.getLocation().getRegionX() - 6) * 8;
            int baseY = (entity.getLocation().getRegionY() - 6) * 8;

            for (int i = steps - 1; i >= 0; i--)
                addToWalkingQueue(bufferX[i] - baseX, bufferY[i] - baseY);
        }
        return steps;
    }

    public void addToWalkingQueue(int x, int y) {
        int diffX = x - walkingQueue[wQueueWritePtr - 1].x, diffY = y - walkingQueue[wQueueWritePtr - 1].y;
        int max = Math.max(Math.abs(diffX), Math.abs(diffY));
        for (int i = 0; i < max; i++) {
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
            addStepToWalkingQueue(x - diffX, y - diffY);
        }
    }

    public void addToWalkingQueueFollow(int x, int y) {
        int diffX = x - walkingQueue[wQueueWritePtr - 1].x, diffY = y - walkingQueue[wQueueWritePtr - 1].y;
        int max = Math.max(Math.abs(diffX), Math.abs(diffY));
        for (int i = 0; i < max; i++) {
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
            int toQueX = x - diffX;
            int toQueY = y - diffY;
            if (toQueX != x || toQueY != y) {
                addStepToWalkingQueue(toQueX, toQueY);
            }
        }
    }

    public void addStepToWalkingQueue(int x, int y) {
        int diffX = x - walkingQueue[wQueueWritePtr - 1].x, diffY = y - walkingQueue[wQueueWritePtr - 1].y;
        byte dir = (byte) Misc.walkingDirection(diffX, diffY);
        if (wQueueWritePtr >= SIZE) {
            return;
        }
        if (dir != -1) {
            walkingQueue[wQueueWritePtr].x = x;
            walkingQueue[wQueueWritePtr].y = y;
            walkingQueue[wQueueWritePtr++].dir = dir;
        }
    }

    public void getNextEntityMovement() {

        if (entity instanceof Player)
            this.setDidTele(((Player) entity).getMask().getRegion().isDidTeleport());

        setWalkDir((byte) -1);
        setRunDir((byte) -1);
        RSTile oldLocation = null;
        byte walkDir = (entity instanceof Player && System.currentTimeMillis() < ((Player) entity).getCombatDefinitions().getLastEmote() - 600) ? -1 : getNextWalkingDirection();
        byte runDir = -1;
        if (runEnergy == 0 && (isRunning || isRunToggled)) {
            isRunning = false;
            isRunToggled = false;
        }
        if (isRunning || isRunToggled) {
            runDir = (entity instanceof Player && System.currentTimeMillis() < ((Player) entity).getCombatDefinitions().getLastEmote() - 600) ? -1 : (byte) getNextWalkingDirection();
        }
        if (entity instanceof Player) {
            Player p = (Player) entity;
            if (!p.getMask().getRegion().isUsingStaticRegion()) {
                oldLocation = p.getMask().getRegion().getLastMapRegion();
                if ((oldLocation.getRegionX() - entity.getLocation().getRegionX()) >= 4
                        || (oldLocation.getRegionX() - entity.getLocation().getRegionX()) <= -4) {
                    p.getMask().getRegion().setNeedReload(true);
                    p.getMask().getRegion().setDidMapRegionChange(true);
                } else if ((oldLocation.getRegionY() - entity.getLocation().getRegionY()) >= 4
                        || (oldLocation.getRegionY() - entity.getLocation().getRegionY()) <= -4) {
                    p.getMask().getRegion().setNeedReload(true);
                    p.getMask().getRegion().setDidMapRegionChange(true);
                }
            }
            if (p.getMask().getRegion().isDidMapRegionChange()) {
                if (walkDir != -1) {
                    wQueueReadPtr--;
                }
                if (runDir != -1) {
                    wQueueReadPtr--;
                }
                walkDir = -1;
                runDir = -1;
            }
        }
        // now then. the code i write here is what will make it so you cant
        // noclip
        int xdiff = 0;
        int ydiff = 0;
        if (walkDir != -1) { // if cant walk to next step cuz clipped it
            // will reset walk queue and stop there
            if (!checkWalkStep(entity.getLocation().getZ(), entity.getLocation().getX(), entity.getLocation().getY(), walkDir,
                    entity.getSize())) {
                walkDir = runDir = -1;
                reset(false);
            } else {
                xdiff += Misc.DIRECTION_DELTA_X[walkDir];
                ydiff += Misc.DIRECTION_DELTA_Y[walkDir];

                if (entity instanceof Npc) {
                    walkDir = (byte) Misc.getNpcMoveDirection(Misc.DIRECTION_DELTA_X[walkDir], Misc.DIRECTION_DELTA_Y[walkDir]);
                }
            }
        }
        if (runDir != -1) {
            int nextXDiff = Misc.DIRECTION_DELTA_X[runDir];
            int nextYDiff = Misc.DIRECTION_DELTA_Y[runDir];
            // same for running step
            if (!checkWalkStep(entity.getLocation().getZ(), entity.getLocation().getX() + xdiff,
                    entity.getLocation().getY() + ydiff, runDir, entity.getSize())) {
                runDir = -1;
                reset(false);
            } else
                runDir = (entity instanceof Player)
                        ? (byte) Misc.runningDirection(xdiff + nextXDiff, ydiff + nextYDiff)
                        : (byte) Misc.getNpcMoveDirection(nextXDiff, nextYDiff);


            if (runDir != -1) { //if found step it wont need as its a double step
                if (entity instanceof Player) //after all fornpc it uses both when running unlike for player
                    walkDir = -1;
                xdiff += nextXDiff;
                ydiff += nextYDiff;
            } else if (walkDir == -1) { //ah ye  cuz some wlak dirs can walk over 1 step at once(diagonal)
                walkDir = (entity instanceof Player)
                        ? (byte) Misc.runningDirection(nextXDiff, nextYDiff)
                        : (byte) Misc.getNpcMoveDirection(nextXDiff, nextYDiff);
                xdiff += nextXDiff;
                ydiff += nextYDiff;
            } else {
                wQueueReadPtr--;
            }


            if (runEnergy > 0) {
                runEnergy--;
                if (entity instanceof Player) {
                    ((Player) entity).getFrames().sendRunEnergy();
                }
            }
        } else if (runEnergy < 100) {
            runEnergy++;
            if (((Player) entity).isResting) {
                runEnergy++;
            }
            if (runEnergy > 100) {
                runEnergy = 100;
            }
            if (entity instanceof Player) {
                ((Player) entity).getFrames().sendRunEnergy();
            }
        }
        if (xdiff != 0 || ydiff != 0) {
            entity.getLocation().set((short) (entity.getLocation().getX() + xdiff),
                    (short) (entity.getLocation().getY() + ydiff));
        }

        setWalkDir(walkDir);
        setRunDir(runDir);
    }

    private byte getNextWalkingDirection() {
        if (wQueueReadPtr == wQueueWritePtr) {
            return -1;
        }
        return walkingQueue[wQueueReadPtr++].dir;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public byte getWalkDir() {
        return WalkDir;
    }

    public void setWalkDir(byte walkDir) {
        WalkDir = walkDir;
    }

    public byte getRunDir() {
        return RunDir;
    }

    public void setRunDir(byte runDir) {
        RunDir = runDir;
    }

    public RSTile getLastWalk() {
        return lastWalkFinalPosition;
    }

    public void setLastWalk(RSTile lastWalk) {
        this.lastWalkFinalPosition = lastWalk;
    }

    public byte getRunEnergy() {
        return runEnergy;
    }

    public void setRunEnergy(byte runEnergy) {
        this.runEnergy = runEnergy;
    }

    public boolean isDidTele() {
        return didTele;
    }

    public void setDidTele(boolean didTele) {
        this.didTele = didTele;
    }

    //projectile. isnt even related to walk but w/e
    //can you do me a favour and not save anything ;3
    public boolean clipedProjectile(RSTile tile, boolean checkClose, int size) {
        int myX = entity.getLocation().getX();
        int myY = entity.getLocation().getY();
        //add if needed
		/*if (this instanceof NPC) {
			NPC n = (NPC) this;
			WorldTile thist = n.getMiddleWorldTile();
			myX = thist.getX();
			myY = thist.getY();
		}*/
        int destX = tile.getX();
        int destY = tile.getY();
        if (myX == destX && destY == myY)
            return true;
        int lastTileX = myX;
        int lastTileY = myY;
        while (true) {
            if (myX < destX)
                myX++;
            else if (myX > destX)
                myX--;
            if (myY < destY)
                myY++;
            else if (myY > destY)
                myY--;
            int dir = Misc.getMoveDirection(myX - lastTileX, myY - lastTileY);
            if (dir == -1)
                return false;
            if (checkClose) {
                if (!checkWalkStep(entity.getLocation().getZ(), lastTileX, lastTileY, dir, size))
                    return false;
            } else if (!checkProjectileStep(entity.getLocation().getZ(), lastTileX, lastTileY, dir, size))
                return false;
            lastTileX = myX;
            lastTileY = myY;
            if (lastTileX == destX && lastTileY == destY)
                return true;
        }
    }

    public class Point {
        public int x;
        public int y;
        public byte dir;
    }


}