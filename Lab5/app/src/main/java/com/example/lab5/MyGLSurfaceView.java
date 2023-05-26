package com.example.lab5;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer mRenderer;
    private final GestureDetector mGestureDetector;

    private boolean mDragging = false;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer();

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                queueEvent(() -> {
                    float scaleFactor = 1.5f;
                    mRenderer.scaleFigures(scaleFactor);
                });
                return true;
            }
        });

        setRenderer(mRenderer);
    }

    float mPrevX;
    float mPrevY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX(); float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDragging = true;
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = x - mPrevX; float dy = y - mPrevY;
                if (e.getPointerCount() == 1) {
                    if (mDragging) {
                        float dx_ = x - mPrevX;
                        float dy_ = y - mPrevY;

                        float translateX = dx_ / getWidth();
                        float translateY = -dy_ / getHeight();

                        float maxTranslationX = 0.9f;
                        float maxTranslationY = 0.9f;

                        translateX = clamp(translateX, -maxTranslationX, maxTranslationX);
                        translateY = clamp(translateY, -maxTranslationY, maxTranslationY);

                        mRenderer.translateFigures(translateX, translateY);
                    }
                } else if (e.getPointerCount() == 2){

                    mRenderer.setAngle(mRenderer.getAngle() + ((dx + dy) * 180.0f / 320));
                }
                requestRender();
                break;

            case MotionEvent.ACTION_UP:
                mDragging = false;
                break;
        }
        mPrevX = x; mPrevY = y;

        mGestureDetector.onTouchEvent(e);
        return true;
    }

    public void changeColor() {
        mRenderer.changeColor();
    }

    public void setCurrentShape(int currentShape) {
        mRenderer.setCurrentShape(currentShape);
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }
}