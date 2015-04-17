package com.opengl.youyang.opengl_es20_study;

import android.annotation.TargetApi;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Build;

import com.opengl.youyang.opengl_es20_study.utils.LogConfig;
import com.opengl.youyang.opengl_es20_study.utils.MatrixHelper;
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
    private static final int POSITION_COMPONENT_COUNT = 2;//顶点拥有几个分量
    private static final int BYTES_PER_FLOAT = 4;//每个float类型含有的字节数
    private static final String A_COLOR = "a_Color";
    private static final String A_POSITION = "a_Position";
    private static final String U_MATRIX = "u_Matrix";
    private static final int COLOR_COMPONENT_COUNT = 3; //颜色的分量数目 rgb
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;//步长 ，每个顶点间隔多少个字节
    private FloatBuffer vertexData; //缓存的顶点数据
    private Context context;
    private int programId;

    //   private static final String U_COLOR="u_Color";//
    //  private int uColorLocation;//着色器在opengl中的位置
    private int aPositionLocation;
    private int aColorLocation;
    //存储矩阵数据
    private final float[] projectionMarix = new float[16];
    //矩阵的位置
    private int uMatrixLocation;

    private final float[] modelMatrix = new float[16];

    public FirstRender(Context context) {
        //桌子对应的两个三角形顶点的位置属性
        float[] tableVertices = {
                0f, 0f, 1f, 1f, 1f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

                -0.5f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 1f, 0f, 0f,

                0f, -0.4f, 0f, 0f, 1f,
                0f, 0.4f, 1f, 0f, 0f

        };
        //allocateDirect分配本地内存  将内存从虚拟机中拷贝到本地
        vertexData = ByteBuffer.allocateDirect(tableVertices.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        //将顶点数据置入缓存
        vertexData.put(tableVertices);
        this.context = context;

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //当surface被创建的时候GlsurfaceView会运行这个方法。这表示在应用程序第一次运行时，设备被唤醒时，或者从其他activity备切换回来时 都有可能执行这个方法。
        GLES20.glClearColor(0.0f, 0.f, 0.f, 0.f);
        String vertexShaderSource = TextResourceReader.readTextResourceFromRaw(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextResourceFromRaw(context, R.raw.simple_fragment_shader);

        //编译顶点着色器和片段着色器
        int vertextShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        //链接 获取程序id
        programId = ShaderHelper.linkProgram(vertextShader, fragmentShader);

        if (LogConfig.ON) {
            ShaderHelper.validateProgram(programId);
        }
        //调用程序
        GLES20.glUseProgram(programId);

        //获取矩阵的位置
        uMatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX);
        aColorLocation = GLES20.glGetAttribLocation(programId, A_COLOR);
        //获取属性的位置 (关联顶点着色器)
        aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION);
        //指针置0，确保它从开头去读数据
        vertexData.position(0);
        //传入 1.属性位置   2.每一个顶点有多少个分量   3.浮点类型  4. 这个参数只有使用整形数据的时候才有意义  5.步长 间隔多长一个属性
        //6.告诉OpenGL从哪里去读取数据
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

        //跳过顶点的位置数据 开始读取颜色数据
        vertexData.position(POSITION_COMPONENT_COUNT);
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
        GLES20.glEnableVertexAttribArray(aColorLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        //surface尺寸发生变化时执行。 比如横竖屏切换
        GLES20.glViewport(0, 0, i, i1);

//        //创建正交投影矩阵
//        final float aspctRatio=i>i1?(float)i/(float)i1:(float)i1/(float)i;
//        if (i>i1){
//            //横屏  参数：目标矩阵 起始偏移量  x轴的范围   y轴的范围   z轴的范围
//            Matrix.orthoM(projectionMarix,0,-aspctRatio,aspctRatio,-1f,1f,-1f,1f);
//        }else{
//            //竖屏
//            Matrix.orthoM(projectionMarix,0,-1,1,-aspctRatio,aspctRatio,-1f,1f);
//        }

        MatrixHelper.perspectiveM(projectionMarix, 45, (float) i / (float) i1, 1f, 10f);
        //将模型矩阵设置为单位矩阵
        Matrix.setIdentityM(modelMatrix, 0);
        //沿着z轴平移-2
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2f);

        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, projectionMarix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMarix, 0, temp.length);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //每次绘制一帧画面时都会调用。如果什么都不做，可能会看到糟糕的闪烁效果
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //传递矩阵给着色器
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMarix, 0);
        //更新着色器中的u_Color的值。
        // GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);

        //绘制桌子 从顶点组 的第0个开始读 总共读6个
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);

        //绘制分割线
//        GLES20.glUniform4f(uColorLocation,1.0f,0f,0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        //绘制第一个木槌
//        GLES20.glUniform4f(uColorLocation,0f,0f,1.0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
        //绘制第二个木槌
//        GLES20.glUniform4f(uColorLocation, 1.0f, 0f, 0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);


    }
}
