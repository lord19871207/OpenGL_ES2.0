package com.opengl.youyang.particles.object;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.opengl.youyang.particles.data.IndexBuffer;
import com.opengl.youyang.particles.data.VertexArray;
import com.opengl.youyang.particles.data.VertexBuffer;

/**
 * Created by youyang on 15-5-26.
 */
public class HeightMap {
    private static final int POSITION_COMPONENT_COUNT =3;
    private final int width;
    private final int height;
    private final int numElements;
    private final VertexBuffer vertexBuffer;
    private final IndexBuffer indexBuffer;
    public HeightMap(Bitmap bitmap){
        width=bitmap.getWidth();
        height=bitmap.getHeight();

        if(width*height>65536){
            throw new RuntimeException("高度图太大了");
        }
        numElements=calculateNumElements();
        vertexBuffer=new VertexBuffer(loadBitmapData(bitmap));
        indexBuffer=new IndexBuffer(createIndexData());

    }

    private int calculateNumElements(){
        //每组两个三角形，每个三角形有3个索引
        return (width-1)*(height-1)*2*3;
    }

    private float[] loadBitmapData(Bitmap bitmap){
        final int[] pixels=new int[width*height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        bitmap.recycle();

        final float[] heightmapVertices=new float[width*height*POSITION_COMPONENT_COUNT];
        int offset=0;

        for (int row=0;row<height;row++){
            for (int col=0;col<width;col++){
                final float xPosition=((float)col/(float)(width-1))-0.5f;
                final float yPosition=(float) Color.red(pixels[(row*height)+col])/(float)255;
                final float zPosition=((float)row/(float)(height-1))-0.5f;

                heightmapVertices[offset++]=xPosition;
                heightmapVertices[offset++]=yPosition;
                heightmapVertices[offset++]=zPosition;
            }
        }
        return heightmapVertices;
    }

    private short[] createIndexData(){
        final short[] indexData=new short[numElements];
        int offset=0;
        for (int row=0;row<height-1;row++){
            for (int col=0;col<width-1;col++){
                short topleftIndexnum=(short)(row*width+col);
                short toprightIndexnum=(short)(row*width+col+1);
                short bottomleftIndexnum=(short)((row+1)*width+col);
                short bottomrightIndexnum=(short)((row+1)*width+col+1);

                indexData[offset++]=topleftIndexnum;
                indexData[offset++]=bottomleftIndexnum;
                indexData[offset++]=toprightIndexnum;

                indexData[offset++]=toprightIndexnum;
                indexData[offset++]=bottomleftIndexnum;
                indexData[offset++]=bottomrightIndexnum;
            }
        }
        return  indexData;
    }
}
