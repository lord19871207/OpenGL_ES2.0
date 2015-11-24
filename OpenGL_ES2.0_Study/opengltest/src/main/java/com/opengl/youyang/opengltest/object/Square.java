package com.opengl.youyang.opengltest.object;

import com.opengl.youyang.opengltest.data.VertexArray;

import java.util.ArrayList;

/**
 * Created by youyang on 15/11/24.
 */
public class Square {
    public float[] getSquare(float height,float width){
        float[] squareVertex = new float[]{
                -width/2,height/2,
                -width/2,-height/2,
                width/2,-height/2,
                width/2,-height/2,
                width/2,height/2,
                -width/2,height/2,
        };

        return squareVertex;
    }

    public float[] getSquareTexture() {
        return new float[]{
                0,0,
                0,1.0f,
                1.0f,1.0f,
                1.0f,1.0f,
                1.0f,0,
                0,0
        };
    }
}
