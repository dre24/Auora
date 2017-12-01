package Auora.service.logic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Legacy614 Reworked
 * @author Harry Andreas
 */
public class TaskExecutor {
	
	/**
	 * Executor Pool for multi threading tasks
	 */
	private static ExecutorService executorPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	/**
	 * Runs tasks, simple as shit
	 * @param tasks
	 */
	public static void executeTasks(Runnable[] tasks) {
		for(Runnable r : tasks) {
			try {
				r.run();
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	/**
	 * Executes task in the thread pool 
	 * @param tasks The task array to execute in the thread pool
	 * @param latch The CountDownLatch for well concurrency
	 * @throws Exception InteruptedException incase some nigger cant program
	 */
	public static void executeTasksAndWait(Runnable[] tasks, final CountDownLatch latch) throws Exception {
		for(final Runnable r : tasks) {
			executorPool.submit(new Runnable() {
				@Override
				public void run() {	
					try {
						r.run();
					} finally {
						latch.countDown();
					}
				}
			});
		}
		latch.await();
	}

}