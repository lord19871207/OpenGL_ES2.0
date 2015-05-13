package com.opengl.youyang.particles.object;

import com.opengl.youyang.particles.utils.Geometry;

/**
 * Created by youyang on 15-5-13.
 */
public class ParticleShooter {
    private final Geometry.Point position;
    private final Geometry.Vector direction;
    private final int color;

    public ParticleShooter(Geometry.Point position ,Geometry.Vector direction ,int color){
        this.color=color;
        this.position=position;
        this.direction=direction;
    }

    public void addParticles(ParticleSystem particleSystem ,float currentTime, int count){
        for (int i=0;i<count;i++){
            particleSystem.addParticles(position,color,direction,currentTime);
        }
    }
}
