package com.opengl.youyang.opengltest.utils;

import android.opengl.Matrix;

import com.opengl.youyang.opengltest.data.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by youyang on 15-4-14.
 */
public class MatrixHelper {

    private static float[] mProjMatrix=new float[16];  //投影矩阵
    private static float[] mVMatrix=new float[16];     //视图矩阵
    private static float[] mMVPMatrix =new float[16];                //最终总变换矩阵
    private static float[] currMatrix; //当前矩阵

    static float[] cameraLocation=new float[3];
    public static FloatBuffer cameraFB;

    static float[][] mStack=new float[10][16];//用于保存变换矩阵的栈
    static int stackTop=-1;//栈顶索引
    public static float[] lightLocation=new float[]{0,0,0};//光源位置数组
    public static FloatBuffer lightPositionFB;
    static ByteBuffer llbbL=ByteBuffer.allocate(3* Constants.BYTES_PER_FLOAT);
    static ByteBuffer light=ByteBuffer.allocate(3* Constants.BYTES_PER_FLOAT);

    public static void setLightLocation(float x,float y,float z){
        light.clear();
        lightLocation[0]=x;lightLocation[1]=y;
        lightLocation[2]=z;
        light.order(ByteOrder.nativeOrder());
        lightPositionFB=light.asFloatBuffer();
        lightPositionFB.put(lightLocation);
        lightPositionFB.position(0);
    }

    public static FloatBuffer getLightPositionFB(){
        light.clear();
        lightLocation[0]=1.5f;lightLocation[1]=1.5f;
        lightLocation[2]=2.5f;
        light.order(ByteOrder.nativeOrder());
        lightPositionFB=light.asFloatBuffer();
        lightPositionFB.put(lightLocation);
        lightPositionFB.position(0);
        return lightPositionFB;
    }


    //产生没有任何变化的矩阵
    public static void initStack(){
        currMatrix=new float[16];
        Matrix.setIdentityM(currMatrix, 0);
        Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }

    //将当前的变换矩阵存入栈中
    public static void pushMatrix(){
        stackTop++;
        for (int i=0;i<16;i++){
            mStack[stackTop][i]=currMatrix[i];
        }
    }

    //取出栈顶矩阵
    public static void popMatrix(){
        for (int i=0;i<16;i++){
            currMatrix[i]=mStack[stackTop][i];
        }
        stackTop--;
    }

    //沿着x，y，z方向平移
    public static void translate(float x,float y,float z){
        Matrix.translateM(currMatrix,0,x,y,z);
    }

    //沿着x，y，z方向旋转
    public static void rotate(int angle,float x,float y,float z){
        Matrix.rotateM(currMatrix, 0, angle, x, y, z);
    }

    public static float[] getMMatrix(){
        return  currMatrix;
    }

    /**
     * 创建投影矩阵
     * @param yFovInDegrees 视野角度
     * @param aspect  屏幕宽高比
     * @param n   到近平面的距离  必须为正值  n=1就相当于 Z=-1
     * @param f   到远平面的距离  必须为正值 且要大于近平面的距离
     */
    public static void perspectiveM(float yFovInDegrees,float aspect,float n,float f){
        //    将视野角度转弧度
        final float angleInradians=(float)(yFovInDegrees*Math.PI/180.0);
        //计算焦距
        final float a=(float)(1.0/Math.tan(angleInradians/2.0));

        //存储矩阵数据  以列来存储
        mProjMatrix[0]=a/aspect;
        mProjMatrix[1]=0f;
        mProjMatrix[2]=0f;
        mProjMatrix[3]=0f;

        mProjMatrix[4]=0f;
        mProjMatrix[5]=a;
        mProjMatrix[6]=0f;
        mProjMatrix[7]=0f;

        mProjMatrix[8]=0f;
        mProjMatrix[9]=0f;
        mProjMatrix[10]=-((f+n)/(f-n));
        mProjMatrix[11]=-1f;
        mProjMatrix[12]=0f;
        mProjMatrix[13]=0f;
        mProjMatrix[14]=-((2f*f*n)/(f-n));
        mProjMatrix[15]=0f;

    }


    /**
     * 设置相机
     * @param cx
     * @param cy
     * @param cz
     * @param tx
     * @param ty
     * @param tz
     * @param ux
     * @param uy
     * @param uz
     */
    public static void setCamera(
            float cx,float cy,float cz,//摄像机的x,y,z坐标
            float tx,float ty,float tz,//观察者到目标点的坐标
            float ux,float uy,float uz//up向量在x,y,z轴的分量
    ){
        Matrix.setLookAtM(mVMatrix,0,cx,cy,cz,tx,ty,tz,ux,uy,uz);

        cameraLocation[0]=cx;cameraLocation[1]=cy;cameraLocation[2]=cz;
        llbbL.clear();
        llbbL.order(ByteOrder.nativeOrder());
        cameraFB=llbbL.asFloatBuffer();
        cameraFB.put(cameraLocation);
        cameraFB.position(0);

    }

    //设置正交矩阵
    public static void setProjectOrtho(float left,float right,
                                       float bottom,float top,
                                       float near,float far){
        Matrix.orthoM(mProjMatrix,0,left,right,bottom,top
        ,near,far);
    }

    //生成总变换矩阵
//    public static float[] getFinalMatrix(float[] spec){
//        mMVPMatrix=new float[16];
//        Matrix.multiplyMM(mMVPMatrix,0,mVMatrix,0,spec,0);
//        Matrix.multiplyMM(mMVPMatrix,0,mProjMatrix,0,mMVPMatrix,0);
//        return  mMVPMatrix;
//    }

    //生成总变换矩阵
    public static float[] getFinalMatrix(){
        Matrix.multiplyMM(mMVPMatrix,0,mVMatrix,0,currMatrix,0);
        Matrix.multiplyMM(mMVPMatrix,0,mProjMatrix,0,mMVPMatrix,0);
        return  mMVPMatrix;
    }


    public static FloatBuffer getCameraFB() {
        return cameraFB;
    }
}
