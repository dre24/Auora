package Auora.events;

public abstract class GameLogicTask implements Runnable {

    protected boolean needRemove;

    public final void stop() {
        needRemove = true;
    }
}
