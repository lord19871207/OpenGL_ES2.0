package com.opengl.youyang.particles.object;

import android.widget.Toast;

import com.opengl.youyang.particles.Constants;
import com.opengl.youyang.particles.data.VertexArray;

/**
 * Created by youyang on 15-5-12.
 */
public class ParticleSystem {
    private static final int POSITION_COMPONENT_COUNT=3;
    private static final int COLOR_COMPONENT_COUNT=3;
    private static final int VECTOR_COMPONENT_COUNT=3;
    private static final int PARTICL_START_TIME_COMPONENT_COUNT=3;

    private static final int TOTAL_COMPONENT_COUNT=POSITION_COMPONENT_COUNT+COLOR_COMPONENT_COUNT+VECTOR_COMPONENT_COUNT+PARTICL_START_TIME_COMPONENT_COUNT;
    private static final int STRIDE=TOTAL_COMPONENT_COUNT* Constants.BYTES_PER_FLOAT;

    private final float[] particles;
    private final VertexArray vertexArray;
    private final int maxParticlCount;

    private int currentParticleCount;
    private int nextParticle;

    public ParticleSystem(int maxParticlCount){
        this.maxParticlCount=maxParticlCount;
        particles=new float[maxParticlCount* TOTAL_COMPONENT_COUNT];
        vertexArray=new VertexArray(particles);
    }

}
