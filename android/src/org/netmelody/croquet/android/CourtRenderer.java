package org.netmelody.croquet.android;

import static android.graphics.Color.parseColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
	
	public CourtRenderer(Pitch court) {
		this.court = court;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0.0f, 0.2f, 0.0f, 1.0f);
		
		for (Iterator<Hoop> iterator = court.hoops.iterator(); iterator.hasNext();) {
			final Hoop hoop = iterator.next();
	    	fixedFeatures.add(scaledCircle(hoop.leg1Position.x, hoop.leg1Position.y, hoop.legRadius, Color.WHITE));
	    	fixedFeatures.add(scaledCircle(hoop.leg2Position.x, hoop.leg2Position.y, hoop.legRadius, Color.WHITE));
		}
		fixedFeatures.add(scaledCircle(court.peg.position.x, court.peg.position.y, court.peg.radius, Color.WHITE));
	}

	private final Circle scaledCircle(float x, float y, float radius, int colour) {
		float scale = 30.0f;
		return new Circle((x - court.peg.position.x) / scale, (y - court.peg.position.y) / scale, radius / scale, colour);
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
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
		
		if (currentTransition != null) {
			long frameTime = SystemClock.uptimeMillis() - this.transitionStart;
			int frame = (int)(frameTime / (currentTransition.timeStep * 1000.0f));
			
			if (frame >= currentTransition.footage().size()) {
				ballPositions = currentTransition.finalPositions();
				currentTransition = null;
			}
			else {
				ballPositions = currentTransition.footage().get(frame);
			}
		}
		
		for (Iterator<BallInPlay> iterator = ballPositions.iterator(); iterator.hasNext();) {
			final BallInPlay ballInPlay = iterator.next();
			scaledCircle(ballInPlay.position.x, ballInPlay.position.y, ballInPlay.ball.radius, parseColor(ballInPlay.ball.hexColor)).draw(transformationMatrix);
		}
	}

	public void setBallPositions(List<BallInPlay> ballPositions) {
		this.ballPositions = ballPositions;
	}

	public boolean playStroke(Transition transition) {
		if (currentTransition != null) {
			return false;
		}
		this.currentTransition = transition;
		this.transitionStart = SystemClock.uptimeMillis();
		return true;
	}

}
