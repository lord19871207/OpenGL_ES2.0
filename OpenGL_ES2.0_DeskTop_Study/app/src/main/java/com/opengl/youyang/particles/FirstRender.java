package com.opengl.youyang.particles;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.opengl.youyang.particles.object.ParticleShooter;
import com.opengl.youyang.particles.object.ParticleSystem;
import com.opengl.youyang.particles.programs.ParticleShaderProgram;
import com.opengl.youyang.particles.utils.Geometry;
import com.opengl.youyang.particles.utils.MatrixHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15-4-12.
 */
public class FirstRender implements GLSurfaceView.Renderer {
    private Context context;
    private final float[] projectionMatrix=new float[16];
    private final float[] viewMatrix=new float[16];
    private final float[] viewProjectionMatrix=new float[16];

    private ParticleShaderProgram particleShaderProgram;
    private ParticleSystem particleSystem;
    private ParticleShooter redParticleShooter;
    private ParticleShooter greenParticleShooter;
    private ParticleShooter blueParticleShooter;

    private long globalStartTime;




    public FirstRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //当surface被创建的时候GlsurfaceView会运行这个方法。这表示在应用程序第一次运行时，设备被唤醒时，或者从其他activity备切换回来时 都有可能执行这个方法。
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        final float angleVar=5f;
        final float speedVar=1f;

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
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) i / (float) i1, 1f, 10f);
        Matrix.setIdentityM(viewMatrix, 0);

        Matrix.translateM(viewMatrix, 0, 0f, -1.5f, -5f);
        Matrix.multiplyMM(viewProjectionMatrix,0,projectionMatrix,0,viewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        float currentTime= (System.nanoTime()-globalStartTime)/1000000000f;
        redParticleShooter.addParticles(particleSystem,currentTime,5);
        greenParticleShooter.addParticles(particleSystem,currentTime,5);
        blueParticleShooter.addParticles(particleSystem,currentTime,5);

        particleShaderProgram.useProgram();
        particleShaderProgram.setUniforms(viewProjectionMatrix, currentTime);
        particleSystem.bindData(particleShaderProgram);
        particleSystem.draw();
    }


    private void divideByW(float[] vector){
        vector[0]/=vector[3];
        vector[1]/=vector[3];
        vector[2]/=vector[3];

    }
}
