package com.scarytom.collider.renderer;

import java.util.List;

import com.scarytom.collider.model.BallInPlay;
import com.scarytom.collider.physics.Transition;

public final class TransitionProjector {

    public static final int DEFAULT_FPS = 60;

    private final PitchView view;

    private boolean working = false;

    public TransitionProjector(PitchView view) {
        this.view = view;
    }

    public synchronized void project(final Transition transition) {
        if (working != true) {
            new Thread(new Runnable(){
                @Override public void run() {
                    project(transition.timeStep, transition.footage());
                }}, "animator").start();
        }
    }

    public synchronized void stop() {
        working = false;
    }

    public void project(float timeStep, List<List<BallInPlay>> footage) {
        long runStartTime = System.nanoTime();
        long frameStartTime = runStartTime;

        working = true;
        while (working) {
            long AVAILABLE_FRAMES_PER_NANOSECOND = (long)(1000000000.0 * timeStep);
            int frameNo = Long.valueOf((System.nanoTime() - runStartTime) / AVAILABLE_FRAMES_PER_NANOSECOND).intValue();
            if (frameNo >= footage.size()) {
                working = false;
                return;
            }
            
            view.draw(footage.get(frameNo));
            
            long sleepTime = (1000000000L / DEFAULT_FPS - (System.nanoTime() - frameStartTime)) / 1000000L;
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
