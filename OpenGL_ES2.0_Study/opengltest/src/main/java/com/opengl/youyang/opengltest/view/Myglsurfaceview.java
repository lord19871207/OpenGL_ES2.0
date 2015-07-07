package com.opengl.youyang.opengltest.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.MotionEvent;

import com.opengl.youyang.opengltest.render.FirstRender;

/**
 * Created by youyang on 15-6-23.
 */
public class Myglsurfaceview extends GLSurfaceView implements FirstRender.DrawCOntroller{
    public Myglsurfaceview(Context context) {
        super(context);
    }

    public int dY=0;
    public int dX=0;
    float touchX=0;
    float touchY=0;
    boolean isXLeft;
    boolean isYTop;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if(event.getX()-touchX<0){
                    isXLeft=true;
                }else{
                    isXLeft=false;
                }

                if(event.getY()-touchY<0){
                    isYTop=true;
                }else{
                    isYTop=false;
                }
                break;
        }
        requestRender();
        touchX=event.getX();
        touchY=event.getY();

        return true;
    }

    @Override
    public void controllMatrix(float[] projectionMarix) {

        if(isYTop){
            dY++;
            Matrix.rotateM(projectionMarix, 0, dY, 1.0f, 0f, 0f);
        }else{
            dY--;
            Matrix.rotateM(projectionMarix, 0, dY, 1.0f, 0f, 0f);
        }

        if(isXLeft){
            dX++;
            Matrix.rotateM(projectionMarix, 0, dX, 0.0f, 1.0f, 0f);
        }else{
            dX--;
            Matrix.rotateM(projectionMarix, 0, dX, 0.0f, 1.0f, 0f);
        }

    }
}
