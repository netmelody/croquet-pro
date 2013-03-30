package org.netmelody.croquet.android;

import java.util.List;

import org.netmelody.croquet.model.Ball;
import org.netmelody.croquet.model.BallInPlay;
import org.netmelody.croquet.model.Game;
import org.netmelody.croquet.model.Strike;
import org.netmelody.croquet.model.Stroke;
import org.netmelody.croquet.model.Team;
import org.netmelody.croquet.physics.StrokeEnactor;
import org.netmelody.croquet.physics.Transition;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public final class CourtView extends GLSurfaceView {

	private final CourtRenderer renderer;

    private final Game game = new Game(new Team("Team 1"), new Team("Team2"));
    private final StrokeEnactor enactor = new StrokeEnactor(game.pitch);
    
    private List<BallInPlay> ballPositions = game.ballPositions;
	private volatile boolean animating = false;
	
    public interface TransitionCompletionHandler {
    	void completed(Transition t);
    }
    
	public CourtView(Context context) {
		super(context);
		setEGLContextClientVersion(2);
		renderer = new CourtRenderer(game.pitch, new TransitionCompletionHandler() {
			@Override
			public void completed(Transition t) {
				animating = false;
				setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			}
		});
        setRenderer(renderer);
        
        renderer.setBallPositions(game.ballPositions);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	private float x = 0f;
	private float y = 0f;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (animating) {
			return true;
		}
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN && event.getActionIndex() == 0) {
			x = event.getX();
			y = event.getY();
		}
		if (event.getActionMasked() == MotionEvent.ACTION_UP && event.getActionIndex() == 0) {
			float dx = event.getX() - x;
			float dy = event.getY() - y;
			float dirn = (float)Math.atan2(-dx, -dy);
			float power = (float)Math.sqrt(dx *dx + dy * dy) / 10f;
			
			System.out.println(dx + " " + dy + " " + dirn);
			final Transition transition = enactor.makeStroke(ballPositions, Stroke.standard(Ball.BLACK, new Strike(dirn, power)));
			renderer.playStroke(transition);
			setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
			ballPositions = transition.finalPositions();
			animating = true;
		}
		return true;
	}
}
