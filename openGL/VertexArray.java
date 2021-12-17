package com.quasartec.apis.openGL;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;

import com.quasartec.apis.openGL.ColorShaderProgram;


public class VertexArray {
    final FloatBuffer floatBuffer;
    static int cellCapacity =3;
    static int linesCapacity = 0;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int POSITION_COMPONENT_COUNT = 2;


    public void refresh(float[] vertexData) {// falta un metodo para agregar y uno para quitar ultimo poligono

    }

    public void add(int index, float[] vertexData) {
        int len = vertexData.length;
        floatBuffer.put(vertexData, index*12,12);

    }

    public VertexArray(float[] vertexData) {

        floatBuffer = ByteBuffer
                .allocateDirect(cellCapacity * 12 * 4 + linesCapacity * 4 * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        ;
        //   try {
        floatBuffer.put(vertexData);
      /*  } catch (Throwable e) {
            GLRenderer.error = true;
            while (working || drawing) {
            }

            if (playing) {
                TransformRunner.notUpdateTR = true;

                if (tr.running) tr.pause();

                Main.mn.get().pb.setPause();
                playing = false;
            }
            floatBuffer.clear();
            System.gc();
            GLRenderer.indexOrder0 = 0;


            Toast.makeText(Main.mn.get().getApplicationContext(), "Recursion level is too high!", Toast.LENGTH_SHORT).show();

            changeEndLevel(true);


            movingBranch = false;
            pressedIndex = -1;
           // floatBuffer.clear();
            TransformRunner.notUpdateTR = false;

            GLRenderer.error = false;


        }
*/
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation,
                                       int componentCount, int stride) {
        floatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT,
                false, stride, floatBuffer);
        glEnableVertexAttribArray(attributeLocation);
        floatBuffer.position(0);

    }


    public void bindData(ColorShaderProgram colorProgram) {
        setVertexAttribPointer(0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }
}