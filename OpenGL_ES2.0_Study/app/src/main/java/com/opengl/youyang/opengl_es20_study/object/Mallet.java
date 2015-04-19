package com.opengl.youyang.opengl_es20_study.object;

import android.opengl.GLES20;

import com.opengl.youyang.opengl_es20_study.Constants;
import com.opengl.youyang.opengl_es20_study.data.VertexArray;
import com.opengl.youyang.opengl_es20_study.programs.ColorShaderProgram;

/**
 * Created by youyang on 15-4-19.
 */
public class Mallet {
    private static final int POSITION_COMPONENT_COUNT=2;
    private static final int COLOR_COMPONENT_COUNT=3;
    private static final int STRIDE=(POSITION_COMPONENT_COUNT+COLOR_COMPONENT_COUNT)* Constants.BYTES_PER_FLOAT;
    private static final float[] VERTEX_DATA={
            0f,-0.4F,0f,0f,1f,
            0f,0.4f,1.0f,0f,0f
    };
    private final VertexArray vertexArray;

    public Mallet(){
        vertexArray=new VertexArray(VERTEX_DATA);

    }

    public void bindData(ColorShaderProgram colorShaderProgram){
        vertexArray.setVertexAttribPointer(0,colorShaderProgram.getPositionAttributionLocation(),POSITION_COMPONENT_COUNT,STRIDE);
        vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT,colorShaderProgram.getColorAttributionLocation(),COLOR_COMPONENT_COUNT,STRIDE);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_POINTS,0,2);
    }
}
