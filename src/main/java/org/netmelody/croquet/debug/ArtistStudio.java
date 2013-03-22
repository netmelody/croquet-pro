package org.netmelody.croquet.debug;

import org.jbox2d.dynamics.World;

public final class ArtistStudio implements Runnable {

    private static final float TIME_STEP = 0.001f;
    private static final int VELOCITY_ITERATIONS_PER_STEP = 3;
    private static final int POSITION_ITERATIONS_PER_STEP = 8;
    public static final int DEFAULT_FPS = 60;

    private final Thread worker;
    private final World world;
    private final Artist artist;

    private boolean working = false;

    public ArtistStudio(World world, Artist artist) {
        this.world = world;
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
            world.step(TIME_STEP, VELOCITY_ITERATIONS_PER_STEP, POSITION_ITERATIONS_PER_STEP);
            artist.draw(world);
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
