package com.opengl.youyang.yumulibrary.shape;


import android.opengl.GLES20;

import com.opengl.youyang.yumulibrary.data.VertexArray;
import com.opengl.youyang.yumulibrary.programs.ColorShaderProgram;
import com.opengl.youyang.yumulibrary.utils.Constants;

/**
 * Created by youyang on 16/4/26.
 */
public class Point implements ShapeObjct {
    private float mPx;
    private float mPy;
    private float mPz;
    private float mPw;

    private static final int POSITION_COMPONENT_COUNT = 2;
    private Circle.ShapeSize mShape;

    private VertexArray vertexArray;
    private float[] VERTEX_DATA;

    public Point(float x,float y, Circle.ShapeSize view){
        this(x,y,0,view);
    }

    public Point(float x,float y,float z, Circle.ShapeSize view ){
        this(x,y,0,1,view);
    }

    public Point(float x,float y,float z ,float w, Circle.ShapeSize view){
        mPx = x;
        mPy = y;
        mPz = z;
        mPw = w;
        mShape = view;
    }

    @Override
    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);
    }

    @Override
    public void generateVertices(boolean isRecrate) {
        if(!isRecrate){
            if(vertexArray !=null){
                return;
            }
        }

        //将视图坐标转化为 归一化坐标
        final float normalizedX = (mPx / (float) 1000) * 2 - 1;
        final float normalizedY = (mPy / (float) 1000) * 2 - 1;
        VERTEX_DATA = new float[2];
        VERTEX_DATA[0] = normalizedX;
        VERTEX_DATA[1] = normalizedY;
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    @Override
    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributionLocation(), POSITION_COMPONENT_COUNT, 2* Constants.BYTES_PER_FLOAT);
    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public int getColor() {
        return 0;
    }
}
