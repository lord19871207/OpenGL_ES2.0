package com.opengl.youyang.opengltest.program;

import android.content.Context;
import android.opengl.GLES20;

import com.opengl.youyang.opengltest.R;
import com.opengl.youyang.opengltest.utils.MatrixHelper;

import java.nio.FloatBuffer;

/**
 * Created by youyang on 15-4-19.
 */
public class ColorShaderProgram extends ShaderProgram{
    private final int u_ColorLocation;
    private final int u_MVPMatrixLocation;
    private final int u_MMatrixLocation;
    private final int aPositionLocation;
    private final int uRLocation;
    private final int uCamera;

    int aNormalLocation;
    int u_LightLocation;

    public ColorShaderProgram(Context context){
    super(context, R.raw.simple_vertex_shader_a,R.raw.simple_fragment_shader_a);
        u_MVPMatrixLocation = GLES20.glGetUniformLocation(program,U_MVPMATRIX);
        u_ColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        uRLocation=GLES20.glGetUniformLocation(program, UR);
        u_MMatrixLocation=GLES20.glGetUniformLocation(program, U_MMATRIX);

        aNormalLocation = GLES20.glGetAttribLocation(program, A_NORMAL);
        u_LightLocation=GLES20.glGetUniformLocation(program,U_LIGHTLOCATION);
        uCamera=GLES20.glGetUniformLocation(program,U_CAMERA);

    }

    public void setUniforms(float[] matrix,float[] mMatrix,FloatBuffer light,float radius){
        GLES20.glUniformMatrix4fv(u_MVPMatrixLocation,1,false,matrix,0);
//        GLES20.glUniform4f(u_ColorLocation,r,g,b,1.0f);

        GLES20.glUniformMatrix4fv(u_MMatrixLocation,1,false,mMatrix,0);
        GLES20.glUniform3fv(u_LightLocation, 1,light);
        GLES20.glUniform1f(uRLocation,radius);
    }

    public void setUniforms_Location(){
        GLES20.glUniform3fv(u_LightLocation, 1, MatrixHelper.getLightPositionFB());
    }

    public void setUniforms_Camera(){
        GLES20.glUniform3fv(uCamera, 1, MatrixHelper.getCameraFB());
    }


    public int getPositionAttributionLocation(){
        return aPositionLocation;
    }

    public int getNormalLocation(){
        return aNormalLocation;
    }


}
