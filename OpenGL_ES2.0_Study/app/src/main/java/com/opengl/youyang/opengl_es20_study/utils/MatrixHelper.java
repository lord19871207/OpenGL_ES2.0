package com.opengl.youyang.opengl_es20_study.utils;

/**
 * Created by youyang on 15-4-14.
 */
public class MatrixHelper {
    public static void perspectiveM(float[] m,float yFovInDegrees,float aspect,float n,float f){
        //计算焦距
        final float angleInradians=(float)(yFovInDegrees*Math.PI/180.0);
        final float a=(float)(1.0/Math.tan(angleInradians/2.0));


    }
}
