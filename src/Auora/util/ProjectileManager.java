package Auora.util;

import Auora.model.Entity;
import Auora.model.World;
import Auora.model.player.Player;

public class ProjectileManager {

    public static void sendGlobalProjectile(Entity from, Entity to, int gfxId,
                                            int startHeight, int endHeight, int speed, int speed1, int speed2) {
        int distance = (int) Math.round(from.getLocation().getDistance(
                to.getLocation())) > 4 ? 75 : 60;
        for (Player p : World.getPlayers()) {
            if (p == null)
                continue;
            synchronized (p) {
                if (p.isOnline()
                        && (p.getLocation().withinDistance(from.getLocation()) || p
                        .getLocation().withinDistance(to.getLocation()))) {
                    p.getFrames().sendProjectile(from, to, gfxId, startHeight,
                            endHeight, speed, distance, speed1, speed2);
                }
            }
        }
    }

    public static void sendGlobalProjectile2(int x, int y, Entity from, Entity to, int gfxId, int startHeight, int endHeight, int speed, int speed1, int speed2, int distance) {
        if (from == null || to == null) {
            return;
        }

        for (Player p : World.getPlayers()) {
            if (p == null)
                continue;
            synchronized (p) {
                if (p.isOnline()
                        && (p.getLocation().withinDistance(from.getLocation()) || p.getLocation().withinDistance(to.getLocation()))) {
                    p.getFrames().sendProjectile(from, to, gfxId, startHeight, endHeight, speed, distance, speed1, speed2);
                }
            }
        }
    }

    public static void sendGlobalProjectile2(Entity from, Entity to, int gfxId, int startHeight, int endHeight, int speed, int speed1, int speed2, int distance) {
        if (from == null || to == null) {
            return;
        }

        for (Player p : World.getPlayers()) {
            if (p == null)
                continue;
            synchronized (p) {
                if (p.isOnline()
                        && (p.getLocation().withinDistance(from.getLocation()) || p.getLocation().withinDistance(to.getLocation()))) {
                    p.getFrames().sendProjectile(from, to, gfxId, startHeight, endHeight, speed, distance, speed1, speed2);
                }
            }
        }
    }

}