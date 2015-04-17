package com.opengl.youyang.opengl_es20_study.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * Created by youyang on 15-4-17.
 */
public class TextureHelper {
    public static final String TAG="TextureHelper";
    public static int loadTexture(Context context,int resourceId){
        final int[] textureObjectIds=new int[1];
        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inScaled=false;
        final Bitmap bitmap =BitmapFactory.decodeResource(context.getResources(),resourceId,options);

        if(bitmap==null){
            if(LogConfig.ON){
                Log.w(TAG,"图片资源无法加载");
            }
            GLES20.glDeleteTextures(1,textureObjectIds,0);
        }
        GLES20.glGenTextures(1,textureObjectIds,0);
        if(textureObjectIds[0]==0){
            if(LogConfig.ON){
                Log.w(TAG,"无法生成一个纹理对象");
            }
            return 0;
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureObjectIds[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        //解除绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);

        return textureObjectIds[0];
    }

    /**
     * Calculates the next highest power of two for a given integer.
     */
    private int getNextHighestPO2(int n) {
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
     * Generates nearest power of two sized Bitmap for give Bitmap. Returns this
     * new Bitmap using default return statement + original texture coordinates
     * are stored into RectF.
     */
    private Bitmap getTexture(Bitmap bitmap, RectF textureRect) {
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
        //TODO
        textureRect.set(0f, 0f, 0.52F, 0.52F);
        return bitmapTex;
    }
}
