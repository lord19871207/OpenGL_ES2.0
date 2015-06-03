package com.opengl.youyang.particles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.opengl.youyang.particles.object.HeightMap;
import com.opengl.youyang.particles.object.ParticleShooter;
import com.opengl.youyang.particles.object.ParticleSystem;
import com.opengl.youyang.particles.object.SkyBox;
import com.opengl.youyang.particles.programs.HeightMapShaderProgram;
import com.opengl.youyang.particles.programs.ParticleShaderProgram;
import com.opengl.youyang.particles.programs.SkyboxShaderProgram;
import com.opengl.youyang.particles.utils.Geometry;
import com.opengl.youyang.particles.utils.MatrixHelper;
import com.opengl.youyang.particles.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15-4-12.
 */
public class FirstRender implements GLSurfaceView.Renderer {
    private Context context;
    private final float[] modelMatrix=new float[16];
    private final float[] tempMatrix=new float[16];
    private final float[] viewMatrix=new float[16];
    private final float[] projectionMatrix=new float[16];

    private final float[] viewMatrixForSkybox=new float[16];
    private final float[] modelViewProjectionMatrix=new float[16];


//    private final float[] viewProjectionMatrix=new float[16];


    private HeightMapShaderProgram heightMapShaderProgram;
    private HeightMap heightMap;

    private ParticleShaderProgram particleShaderProgram;
    private SkyboxShaderProgram skyboxShaderProgram;
    private SkyBox skyBox;
    private int skyboxTexture;

    private ParticleSystem particleSystem;
    private ParticleShooter redParticleShooter;
    private ParticleShooter greenParticleShooter;
    private ParticleShooter blueParticleShooter;

    private long globalStartTime;

    private int particleTexture;


    public FirstRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //当surface被创建的时候GlsurfaceView会运行这个方法。这表示在应用程序第一次运行时，设备被唤醒时，或者从其他activity备切换回来时 都有可能执行这个方法。
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CULL_FACE);

        final float angleVar=5f;
        final float speedVar=1.5f;

        skyboxShaderProgram=new SkyboxShaderProgram(context);

        heightMapShaderProgram=new HeightMapShaderProgram(context);
        Bitmap bt= BitmapFactory.decodeResource(context.getResources(),R.drawable.heightmap);
        //((BitmapDrawable)context.getResources().getDrawable(R.drawable.heightmap)).getBitmap()
        heightMap=new HeightMap(bt);

        skyBox=new SkyBox();
        skyboxTexture=TextureHelper.loadCubeMap(context,new int[]{
                R.drawable.left,R.drawable.right,R.drawable.bottom,R.drawable.top,R.drawable.front,R.drawable.back
        });


        particleTexture= TextureHelper.loadTexture(context,R.drawable.particle_texture);

        particleShaderProgram =new ParticleShaderProgram(context);
        particleSystem=new ParticleSystem(10000);
        globalStartTime=System.nanoTime();

        final Geometry.Vector particleDirection=new Geometry.Vector(0f,0.5f,0f);

        redParticleShooter=new ParticleShooter(new Geometry.Point(-1f,0f,0f),particleDirection, Color.rgb(255,50,5),angleVar,speedVar);
        greenParticleShooter=new ParticleShooter(new Geometry.Point(0f,0f,0f),particleDirection, Color.rgb(25,255,25),angleVar,speedVar);
        blueParticleShooter=new ParticleShooter(new Geometry.Point(1f,0f,0f),particleDirection, Color.rgb(5,50,255),angleVar,speedVar);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        //surface尺寸发生变化时执行。 比如横竖屏切换
        GLES20.glViewport(0, 0, i, i1);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) i / (float) i1, 1f, 100f);
        updateViewMatrix();
//        Matrix.setIdentityM(viewMatrix, 0);
//
//        Matrix.translateM(viewMatrix, 0, 0f, -1.5f, -5f);
//        Matrix.multiplyMM(viewProjectionMatrix,0,projectionMatrix,0,viewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        drawHeightMap();
        drawSkybox();
        drawParticles();
    }


    private void divideByW(float[] vector){
        vector[0]/=vector[3];
        vector[1]/=vector[3];
        vector[2]/=vector[3];

    }

    public void drawHeightMap(){
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.scaleM(modelMatrix, 0, 100f, 10f, 100f);
        updateMvpMatrix();
        heightMapShaderProgram.useProgram();
        heightMapShaderProgram.setUniforms(modelViewProjectionMatrix);
        heightMap.bindData(heightMapShaderProgram);
        heightMap.draw();

    }

    public void drawSkybox(){
        //把视图矩阵转化为单位矩阵
        Matrix.setIdentityM(viewMatrix, 0);
//        Matrix.rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
//        Matrix.rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f);
////        Matrix.translateM(viewMatrix,0,0f,-1.5f,-5f);
//        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
       updateMvpMatrixForSkybox();

        skyboxShaderProgram.useProgram();
        skyboxShaderProgram.setUniforms(modelViewProjectionMatrix, skyboxTexture);
        skyBox.binfData(skyboxShaderProgram);
        skyBox.draw();
        GLES20.glDepthFunc(GLES20.GL_LESS);
    }

    public void drawParticles(){
        float currentTime= (System.nanoTime()-globalStartTime)/1000000000f;
        redParticleShooter.addParticles(particleSystem,currentTime,1);
        greenParticleShooter.addParticles(particleSystem, currentTime, 1);
        blueParticleShooter.addParticles(particleSystem, currentTime, 1);

        Matrix.setIdentityM(viewMatrix, 0);

//        Matrix.translateM(viewMatrix, 0, 0f, -1.5f, -5f);
//        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        updateMvpMatrix();

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE);

        particleShaderProgram.useProgram();
        particleShaderProgram.setUniforms(modelViewProjectionMatrix, currentTime, particleTexture);
        particleSystem.bindData(particleShaderProgram);

        particleSystem.draw();

        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDepthMask(true);
    }


    private float xRotation,yRotation;
    public void handleTouchDrag(float deltaX, float deltaY) {
        xRotation+=deltaX/16f;
        yRotation+=deltaY/16f;

        if(yRotation<-90){
            yRotation=-90;
        }else if(yRotation>90){
            yRotation=90;
        }

        updateViewMatrix();
    }

    private void updateViewMatrix(){
        Matrix.setIdentityM(viewMatrix,0);
        Matrix.rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f);
        Matrix.rotateM(viewMatrix,0,-xRotation,0f,1f,0f);
        System.arraycopy(viewMatrix, 0, viewMatrixForSkybox, 0, viewMatrix.length);

        Matrix.translateM(viewMatrix, 0, 0, -1.5f, -5f);
    }

    private void updateMvpMatrix(){
        Matrix.multiplyMM(tempMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, tempMatrix, 0);

    }

    private void updateMvpMatrixForSkybox(){
        Matrix.multiplyMM(tempMatrix,0,viewMatrixForSkybox,0,modelMatrix,0);
        Matrix.multiplyMM(modelViewProjectionMatrix,0,projectionMatrix,0,tempMatrix,0);
    }


}
