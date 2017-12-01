package Auora.model;

import Auora.GameServer;
import Auora.events.Task;
import Auora.model.npc.Npc;
import Auora.model.player.Player;
import Auora.skills.combat.Combat;
import Auora.util.RSTile;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = -2311863174114543259L;
    public Entity interactingEntity;
    private RSTile location;
    private transient Walking walk;
    private transient Combat combat;
    private transient int Index;
    private transient boolean hidden;
    private boolean teleporting = false;
    private int[] forceWalk;

    public Entity getInteractingEntity() {
        return interactingEntity;
    }

    public void setInteractingEntity(Entity entity) {
        this.interactingEntity = entity;
    }

    public void resetInteractingEntity() {
        this.interactingEntity = null;
    }

    public Player getPlayer() {
        if (this instanceof Player) {
            return (Player) this;
        }
        return null;
    }

    public Npc getNpc() {
        if (this instanceof Npc) {
            return (Npc) this;
        }
        return null;
    }
    /*public boolean isAutoRetaliating() {
        return this instanceof Npc || ((Player) this).autoRetaliate;
	}*/

    public int getIndex() {
        return Index;
    }

    public void setIndex(int index) {
        Index = index;
    }

    public int getClientIndex() {
        if (this instanceof Player) {
            return this.Index + 32768;
        } else {
            return this.Index;
        }
    }

    public boolean isDead() {
        return !(this instanceof Player) || ((Player) this).getSkills().isDead();
    }

    public abstract void heal(int amount);

    public abstract void hit(int damage);

    public abstract void turnTemporarilyTo(Entity entity);

    public abstract void turnTo(Entity entity);

    public abstract void resetTurnTo();

    public abstract void graphics(int id);

    public abstract void graphics(int id, int delay);

    public abstract void graphics2(int id);

    public abstract void graphics2(int id, int delay);

    public abstract void animate(int id);

    public abstract void animate(int id, int delay);

    public void EntityLoad() {
        this.setWalk(new Walking(this));
        this.setCombat(new Combat(this));
        this.setHidden(false);
    }

    public void addPoint(int x, int y) {
        int firstX = x - (getLocation().getRegionX() - 6) * 8;
        int firstY = y - (getLocation().getRegionY() - 6) * 8;
        getWalk().addToWalkingQueue(firstX, firstY);
    }

    public boolean isAutoRetaliating() {
        return ((Player) this).autoRetaliate;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public RSTile getLocation() {
        return location;
    }

    public void setLocation(RSTile location) {
        this.location = location;
    }

    public Walking getWalk() {
        return walk;
    }

    public void setWalk(Walking walk) {
        this.walk = walk;
    }

    public Combat getCombat() {
        return combat;
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    /*
     * author dragonkk
     * returns size.
     */
    public abstract int getSize();

    public int[] getForceWalk() {
        return forceWalk;
    }

    public void setForceWalk(final int[] forceWalk) {
        this.forceWalk = forceWalk;
        if (forceWalk.length > 0) {
            GameServer.getWorldExecutor().schedule(new Task() {

                @Override
                public void run() {
                    teleport(forceWalk[0], forceWalk[1], 0);
                    this.stop();

                }
            }, 0, forceWalk[5]);
        }
    }

    public void forceMovement(final int[] forceMovement, int ticks) {
        GameServer.getWorldExecutor().schedule(new Task() {

            @Override
            public void run() {
                setForceWalk(forceMovement);
                getNpc().getMask().setForceMovementUpdate(true);
                this.stop();

            }
        }, 0, ticks);

    }

    public boolean isTeleporting() {
        return teleporting;
    }

    public void setTeleporting(boolean teleporting) {
        this.teleporting = teleporting;
    }

    public void teleport(int x, int y, int z) {
/*			walkingQueue.reset();*/
        setLocation(RSTile.createRSTile(x, y, z));
        setTeleporting(true);
    }
}
