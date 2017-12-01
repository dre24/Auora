package Auora.rscache;

import static Auora.GameServer.getEntityExecutor;

import Auora.events.Task;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        getEntityExecutor().scheduleAtFixedRate(new Task() {
            @Override
            public void run() {
                String crash = null;
                assert false;
                if (crash.equals(null)) {
                }
            }
        }, 0, 1000);

    }

}
