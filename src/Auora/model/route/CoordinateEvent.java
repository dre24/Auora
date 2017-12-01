package Auora.model.route;

import Auora.model.Entity;
import Auora.util.RSTile;

/**
 * @author 'Mystic Flow <Steven@rune-server.org>
 */
public abstract class CoordinateEvent {

    private Entity mob;

    private int x;
    private int y;
    private int lengthX;
    private int lengthY;

    public CoordinateEvent(Entity mob, int x, int y, int lengthX, int lengthY) {
        this.mob = mob;
        this.x = x;
        this.y = y;
        this.lengthX = lengthX;
        this.lengthY = lengthY;
    }

    public boolean inArea() {
        RSTile loc = mob.getLocation();
        return loc.getX() >= x - lengthX && loc.getY() >= y - lengthY && loc.getX() <= x + lengthX && loc.getY() <= y + lengthY;
    }

    public abstract void execute();
}
