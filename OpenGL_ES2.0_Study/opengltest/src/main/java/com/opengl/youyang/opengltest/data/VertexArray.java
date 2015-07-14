package com.opengl.youyang.opengltest.data;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by youyang on 15-4-18.
 */
public class VertexArray {
    private final FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData){
        floatBuffer= ByteBuffer
                //请求c层内存空间
                .allocateDirect(vertexData.length * Constants.BYTES_PER_FLOAT)
                //按照本地字节序排序
                .order(ByteOrder.nativeOrder())
                //转化为floatbuffer
                .asFloatBuffer()
                //填充数据
                .put(vertexData);
    }

    /**
     * 把程序中的数据传递给GPU ，给着色器中的属性设置值
     * @param dataoffset 起始偏移量
     * @param attributeLocation 属性的位置句柄
     * @param componentCount 这个属性占用了几个位置 比如RGBA 占用了4个   x,y,z占用了3个
     * @param stride 顶点的 步长。 每个顶点包含的所有属性加起来占用的位置
     */
    public void setVertexAttribPointer(int dataoffset,int attributeLocation,int componentCount,int stride){
        floatBuffer.position(dataoffset);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        floatBuffer.position(0);
    }

    public FloatBuffer getFloatBuffer(){
        return  floatBuffer;
    }


}
