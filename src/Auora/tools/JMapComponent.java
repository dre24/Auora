package Auora.tools;

import java.awt.*;

/**
 * Represents an entity which may be drawn on a JMap.
 *
 * @author netherfoam
 */
public interface JMapComponent {
    /**
     * The X coordinate of the entity on the server, eg, the player's location.
     * This is anchored on the top-left corner of the image on the map
     *
     * @return the x coordinate
     */
    public abstract int getX();

    /**
     * The Y coordinate of the entity on the server, eg, the player's location.
     * This is anchored on the top-left corner of the image on the map
     *
     * @return the y coordinate
     */
    public abstract int getY();

    /**
     * The height of this JMapComponent. As the map viewer zooms in,
     * this will be multiplied automatically. This is the number of
     * tiles the entity takes up
     *
     * @return the height in tiles at zoom = 1.0
     */
    public abstract int getTileHeight();

    /**
     * The width of this JMapComponent. As the map viewer zooms in,
     * this will be multiplied automatically. This is the number of
     * tiles the entity takes up
     *
     * @return the width in tiles at zoom = 1.0
     */
    public abstract int getTileWidth();

    /**
     * The resolution of the width of this component. This does not
     * modify the size of the component visually, but modifies the
     * resolution. The draw(Graphics g) method is invoked, and the
     * width of the given graphics object is the result of calling
     * this method. The image is later scaled accordingly on the
     * map automatically.
     *
     * @return the resolution for width
     */
    public abstract int getResolutionX();

    /**
     * The resolution of the height of this component. This does not
     * modify the size of the component visually, but modifies the
     * resolution. The draw(Graphics g) method is invoked, and the
     * height of the given graphics object is the result of calling
     * this method. The image is later scaled accordingly on the
     * map automatically.
     *
     * @return the resolution for height
     */
    public abstract int getResolutionY();

    /**
     * Draws this component on the given graphics object. This should not take
     * into account the x and y coordinate of the entity or the zoom, as those
     * effects will be added automatically by the map.
     *
     * @param g the graphics to draw with (It belongs to a BufferedImage)
     */
    public abstract void draw(Graphics g);
}
