package org.netmelody.croquet.renderer;

import java.util.List;

import org.netmelody.croquet.model.BallInPlay;
import org.netmelody.croquet.physics.Transition;

public final class TransitionProjector {

    public static final int DEFAULT_FPS = 60;
    private static final long NANOS_PER_FRAME = 1000000000L / DEFAULT_FPS;

    private final PitchView view;

    public TransitionProjector(PitchView view) {
        this.view = view;
    }

    public void project(final Transition transition) {
        project(transition.timeStep, transition.footage());
    }

    public void project(float timeStep, List<List<BallInPlay>> footage) {
        final long cellsPerNano = (long)(1000000000.0 * timeStep);
        final long runStartTime = System.nanoTime();
        
        long frameStartTime = runStartTime;
        int frameNo = 0;
        
        do {
            view.draw(footage.get(frameNo));
            
            long sleepTimeMillis = (NANOS_PER_FRAME - (System.nanoTime() - frameStartTime)) / 1000000L;
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
