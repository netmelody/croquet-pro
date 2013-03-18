package com.scarytom.collider.sandpit;

public final class ArtistStudio implements Runnable {

    public static final int DEFAULT_FPS = 60;

    private final Thread worker;
    private final Model model;
    private final Artist artist;

    private boolean working = false;

    public ArtistStudio(Model model, Artist artist) {
        this.model = model;
        this.artist = artist;
        worker = new Thread(this, "animator");
    }

    public synchronized void start() {
        if (working != true) {
            worker.start();
        }
    }

    public synchronized void stop() {
        working = false;
    }

    public void run() {
        long beforeTime;
        beforeTime = System.nanoTime();

        working = true;
        while (working) {
            model.step();
            artist.draw(model);
            long sleepTime = (1000000000 / DEFAULT_FPS - (System.nanoTime() - beforeTime)) / 1000000;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {
                }
            }

            beforeTime = System.nanoTime();
        }
    }
}
