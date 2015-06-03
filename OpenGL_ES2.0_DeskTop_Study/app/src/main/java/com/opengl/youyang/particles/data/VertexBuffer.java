package com.opengl.youyang.particles.data;

import android.opengl.GLES20;

import com.opengl.youyang.particles.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by youyang on 15-5-25.
 * 创建顶点缓冲区对象
 */
public class VertexBuffer {
    private final int bufferId;

    public VertexBuffer(float[] vertextData){
        final int buffers[] =new int[1];
        GLES20.glGenBuffers(buffers.length,buffers,0);
        if(buffers[0]==0){
            throw new RuntimeException("无法创建一个顶点缓冲区对象");
        }
        bufferId =buffers[0];

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,buffers[0]);

        //将java层的数据转化到本地内存
        FloatBuffer vertexArray= ByteBuffer.allocateDirect(vertextData.length * Constants.BYTES_PER_FLOAT).order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(vertextData);

        vertexArray.position(0);
        //将本地内存转化到GPU
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexArray.capacity() * Constants.BYTES_PER_FLOAT, vertexArray, GLES20.GL_STATIC_DRAW);
        //解除绑定
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);
    }

    public void setVertexAttribPointer(int dataOffset,int attributeLocation ,int componentCount,int stride){
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,bufferId);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, dataOffset);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);
    }
}
