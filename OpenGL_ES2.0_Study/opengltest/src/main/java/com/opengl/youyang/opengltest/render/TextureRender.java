package com.opengl.youyang.opengltest.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.opengl.youyang.opengltest.R;
import com.opengl.youyang.opengltest.data.VertexArray;
import com.opengl.youyang.opengltest.object.Square;
import com.opengl.youyang.opengltest.program.TextureShaderProgram;
import com.opengl.youyang.opengltest.utils.MatrixHelper;
import com.opengl.youyang.opengltest.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by youyang on 15/11/24.
 */
public class TextureRender implements GLSurfaceView.Renderer {

    private TextureShaderProgram textureShaderProgram;
    private Context context;
    private float[] vertex;
    private VertexArray vertexArray;

    private Square square;

    public TextureRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0f, 0f, 0f, 0.0f);
        MatrixHelper.initStack();//初始化矩阵
        square = new Square();
        textureShaderProgram = new TextureShaderProgram(context);
        textureShaderProgram.useProgram();
        vertex = square.getSquare(1f, 1f);
        vertexArray = new VertexArray(vertex);

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(45, (float) width / (float) height, 1f, 10f);
        MatrixHelper.setCamera(0, 0, 8.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        vertexArray.setVertexAttribPointer(0, textureShaderProgram.getPositionAttributeLocation(), 3, 5);
        vertexArray.setVertexAttribPointer(2, textureShaderProgram.getTextureCoordinatesAttributeLocation(), 2, 5);
        textureShaderProgram.setUniforms(MatrixHelper.getFinalMatrix(), TextureHelper.loadTexture(context, R.drawable.aaa));
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        MatrixHelper.pushMatrix();
        MatrixHelper.pushMatrix();
        MatrixHelper.translate(-0.5f, 0f, 1f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 2);
        MatrixHelper.popMatrix();

        MatrixHelper.popMatrix();

    }
}
