package org.netmelody.croquet.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public final class CourtView extends GLSurfaceView {

	private final CourtRenderer renderer;

	public CourtView(Context context) {
		super(context);
		setEGLContextClientVersion(2);
		renderer = new CourtRenderer();
        setRenderer(renderer);
        
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
}
