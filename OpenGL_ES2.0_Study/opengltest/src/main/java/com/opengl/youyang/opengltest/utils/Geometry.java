package com.opengl.youyang.opengltest.utils;

import android.util.FloatMath;

/**
 * Created by youyang on 15-6-27.
 */
public class Geometry {
    //点
    public static class Point{
        public final float x,y,z;
        public Point(float x,float y,float z){
            this.x=x;
            this.y=y;
            this.z=z;
        }

        public Point translate(){

        }
    }

    //向量
    public static class Vector{
        public final float x,y,z;
        public Vector(float x,float y,float z){
            this.x=x;
            this.y=y;
            this.z=z;
        }

        public float length(){
            return FloatMath.sqrt(x*x+y*y+z*z);
        }

        public Vector clossProduct(Vector other){
            return
        }

    }

    //射线

    //面

    //圆

    //圆柱

    //圆球


}
