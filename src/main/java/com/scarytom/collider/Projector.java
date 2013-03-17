package com.scarytom.collider;

import java.util.List;

import com.scarytom.collider.Model.BallPosition;

public final class Projector implements Runnable {

    public static final int DEFAULT_FPS = 60;

    private final Thread worker;
    private final List<List<BallPosition>> simulation;
    private final Artist artist;

    private boolean working = false;

    public Projector(List<List<BallPosition>> simulation, Artist artist) {
        this.simulation = simulation;
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
        long runStartTime = System.nanoTime();
        long frameStartTime = runStartTime;

        working = true;
        while (working) {
            int frameNo = Long.valueOf((System.nanoTime() - runStartTime) / 10000000L).intValue();
            if (frameNo >= simulation.size()) {
                working = false;
                return;
            }
            
            artist.draw(simulation.get(frameNo));
            
            long sleepTime = (1000000000 / DEFAULT_FPS - (System.nanoTime() - frameStartTime)) / 1000000;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {
                }
            }
            frameStartTime = System.nanoTime();
        }
    }
}
