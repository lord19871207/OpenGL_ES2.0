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

        public Point translate(Vector vector){
            return
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

        //叉乘  获取对应向量的法向量
        public Vector clossProduct(Vector other){
            return new Vector((y*other.z)-(z*other.y),(z*other.x-x*other.z),(x*other.y)-(y*other.x));
        }

        //点乘 获取角度
        public float dotProduct(Vector vector){
            return vector.x*x+vector.y*y+vector.z*z;
        }

        //缩放
        public Vector scale(float scale){
            return new Vector(x*scale,y*scale,z*scale);
        }

    }

    //射线

    //面

    //圆

    //圆柱

    //圆球


}
