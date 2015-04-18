package com.opengl.youyang.opengl_es20_study.data;

import android.opengl.GLES20;

import com.opengl.youyang.opengl_es20_study.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by youyang on 15-4-18.
 */
public class VertexArray {
    private final FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData){
        floatBuffer= ByteBuffer.allocateDirect(vertexData.length * Constants.BYTES_PER_FLOAT).order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(vertexData);
    }

    public void setVertexAttribPointer(int dataoffset,int attributeLocation,int componentCount,int stride){
        floatBuffer.position(dataoffset);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        floatBuffer.position(0);
    }
}
