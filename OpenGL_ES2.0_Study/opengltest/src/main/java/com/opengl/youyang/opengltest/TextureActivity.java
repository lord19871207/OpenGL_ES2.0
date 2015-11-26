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

    TextureSurfaceView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new TextureSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setRenderer(new TextureRender(this));
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(view);
    }

    @Override
    protected void onPause() {
        super.onPause();

        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        view.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
    }
}
