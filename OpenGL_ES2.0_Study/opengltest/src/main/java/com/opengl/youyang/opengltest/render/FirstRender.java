package com.opengl.youyang.opengltest.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.opengl.youyang.opengltest.data.VertexArray;
import com.opengl.youyang.opengltest.program.ColorShaderProgram;
import com.opengl.youyang.opengltest.program.TextureShaderProgram;
import com.opengl.youyang.opengltest.utils.MatrixHelper;
import com.opengl.youyang.opengltest.utils.ShaderHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15-6-26.
 */
public class FirstRender implements GLSurfaceView.Renderer {
    private  final float[] projectionMatrix= new float[16];
    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;
    private Context context;
    private float a=0.5f;
    private final float[] vertex=new float[]{
            -a,a,
            -a,-a,
            a,a,

            a,a,
            -a,-a,
            a,-a
    };
    private VertexArray vertexArray;

    public FirstRender(Context context){
     this.context=context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f,1.0f,1.0f,1.0f);
        vertexArray=new VertexArray(vertex);
        textureShaderProgram= new TextureShaderProgram(context);
        colorShaderProgram = new ColorShaderProgram(context);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        MatrixHelper.perspectiveM(projectionMatrix,45f,(float)width/(float)height,1f,10f);
//        Matrix.setLookAtM();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        colorShaderProgram.useProgram();;
        colorShaderProgram.setUniforms(projectionMatrix,0.5f,0.6f,0.7f);
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributionLocation(), 2, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,6);
    }
}
