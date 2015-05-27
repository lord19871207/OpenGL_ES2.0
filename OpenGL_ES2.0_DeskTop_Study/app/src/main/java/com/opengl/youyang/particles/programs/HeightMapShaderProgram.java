package com.opengl.youyang.particles.programs;

import android.content.Context;
import android.opengl.GLES20;
import com.opengl.youyang.particles.R;

/**
 * Created by youyang on 15-5-26.
 */
public class HeightMapShaderProgram extends ShaderProgram{
    private final int uMatrixLocation;
    private final int aPositionLocation;

    public HeightMapShaderProgram(Context context){
        super(context, R.raw.heightmap_vertex_shader,R.raw.heightmap_fragment_shader);
        uMatrixLocation= GLES20.glGetUniformLocation(program,U_MATRIX);
        aPositionLocation= GLES20.glGetAttribLocation(program,A_POSITION);
    }

    public void setUniforms(float[] matrix ,int textureId){
        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false, matrix,0);

    }

    public int getPositionAttributeLocation(){
        return aPositionLocation;
    }
}
