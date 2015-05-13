package com.opengl.youyang.particles.object;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.opengl.youyang.particles.utils.Geometry;

import java.util.Random;

/**
 * Created by youyang on 15-5-13.
 */
public class ParticleShooter {
    private final float angleVar;
    private final float speedVar;

    private final Random random=new Random();
    private float[] rotationMatrix=new float[16];
    private float[] directionVector=new float[4];
    private float[] resultVector=new float[4];

    private final Geometry.Point position;
    private final int color;

    public ParticleShooter(Geometry.Point position ,Geometry.Vector direction ,int color,float angleVar ,float speedVar){
        this.color=color;
        this.position=position;
        this.angleVar=angleVar;
        this.speedVar=speedVar;

        directionVector[0]=direction.x;
        directionVector[1]=direction.y;
        directionVector[2]=direction.z;


    }

    public void addParticles(ParticleSystem particleSystem ,float currentTime, int count){
        for (int i=0;i<count;i++){
            Matrix.setRotateEulerM(rotationMatrix, 0,
                    (random.nextFloat() - 0.5f) * angleVar,
                    (random.nextFloat() - 0.5f) * angleVar,
                    (random.nextFloat() - 0.5f) * angleVar);
            Matrix.multiplyMV(resultVector,0,rotationMatrix,0,directionVector,0);

            float speedAdjustment=1f+random.nextFloat()*speedVar;

            Geometry.Vector thisDirection=new Geometry.Vector(resultVector[0]*speedAdjustment,
                    resultVector[1]*speedAdjustment,resultVector[2]*speedAdjustment);

            particleSystem.addParticles(position,color,thisDirection,currentTime);
        }
    }
}
