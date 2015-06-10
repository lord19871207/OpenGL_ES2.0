package com.opengl.youyang.opengl_es20_study.utils;

/**
 * Created by youyang on 15-4-14.
 */
public class MatrixHelper {

    /**
     * 创建投影矩阵
     * @param m 存放矩阵数据的数组
     * @param yFovInDegrees 视野角度
     * @param aspect  屏幕宽高比
     * @param n   到近平面的距离  必须为正值  n=1就相当于 Z=-1
     * @param f   到远平面的距离  必须为正值 且要大于近平面的距离
     */
    public static void perspectiveM(float[] m,float yFovInDegrees,float aspect,float n,float f){
        //    将视野角度转弧度
        final float angleInradians=(float)(yFovInDegrees*Math.PI/180.0);
        //计算焦距
        final float a=(float)(1.0/Math.tan(angleInradians/2.0));

        //存储矩阵数据  以列来存储
        m[0]=a/aspect;
        m[1]=0f;
        m[2]=0f;
        m[3]=0f;

        m[4]=0f;
        m[5]=a;
        m[6]=0f;
        m[7]=0f;

        m[8]=0f;
        m[9]=0f;
        m[10]=-((f+n)/(f-n));
        m[11]=-1f;
        m[12]=0f;
        m[13]=0f;
        m[14]=-((2f*f*n)/(f-n));
        m[15]=0f;

    }
}
