package com.opengl.youyang.opengltest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.opengl.youyang.opengltest.data.VertexArray;
import com.opengl.youyang.opengltest.render.FirstRender;
import com.opengl.youyang.opengltest.utils.MatrixHelper;
import com.opengl.youyang.opengltest.utils.ShaderHelper;
import com.opengl.youyang.opengltest.utils.TextResourceReader;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15/11/24.
 */
public class Texture1Activity extends Activity {


    protected static final String U_MVPMATRIX = "u_MVPMatrix";
    protected static final String U_COLOR = "u_Color";
    protected static final String UR = "uR";
    protected static final String A_NORMAL = "a_Normal";
    protected static final String U_LIGHTLOCATION = "uLightLocation";
    protected static final String U_MMATRIX = "uMMatrix";
    protected static final String U_CAMERA = "uCamera";
    private static final String TAG = "Texture1Activity";

    int u_MVPMatrixLocation;

    int gwidth, gheight;
    //attribute常量
    protected static final String A_POSITION = "a_Position";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected static final String A_COLOR = "a_Color";

    GLSurfaceView view;

    VertexArray mVertexArray;

    int u_ColorSeparation;
    int u_From;
    int u_To;
    int u_Progress;
    int u_Size;
    int u_Resolution;
    int u_Zoom;

    float[] mVertexs = new float[]{
            -1f, 1f, 0, 0,          //左上
            -1f, -1f, 0, 1f,           //左下
            1f, -1f, 1f, 1f,            //右下
            -1, 1, 0, 0,            //左上
            1, -1, 1f, 1f,            //右下
            1, 1, 1f, 0,            //右上
    };
    float[] res;

    protected int program;
    int[] textureIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean supportES2 = isSupportES2();
        view = new GLSurfaceView(this);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                index++;
                index = index % 1000;
            }
        }, 30);

        program = ShaderHelper.
                buildProgram(TextResourceReader.readTextResourceFromRaw(Texture1Activity.this,
                        R.raw.texture_loading_vertext_shader),
                        TextResourceReader.readTextResourceFromRaw(Texture1Activity.this,
                                R.raw.texture_loading_fragment_shader));

        if (supportES2) {
            view.setEGLContextClientVersion(2);
            view.setRenderer(new GLSurfaceView.Renderer() {
                @Override
                public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                    GLES20.glClearColor(0f, 0f, 0f, 0.0f);
                    MatrixHelper.initStack();//初始化矩阵

                    //1.定义顶点坐标 和纹理映射坐标
                    mVertexArray = new VertexArray(mVertexs);
                    //2.写顶点着色器和片元着色器
                    //3.编译着色器程序
                    GLES20.glUseProgram(program);
                    //4.给着色器中的属性赋值

                    final int aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
                    final int aTextureCoordinates = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);

                    u_MVPMatrixLocation = GLES20.glGetUniformLocation(program, U_MVPMATRIX);
                    u_Progress = GLES20.glGetUniformLocation(program, "progress");
                    u_Resolution = GLES20.glGetUniformLocation(program, "resolution");
                    u_Size = GLES20.glGetUniformLocation(program, "size");
                    u_Zoom = GLES20.glGetUniformLocation(program, "zoom");
                    u_ColorSeparation = GLES20.glGetUniformLocation(program, "colorSeparation");

                    u_From = GLES20.glGetUniformLocation(program, "from");
                    u_To = GLES20.glGetUniformLocation(program, "to");

                    mVertexArray.setVertexAttribPointer(0, aPositionLocation, 2, 16);
                    mVertexArray.setVertexAttribPointer(2, aTextureCoordinates, 2, 16);
                }

                @Override
                public void onSurfaceChanged(GL10 gl, int width, int height) {
                    GLES20.glViewport(0, 0, width, height);
                    MatrixHelper.perspectiveM(45, (float) width / (float) height, 1f, 10f);
                    MatrixHelper.setCamera(0, 0, 8.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
                    res = new float[]{(float) width, (float) height};
                    textureIds = getTextures();

                }


                @Override
                public void onDrawFrame(GL10 gl) {
                    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
                    GLES20.glUniformMatrix4fv(u_MVPMatrixLocation, 1, false, MatrixHelper.getFinalMatrix(), 0);
                    GLES20.glUniform1f(u_Progress, index / 1000);
                    GLES20.glUniform1f(u_Size, 0.04f);
                    GLES20.glUniform1f(u_Zoom, 30f);
                    GLES20.glUniform1f(u_ColorSeparation, 0.3f);
                    GLES20.glUniform2fv(u_Resolution, 1, res, 0);

                    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,
                            textureIds[0]);
                    GLES20.glUniform1i(u_From, 0);

                    GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,
                            textureIds[1]);
                    GLES20.glUniform1i(u_To, 1);

                    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);

                }
            });
        } else {
            Toast.makeText(this, "不支持OpenGL ES2.0", Toast.LENGTH_SHORT).show();
            return;
        }


        setContentView(view);
    }

    int index = 0;

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();

    }


    // 纹理id 数组
    private int[] mTextureIds = null;

    public int[] getTextures() {

        //第一次如果没有纹理id的话就直接创建
        if (mTextureIds == null) {
            // 生成纹理
            mTextureIds = new int[2];
            GLES20.glGenTextures(2, mTextureIds, 0);
            for (int textureId : mTextureIds) {
                // 设置纹理属性
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            }
        }


        Bitmap b1 = getTextures(R.drawable.img_loading);
        Bitmap b2 = getTextures(R.drawable.aaa);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureIds[0]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0,
                b1, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureIds[1]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0,
                b2, 0);
        b1.recycle();
        b2.recycle();

        return mTextureIds;
    }

    private Bitmap getTextures(int resourceId) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId, options);
        bitmap = getTexture(bitmap);
        return bitmap;
    }

    /**
     * Generates nearest power of two sized Bitmap for give Bitmap. Returns this new Bitmap using
     * default return statement + original texture coordinates are stored into RectF.
     */
    private static Bitmap getTexture(Bitmap bitmap) {
        // Bitmap original size.
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        // Bitmap size expanded to next power of two. This is done due to
        // the requirement on many devices, texture width and height should
        // be power of two.
        int newW = getNextHighestPO2(w);
        int newH = getNextHighestPO2(h);

        // TODO: Is there another way to create a bigger Bitmap and copy
        // original Bitmap to it more efficiently? Immutable bitmap anyone?
        Bitmap bitmapTex = Bitmap.createBitmap(newW, newH, bitmap.getConfig());
        Canvas c = new Canvas(bitmapTex);
        c.drawBitmap(bitmap, 0, 0, null);

        // Calculate final texture coordinates.
        float texX = (float) w / newW;
        float texY = (float) h / newH;
        return bitmapTex;
    }

    /**
     * Calculates the next highest power of two for a given integer.
     */
    private static int getNextHighestPO2(int n) {
        n -= 1;
        n = n | (n >> 1);
        n = n | (n >> 2);
        n = n | (n >> 4);
        n = n | (n >> 8);
        n = n | (n >> 16);
        n = n | (n >> 32);
        return n + 1;
    }

    /**
     * 先测试是否为模拟器，如果是的话就假设其能运行OpenGL。不是模拟器的话就判断其是否支持OpenGL ES2.0
     */
    private boolean isSupportES2() {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = manager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic"))
                || (Build.FINGERPRINT.startsWith("unknow"))
                || (Build.MODEL.contains("google_sdk"))
                || (Build.MODEL.contains("Emulator"))
                || (Build.MODEL.contains("Android SDK built for x86"))
        );
    }
}
