package com.opengl.youyang.opengl_es20_study;

import android.annotation.TargetApi;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Build;

import com.opengl.youyang.opengl_es20_study.object.Mallet;
import com.opengl.youyang.opengl_es20_study.object.Puck;
import com.opengl.youyang.opengl_es20_study.object.Table;
import com.opengl.youyang.opengl_es20_study.programs.ColorShaderProgram;
import com.opengl.youyang.opengl_es20_study.programs.TextureShaderProgram;
import com.opengl.youyang.opengl_es20_study.utils.Geometry;
import com.opengl.youyang.opengl_es20_study.utils.MatrixHelper;
import com.opengl.youyang.opengl_es20_study.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15-4-12.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class FirstRender implements GLSurfaceView.Renderer {
    private final float[] viewMatrix=new float[16];
    private final float[] viewProjectionMatrix=new float[16];
    private final float[] modelViewProjectionMatrix=new float[16];

    //边界条件
    private final float leftBound=-0.5f;
    private final float rightBound=0.5f;
    private final float farBound=-0.8f;
    private final float nearBound=0.8f;

    private Geometry.Point previousBlueMalletPosition;
    private Geometry.Point previousRedMalletPosition;
    private Geometry.Point puckPosition;
    private Geometry.Vector puckVector;;

    private Puck puck;
    private final Context context;
    //存储矩阵数据
    private final float[] projectionMarix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table table;
    private Mallet mallet;

    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;

    private int texture;

    private boolean blueMalletPressed =false;
    private boolean redMalletPressed =false;

    private Geometry.Point blueMalletPosition;
    private Geometry.Point redMalletPosition;

    private final float[] invertedViewProjectionMatrix=new float[16];

    public FirstRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //当surface被创建的时候GlsurfaceView会运行这个方法。这表示在应用程序第一次运行时，设备被唤醒时，或者从其他activity备切换回来时 都有可能执行这个方法。
        GLES20.glClearColor(0.0f, 0.0f, 0.5f, 0.5f);
        table=new Table();
        mallet=new Mallet(0.08f,0.15f,32);
        puck=new Puck(0.06f,0.02f,32);

        puckPosition=new Geometry.Point(0f,puck.height/2f,0f);
        puckVector=new Geometry.Vector(0f,0f,0f);

        textureShaderProgram=new TextureShaderProgram(context);
        colorShaderProgram=new ColorShaderProgram(context);

        texture= TextureHelper.loadTexture(context,R.drawable.grass);
        blueMalletPosition =new Geometry.Point(0f,mallet.height/2f,0.4f);
        redMalletPosition=new Geometry.Point(0f,mallet.height/2f,-0.4f);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        //surface尺寸发生变化时执行。 比如横竖屏切换
        GLES20.glViewport(0, 0, i, i1);
        MatrixHelper.perspectiveM(projectionMarix, 45, (float) i / (float) i1, 1f, 10f);
        Matrix.setLookAtM(viewMatrix,0,0f,1.2f,2.2f,0f,0f,0f,0f,1f,0f);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        puckPosition=puckPosition.translate(puckVector);
        if(puckPosition.x<leftBound+puck.radius||puckPosition.x>rightBound-puck.radius){
            puckVector=new Geometry.Vector(-puckVector.x,puckVector.y,puckVector.z);

        }

        if(puckPosition.z<farBound+puck.radius||puckPosition.z>nearBound-puck.radius){
            puckVector=new Geometry.Vector(puckVector.x,puckVector.y,-puckVector.z);
        }

        puckPosition=new Geometry.Point(clamp(puckPosition.x,leftBound+puck.radius,rightBound-puck.radius),puckPosition.y,
                clamp(puckPosition.z,farBound+puck.radius,nearBound-puck.radius));
        puckVector=puckVector.scale(0.95f);

        //每次绘制一帧画面时都会调用。如果什么都不做，可能会看到糟糕的闪烁效果
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMarix, 0, viewMatrix, 0);
        Matrix.invertM(invertedViewProjectionMatrix, 0, viewProjectionMatrix, 0);

        positionTableInScene();
        textureShaderProgram.useProgram();
        textureShaderProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureShaderProgram);
        table.draw();

//        positionObjectInScene(0f, mallet.height / 2f, -0.4f);
        positionObjectInScene(redMalletPosition.x, redMalletPosition.y, redMalletPosition.z);
        colorShaderProgram.useProgram();
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mallet.bindData(colorShaderProgram);
        mallet.draw();
        positionObjectInScene(blueMalletPosition.x, blueMalletPosition.y, blueMalletPosition.z);


        colorShaderProgram.setUniforms(modelViewProjectionMatrix,0f,0f,1f);
        mallet.draw();

        positionObjectInScene(puckPosition.x,puckPosition.y,puckPosition.z);
        colorShaderProgram.setUniforms(modelViewProjectionMatrix,0.8f,0.8f,1f);
        puck.binfData(colorShaderProgram);
        puck.draw();


    }

    private void positionTableInScene(){
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        Matrix.multiplyMM(modelViewProjectionMatrix,0,viewProjectionMatrix,0,modelMatrix,0);
    }

    private void positionObjectInScene(float x,float y,float z){
        Matrix.setIdentityM(modelMatrix,0);
        Matrix.translateM(modelMatrix, 0, x, y, z);
        Matrix.multiplyMM(modelViewProjectionMatrix,0,viewProjectionMatrix,0,modelMatrix,0);
    }


    public void handleTouchPress(float normalizedX,float normalizedY){
        Geometry.Ray ray=convertNormalized2DPointToRay(normalizedX,normalizedY);
        Geometry.Sphere sphere=new Geometry.Sphere(new Geometry.Point(
                blueMalletPosition.x,
                blueMalletPosition.y,
                blueMalletPosition.z),
                mallet.height/2f
        );

        Geometry.Sphere redphere=new Geometry.Sphere(new Geometry.Point(
                redMalletPosition.x,
                redMalletPosition.y,
                redMalletPosition.z),
                mallet.height/2f
        );


        blueMalletPressed =Geometry.intersects(sphere,ray);
        redMalletPressed =Geometry.intersects(redphere,ray);
    }

    public void handleTouchDrag(float normalizedX,float normalizedY){
        previousBlueMalletPosition=blueMalletPosition;
        previousRedMalletPosition=redMalletPosition;
        if(blueMalletPressed){
            Geometry.Ray ray=convertNormalized2DPointToRay(normalizedX,normalizedY);
            Geometry.Plane plane=new Geometry.Plane(new Geometry.Point(0,0,0),new Geometry.Vector(0,1,0));
            Geometry.Point touchedoint=Geometry.intersectionPoint(ray,plane);
            blueMalletPosition=new Geometry.Point(clamp(touchedoint.x,leftBound+mallet.radius,rightBound-mallet.radius)
                    ,mallet.height/2f,clamp(touchedoint.z,0f+mallet.radius,nearBound-mallet.radius));

            float distance=Geometry.vectorBetween(blueMalletPosition,puckPosition).length();
            if (distance<(puck.radius+mallet.radius)){
                puckVector=Geometry.vectorBetween(previousBlueMalletPosition,blueMalletPosition);
            }
        }

        if(redMalletPressed){
            Geometry.Ray ray=convertNormalized2DPointToRay(normalizedX,normalizedY);
            Geometry.Plane plane=new Geometry.Plane(new Geometry.Point(0,0,0),new Geometry.Vector(0,1,0));
            Geometry.Point touchedoint=Geometry.intersectionPoint(ray,plane);
            redMalletPosition=new Geometry.Point(clamp(touchedoint.x,leftBound+mallet.radius,rightBound-mallet.radius)
                    ,mallet.height/2f,clampRed(touchedoint.z,0-mallet.radius,farBound+mallet.radius));

            float distance=Geometry.vectorBetween(redMalletPosition,puckPosition).length();
            if (distance<(puck.radius+mallet.radius)){
                puckVector=Geometry.vectorBetween(previousRedMalletPosition,redMalletPosition);
            }
        }


    }

    private float clamp(float value ,float min,float max){
        return Math.min(max,Math.max(value,min));
    }

    private float clampRed(float value ,float min,float max){
        return Math.max(max, Math.min(value, min));
    }

    private Geometry.Ray convertNormalized2DPointToRay(float normalizedX,float normalizedY){
        final float[] nearPointNdc={normalizedX,normalizedY,-1,1};
        final float[] farPointNdc={normalizedX,normalizedY,1,1};

        final float[] nearPointWorld=new float[4];
        final float[] farPointWorld=new float[4];

        //翻转透视投影
        Matrix.multiplyMV(nearPointWorld,0,invertedViewProjectionMatrix,0,nearPointNdc,0);
        Matrix.multiplyMV(farPointWorld, 0, invertedViewProjectionMatrix, 0, farPointNdc, 0);

        //反转透视除法
        divideByW(nearPointWorld);
        divideByW(farPointWorld);

        Geometry.Point nearPointRay=new Geometry.Point(nearPointWorld[0],nearPointWorld[1],nearPointWorld[2]);
        Geometry.Point farPointRay=new Geometry.Point(farPointWorld[0],farPointWorld[1],farPointWorld[2]);

        return new Geometry.Ray(nearPointRay,Geometry.vectorBetween(nearPointRay,farPointRay));

    };

    private void divideByW(float[] vector){
        vector[0]/=vector[3];
        vector[1]/=vector[3];
        vector[2]/=vector[3];

    }
}
