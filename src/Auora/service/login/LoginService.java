package Auora.service.login;

import Auora.util.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class LoginService implements Runnable {
	
	/**
	 * Singleton instance
	 */
	private static LoginService instance;
	
	/**
	 * Is the service running?
	 */
	private Boolean serviceRunning = Boolean.FALSE;
	
	
	/**
	 * The thread that belongs to the LoginService
	 */
	private Thread loginThread;
	
	/**
	 * A Queue of tasks
	 */
	private BlockingQueue<LoginTask> loginTasks;
	
	/**
	 * A map of login requests
	 */
	private Map<String, LoginTask> serverTasks = new HashMap<String, LoginTask>();
	
	/**
	 * Gets the singleton instance 
	 * @return The instance
	 */
	public static LoginService getSingleton() {
		if(instance == null) {
			instance = new LoginService();
		}
		return instance;
	}
	
	/**
	 * Construct the Service
	 */
	private LoginService() {
		loginThread = new Thread(this);
		loginTasks = new LinkedBlockingQueue<LoginTask>();
	}
	
	/**
	 * Starts the service
	 */
	public void init() {
		serviceRunning = Boolean.TRUE;
		loginThread.start();
	}
	
	/**
	 * Submits a login task
	 * @param task The login task to queue
	 */
	public void submitLogin(LoginTask task) {
		loginTasks.offer(task);
	}
	
	/**
	 * Runs the service
	 */
	@Override
	public void run() {
		Logger.log("World", "LoginService has been started!");
		while(serviceRunning) {
			try {
				LoginTask task = loginTasks.take();
				//service.execute(task);
				task.run();
			} catch(Exception e) {
			//	e.printStackTrace();
				continue;
			}
		} 
	}

	/**
	 * @return the serverTasks
	 */
	public Map<String, LoginTask> getServerTasks() {
		return serverTasks;
	}

	/**
	 * @param serverTasks the serverTasks to set
	 */
	public void setServerTasks(Map<String, LoginTask> serverTasks) {
		this.serverTasks = serverTasks;
	}

}