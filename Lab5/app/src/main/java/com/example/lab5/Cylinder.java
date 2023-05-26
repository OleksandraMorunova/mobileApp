package com.example.lab5;

import static com.example.lab5.OpenGLES20Activity.checkGlError;
import static com.example.lab5.OpenGLES20Activity.loadShader;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Cylinder {

    private final int mProgram;
    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;

    float[] color = {0.863671875f, 0.876953125f, 0.22265625f, 0.0f};

    private static final int COORDS_PER_VERTEX = 3;

    private static float[] cylinderCoords;
    private static short[] drawOrder;

    private static void generateCylinderGeometry() {
        int numPoints = 250;
        cylinderCoords = new float[numPoints * 12];
        drawOrder = new short[numPoints * 36];

        float angleStep = 360.0f / numPoints;
        float angle = 150.0f;
        int index = 0;

        for (int i = 0; i < numPoints; i++) {
            float radius = 0.25f;
            float x = radius * (float) Math.cos(Math.toRadians(angle));
            float z = radius * (float) Math.sin(Math.toRadians(angle));

            cylinderCoords[index++] = x;
            float height = 0.5f;
            cylinderCoords[index++] = height / 2 + 0.25f;
            cylinderCoords[index++] = z;

            cylinderCoords[index++] = x;
            cylinderCoords[index++] = -height / 2 + 0.25f;
            cylinderCoords[index++] = z;

            cylinderCoords[index++] = x;
            cylinderCoords[index++] = height / 2 + 0.25f;
            cylinderCoords[index++] = z;

            cylinderCoords[index++] = x;
            cylinderCoords[index++] = -height / 2 + 0.25f;
            cylinderCoords[index++] = z;

            angle += angleStep;
        }

        index = 0;

        for (short i = 0; i < numPoints; i++) {
            drawOrder[index++] = (short) (i * 4);
            drawOrder[index++] = (short) ((i + 1) % numPoints * 4);
            drawOrder[index++] = (short) (i * 4 + 1);
            drawOrder[index++] = (short) (i * 4 + 1);
            drawOrder[index++] = (short) ((i + 1) % numPoints * 4);
            drawOrder[index++] = (short) (((i + 1) % numPoints * 4) + 1);

            drawOrder[index++] = (short) (i * 4 + 2);
            drawOrder[index++] = (short) ((i + 1) % numPoints * 4 + 2);
            drawOrder[index++] = (short) (i * 4 + 3);
            drawOrder[index++] = (short) (i * 4 + 3);
            drawOrder[index++] = (short) ((i % numPoints * 4 + 2));
            drawOrder[index++] = (short) (((i + 1) % numPoints * 4 + 3));

            drawOrder[index++] = (short) (i * 4);
            drawOrder[index++] = (short) (i * 4 + 1);
            drawOrder[index++] = (short) ((i + 1) % numPoints * 4 + 1);
            drawOrder[index++] = (short) (i * 4);
            drawOrder[index++] = (short) ((i + 1) % numPoints * 4 + 1);
            drawOrder[index++] = (short) ((i + 1) % numPoints * 4);

            drawOrder[index++] = (short) (i * 4 + 2);
            drawOrder[index++] = (short) ((i + 1) % numPoints * 4 + 2);
            drawOrder[index++] = (short) (i * 4 + 3);
            drawOrder[index++] = (short) (i * 4 + 3);
            drawOrder[index++] = (short) ((i + 1) % numPoints * 4 + 2);
            drawOrder[index++] = (short) (((i + 1) % numPoints * 4 + 3));
        }
    }

    public Cylinder() {
        generateCylinderGeometry();

        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(cylinderCoords.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = vertexByteBuffer.asFloatBuffer();
        vertexBuffer.put(cylinderCoords);
        vertexBuffer.position(0);

        ByteBuffer drawListByteBuffer = ByteBuffer.allocateDirect(drawOrder.length * 2);
        drawListByteBuffer.order(ByteOrder.nativeOrder());
        drawListBuffer = drawListByteBuffer.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        String vertexShaderCode = "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main(){" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}";
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);

        String fragmentShaderCode = "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main(){" +
                "  gl_FragColor = vColor;" +
                "}";
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(mProgram);

        int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        int vertexStride = COORDS_PER_VERTEX * 4;
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        int mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        int mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        checkGlError("glUniformMatrix4fv");

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    public void scale(float scaleFactor) {
        for (int i = 0; i < cylinderCoords.length; i++) {
            cylinderCoords[i] *= scaleFactor;
        }

        ByteBuffer bb = ByteBuffer.allocateDirect(cylinderCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer.clear();
        vertexBuffer.put(cylinderCoords);
        vertexBuffer.position(0);
    }

    public void changeColor() {
        float red = (float) Math.random();
        float green = (float) Math.random();
        float blue = (float) Math.random();
        color[0] = red;
        color[1] = green;
        color[2] = blue;
    }

    public void translateCylinder(float dx, float dy) {
        for (int i = 0; i < cylinderCoords.length; i += COORDS_PER_VERTEX) {
            cylinderCoords[i] += dx;
            cylinderCoords[i + 1] += dy;
        }

        ByteBuffer bb = ByteBuffer.allocateDirect(cylinderCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer.clear();
        vertexBuffer.put(cylinderCoords);
        vertexBuffer.position(0);
    }
}