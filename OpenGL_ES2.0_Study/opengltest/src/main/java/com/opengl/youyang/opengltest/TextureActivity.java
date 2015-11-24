package com.opengl.youyang.opengltest;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.opengl.youyang.opengltest.render.TextureRender;
import com.opengl.youyang.opengltest.view.TextureSurfaceView;

/**
 * Created by youyang on 15/11/24.
 */
public class TextureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextureSurfaceView view = new TextureSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setRenderer(new TextureRender(this));
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(view);
    }
}
