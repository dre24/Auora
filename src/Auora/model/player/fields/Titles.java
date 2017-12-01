package Auora.model.player.fields;

import Auora.model.player.Player;

/**
 * Handles all points for SpawnScape players
 *
 * @Author Jonny
 */
public final class Titles {

    /**
     * The associated {@link Player}.
     */
    private Player player;
    private String title;
    private String color = "";
    private String shad = "";
    public Titles(Player player) {
        this.player = player;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShad() {
        return shad;
    }

    public void setShad(String shad) {
        this.shad = shad;
    }

}
