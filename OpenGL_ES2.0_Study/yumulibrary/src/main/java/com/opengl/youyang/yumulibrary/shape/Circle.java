package com.opengl.youyang.yumulibrary.shape;

import android.graphics.Color;
import android.opengl.GLES20;
import android.view.View;

import com.opengl.youyang.yumulibrary.data.VertexArray;
import com.opengl.youyang.yumulibrary.programs.ColorShaderProgram;
import com.opengl.youyang.yumulibrary.utils.Constants;

/**
 * Created by youyang on 15-4-18.
 */
public class Circle implements ShapeObjct {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;
    private VertexArray vertexArray;
    private float[] VERTEX_DATA;
    int seg = 20;
    private float mCx;
    private float mCy;
    private float mRadius;
    private ShapeSize mShape;

    private int mColor;

    public Circle(float cx, float cy, float radius, ShapeSize view) {
        mCx = cx;
        mCy = cy;
        mRadius = radius;
        mShape =view;
    }

    public float getCx() {
        return mCx;
    }

    public void setCx(float cx) {
        mCx = cx;
    }

    public float getCy() {
        return mCy;
    }

    public void setCy(float cy) {
        mCy = cy;
    }

    public float getRadius() {
        return mRadius;
    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributionLocation(), POSITION_COMPONENT_COUNT, 2* Constants.BYTES_PER_FLOAT);
    }

    @Override
    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public int getColor() {
        return mColor;
    }

    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, seg);
    }

    @Override
    public void generateVertices(boolean isRecrate) {

        if(!isRecrate){
            if(vertexArray !=null){
                return;
            }
        }

        //将视图坐标转化为 归一化坐标
        final float normalizedX = (mCx / (float) 1000.0) * 2 - 1;
        final float normalizedY = (mCy / (float) 1000.0) * 2 - 1;
        final float normalizedR = (mRadius / (float) 1000.0);

        VERTEX_DATA = new float[seg * 2];
        int j = 0;
        for (float i = 0; i < 360.0f; i += (360.0f / seg)) {
            VERTEX_DATA[j++] = (float) Math.cos(angleTOradian(i)) * normalizedR + normalizedX;
            VERTEX_DATA[j++] = (float) Math.sin(angleTOradian(i)) * normalizedR + normalizedY;
        }
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    private double angleTOradian(float radian) {
        return radian * Math.PI / 180.0f;
    }

    public interface ShapeSize {
        int getWidth();
        int getHeight();
    }
}
