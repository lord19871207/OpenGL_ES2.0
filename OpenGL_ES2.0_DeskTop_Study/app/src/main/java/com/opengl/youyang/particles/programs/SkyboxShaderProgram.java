package com.opengl.youyang.particles.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.opengl.youyang.particles.R;

/**
 * Created by youyang on 15-5-16.
 */
public class SkyboxShaderProgram extends ShaderProgram{
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;
    private final int aPositionLocation;

    public SkyboxShaderProgram(Context context){
        super(context, R.raw.skybox_vertex_shader,R.raw.skybox_fragment_shader);

        uMatrixLocation= GLES20.glGetUniformLocation(program,U_MATRIX);
        uTextureUnitLocation= GLES20.glGetUniformLocation(program,U_TEXTURE_UNIT);
        aPositionLocation= GLES20.glGetUniformLocation(program,A_POSITION);
    }

    public void setUniforms(float[] matrix ,int textureId){
        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false, matrix,0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, textureId);
        GLES20.glUniform1i(uTextureUnitLocation,0);

    }

    public int getPositionAttributeLocation(){
        return aPositionLocation；
    }
}
