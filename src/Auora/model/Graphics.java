package Auora.model;

/**
 * Represents 'still graphics'.
 *
 * @author Graham
 */
public class Graphics {

    private short id, delay, height;

    public Graphics(short id, short delay) {
        this.id = id;
        this.delay = delay;
        this.height = 0;
    }

    public Graphics(short id, short delay, short height) {
        this.id = id;
        this.delay = delay;
        this.height = height;
    }

    public short getId() {
        return id;
    }

    public short getDelay() {
        return delay;
    }

    public short getHeight() {
        return height;
    }

}
