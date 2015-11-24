package com.opengl.youyang.opengltest.object;

import java.util.ArrayList;

/**
 * Created by youyang on 15-7-6.
 */
public class Ball {
    float radius;
    private static final int UNIT_SIZE = 1;
    private int vCount = 0;

    public Ball(float r){
        this.radius=r;
    }

    public int getvCount(){
        return vCount;
    }

    public float[] generateBall(int angleSpan){
        ArrayList<Float> aList = new ArrayList<Float>();

        float[] ballVertex;
        for (int vAngle = -90; vAngle < 90; vAngle += angleSpan) {//纬度
            for (int hangle = 0; hangle <= 360; hangle += angleSpan) {//经度
                float x0 = (float) (radius * UNIT_SIZE * Math.cos(Math.toRadians(vAngle)) * Math.cos(Math.toRadians(hangle)));
                float y0 = (float) (radius * UNIT_SIZE * Math.cos(Math.toRadians(vAngle)) * Math.sin(Math.toRadians(hangle)));
                float z0 = (float) (radius * UNIT_SIZE * Math.sin(Math.toRadians(vAngle)));

                float x1 = (float) (radius * UNIT_SIZE * Math.cos(Math.toRadians(vAngle)) * Math.cos(Math.toRadians(hangle + angleSpan)));
                float y1 = (float) (radius * UNIT_SIZE * Math.cos(Math.toRadians(vAngle)) * Math.sin(Math.toRadians(hangle + angleSpan)));
                float z1 = (float) (radius * UNIT_SIZE * Math.sin(Math.toRadians(vAngle)));

                float x2 = (float) (radius * UNIT_SIZE * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.cos(Math.toRadians(hangle + angleSpan)));
                float y2 = (float) (radius * UNIT_SIZE * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.sin(Math.toRadians(hangle + angleSpan)));
                float z2 = (float) (radius * UNIT_SIZE * Math.sin(Math.toRadians(vAngle + angleSpan)));

                float x3 = (float) (radius * UNIT_SIZE * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.cos(Math.toRadians(hangle)));
                float y3 = (float) (radius * UNIT_SIZE * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.sin(Math.toRadians(hangle)));
                float z3 = (float) (radius * UNIT_SIZE * Math.sin(Math.toRadians(vAngle + angleSpan)));

                //1 3 0    1 2 3
                aList.add(x1);
                aList.add(y1);
                aList.add(z1);

                aList.add(x3);
                aList.add(y3);
                aList.add(z3);

                aList.add(x0);
                aList.add(y0);
                aList.add(z0);

                aList.add(x1);
                aList.add(y1);
                aList.add(z1);

                aList.add(x2);
                aList.add(y2);
                aList.add(z2);

                aList.add(x3);
                aList.add(y3);
                aList.add(z3);

            }
        }
        vCount = aList.size() / 3;
        ballVertex = new float[aList.size()];
        for (int i = 0; i < aList.size(); i++) {
            ballVertex[i] = aList.get(i);
        }
        return ballVertex;
    }
}
