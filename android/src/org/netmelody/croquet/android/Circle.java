package org.netmelody.croquet.android;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

public final class Circle {

	private static final int BYTES_PER_FLOAT = 4;
	private static final int COORDS_PER_VERTEX = 3;
	private static final int POINTS_IN_CIRCLE = 13;
	private static final int VERTEX_COUNT = POINTS_IN_CIRCLE + 1;
	
    private static final String VERTEX_SHADER =
        "uniform mat4 matrix;" +
        "attribute vec4 position;" +
        "void main() {" +
        "  gl_Position = position * matrix;" +
        "}";

    private static final String FRAGMENT_SHADER =
        "precision mediump float;" +
        "uniform vec4 colour;" +
        "void main() {" +
        "  gl_FragColor = colour;" +
        "}";
	
    private final float color[] = { 0.19f, 0.864734f, 0.746352f, 1.0f };
	private final FloatBuffer vertexBuffer;
	private final int graphicsProgram;
	
	public Circle() {
		final float coords[] = calculateCircleCoords(0.0f, 0.0f, 0.6f);
        
		final ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * BYTES_PER_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);
        
        final int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
        final int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
        graphicsProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(graphicsProgram, vertexShader);
        GLES20.glAttachShader(graphicsProgram, fragmentShader);
        GLES20.glLinkProgram(graphicsProgram); 
	}

	public void draw(float[] transformationMatrix) {
		GLES20.glUseProgram(graphicsProgram);

		final int positionHandle = GLES20.glGetAttribLocation(graphicsProgram, "position");
		GLES20.glEnableVertexAttribArray(positionHandle);
		GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                					 GLES20.GL_FLOAT, false,
                					 COORDS_PER_VERTEX * BYTES_PER_FLOAT, vertexBuffer);

		final int colorHandle = GLES20.glGetUniformLocation(graphicsProgram, "colour");
		GLES20.glUniform4fv(colorHandle, 1, color, 0);

		final int matrixHandle = GLES20.glGetUniformLocation(graphicsProgram, "matrix");
		GLES20.glUniformMatrix4fv(matrixHandle, 1, false, transformationMatrix, 0);
		
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, VERTEX_COUNT);
//		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 1);
		
		GLES20.glDisableVertexAttribArray(positionHandle);
	}
	
	/**
	 * first coordinate is centre of circle, followed by N points on circumference.
	 */
	private static float[] calculateCircleCoords(float centreX, float centreY, float radius) {
		final float coords[] = new float[VERTEX_COUNT * COORDS_PER_VERTEX];
		final float inc = (float)(Math.PI * 2.0f / POINTS_IN_CIRCLE);
		
		coords[0] = centreX; coords[1] = centreY; coords[2] = 0.0f;
        for (int i = 1; i < POINTS_IN_CIRCLE; i += COORDS_PER_VERTEX) {
        	coords[i + 0] = (float)(centreX + Math.cos(i * inc) * radius);
        	coords[i + 1] = (float)(centreY + Math.sin(i * inc) * radius);
        	coords[i + 2] = 0.0f;
        }
        return coords;
	}
	
    private static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
