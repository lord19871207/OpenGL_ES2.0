package com.opengl.youyang.opengl_es20_study.object;

import android.opengl.GLES20;

import com.opengl.youyang.opengl_es20_study.Constants;
import com.opengl.youyang.opengl_es20_study.data.VertexArray;
import com.opengl.youyang.opengl_es20_study.programs.ColorShaderProgram;
import com.opengl.youyang.opengl_es20_study.programs.TextureShaderProgram;

/**
 * Created by youyang on 15-4-18.
 */
public class Circle {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;
    private VertexArray vertexArray;
    private  float[] VERTEX_DATA;
    int seg;
    private float r=0.5f;

    public Circle(int seg) {
        this.seg=seg;
        VERTEX_DATA=new float[seg*2];
        int j=0;
        for (float i=0; i<360.0f; i+=(360.0f/seg)){
            VERTEX_DATA[j++]= (float)Math.cos(angleTOradian(i))*r;
            VERTEX_DATA[j++]= (float)Math.sin(angleTOradian(i))*r;
        }
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributionLocation(), POSITION_COMPONENT_COUNT, 0);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,seg);
    }

    private double angleTOradian(float radian){
        return radian*Math.PI/180.0f;
    }
}
