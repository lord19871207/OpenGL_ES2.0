package com.opengl.youyang.opengl_es20_study.object;

import android.opengl.GLES20;

import com.opengl.youyang.opengl_es20_study.Constants;
import com.opengl.youyang.opengl_es20_study.data.VertexArray;
import com.opengl.youyang.opengl_es20_study.programs.ColorShaderProgram;
import com.opengl.youyang.opengl_es20_study.utils.Geometry;

import java.util.List;

/**
 * Created by youyang on 15-4-19.
 */
public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private final float radius;
    public final float height;

    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand>drawList;

    public Mallet(float radius,float height,int numPintsAroundMallet) {
        ObjectBuilder.GeneratedData generatedData=ObjectBuilder.createMallet(new Geometry.Point(0f,0f,0f),radius,height,numPintsAroundMallet);
        this.radius=radius;
        this.height=height;
        vertexArray = new VertexArray(generatedData.vertexData);
        drawList=generatedData.drawList;

    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributionLocation(), POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
