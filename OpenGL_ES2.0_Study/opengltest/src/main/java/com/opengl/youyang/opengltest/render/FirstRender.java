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

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15-6-26.
 */
public class FirstRender implements GLSurfaceView.Renderer {
    private static final int UNIT_SIZE = 1;
    private  final float[] projectionMarix= new float[16];
    private  final float[] viewMatrix= new float[16];
    private  final float[] viewProjectionMatrix= new float[16];
    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;
    private Context context;
    private float a=0.4f;
    private float[] vertex;
    private VertexArray vertexArray;
    private int vCount=0;
    DrawCOntroller drawCOntroller;
    public FirstRender(Context context,DrawCOntroller drawCOntroller){
     this.context=context;
        this.drawCOntroller=drawCOntroller;
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
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributionLocation(), 3, 0);

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
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//        Matrix.translateM(viewMatrix,0,0f,0.2f,0f);
        drawCOntroller.controllMatrix(projectionMarix);
        colorShaderProgram.setUniforms(projectionMarix,0.6f,0.4f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP,0,vCount);

//        Matrix.setIdentityM(viewMatrix,0);
//        Matrix.translateM(viewMatrix,0,-0.4f,0f,0f);
//        Matrix.rotateM(viewMatrix,0,b,0.0f,1f,0f);
//        Matrix.translateM(viewMatrix,0,0.4f,0f,0f);
//        colorShaderProgram.setUniforms(viewMatrix,0.9f,0.2f,1.0f);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);
    }

    public interface DrawCOntroller {
        void controllMatrix(float[] projectionMarix);
    }

    private float[] generateBall(){
        int count=0;
        ArrayList<Float> aList=new ArrayList<Float>();
        float r=0.8f;
        float[] ballVertex;
        final int angleSpan=10;
        for (int vAngle=-90;vAngle<90;vAngle+=angleSpan){
            for(int hangle=0;hangle<=360;hangle+=angleSpan){
                float x0= (float) (r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hangle)));
                float y0= (float) (r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hangle)));
                float z0= (float) (r*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));

                float x1= (float) (r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hangle+angleSpan)));
                float y1= (float) (r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hangle+angleSpan)));
                float z1= (float) (r*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));

                float x2= (float) (r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.cos(Math.toRadians(hangle+angleSpan)));
                float y2= (float) (r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.sin(Math.toRadians(hangle+angleSpan)));
                float z2= (float) (r*UNIT_SIZE*Math.sin(Math.toRadians(vAngle+angleSpan)));

                float x3= (float) (r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.cos(Math.toRadians(hangle)));
                float y3= (float) (r*UNIT_SIZE*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.sin(Math.toRadians(hangle)));
                float z3= (float) (r*UNIT_SIZE*Math.sin(Math.toRadians(vAngle+angleSpan)));

                //1 3 0    123
                aList.add(x1);aList.add(y1);aList.add(z1);
                aList.add(x3);aList.add(y3);aList.add(z3);
                aList.add(x0);aList.add(y0);aList.add(z0);

                aList.add(x1);aList.add(y1);aList.add(z1);
                aList.add(x2);aList.add(y2);aList.add(z2);
                aList.add(x3);aList.add(y3);aList.add(z3);

            }
        }
        vCount=aList.size()/3;
        ballVertex=new float[aList.size()];
        for (int i=0;i<aList.size();i++){
            ballVertex[i]=aList.get(i);
        }
        return ballVertex;
    }

}
