package Auora.tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A JPanel subclass that displays the map, this is movable.
 *
 * @author netherfoam
 */
public class JMap implements MouseMotionListener, MouseWheelListener, KeyListener {
    /**
     * The map itself as an image.
     */
    private BufferedImage image;
    /**
     * The current zoom ratio. Smaller means zoomed out, bigger means zoomed in.
     */
    private double zoom = 1.0;
    /**
     * The distance between the left of the window and the left of the image. This is
     * a number of pixels, after resolution is applied.
     */
    private int xOffset = 0;
    /**
     * The distance between the top of the window and the top of the image. This is
     * a number of pixels, after resolution is applied.
     */
    private int yOffset = 0;

    /**
     * A magic number that needs to be added to the image's coordinates in order to get the 0,0 coordinate of the world
     */
    private int adjustX;
    /**
     * A magic number that needs to be added to the image's coordinates in order to get the 0,0 coordinate of the world
     */
    private int adjustY;

    /**
     * The last known coordinate of the mouseX, relative to top-left of frame in pixels
     */
    private int mouseX;
    /**
     * The last known coordinate of the mouseY, relative to top-left of frame in pixels
     */
    private int mouseY;

    /**
     * The number of pixels the map image has per tile on the ground.
     */
    private double ratio;

    private ArrayList<JMapComponent> components;

    private JPanel panel;

    /**
     * Constructs a new JMap. This starts a new thread which will update.
     *
     * @param f             the file we're loading the image from
     * @param xOff          the starting coordinate. number of pixels to move the map in from the left
     * @param yOff          the starting coordinate. Number of pixels to move that map in from the right
     * @param adjustX       the magic number for the xoffset. This is where, on the image, x=0 occurs
     * @param adjustY       the magic number for the y offset. This is where, on the image, y=0 occurs.
     * @param pixelsPerTile the number of pixels used to represent a single tile horizontally or vertically
     * @throws IOException if there was an error loading the image from file.
     */
    public JMap(File f, int xOff, int yOff, int adjustx, int adjusty, double pixelsPerTile) throws IOException {
        super();
        this.image = ImageIO.read(f);
        this.xOffset = xOff;
        this.yOffset = yOff;

        this.adjustX = adjustx;
        this.adjustY = adjusty;
        this.ratio = pixelsPerTile;

        this.components = new ArrayList<JMapComponent>();

        this.panel = new JPanel() {
            private static final long serialVersionUID = 8212722501098820102L;

            @Override
            public void paint(Graphics g) {
                //Draw a fully grey background
                g.setColor(Color.GRAY);
                g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

                //Now make valid areas black
                g.setColor(Color.BLACK);
                g.fillRect(posXToPixel(0), posYToPixel(0), (int) (0x3FFF * zoom * ratio), (int) -(0x3FFF * zoom * ratio));

                g.drawImage(image, -xOffset, -yOffset, (int) (zoom * image.getWidth()), (int) (zoom * image.getHeight()), null);

                g.setColor(Color.RED);

                for (JMapComponent m : components) {
                    double px = m.getX() * zoom * ratio + adjustX * zoom;
                    double py = m.getY() * zoom * ratio + adjustY * zoom;

                    double x = px - xOffset;
                    double y = image.getHeight() * zoom - (py + yOffset);

                    BufferedImage bi = new BufferedImage(m.getResolutionX(), m.getResolutionY(), BufferedImage.TYPE_4BYTE_ABGR);
                    m.draw(bi.getGraphics());

                    g.drawImage(bi, (int) x, (int) y, (int) (m.getTileWidth() * zoom * ratio), (int) (m.getTileHeight() * zoom * ratio), null);
                }

                g.setColor(Color.YELLOW);
                g.drawString("Offset: (" + xOffset + ", " + yOffset + ")", 10, 10);
                g.drawString("Zoom: " + zoom, 10, 22);
                g.drawString("Mouse: (" + getMouseXTile() + ", " + getMouseYTile() + ")", 10, 34);
            }
        };
        this.panel.addMouseMotionListener(this);
        this.panel.addMouseWheelListener(this);
        this.panel.addKeyListener(this);
        this.panel.setFocusable(true);
    }

    private static double between(double min, double max, double val) {
        if (val > max) return max;
        if (val < min) return min;
        return val;
    }

    /**
     * An immutable list which represents all of the JMapComponents on
     * this map. These do not have to be in view to be in the list.
     *
     * @return the list of JMapComponents which have been added to this map.
     */
    public List<JMapComponent> getEntities() {
        return Collections.unmodifiableList(this.components);
    }

    /**
     * Removes the given component from the map
     *
     * @param m the component to remove
     */
    public void remove(JMapComponent m) {
        this.components.remove(m);
    }

    /**
     * Adds the given component to the map
     *
     * @param m the component to add
     */
    public void add(JMapComponent m) {
        this.components.add(m);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
            //Right click
            xOffset += (mouseX - e.getX()) * 1.8;
            yOffset += (mouseY - e.getY()) * 1.8;
            getPanel().repaint();
        }

        mouseX = e.getX(); //Not sure why these requirements exist
        mouseY = e.getY(); //But this fixes the location of the mouse.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        getPanel().repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rot = e.getWheelRotation();

        double delta = (rot / 10D);

        if (this.zoom <= 0.05 && delta > 0) {
            return;
        }
        if (this.zoom >= 100 && delta < 0) {
            return;
        }

        double zoom = between(0.05, 100, this.zoom - delta);
        delta = (this.zoom - zoom);
        this.zoom = zoom;

        double mousedx = 0;
        double mousedy = 0;
        if (delta < 0) {
            //Zooming in
            mousedx = between(-200, 200, ((mouseX - panel.getSize().width / 2) * -delta));
            mousedy = between(-200, 200, ((mouseY - panel.getSize().height / 2) * -delta));
        }

        this.xOffset = (int) ((this.xOffset / this.zoom) * (this.zoom - delta)) + (int) mousedx;
        this.yOffset = (int) ((this.yOffset / this.zoom) * (this.zoom - delta)) + (int) mousedy;
        getPanel().repaint();

    }

    /**
     * The panel where the map is actually painted into
     *
     * @return the panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * The number of pixels used to represent a tile. This is horizontally, or vertically. For
     * example, the default map uses 4 pixels (2x2) to represent each tile. In this case,
     * this method would return 2 (2-width or 2-height)
     *
     * @return the number of pixels per-tile.
     */
    public double getPixelsPerTile() {
        return ratio;
    }

    /**
     * Centers the map on the given coordinates.
     *
     * @param posX the tile X coordinate
     * @param posY the tile Y coordinate
     */
    public void center(int posX, int posY) {
        double px = posX * zoom * ratio + adjustX * zoom - panel.getSize().width / 2;
        double py = posY * zoom * ratio + adjustY * zoom + panel.getSize().height / 2;

        xOffset = (int) px;
        yOffset = (int) (image.getHeight() * zoom - py);
        panel.repaint();
    }

    private int posXToPixel(int posX) {
        return (int) (posX * zoom * ratio + adjustX * zoom) - xOffset;
    }

    private int posYToPixel(int posY) {
        return (int) (image.getHeight() * zoom - (posY * zoom * ratio + adjustY * zoom + yOffset));
    }

    public int getMouseXTile() {
        return (int) ((((xOffset / zoom) - adjustX) / ratio) + mouseX / zoom / ratio);
    }

    public int getMouseYTile() {
        return (int) (((image.getHeight() - yOffset / zoom) - adjustY) / ratio - mouseY / zoom / ratio);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Empty
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 82: //r
                this.zoom = 1.0;
                this.center(3221, 3221);
                break;
            case 109: //-
            case 45: //-
                this.zoom -= 0.1;
                break;
            case 107: //+
            case 61: //=
                this.zoom += 0.1;
                break;
            case 87: //w
            case 38: //up arrow
                this.yOffset -= 50;
                break;
            case 83: //s
            case 40: //down arrow
                this.yOffset += 50;
                break;
            case 65: //a
            case 37: //left arrow
                this.xOffset -= 50;
                break;
            case 68: //d
            case 39: //right arrow
                this.xOffset += 50;
                break;
            default:
                //We do not want to repaint
                return;
        }
        panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Empty
    }
}