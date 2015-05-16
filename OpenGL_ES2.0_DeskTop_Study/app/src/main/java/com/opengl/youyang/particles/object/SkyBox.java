package com.opengl.youyang.particles.object;

import android.opengl.GLES20;

import com.opengl.youyang.particles.data.VertexArray;
import com.opengl.youyang.particles.programs.SkyboxShaderProgram;

import java.nio.ByteBuffer;

/**
 * Created by youyang on 15-5-16.
 */
public class SkyBox {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private final VertexArray vertexArray;
    private final ByteBuffer indexArray;

    public SkyBox() {

        vertexArray = new VertexArray(new float[]{
                -1, 1, 1,
                1, 1, 1,
                -1, -1, 1,
                1, -1, 1
                - 1, 1, -1,
                1, 1, -1,
                -1, -1, -1,
                1, -1, -1

        });

        indexArray = ByteBuffer.allocate(6 * 6).put(new byte[]{
                //前
                1, 3, 0,
                0, 3, 2,
                //后
                4, 6, 5,
                5, 6, 7,
                //左
                0, 2, 4,
                4, 2, 6,
                //右
                5, 7, 1,
                1, 7, 3,

                //上
                5, 1, 4,
                4, 1, 0,

                //下
                6, 2, 7,
                7, 2, 3

        });

        indexArray.position(0);
    }


    public  void binfData(SkyboxShaderProgram skyboxShaderProgram){
        vertexArray.setVertexAttribPointer(0, skyboxShaderProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, 0);

    }

    public void draw(){
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,36,GLES20.GL_UNSIGNED_BYTE,indexArray);
    }
}
