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
    private float[] vertex_texture;
    private VertexArray vertexArray;
    private VertexArray vertex_texture_array;

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
        vertex = square.getSquare(2f, 2f);
        vertex_texture = square.getSquareTexture();
        vertexArray = new VertexArray(vertex);
        vertex_texture_array = new VertexArray(vertex_texture);

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(45, (float) width / (float) height, 1f, 10f);
        MatrixHelper.setCamera(0, 0, 8.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        vertexArray.setVertexAttribPointer(0, textureShaderProgram.getPositionAttributeLocation(), 3 * 4, 0);
        vertex_texture_array.setVertexAttribPointer(0, textureShaderProgram.getTextureCoordinatesAttributeLocation(), 2 * 4, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        MatrixHelper.pushMatrix();

        MatrixHelper.pushMatrix();
        MatrixHelper.translate(0f, 0f, 1f);
        textureShaderProgram.setUniforms(MatrixHelper.getFinalMatrix(), TextureHelper.loadTexture(context, R.drawable.ic_launcher));
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 2);
        MatrixHelper.popMatrix();

        MatrixHelper.popMatrix();

    }
}
