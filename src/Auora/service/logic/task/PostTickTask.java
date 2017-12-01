package Auora.service.logic.task;

import Auora.model.Entity;
import Auora.model.npc.Npc;
import Auora.model.player.Player;

/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class PostTickTask implements Runnable {
	
	/**
	 * Player instance
	 */
	private Entity entity;

	/**
	 * Construct the task
	 * @param p
	 */
	public PostTickTask(Entity p) {
		this.entity = p;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(entity == null)
			return;
		if(entity instanceof Player) {
			Player player = (Player)entity;
			if (player.isOnline()) {
				player.getMask().reset();
			}
		} else {
			Npc npc = (Npc)entity;
			npc.getMask().reset();
		}
	}

}
