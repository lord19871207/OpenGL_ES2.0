package com.opengl.youyang.yumulibrary;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.opengl.youyang.yumulibrary.utils.IGLCanvas;
import com.opengl.youyang.yumulibrary.utils.IGLRender;

/**
 * Created by youyang on 16/3/30.
 */
public class GLView extends GLSurfaceView implements IGLRender{
    protected IGLCanvas mCanvas;
    protected Context mContext ;

    public GLView(Context context) {
        super(context);
        mContext = context;
        mCanvas = new GLRender(mContext,this);
    }

    public GLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mCanvas = new GLRender(mContext,this);
        setEGLContextClientVersion(2);
        setRenderer((GLRender)mCanvas);
        setRenderMode(RENDERMODE_CONTINUOUSLY);

    }

    public IGLCanvas getCanvas(){
        return mCanvas;
    }



}
