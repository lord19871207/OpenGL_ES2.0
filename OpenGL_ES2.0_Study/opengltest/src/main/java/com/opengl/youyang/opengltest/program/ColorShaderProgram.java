package com.opengl.youyang.opengltest.program;

import android.content.Context;
import android.opengl.GLES20;

import com.opengl.youyang.opengltest.R;

import java.nio.FloatBuffer;

/**
 * Created by youyang on 15-4-19.
 */
public class ColorShaderProgram extends ShaderProgram{
    private final int u_ColorLocation;
    private final int u_MatrixLocation;
    private final int aPositionLocation;
    private final int uRLocation;

    int aNormalLocation;
    int u_LightLocation;

    public ColorShaderProgram(Context context){
    super(context, R.raw.simple_vertex_shader_a,R.raw.simple_fragment_shader_a);
        u_MatrixLocation = GLES20.glGetUniformLocation(program,U_MATRIX);
        u_ColorLocation = GLES20.glGetUniformLocation(program,U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program,A_POSITION);
        uRLocation=GLES20.glGetUniformLocation(program,UR);

        aNormalLocation = GLES20.glGetAttribLocation(program,A_NORMAL);
        u_LightLocation=GLES20.glGetUniformLocation(program,U_LIGHTLOCATION);


    }

    public void setUniforms(float[] matrix,FloatBuffer light,float radius){
        GLES20.glUniformMatrix4fv(u_MatrixLocation,1,false,matrix,0);
//        GLES20.glUniform4f(u_ColorLocation,r,g,b,1.0f);
        GLES20.glUniform3fv(u_LightLocation, 1,light);
        GLES20.glUniform1f(uRLocation,radius);
    }



    public int getPositionAttributionLocation(){
        return aPositionLocation;
    }

    public int getNormalLocation(){
        return aNormalLocation;
    }


}
