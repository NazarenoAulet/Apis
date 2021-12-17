package com.quasartec.apis.openGL;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.orthoM;
import static android.opengl.Matrix.scaleM;
import static android.opengl.Matrix.setIdentityM;

public class GLRenderer implements GLSurfaceView.Renderer {
    final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    static volatile float aspectRatio;
    static volatile VertexArray vertexArray;

    private static ColorShaderProgram colorProgram;

    public GLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        System.out.println("SURFACECREATED");
        colorProgram = new ColorShaderProgram(context);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        glViewport(0, 0, width, height);
        glClearColor(1f, 0f, 0f, 0f);

        aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;
        if (width > height) {
// Landscape
            orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
// Portrait or square
            orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }


        setIdentityM(viewMatrix, 0);
        // rotateM(viewMatrix, 0, 30, 0, 0, 1);
        scaleM(viewMatrix, 0, 0.2f, 0.2f, 1);
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        vertexArray.bindData(colorProgram);
        colorProgram.useProgram();
        colorProgram.setUniforms(viewProjectionMatrix);

    }


    @Override
    public synchronized void onDrawFrame(GL10 glUnused) {
        //   long time1 = System.currentTimeMillis();
        glClear(GL_COLOR_BUFFER_BIT);
        //    colorProgram.setUniforms(viewProjectionMatrix);

        glDrawArrays(GL_TRIANGLES, 0, 6);
        // System.out.println(System.currentTimeMillis()-time1);
    }

}