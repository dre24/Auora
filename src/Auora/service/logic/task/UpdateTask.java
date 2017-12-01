package Auora.service.logic.task;

import Auora.model.player.Player;

/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class UpdateTask implements Runnable {
	
	/**
	 * Player instance
	 */
	private Player player;

	/**
	 * Construct the task
	 * @param p
	 */
	public UpdateTask(Player p) {
		this.player = p;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			if(player == null)
				return;
			player.getPlayerUpdate().sendUpdate();
			player.getGni().sendUpdate();
			checkAttackOption(player);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void checkAttackOption(Player player) {
		if (player.getDuelSession() != null && player.getCombat().duelFight(player) || player.duelPartner != null) {
			player.getFrames().sendPlayerOption("Attack", 1, false);
			player.getFrames().sendPlayerOption("Follow", 2, false);
			player.getFrames().sendPlayerOption("Null", 3, false);
			player.getFrames().sendPlayerOption("Null", 4, false);
			player.getFrames().sendInterfaceConfig(745, 1, true);
			player.getFrames().sendInterfaceConfig(745, 3, true);
			player.getFrames().sendInterfaceConfig(745, 6, true);
		} else if (player.getCombat().duelArena(player)) {
			player.getFrames().sendPlayerOption("Null", 1, false);
			player.getFrames().sendPlayerOption("Follow", 2, false);
			player.getFrames().sendPlayerOption("Null", 3, false);
			player.getFrames().sendPlayerOption("Challenge", 4, false);
			player.getFrames().sendInterfaceConfig(745, 1, true);
			player.getFrames().sendInterfaceConfig(745, 3, true);
			player.getFrames().sendInterfaceConfig(745, 6, true);
		/*	player.getFrames().sendInterfaceConfig(745, 4, false);*/
		} else if (player.getCombat().isSafe(player) && player.getCombat().Multi(player)) {
			player.getFrames().sendPlayerOption("Null", 1, false);
			player.getFrames().sendPlayerOption("Follow", 2, false);
			player.getFrames().sendPlayerOption("Trade", 3, false);
			player.getFrames().sendPlayerOption("Null", 4, false);
			player.getFrames().sendInterfaceConfig(745, 1, true);
			player.getFrames().sendInterfaceConfig(745, 3, false);
			player.getFrames().sendInterfaceConfig(745, 6, true);
		} else if (player.getCombat().Multi(player)) {
			player.getFrames().sendInterfaceConfig(745, 1, false);
			player.getFrames().sendInterfaceConfig(745, 3, true);
			player.getFrames().sendInterfaceConfig(745, 6, false);
			player.getFrames().sendPlayerOption("Attack", 1, false);
			player.getFrames().sendPlayerOption("Follow", 2, false);
			player.getFrames().sendPlayerOption("Null", 3, false);
		} else if (!player.getCombat().isSafe(player)) {
			player.getFrames().sendInterfaceConfig(745, 1, true);
			player.getFrames().sendInterfaceConfig(745, 3, true);
			player.getFrames().sendInterfaceConfig(745, 6, false);
			player.getFrames().sendPlayerOption("Attack", 1, false);
			player.getFrames().sendPlayerOption("Follow", 2, false);
			player.getFrames().sendPlayerOption("Null", 3, false);
		} else {
			player.getFrames().sendPlayerOption("Null", 1, false);
			player.getFrames().sendPlayerOption("Follow", 2, false);
			player.getFrames().sendPlayerOption("Trade", 3, false);
			player.getFrames().sendPlayerOption("Null", 4, false);
			player.getFrames().sendInterfaceConfig(745, 1, true);
			player.getFrames().sendInterfaceConfig(745, 3, false);
			player.getFrames().sendInterfaceConfig(745, 6, true);
		}
	}

}
