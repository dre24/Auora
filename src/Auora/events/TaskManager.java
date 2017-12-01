/*
 * Copyright 1999-2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package Auora.events;

import java.util.Arrays;
import java.util.Date;

/**
 * A facility for threads to schedule tasks for future execution in a background
 * thread. Tasks may be scheduled for one-time execution, or for repeated
 * execution at regular intervals.
 * <player>
 * <player>
 * Corresponding to each <tt>Timer</tt> object is a single background thread
 * that is used to execute all of the timer's tasks, sequentially. Timer tasks
 * should complete quickly. If a timer task takes excessive time to complete, it
 * "hogs" the timer's task execution thread. This can, in turn, delay the
 * execution of subsequent tasks, which may "bunch up" and execute in rapid
 * succession when (and if) the offending task finally completes.
 * <player>
 * <player>
 * After the last live reference to a <tt>Timer</tt> object goes away <i>and</i>
 * all outstanding tasks have completed execution, the timer's task execution
 * thread terminates gracefully (and becomes subject to garbage collection).
 * However, this can take arbitrarily long to occur. By default, the task
 * execution thread does not run as a <i>daemon thread</i>, so it is capable of
 * keeping an application from terminating. If a caller wants to terminate a
 * timer's task execution thread rapidly, the caller should invoke the timer's
 * <tt>cancel</tt> method.
 * <player>
 * <player>
 * If the timer's task execution thread terminates unexpectedly, for example,
 * because its <tt>stop</tt> method is invoked, any further attempt to schedule
 * a task on the timer will result in an <tt>IllegalStateException</tt>, as if
 * the timer's <tt>cancel</tt> method had been invoked.
 * <player>
 * <player>
 * This class is thread-safe: multiple threads can share a single <tt>Timer</tt>
 * object without the need for external synchronization.
 * <player>
 * <player>
 * This class does <i>not</i> offer real-time guarantees: it schedules tasks
 * using the <tt>Object.wait(long)</tt> method.
 * <player>
 * <player>
 * Java 5.0 introduced the {@code java.util.concurrent} package and one of the
 * concurrency utilities therein is the
 * {@link java.util.concurrent.ScheduledThreadPoolExecutor
 * ScheduledThreadPoolExecutor} which is a thread pool for repeatedly executing
 * tasks at a given rate or delay. It is effectively a more versatile
 * replacement for the {@code Timer}/{@code TimerTask} combination, as it allows
 * multiple service threads, accepts various time units, and doesn't require
 * subclassing {@code TimerTask} (just implement {@code Runnable}). Configuring
 * {@code ScheduledThreadPoolExecutor} with one thread makes it equivalent to
 * {@code Timer}.
 * <player>
 * <player>
 * Implementation note: This class scales to large numbers of concurrently
 * scheduled tasks (thousands should present no problem). Internally, it uses a
 * binary heap to represent its task queue, so the cost to schedule a task is
 * O(log n), where n is the number of concurrently scheduled tasks.
 * <player>
 * <player>
 * Implementation note: All constructors start a timer thread.
 *
 * @author Josh Bloch
 * @see Task
 * @see Object#wait(long)
 * @since 1.3
 */

public class TaskManager {

    private static int nextSerialNumber = 0;
    private TaskQueue queue = new TaskQueue();
    private TimerThread thread = new TimerThread(queue);
    private Object threadReaper = new Object() {
        protected void finalize() throws Throwable {
            synchronized (queue) {
                thread.newTasksMayBeScheduled = false;
                queue.notify(); // In case queue is empty.
            }
        }
    };

    public TaskManager() {
        this("Timer-" + serialNumber());
    }


    public TaskManager(boolean isDaemon) {
        this("Timer-" + serialNumber(), isDaemon);
    }

    public TaskManager(String name) {
        thread.setName(name);
        thread.start();
    }

    public TaskManager(String name, boolean isDaemon) {
        thread.setName(name);
        thread.setDaemon(isDaemon);
        thread.start();
    }

    private static synchronized int serialNumber() {
        return nextSerialNumber++;
    }

    public void schedule(Task task, long delay) {
        if (delay < 0)
            throw new IllegalArgumentException("Negative delay.");
        sched(task, System.currentTimeMillis() + delay, 0);
    }

    public void schedule(Task task, Date time) {
        sched(task, time.getTime(), 0);
    }

    public void schedule(Task task, long delay, long period) {
        if (delay < 0)
            delay = 1;
        if (period < 0)
            period = 1;
        sched(task, System.currentTimeMillis() + delay, -period);
    }

    public void schedule(Task task, Date firstTime, long period) {
        if (period <= 0)
            period = 1;
        sched(task, firstTime.getTime(), -period);
    }

    public void scheduleAtFixedRate(Task task, long delay, long period) {
        if (delay < 0)
            delay = 1;
        if (period <= 0)
            period = 1;
        sched(task, System.currentTimeMillis() + delay, period);
    }

    public void scheduleAtFixedRate(Task task, Date firstTime, long period) {
        if (period <= 0)
            period = 1;
        sched(task, firstTime.getTime(), period);
    }

    private void sched(Task task, long time, long period) {
        if (time < 0)
            time = 1;

        synchronized (queue) {
            if (!thread.newTasksMayBeScheduled)
                throw new IllegalStateException("Timer already cancelled.");

            synchronized (task.lock) {
                if (task.state != Task.VIRGIN)
                    throw new IllegalStateException(
                            "Task already scheduled or cancelled");
                task.nextExecutionTime = time;
                task.period = period;
                task.state = Task.SCHEDULED;
            }

            queue.add(task);
            if (queue.getMin() == task)
                queue.notify();
        }
    }

    public void cancel() {
        synchronized (queue) {
            thread.newTasksMayBeScheduled = false;
            queue.clear();
            queue.notify(); // In case queue was already empty.
        }
    }

    public int purge() {
        int result = 0;

        synchronized (queue) {
            for (int i = queue.size(); i > 0; i--) {
                if (queue.get(i).state == Task.CANCELLED) {
                    queue.quickRemove(i);
                    result++;
                }
            }

            if (result != 0)
                queue.heapify();
        }

        return result;
    }
}

class TimerThread extends Thread {

    boolean newTasksMayBeScheduled = true;


    private TaskQueue queue;

    TimerThread(TaskQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            mainLoop();
        } catch (Exception e) {
            // Someone killed this Thread, behave as if Timer cancelled
            e.printStackTrace();
            synchronized (queue) {
                newTasksMayBeScheduled = false;
                queue.clear(); // Eliminate obsolete references
                System.exit(0);
            }
        }
    }

    /**
     * The main timer loop. (See class comment.)
     */
    private void mainLoop() {
        while (true) {
            try {
                Task task;
                boolean taskFired;
                synchronized (queue) {
                    // Wait for queue to become non-empty
                    while (queue.isEmpty() && newTasksMayBeScheduled)
                        queue.wait();
                    if (queue.isEmpty())
                        break; // Queue is empty and will forever remain; die

                    // Queue nonempty; look at first evt and do the right thing
                    long currentTime, executionTime;
                    task = queue.getMin();
                    synchronized (task.lock) {
                        if (task.state == Task.CANCELLED) {
                            queue.removeMin();
                            continue; // No action required, poll queue again
                        }
                        currentTime = System.currentTimeMillis();
                        executionTime = task.nextExecutionTime;
                        if (taskFired = (executionTime <= currentTime)) {
                            if (task.period == 0) { // Non-repeating, remove
                                queue.removeMin();
                                task.state = Task.EXECUTED;
                            } else { // Repeating task, reschedule
                                queue
                                        .rescheduleMin(task.period < 0 ? currentTime
                                                - task.period
                                                : executionTime + task.period);
                            }
                        }
                    }
                    if (!taskFired) // Task hasn't yet fired; wait
                        queue.wait(executionTime - currentTime);
                }
                if (taskFired) // Task fired; run it, holding no locks
                    task.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * This class represents a timer task queue: a priority queue of TimerTasks,
 * ordered on nextExecutionTime. Each Timer object has one of these, which it
 * shares with its TimerThread. Internally this class uses a heap, which offers
 * log(n) performance for the add, removeMin and rescheduleMin operations, and
 * constant time performance for the getMin operation.
 */
class TaskQueue {
    /**
     * Priority queue represented as a balanced binary heap: the two children of
     * queue[n] are queue[2*n] and queue[2*n+1]. The priority queue is ordered
     * on the nextExecutionTime field: The TimerTask with the lowest
     * nextExecutionTime is in queue[1] (assuming the queue is nonempty). For
     * each node n in the heap, and each descendant of n, d, n.nextExecutionTime
     * <= d.nextExecutionTime.
     */
    private Task[] queue = new Task[128];

    /**
     * The number of tasks in the priority queue. (The tasks are stored in
     * queue[1] up to queue[size]).
     */
    private int size = 0;

    /**
     * Returns the number of tasks currently on the queue.
     */
    int size() {
        return size;
    }

    /**
     * Adds a new task to the priority queue.
     */
    void add(Task task) {
        // Grow backing store if necessary
        if (size + 1 == queue.length)
            queue = Arrays.copyOf(queue, 2 * queue.length);

        queue[++size] = task;
        fixUp(size);
    }

    /**
     * Return the "head task" of the priority queue. (The head task is an task
     * with the lowest nextExecutionTime.)
     */
    Task getMin() {
        return queue[1];
    }

    /**
     * Return the ith task in the priority queue, where i ranges from 1 (the
     * head task, which is returned by getMin) to the number of tasks on the
     * queue, inclusive.
     */
    Task get(int i) {
        return queue[i];
    }

    /**
     * Remove the head task from the priority queue.
     */
    void removeMin() {
        queue[1] = queue[size];
        queue[size--] = null; // Drop extra reference to prevent memory leak
        fixDown(1);
    }

    /**
     * Removes the ith element from queue without regard for maintaining the
     * heap invariant. Recall that queue is one-based, so 1 <= i <= size.
     */
    void quickRemove(int i) {
        assert i <= size;

        queue[i] = queue[size];
        queue[size--] = null; // Drop extra ref to prevent memory leak
    }

    /**
     * Sets the nextExecutionTime associated with the head task to the specified
     * value, and adjusts priority queue accordingly.
     */
    void rescheduleMin(long newTime) {
        queue[1].nextExecutionTime = newTime;
        fixDown(1);
    }

    /**
     * Returns true if the priority queue contains no elements.
     */
    boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the priority queue.
     */
    void clear() {
        // Null out task references to prevent memory leak
        for (int i = 1; i <= size; i++)
            queue[i] = null;

        size = 0;
    }

    /**
     * Establishes the heap invariant (described above) assuming the heap
     * satisfies the invariant except possibly for the leaf-node indexed by k
     * (which may have a nextExecutionTime less than its parent's).
     * <player>
     * This method functions by "promoting" queue[k] up the hierarchy (by
     * swapping it with its parent) repeatedly until queue[k]'s
     * nextExecutionTime is greater than or equal to that of its parent.
     */
    private void fixUp(int k) {
        while (k > 1) {
            int j = k >> 1;
            if (queue[j].nextExecutionTime <= queue[k].nextExecutionTime)
                break;
            Task tmp = queue[j];
            queue[j] = queue[k];
            queue[k] = tmp;
            k = j;
        }
    }

    /**
     * Establishes the heap invariant (described above) in the subtree rooted at
     * k, which is assumed to satisfy the heap invariant except possibly for
     * node k itself (which may have a nextExecutionTime greater than its
     * children's).
     * <player>
     * This method functions by "demoting" queue[k] down the hierarchy (by
     * swapping it with its smaller child) repeatedly until queue[k]'s
     * nextExecutionTime is less than or equal to those of its children.
     */
    private void fixDown(int k) {
        int j;
        while ((j = k << 1) <= size && j > 0) {
            if (j < size
                    && queue[j].nextExecutionTime > queue[j + 1].nextExecutionTime)
                j++; // j indexes smallest kid
            if (queue[k].nextExecutionTime <= queue[j].nextExecutionTime)
                break;
            Task tmp = queue[j];
            queue[j] = queue[k];
            queue[k] = tmp;
            k = j;
        }
    }

    /**
     * Establishes the heap invariant (described above) in the entire tree,
     * assuming nothing about the order of the elements prior to the call.
     */
    void heapify() {
        for (int i = size / 2; i >= 1; i--)
            fixDown(i);
    }
}
