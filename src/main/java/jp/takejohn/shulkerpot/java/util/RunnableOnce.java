package jp.takejohn.shulkerpot.java.util;

public abstract class RunnableOnce implements Runnable {

    private final Object lock = new Object();

    private volatile boolean executed = false;

    public void runOnce() {
        if (executed) {
            return;
        }
        synchronized (lock) {
            if (executed) {
                return;
            }
            executed = true;
        }
        run();
    }

}
