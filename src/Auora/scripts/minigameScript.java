package Auora.scripts;

import Auora.model.Entity;
import Auora.model.player.Player;

public abstract class minigameScript {

    public abstract void checkPlayer(Player p);

    public abstract void interactObject(Player p, int id, int x, int y, int clickType);

    public abstract void interactEntity(Player p, Entity target, int clickType);

    public abstract void forceRemovePlayer(Player p);

    public abstract void sendDie(Player p);

    public abstract String minigameName();

    private final void addPlayerToMini(Player p) {
        p.getMinigamemanager().addMinigame(minigameName());
    }
}
