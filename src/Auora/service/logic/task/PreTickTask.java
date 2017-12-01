package Auora.service.logic.task;

import Auora.model.Entity;
import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.model.player.Player;

/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class PreTickTask implements Runnable {
	
	/**
	 * Player instance
	 */
	private Entity entity;

	/**
	 * Construct the task
	 * @param p
	 */
	public PreTickTask(Entity p) {
		this.entity = p;
	}
	
	@Override
	public void run() {
		World.updateUptime();
		boolean isPlayer = entity instanceof Player;
		if(entity != null) {
			if(isPlayer) {
				Player player = (Player)entity;
				player.tick();
				if(player.isOnline()) {
					entity.getWalk().getNextEntityMovement();
				}
				if(entity.getCombat() != null)
					entity.getCombat().tick();

				player.processQueuedHits();
				//entity.getCombatState().cycle();
				try {
					//entity.getCombatState().run();
				} catch(Exception e) {
					e.printStackTrace();
				}
				player.processQueuedHits();
				World.playerUpdates[entity.getIndex()] = player.getMask().isUpdateNeeded();
			} else {
				Npc npc = (Npc)entity;
				try {
					npc.processQueuedHits();
					entity.getWalk().getNextEntityMovement();
					//entity.getCombatState().cycle();
					//entity.getCombatState().run();
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

}
