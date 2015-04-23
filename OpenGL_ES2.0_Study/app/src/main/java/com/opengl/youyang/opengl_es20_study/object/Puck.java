package com.opengl.youyang.opengl_es20_study.object;

import com.opengl.youyang.opengl_es20_study.data.VertexArray;
import com.opengl.youyang.opengl_es20_study.programs.ColorShaderProgram;
import com.opengl.youyang.opengl_es20_study.utils.Geometry;

import java.util.List;

/**
 * Created by youyang on 15-4-22.
 */
public class Puck {
    public static final int POSITION_COMPONENT_COUNT = 3;
    public final float radius, height;
    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsAroundPuck) {
        ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createPuck(
                new Geometry.Cylinder(new Geometry.Point(0f, 0f, 0f), radius, height), numPointsAroundPuck);

        this.radius = radius;
        this.height = height;
        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }
    public void binfData(ColorShaderProgram colorShaderProgram){
        vertexArray.setVertexAttribPointer(0,colorShaderProgram.getPositionAttributionLocation(),POSITION_COMPONENT_COUNT,0);
    }
    public void draw(){
        for(ObjectBuilder.DrawCommand drawCommand : drawList){
            drawCommand.draw();
        }
    }

}
