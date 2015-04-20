package com.opengl.youyang.opengl_es20_study.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.opengl.youyang.opengl_es20_study.R;

/**
 * Created by youyang on 15-4-19.
 */
public class ColorShaderProgram extends ShaderProgram{
    private final int u_MatrixLocation;
    private final int aColorLocation;
    private final int aPositionLocation;
    public ColorShaderProgram(Context context){
    super(context,R.raw.simple_vertex_shader,R.raw.simple_fragment_shader);
        u_MatrixLocation= GLES20.glGetUniformLocation(program,U_MATRIX);
        aPositionLocation=GLES20.glGetAttribLocation(program,A_POSITION);
        aColorLocation=GLES20.glGetAttribLocation(program,A_COLOR);
    }

    public void setUniforms(float[] matrix){
        GLES20.glUniformMatrix4fv(u_MatrixLocation,1,false,matrix,0);
    }

    public int getPositionAttributionLocation(){
        return aPositionLocation;
    }

    public int getColorAttributionLocation(){
        return aColorLocation;
    }

}
