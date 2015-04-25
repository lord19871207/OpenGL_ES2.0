package com.opengl.youyang.opengl_es20_study.object;

import android.opengl.GLES20;
import android.util.FloatMath;

import com.opengl.youyang.opengl_es20_study.utils.Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youyang on 15-4-22.
 */
public class ObjectBuilder {
    private static final int FLOAT_PER_VERTEX=3;
    private final float[] vertexData;
    private final List<DrawCommand> drawList=new ArrayList<DrawCommand>();
    private int offset=0;

    static interface DrawCommand{
        void draw();
    }

    static class GeneratedData{
        final float[] vertexData;
        final List<DrawCommand>drawList;

        GeneratedData(float[] vertexData,List<DrawCommand> drawList){
            this.vertexData=vertexData;
            this.drawList=drawList;
        }
    }

    private GeneratedData build(){
        return new GeneratedData(vertexData,drawList);
    }

    private ObjectBuilder(int sizeInVertices){
        vertexData=new float[sizeInVertices*FLOAT_PER_VERTEX];

    }

    /**
     * 返回圆柱体顶部顶点数量
     * @param numPoints
     * @return
     */
    private static int sizeOfCircleInvertices(int numPoints){
        return 1+(numPoints+1);
    }

    private static int sizeOfOpenCylinderInvertices(int numPoints){
        return (numPoints+1)*2;
    }

    private void appendCircle(Geometry.Circle circle, final int numPoints){
        final int startVertex=offset/FLOAT_PER_VERTEX;
        final int numVertices=sizeOfCircleInvertices(numPoints);

        vertexData[offset++]=circle.center.x;
        vertexData[offset++]=circle.center.y;
        vertexData[offset++]=circle.center.z;

        for (int i=0;i<=numPoints;i++){
            float angleInRadians=((float)i/(float)numPoints)*((float)Math.PI*2f);

            vertexData[offset++]=circle.center.x+circle.radius* FloatMath.cos(angleInRadians);
            vertexData[offset++]=circle.center.y;
            vertexData[offset++]=circle.center.z+circle.radius*FloatMath.sin(angleInRadians);

        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,startVertex,numVertices);

            }
        });
    }

    private void appendCylinder(Geometry.Cylinder cylinder, final int numPoints){
        final int startVertex =offset/FLOAT_PER_VERTEX;
        final int numVertices=sizeOfOpenCylinderInvertices(numPoints);
        final float yStart=cylinder.center.y-(cylinder.height/2f);
        final float yEnd=cylinder.center.y+(cylinder.height/2f);

        for (int i=0;i<=numPoints;i++){
            float angleInRadians=((float)i/(float)numPoints)*((float)Math.PI*2f);
            float xPosition=cylinder.center.x+cylinder.radius*FloatMath.cos(angleInRadians);
            float zPosition=cylinder.center.z+cylinder.radius*FloatMath.sin(angleInRadians);

            vertexData[offset++]=xPosition;
            vertexData[offset++]=yStart;
            vertexData[offset++]=zPosition;
            vertexData[offset++]=xPosition;
            vertexData[offset++]=yEnd;
            vertexData[offset++]=zPosition;

        }
            drawList.add(new DrawCommand() {
                @Override
                public void draw() {
                    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,startVertex,numPoints);
                }
            });


    }



    static GeneratedData createPuck(Geometry.Cylinder puck,int numPoints){
        int size=sizeOfCircleInvertices(numPoints)+sizeOfOpenCylinderInvertices(numPoints);
        ObjectBuilder builder=new ObjectBuilder(size);

        Geometry.Circle puckTop=new Geometry.Circle(puck.center.translateY(puck.height/2f),puck.radius);
        builder.appendCircle(puckTop, numPoints);
        builder.appendCylinder(puck, numPoints);
        return builder.build();
    }


    static GeneratedData createMallet(Geometry.Point center, float radius,float height,int numPoints){
        int size=sizeOfCircleInvertices(numPoints)*2+sizeOfOpenCylinderInvertices(numPoints)*2;
        ObjectBuilder builder=new ObjectBuilder(size);
        float baseHeight=height*0.25f;
        Geometry.Circle baseCircle=new Geometry.Circle(center.translateY(-baseHeight),radius);
        Geometry.Cylinder baseCylinder=new Geometry.Cylinder(baseCircle.center.translateY(-baseHeight/2f),radius,baseHeight);

        builder.appendCircle(baseCircle, numPoints);
        builder.appendCylinder(baseCylinder,numPoints);

        float handleHeight=height*0.75f;
        float handleRadius=radius/3f;
        Geometry.Circle handleCircle=new Geometry.Circle(center.translateY(height*0.5f),handleRadius);
        Geometry.Cylinder handleCylinder=new Geometry.Cylinder(handleCircle.center.translateY(-handleHeight/2f),handleRadius,handleHeight);
        builder.appendCircle(handleCircle,numPoints);
        builder.appendCylinder(handleCylinder,numPoints);

        return builder.build();

    }


}
