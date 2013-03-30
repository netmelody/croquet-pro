package org.netmelody.croquet.android;

import static android.graphics.Color.parseColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.netmelody.croquet.android.CourtView.TransitionCompletionHandler;
import org.netmelody.croquet.model.BallInPlay;
import org.netmelody.croquet.model.Hoop;
import org.netmelody.croquet.model.Pitch;
import org.netmelody.croquet.physics.Transition;

import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.os.SystemClock;

public final class CourtRenderer implements Renderer {

	private final Pitch court;
	
	private final float[] projectionMatrix = new float[16];
	private final float[] viewMatrix = new float[16];
	
	private final float[] transformationMatrix = new float[16];
	private final List<Circle> fixedFeatures = new ArrayList<Circle>();

	private List<BallInPlay> ballPositions = new ArrayList<BallInPlay>();

	private volatile Transition currentTransition;
	private volatile long transitionStart;

	private final TransitionCompletionHandler transitionCompletionHandler;
	
	public CourtRenderer(Pitch court, TransitionCompletionHandler transitionCompletionHandler) {
		this.court = court;
		this.transitionCompletionHandler = transitionCompletionHandler;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0.0f, 0.2f, 0.0f, 1.0f);
		
		for (Iterator<Hoop> iterator = court.hoops.iterator(); iterator.hasNext();) {
			final Hoop hoop = iterator.next();
	    	fixedFeatures.add(circle(hoop.leg1Position.x, hoop.leg1Position.y, hoop.legRadius, Color.WHITE));
	    	fixedFeatures.add(circle(hoop.leg2Position.x, hoop.leg2Position.y, hoop.legRadius, Color.WHITE));
		}
		fixedFeatures.add(circle(court.peg.position.x, court.peg.position.y, court.peg.radius, Color.WHITE));
	}

	private final Circle circle(float x, float y, float radius, int colour) {
		return new Circle(x - court.peg.position.x, y - court.peg.position.y, radius, colour);
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		
		float zoom = 0.1f;
//		Matrix.frustumM(projectionMatrix, 0, -ratio/zoom, ratio/zoom, -1/zoom, 1/zoom, 1, 25);
		Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, zoom, 25);
//		Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, zoom, 25*zoom);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		Matrix.setLookAtM(viewMatrix, 0,
				          0, 0, -3,
				          0, 0, 0f,
				          0.0f, -1.0f, 0.0f);
		
		Matrix.multiplyMM(transformationMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
		
		for (Iterator<Circle> iterator = fixedFeatures.iterator(); iterator.hasNext();) {
			iterator.next().draw(transformationMatrix);
		}
		
		final Transition transition = currentTransition;
		if (transition != null) {
			long frameTime = SystemClock.uptimeMillis() - this.transitionStart;
			int frame = (int)(frameTime / (transition.timeStep * 1000.0f));
			
			if (frame >= transition.footage().size()) {
				ballPositions = transition.finalPositions();
				transitionCompletionHandler.completed(transition);
				currentTransition = null;
			}
			else {
				ballPositions = transition.footage().get(frame);
			}
		}
		
		for (Iterator<BallInPlay> iterator = ballPositions.iterator(); iterator.hasNext();) {
			final BallInPlay ballInPlay = iterator.next();
			circle(ballInPlay.position.x, ballInPlay.position.y, ballInPlay.ball.radius, parseColor(ballInPlay.ball.hexColor)).draw(transformationMatrix);
		}
	}

	public void setBallPositions(List<BallInPlay> ballPositions) {
		this.ballPositions = ballPositions;
	}

	public void playStroke(Transition transition) {
		if (currentTransition != null) {
			return;
		}
		this.currentTransition = transition;
		this.transitionStart = SystemClock.uptimeMillis();
	}

}
