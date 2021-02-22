package ch.band.inf2019.uk335.model;

import java.util.concurrent.Executor;

public class NewThreadExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
