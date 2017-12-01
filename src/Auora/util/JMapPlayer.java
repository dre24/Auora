package Auora.util;

import Auora.model.player.Player;
import Auora.tools.JMapComponent;

import java.awt.*;

public class JMapPlayer implements JMapComponent {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    public static final int RESOL_WIDTH = 24;
    public static final int RESOL_HEIGHT = 24;

    private Player p;

    public JMapPlayer(Player p) {
        this.p = p;
    }

    public Player getPlayer() {
        return p;
    }


    public int getY() {
        //Add HEIGHT / 2 because we want to center it on the player
        //Add because it is anchored to the bottom
        return p.getLocation().getY() + HEIGHT / 2;
    }


    public int getX() {
        //Take WIDTH / 2 because we want to center it on the player
        //Take because it is anchored to the left
        return p.getLocation().getX() - WIDTH / 2;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0.0F, 1.0F, 0F, 0.33F));
        g.fillOval(0, 0, RESOL_WIDTH, RESOL_HEIGHT);

        Font f = g.getFont();
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 0, RESOL_WIDTH / 2));
        String c = p.getDisplayName().substring(0, 1).toUpperCase();
        g.drawString(c, RESOL_WIDTH / 4, RESOL_HEIGHT - 9);
        g.setFont(f); //Reset font
    }


    public int getTileHeight() {
        return HEIGHT;
    }


    public int getTileWidth() {
        return WIDTH;
    }


    public int getResolutionX() {
        return RESOL_WIDTH;
    }

    public int getResolutionY() {
        return RESOL_HEIGHT;
    }
}
