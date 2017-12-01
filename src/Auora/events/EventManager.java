package Auora.events;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class EventManager {

    private static final ScheduledExecutorService events = Executors.newScheduledThreadPool(1);


    public static void submit(Event event) {
        submit(event, event.getDelay());
    }

    private static void submit(final Event event, final long delay) {
        events.schedule(new Runnable() {

            public void run() {
                long start = System.currentTimeMillis();
                if (event.isRunning()) {
                    event.execute();
                }
                long elapsed = System.currentTimeMillis() - start;
                long remaining = event.getDelay() - elapsed;
                if (remaining < 0) {
                    remaining = 0;
                }
                if (event.isRunning()) {
                    submit(event, remaining);
                }
            }
        }, delay, TimeUnit.MILLISECONDS);
    }
}