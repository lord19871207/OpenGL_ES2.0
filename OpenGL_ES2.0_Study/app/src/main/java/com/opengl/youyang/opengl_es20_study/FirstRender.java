package com.opengl.youyang.opengl_es20_study;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Shader;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;

import com.opengl.youyang.opengl_es20_study.utils.ShaderHelper;
import com.opengl.youyang.opengl_es20_study.utils.TextResourceReader;

import org.w3c.dom.Text;

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

    public FirstRender(Context context) {
        //桌子对应的两个三角形顶点的位置属性
        float[] tableVertices = {
                //三角形1
                0f, 0f,
                9f, 14f,
                0f, 14f,
                //三角形2
                0f, 0f,
                9f, 0f,
                9f, 14f,
                //line1
                0f,7f,
                9f,7f,
                //球
                4.5f,2f,
                4.5f,12f

        };
        //allocateDirect分配本地内存  将内存从虚拟机中拷贝到本地
        vertexData= ByteBuffer.allocateDirect(tableVertices.length * BYTES_PRE_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(tableVertices);
        this.context=context;


    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //当surface被创建的时候GlsurfaceView会运行这个方法。这表示在应用程序第一次运行时，设备被唤醒时，或者从其他activity备切换回来时 都有可能执行这个方法。
        GLES20.glClearColor(1.0f, 0.f, 0.f, 0.f);
        String  vertexShaderSource= TextResourceReader.readTextResourceFromRaw(context,R.raw.simple_vertex_shader);
        String fragmentShaderSource= TextResourceReader.readTextResourceFromRaw(context,R.raw.simple_fragment_shader);

        int vertextShader= ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader=ShaderHelper.compileFragmentShader(fragmentShaderSource);
        programId=ShaderHelper.linkProgram(vertextShader,fragmentShader);

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

    }
}
