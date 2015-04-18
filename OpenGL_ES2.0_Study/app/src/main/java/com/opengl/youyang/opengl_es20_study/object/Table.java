package com.opengl.youyang.opengl_es20_study.object;

import com.opengl.youyang.opengl_es20_study.Constants;

/**
 * Created by youyang on 15-4-18.
 */
public class Table {
    private static final int POSITION_COMPONENT_COUNT=2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT=2;
    private static final int STRIDE=(POSITION_COMPONENT_COUNT+TEXTURE_COORDINATES_COMPONENT_COUNT)* Constants.BYTES_PER_FLOAT;

    private static final float[] VERTEX_DATA={

            //order of coordinates :X,Y,S,T
            0f,0f,0.5f,0.5f,
            -0.5f,-0.8f,0f,0.9f,
            0.5f,-0.8f,1f,0.9f,
            0.5f,0.8f,1f,0.1f,
            -0.5f,0.8f,0f,0.1f,
            -0.5f,-0.8f,0f,0.9f
    };


}
