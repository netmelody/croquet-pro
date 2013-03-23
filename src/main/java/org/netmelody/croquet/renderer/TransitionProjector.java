package org.netmelody.croquet.renderer;

import java.util.List;

import org.netmelody.croquet.model.BallInPlay;
import org.netmelody.croquet.physics.Transition;

public final class TransitionProjector {

    private static final short DEFAULT_FPS = 60;
    private static final long NANOS_PER_SECOND = 1000000000L;

    private final PitchView view;
    private final long nanosPerFrame;

    public TransitionProjector(PitchView view) {
        this(view, DEFAULT_FPS);
    }

    public TransitionProjector(PitchView view, short framesPerSecond) {
        this.view = view;
        this.nanosPerFrame = NANOS_PER_SECOND / framesPerSecond;
    }

    public void project(final Transition transition) {
        project(transition.timeStep, transition.footage());
    }

    private void project(float timeStep, List<List<BallInPlay>> footage) {
        final long cellsPerNano = (long)(NANOS_PER_SECOND * timeStep);
        final long runStartTime = System.nanoTime();
        
        long frameStartTime = runStartTime;
        int frameNo = 0;
        
        do {
            view.draw(footage.get(frameNo));
            
            long sleepTimeMillis = (nanosPerFrame - (System.nanoTime() - frameStartTime)) / 1000000L;
            if (sleepTimeMillis > 0) {
                try {
                    Thread.sleep(sleepTimeMillis);
                } catch (InterruptedException ex) {
                    System.out.println("boom");
                }
            }

            frameStartTime = System.nanoTime();
            frameNo = (int)((frameStartTime - runStartTime) / cellsPerNano);
        } while (frameNo < footage.size());
    }
}
