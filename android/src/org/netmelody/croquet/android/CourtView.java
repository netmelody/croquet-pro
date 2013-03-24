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
	
	public CourtView(Context context) {
		super(context);
		setEGLContextClientVersion(2);
		renderer = new CourtRenderer(game.pitch);
        setRenderer(renderer);
        
        renderer.setBallPositions(game.ballPositions);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final Transition transition = enactor.makeStroke(ballPositions, Stroke.standard(Ball.BLACK, new Strike(0.05f, 10f)));
		if (renderer.playStroke(transition)) {
		    ballPositions = transition.finalPositions();
		}
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		return true;
	}
}
