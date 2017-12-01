package Auora.tools;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * A MapViewer that shows all current players on the server in a
 * moveable, zoomable map.
 *
 * @author netherfoam
 */
public class MapViewer {
    /**
     * The frame that the map is in
     */
    private JFrame frame;
    /**
     * The map JPanel itself. This listens to mouse events.
     */
    private JMap map;
    /**
     * Constructs a new MapViewer
     *
     * @throws IOException if there was an error loading the map
     */
    public MapViewer() throws IOException {
        frame = new JFrame("World Map");
        //Lower X is further left.
        map = new JMap(new File("data", "map.jpg"), 0, 0, -4081, -5103, 2.0D);
        frame.add(map.getPanel());
        frame.setVisible(true);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        map.getPanel().setSize(frame.getSize());

        map.center(3221, 3221);

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(600); //Tick = 600ms
                    } catch (InterruptedException e) {
                        return;
                    }
                    if (frame.isVisible() == false) return; //Window closed, we no longer need this thread.

                    map.getPanel().repaint();
                }
            }
        }.start();
    }

    public static void main(String[] args) throws IOException, SQLException {
        new MapViewer();
    }

    public Frame getFrame() {
        return frame;
    }

    public JMap getMap() {
        return map;
    }


}
