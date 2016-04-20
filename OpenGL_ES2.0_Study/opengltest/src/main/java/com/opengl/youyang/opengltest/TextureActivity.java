package com.opengl.youyang.opengltest;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.opengl.youyang.opengltest.render.TextureRender;
import com.opengl.youyang.opengltest.view.TextureSurfaceView;
import com.opengl.youyang.opengltest.view.YumuView;
import com.opengl.youyang.yumulibrary.utils.IGLCanvas;

/**
 * Created by youyang on 15/11/24.
 */
public class TextureActivity extends Activity {

    YumuView view;
    IGLCanvas mCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new YumuView(this,null);
        mCanvas = view.getCanvas();
        setContentView(view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();

        if(mCanvas!=null){
            mCanvas.drawCircle(200,200,60);
        }
    }
}
