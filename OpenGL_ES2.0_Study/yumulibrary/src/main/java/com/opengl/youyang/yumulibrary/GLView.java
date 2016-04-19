package com.opengl.youyang.yumulibrary;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.opengl.youyang.yumulibrary.utils.IGLCanvas;

/**
 * Created by youyang on 16/3/30.
 */
public class GLView extends GLSurfaceView {
    IGLCanvas mCanvas;

    public GLView(Context context) {
        super(context);
    }

    public GLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRenderMode(RENDERMODE_WHEN_DIRTY);

    }



}
