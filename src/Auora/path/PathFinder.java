package Auora.path;

import Auora.model.Entity;
import Auora.util.RSTile;

/**
 * @author Graham
 */
public interface PathFinder {

    public PathState findPath(Entity mob, RSTile base, int srcX, int srcY, int dstX, int dstY, int z, int radius, boolean running, boolean ignoreLastStep, boolean moveNear);
}
