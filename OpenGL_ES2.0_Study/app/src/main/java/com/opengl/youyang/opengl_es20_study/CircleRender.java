package com.opengl.youyang.opengl_es20_study;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import com.opengl.youyang.opengl_es20_study.object.Circle;
import com.opengl.youyang.opengl_es20_study.programs.ColorShaderProgram;
import com.opengl.youyang.opengl_es20_study.utils.MatrixHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15-6-10.
 */
public class CircleRender implements Renderer {
    private Circle circle;
    //存储矩阵数据
    private final float[] projectionMarix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix=new float[16];


    private ColorShaderProgram colorShaderProgram;
    private Context context;

    public CircleRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        circle = new Circle(100);
        colorShaderProgram = new ColorShaderProgram(context);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        GLES20.glViewport(0, 0, i, i1);
        MatrixHelper.perspectiveM(projectionMarix, 45, (float) i / (float) i1, 1f, 5f);
        Matrix.setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //每次绘制一帧画面时都会调用。如果什么都不做，可能会看到糟糕的闪烁效果
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMarix, 0, viewMatrix, 0);

        colorShaderProgram.useProgram();
        Matrix.setIdentityM(viewMatrix,0);
        Matrix.translateM(viewProjectionMatrix,0,0,0,-5f);
//        Matrix.rotateM(viewProjectionMatrix,0,-30f,1f,0f,0f);
        colorShaderProgram.setUniforms(viewProjectionMatrix, 1f, 0f, 0f);
        circle.bindData(colorShaderProgram);
        circle.draw();
    }
}
