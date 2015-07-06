package com.opengl.youyang.opengltest.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.opengl.youyang.opengltest.R;
import com.opengl.youyang.opengltest.data.VertexArray;
import com.opengl.youyang.opengltest.program.ColorShaderProgram;
import com.opengl.youyang.opengltest.program.TextureShaderProgram;
import com.opengl.youyang.opengltest.utils.LogConfig;
import com.opengl.youyang.opengltest.utils.MatrixHelper;
import com.opengl.youyang.opengltest.utils.ShaderHelper;
import com.opengl.youyang.opengltest.utils.TextResourceReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15-6-26.
 */
public class FirstRender implements GLSurfaceView.Renderer {
    private  final float[] projectionMarix= new float[16];
    private  final float[] viewMatrix= new float[16];
    private  final float[] viewProjectionMatrix= new float[16];
    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;
    private Context context;
    private float a=0.4f;
    private float[] vertex;
    private VertexArray vertexArray;
    public int b=0;
    public FirstRender(Context context){
     this.context=context;

        vertex=new float[]{
                -a,-a,
                a,a,
                -a,a,

                -a,-a,
                a,-a,
                a,a
        };
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f,1.0f,0.5f,0.0f);
        colorShaderProgram = new ColorShaderProgram(context);
        vertex=generateBall();
        vertexArray=new VertexArray(vertex);

        colorShaderProgram.useProgram();
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributionLocation(), 2, 0);

//        textureShaderProgram= new TextureShaderProgram(context);


    }
    float rate;
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        MatrixHelper.perspectiveM(projectionMarix,45,(float)width/(float)height,1f,10f);
//        rate = (float)width/(float)height;
        Matrix.setIdentityM(viewMatrix,0);
        Matrix.translateM(viewMatrix,0,0f,0f,-5f);
        final float[] temp=new float[16];
        Matrix.multiplyMM(temp,0,projectionMarix,0,viewMatrix,0);
        System.arraycopy(temp,0,projectionMarix,0,temp.length);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        b=b+5;
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//        Matrix.translateM(viewMatrix,0,0f,0.2f,0f);
        colorShaderProgram.setUniforms(projectionMarix,0.6f,0.4f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP,0,2160);

//        Matrix.setIdentityM(viewMatrix,0);
//        Matrix.translateM(viewMatrix,0,-0.4f,0f,0f);
//        Matrix.rotateM(viewMatrix,0,b,0.0f,1f,0f);
//        Matrix.translateM(viewMatrix,0,0.4f,0f,0f);
//        colorShaderProgram.setUniforms(viewMatrix,0.9f,0.2f,1.0f);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);
    }

    private float[] generateBall(){
        int count=0;
        float[] ballVertex=new float[720*3];//20层 每一层的圆 36个点
        for (int w=-90;w<90;w=w+9){
            for(int angle=0;angle<360;angle=angle+10){
                double r=Math.cos(angleTOradian(w));
                float y= (float) Math.sin(angleTOradian(w));
                float x= (float) (r*Math.cos(angleTOradian(angle)));
                float z= (float) (r*Math.sin(angleTOradian(angle)));
                ballVertex[count++]=x;
                ballVertex[count++]=y;
                ballVertex[count++]=z;

            }
        }
        return ballVertex;
    }


    private double angleTOradian(float radian){
        return radian*Math.PI/180.0f;
    }
}
