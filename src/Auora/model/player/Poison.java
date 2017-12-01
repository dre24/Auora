package Auora.model.player;

import Auora.model.Entity;
import Auora.model.Hits;

import java.io.Serializable;

public final class Poison implements Serializable {

    private static final long serialVersionUID = -6324477860776313690L;

    private transient Player entity;
    private int poisonDamage;
    private int poisonCount;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Player entity) {
        this.entity = entity;
    }

    public void makePoisoned(int startDamage) {
        /*if (poisonDamage > startDamage)
			return;*/
        if (entity instanceof Player) {
            Player player = ((Player) entity);
			/*if (player.getPoisonImmune() > System.currentTimeMillis())
				return;*/
            //if (poisonDamage == 0)
            //player.getPackets().sendGameMessage("You are poisoned.");
            player.getFrames().sendChatMessage(0, "You have been poisoned.");
        }
        poisonDamage = startDamage;
        refresh();
    }

    public void processPoison() {
        if (!entity.isDead() && isPoisoned()) {
            if (poisonCount > 0) {
                poisonCount--;
                return;
            }

            entity.hitType(poisonDamage, Hits.HitType.POISON_DAMAGE);
            poisonDamage -= 2;
            if (isPoisoned()) {
                poisonCount = 30;
                return;
            }
            reset();
        }
    }

    public void reset() {
        poisonDamage = 0;
        poisonCount = 0;
        refresh();
    }

    public void refresh() {
        if (entity instanceof Player) {
            Player player = ((Player) entity);
            player.getFrames().sendConfig(102, isPoisoned() ? 1 : 0);
        }
    }

    public boolean isPoisoned() {
        return poisonDamage >= 1;
    }
}
