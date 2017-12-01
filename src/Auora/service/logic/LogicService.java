package Auora.service.logic;

import Auora.concurrent.Condition;
import Auora.events.GameLogicTaskManager;
import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.model.player.Player;
import Auora.service.logic.task.PostTickTask;
import Auora.service.logic.task.PreTickTask;
import Auora.service.logic.task.UpdateTask;
import Auora.util.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Legacy614 Reworked
 * 
 * @author Harry Andreas
 */
public class LogicService implements Runnable {

	/**
	 * The LogicService instance
	 */
	private static LogicService service;

	/**
	 * Is the service running?
	 */
	private boolean serviceRunning = Boolean.FALSE;

	/**
	 * The thread this service has instanced
	 */
	public Thread thread;
	
	/**
	 * Last tick time
	 */
	public long lastTickTime = 0;

	
	/** 
	 * 
	 */
	private List<Runnable> pre = new ArrayList<Runnable>();
	private List<Runnable> tick = new ArrayList<Runnable>();
	private List<Runnable> post = new ArrayList<Runnable>();

	/**
	 * Gets the singleton instance
	 * 
	 * @return The LogicService
	 */
	public static LogicService getInstance() {
		if (service == null) {
			service = new LogicService();
		}
		return service;
	}

	/**
	 * Constructor
	 */
	private LogicService() {
		thread = new Thread(this);
	}

	public void init() throws Exception {
		if (serviceRunning) {
			throw new Exception("LogicService is already running!");
		}
		serviceRunning = Boolean.TRUE;
		thread.start();
	}
	
	public static Condition<Player> disconnectCondition() {
		return new Condition<Player>() {
			@Override
			public boolean activate(Player node) {
				return true;
				//long difference = System.currentTimeMillis() - node.getCombat().lastHit;
				//return difference > 10000;
			}
		};
	}
	
	public static boolean meetsCondition(final Player p) {
		Condition<Player> condition = disconnectCondition();
		return condition.activate(p);
	}

	/**
	 * Runs the service
	 */
	@Override
	public void run() {
		Logger.log("World", "LogicService is running");
		while (serviceRunning) {
			long startTime = System.currentTimeMillis();
			try {
				GameLogicTaskManager.processTasks();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Iterator<Player> it$ = World.getPlayers().iterator();
			while (it$.hasNext()) {
				Player p = it$.next();
				if (p == null) {
					it$.remove();
					continue;
				}
				try {
					if ((p.getConnection().disconnected || !p.getConnection().getChannel().isConnected()) && meetsCondition(p)) {
						Logger.log("World", p.getDisplayName() + " has disconnected from Auora614.");
						World.unRegisterConnection(p.getConnection());
						World.getPlayers().remove(p.getIndex());
						it$.remove();
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				pre.add(new PreTickTask(p));
				tick.add(new UpdateTask(p));
				post.add(new PostTickTask(p));
			}
			Iterator<Npc> it$$ = World.getNpcs().iterator();
			while(it$$.hasNext()) {
				Npc n = it$$.next();
				pre.add(new PreTickTask(n));
				post.add(new PostTickTask(n));
			}
			TaskExecutor.executeTasks(pre.toArray(new Runnable[0]));
			TaskExecutor.executeTasks(tick.toArray(new Runnable[0]));
			TaskExecutor.executeTasks(post.toArray(new Runnable[0]));
			pre.clear();
			tick.clear();
			post.clear();
			long elapsed = System.currentTimeMillis() - startTime;
			this.lastTickTime = elapsed;
			if (elapsed < 600) {
				try {
					Thread.sleep(600 - elapsed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}