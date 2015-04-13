package com.opengl.youyang.opengl_es20_study;

import android.annotation.TargetApi;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;

import com.opengl.youyang.opengl_es20_study.utils.LogConfig;
import com.opengl.youyang.opengl_es20_study.utils.ShaderHelper;
import com.opengl.youyang.opengl_es20_study.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15-4-12.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class FirstRender implements GLSurfaceView.Renderer {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PRE_FLOAT=4;
    private FloatBuffer vertexData;
    private Context context;
    private int programId;
    private static final String U_COLOR="u_Color";//
    private int uColorLocation;//着色器在opengl中的位置
    private static final String A_POSITION="a_Position";
    private int aPositionLocation;


    public FirstRender(Context context) {
        //桌子对应的两个三角形顶点的位置属性
        float[] tableVertices = {
                //三角形1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,
                //三角形2
                -0.5f, -0.5f,
                0.5f, -0.5f,
               0.5f, 0.5f,
                //line1
                -0.5f,0f,
                0.5f,0f,
                //球
                0f,-0.25f,
                0f,0.25f

        };
        //allocateDirect分配本地内存  将内存从虚拟机中拷贝到本地
        vertexData= ByteBuffer.allocateDirect(tableVertices.length * BYTES_PRE_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(tableVertices);
        this.context=context;


    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //当surface被创建的时候GlsurfaceView会运行这个方法。这表示在应用程序第一次运行时，设备被唤醒时，或者从其他activity备切换回来时 都有可能执行这个方法。
        GLES20.glClearColor(0.0f, 0.f, 0.f, 0.f);
        String  vertexShaderSource= TextResourceReader.readTextResourceFromRaw(context,R.raw.simple_vertex_shader);
        String fragmentShaderSource= TextResourceReader.readTextResourceFromRaw(context,R.raw.simple_fragment_shader);

        int vertextShader= ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader=ShaderHelper.compileFragmentShader(fragmentShaderSource);
        programId=ShaderHelper.linkProgram(vertextShader,fragmentShader);

        if(LogConfig.ON){
            ShaderHelper.validateProgram(programId);
        }
        //调用程序
        GLES20.glUseProgram(programId);
        //将raw文件中的片段着色器和 OpenGL彻底联系起来  获取到uniform的位置 并把位置传入uColorLocation
        uColorLocation=GLES20.glGetUniformLocation(programId, U_COLOR);
        //获取属性的位置 (关联顶点着色器)
        aPositionLocation=GLES20.glGetAttribLocation(programId,A_POSITION);
        //指针置0，确保它从开头去读数据
        vertexData.position(0);
        //传入 1.属性位置   2.每一个顶点有多少个分量   3.浮点类型  4. 这个参数只有使用整形数据的时候才有意义  5.步长 间隔多长一个属性
        //6.告诉OpenGL从哪里去读取数据
        GLES20.glVertexAttribPointer(aPositionLocation,POSITION_COMPONENT_COUNT,GLES20.GL_FLOAT,false,0,vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        //surface尺寸发生变化时执行。 比如横竖屏切换
        GLES20.glViewport(0, 0, i, i1);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //每次绘制一帧画面时都会调用。如果什么都不做，可能会看到糟糕的闪烁效果
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //更新着色器中的u_Color的值。
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        //绘制桌子 从顶点组 的第0个开始读 总共读6个
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);

        //绘制分割线
        GLES20.glUniform4f(uColorLocation,1.0f,0f,0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        //绘制第一个木槌
        GLES20.glUniform4f(uColorLocation,0f,0f,1.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,8,1);
        //绘制第二个木槌
        GLES20.glUniform4f(uColorLocation, 1.0f, 0f, 0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);


    }
}
