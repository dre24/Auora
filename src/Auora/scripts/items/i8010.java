package Auora.scripts.items;
/*package Sirens.scripts.items;

import Sirens.events.GameLogicTask;
import Sirens.events.GameLogicTaskManager;
import Sirens.model.player.Player;
import Sirens.scripts.itemScript;

public class i8010 extends itemScript {

	@Override
	public void option1(final Player p, final int itemId, final int interfaceId, final int slot) {
		if(p.getInventory().getContainer().get(slot) == null)
			return;
		if(p.getInventory().getContainer().get(slot).getId() != itemId)
			return;
		if(interfaceId != 149)
			return;
			if(p.isDead()) {
			return;
			}
			if(p.jailTimer > 0) {
			p.getFrames().sendChatMessage(0, "You are jailed.");
			return;
			}
			if(p.teleblockDelay > 0) {
			p.getFrames().sendChatMessage(0, "You cannot teleport while teleblocked.");
			return;
			}
			if(p.tabbing > 0) {
				p.getFrames().sendMessage("Please wait "+p.tabbing / 2 +" to teleport tab.");
			return;
			}
			p.animate(4069);
			p.tabbing = 20;
			GameLogicTaskManager.schedule(new GameLogicTask() {
			int count = 0;
			@Override
			public void run() {
				if(!p.isOnline()) {
					this.stop();
					return;
				}
				if(count++ == 0) {
					p.animate(4071);
					p.graphics(678);
					p.getInventory().deleteItem(8010, 1, slot);
				} else {
				p.getMask().getRegion().teleport(2757, 3477, 0, 0);
					p.animate(-1);	
					this.stop();
				}
			}
			}
		});
	}
}*/