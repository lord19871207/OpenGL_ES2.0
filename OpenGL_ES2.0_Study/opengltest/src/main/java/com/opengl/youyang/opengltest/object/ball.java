package com.opengl.youyang.opengltest.object;

/**
 * Created by youyang on 15-7-6.
 */
public class ball {
    float radius;

    public ball(float r){
        this.radius=r;
    }
    private float[] generateBall(){
        int count=0;
        float[] ballVertex=new float[720];//20层 每一层的圆 36个点
            for (int w=-90;w<=90;w=w+9){
                for(int angle=0;angle<=360;angle=angle+10){
                    double r=Math.cos(w);
                    float y= (float) Math.sin(w);
                    float x= (float) (r*Math.cos(angle));
                    float z= (float) (r*Math.sin(angle));
                    ballVertex[count++]=x;
                    ballVertex[count++]=y;
                    ballVertex[count++]=z;

                }
            }
        return ballVertex;
    }
}
