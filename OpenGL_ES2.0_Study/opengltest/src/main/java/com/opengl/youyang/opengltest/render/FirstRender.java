package com.opengl.youyang.opengltest.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.opengl.youyang.opengltest.data.VertexArray;
import com.opengl.youyang.opengltest.object.Ball;
import com.opengl.youyang.opengltest.program.ColorShaderProgram;
import com.opengl.youyang.opengltest.program.TextureShaderProgram;
import com.opengl.youyang.opengltest.utils.MatrixHelper;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15-6-26.
 */
public class FirstRender implements GLSurfaceView.Renderer {

    private ColorShaderProgram colorShaderProgram;
    private Context context;
    float radius;
    private float[] vertex;
    private VertexArray vertexArray;

    private Ball ball;
    DrawCOntroller drawCOntroller;

    public FirstRender(Context context, DrawCOntroller drawCOntroller) {
        this.context = context;
        this.drawCOntroller = drawCOntroller;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 0f, 0f, 0.0f);
        MatrixHelper.initStack();//初始化矩阵
        radius=0.8f;
        ball=new Ball(radius);
        colorShaderProgram = new ColorShaderProgram(context);
        colorShaderProgram.useProgram();
//        textureShaderProgram=new TextureShaderProgram(context);
//        textureShaderProgram.useProgram();
        vertex = ball.generateBall();
        vertexArray = new VertexArray(vertex);

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(45, (float) width / (float) height, 1f, 10f);
        MatrixHelper.setCamera(0, 0, 8.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributionLocation(), 3, 0);
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getNormalLocation(), 3, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//        MatrixHelper.setLightLocation(0.5f,0.5f,0.5f);
//        colorShaderProgram.setUniforms_Location();
//        colorShaderProgram.setUniforms_Camera();
        MatrixHelper.pushMatrix();

        MatrixHelper.pushMatrix();
        MatrixHelper.translate(-0.5f, 0f, 1f);
        drawCOntroller.controllMatrix(MatrixHelper.getFinalMatrix());
        colorShaderProgram.setUniforms(MatrixHelper.getFinalMatrix(), MatrixHelper.getMMatrix(), vertexArray.getFloatBuffer(), radius);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, ball.getvCount());
        MatrixHelper.popMatrix();

        MatrixHelper.pushMatrix();
        MatrixHelper.translate(1.5f, 0f, 0f);
        drawCOntroller.controllMatrix(MatrixHelper.getFinalMatrix());
        colorShaderProgram.setUniforms(MatrixHelper.getFinalMatrix(),MatrixHelper.getMMatrix(), vertexArray.getFloatBuffer(), radius);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, ball.getvCount());
        MatrixHelper.popMatrix();


        MatrixHelper.popMatrix();

    }


    public interface DrawCOntroller {
        void controllMatrix(float[] projectionMarix);
    }

}
