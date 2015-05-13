package com.opengl.youyang.particles.object;

import android.graphics.Color;
import android.graphics.Path;
import android.opengl.GLES20;

import com.opengl.youyang.particles.Constants;
import com.opengl.youyang.particles.data.VertexArray;
import com.opengl.youyang.particles.programs.ParticleShaderProgram;
import com.opengl.youyang.particles.utils.Geometry;

/**
 * Created by youyang on 15-5-12.
 */
public class ParticleSystem {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int VECTOR_COMPONENT_COUNT = 3;
    private static final int PARTICL_START_TIME_COMPONENT_COUNT = 3;

    private static final int TOTAL_COMPONENT_COUNT = POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT + VECTOR_COMPONENT_COUNT + PARTICL_START_TIME_COMPONENT_COUNT;
    private static final int STRIDE = TOTAL_COMPONENT_COUNT * Constants.BYTES_PER_FLOAT;

    private final float[] particles;
    private final VertexArray vertexArray;
    private final int maxParticlCount;

    private int currentParticleCount;
    private int nextParticle;

    public ParticleSystem(int maxParticlCount) {
        this.maxParticlCount = maxParticlCount;
        particles = new float[maxParticlCount * TOTAL_COMPONENT_COUNT];
        vertexArray = new VertexArray(particles);
    }

    public void addParticles(Geometry.Point position, int color, Geometry.Vector direction, float particleStartTime) {
        final int particleOffset = nextParticle * TOTAL_COMPONENT_COUNT;
        int currentOffset = particleOffset;
        nextParticle++;

        if (currentParticleCount < maxParticlCount) {
            currentParticleCount++;
        }

        if (nextParticle == maxParticlCount) {
            nextParticle = 0;
        }

        particles[currentOffset++] = position.x;
        particles[currentOffset++] = position.y;
        particles[currentOffset++] = position.z;

        particles[currentOffset++] = Color.red(color) / 255f;
        particles[currentOffset++] = Color.green(color) / 255f;
        particles[currentOffset++] = Color.blue(color) / 255f;

        particles[currentOffset++] = direction.x;
        particles[currentOffset++] = direction.x;
        particles[currentOffset++] = direction.x;

        particles[currentOffset++] = particleStartTime;

        vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT);
    }

    public void bindData(ParticleShaderProgram particleShaderProgram) {
        int dataOffset = 0;
        vertexArray.setVertexAttribPointer(dataOffset, particleShaderProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, STRIDE);
        dataOffset += POSITION_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset, particleShaderProgram.getColorAttributeLocation(), COLOR_COMPONENT_COUNT, STRIDE);
        dataOffset += COLOR_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset, particleShaderProgram.getDirectionVectorAttributeLocation(), VECTOR_COMPONENT_COUNT, STRIDE);
        dataOffset += VECTOR_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(dataOffset, particleShaderProgram.getParticleStartTimeAttributeLocation(), PARTICL_START_TIME_COMPONENT_COUNT, STRIDE);

    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_POINTS,0,currentParticleCount);
    }


}
