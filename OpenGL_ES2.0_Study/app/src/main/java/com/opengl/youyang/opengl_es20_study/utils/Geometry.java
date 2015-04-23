package com.opengl.youyang.opengl_es20_study.utils;

import android.util.FloatMath;

/**
 * Created by youyang on 15-4-22.
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
            return new Point(x+vector.x,y+vector.y,z+vector.z);
        }
        public Point translateY(float distance){
            return new Point(x,y+distance,z);
        }
    }


    //圆
    public static class Circle{
        public final Point center;
        public final float radius;

        public Circle(Point center,float radius){
            this.center=center;
            this.radius=radius;
        }

        public Circle scale(float scale){
            return new Circle(center,radius*scale);
        }
    }

    //圆柱
    public static class Cylinder{
        public final Point center;
        public final float radius;
        public final float height;

        public Cylinder(Point center,float radius,float height){
            this.center=center;
            this.radius=radius;
            this.height=height;
        }
    }

    public static class Ray{
        public final Point point;
        public final Vector vector;

        public Ray(Point point,Vector vector){
            this.point=point;
            this.vector=vector;
        }

    }

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

        public Vector crossProduct(Vector other){
            return new Vector((y*other.z)-(z*other.y),
                    (z*other.x)-(x*other.z),
                    (x*other.y)-(y*other.x)
                    );
        }

        public float dotProduct(Vector other){
            return x*other.x+y*other.y+z*other.z;
        }

        public Vector scale(float f){
            return new Vector(x*f,y*f,z*f);
        }
    }

    public static class Plane{
        public final Point point;
        public final Vector vector;

        public Plane(Point point,Vector vector){
            this.point=point;
            this.vector=vector;
        }
    }

    public static class Sphere{
        public final Point center;
        public final float radius;

        public Sphere(Point center,float radius){
            this.radius=radius;
            this.center=center;

        }
    }

    public static Vector vectorBetween(Point from,Point to){
        return new Vector(to.x-from.x,
                to.y-from.y,
                to.z-from.z
        );
    }

    public static boolean intersects(Sphere s,Ray r){
        return distanceBetween(s.center,r)<s.radius;
    }

    public static float distanceBetween(Point point ,Ray ray){
        Vector p1ToPoint=vectorBetween(ray.point,point);
        Vector p2ToPoint=vectorBetween(ray.point.translate(ray.vector),point);

        float areaOfTriangleTimesTwo=p1ToPoint.crossProduct(p2ToPoint).length();
        float lengthOfBase=ray.vector.length();

        float distanceFromPointToRay=areaOfTriangleTimesTwo/lengthOfBase;
        return distanceFromPointToRay;

    }

    public static Point intersectionPoint(Ray ray ,Plane plane){
        Vector rayToPlaneVectory=vectorBetween(ray.point,plane.point);
        float scaleFactor=rayToPlaneVectory.dotProduct(plane.vector)/ray.vector.dotProduct(plane.vector);
        Point intersectionPoint=ray.point.translate(ray.vector.scale(scaleFactor));
        return intersectionPoint;
    }


}
