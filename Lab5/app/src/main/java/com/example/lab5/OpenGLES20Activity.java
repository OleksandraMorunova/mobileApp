package com.example.lab5;

import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class OpenGLES20Activity extends AppCompatActivity {
    static final String TAG = "OpenGLES20Activity";
    GLSurfaceView mGLView;

    private MediaPlayer mMediaPlayer;

    private int currentShape = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new MyGLSurfaceView(this);
        mMediaPlayer = MediaPlayer.create(this, R.raw.melody);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

        setContentView(mGLView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = MediaPlayer.create(this, R.raw.melody);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        mGLView.onResume();
    }

     public static int loadShader(int type, String shaderCode) {
         int shader = GLES20.glCreateShader(type);
         GLES20.glShaderSource(shader, shaderCode);
         GLES20.glCompileShader(shader);
         return shader;
     }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError ");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_color) {
            ((MyGLSurfaceView) mGLView).changeColor();
            return true;
        }
        if (id == R.id.action_figures) {
            currentShape = (currentShape + 1) % 2;
            ((MyGLSurfaceView) mGLView).setCurrentShape(currentShape);
            mGLView.requestRender();
        }
        return super.onOptionsItemSelected(item);
    }
}