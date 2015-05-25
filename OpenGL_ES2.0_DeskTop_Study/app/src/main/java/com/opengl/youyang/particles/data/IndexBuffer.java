package com.opengl.youyang.particles.data;

import android.opengl.GLES20;

import com.opengl.youyang.particles.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by youyang on 15-5-25.
 * 创建顶点缓冲区对象
 */
public class IndexBuffer {
    private final int bufferId;

    public IndexBuffer(short[] indextData){
        final int buffers[] =new int[1];
        GLES20.glGenBuffers(buffers.length,buffers,0);
        if(buffers[0]==0){
            throw new RuntimeException("无法创建一个顶点缓冲区对象");
        }
        bufferId =buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,buffers[0]);

        ShortBuffer vertexArray= ByteBuffer.allocateDirect(indextData.length * Constants.BYTES_PER_SHORT).order(ByteOrder.nativeOrder())
                .asShortBuffer().put(indextData);

        vertexArray.position(0);

        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, vertexArray.capacity() * Constants.BYTES_PER_FLOAT, vertexArray, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,0);
    }

    public void setVertexAttribPointer(int dataOffset,int attributeLocation ,int componentCount,int stride){
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,bufferId);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, dataOffset);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER,0);
    }

    public int getBufferId(){
        return bufferId;
    }
}
