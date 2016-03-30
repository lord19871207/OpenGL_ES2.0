package com.opengl.youyang.yumulibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by youyang on 16/3/30.
 */
public interface IGLCanvas {
    /**
     * 绘制argb背景
     * @param a
     * @param r
     * @param g
     * @param b
     */
    void drawARGB(int a, int r, int g, int b);

    /**
     * 绘制点
     * @param pts
     * @param offset
     * @param count
     */
    void drawPoints(float[] pts, int offset, int count);

    /**
     * 绘制线
     * @param startX
     * @param startY
     * @param stopX
     * @param stopY
     */
    void drawLine(float startX, float startY, float stopX, float stopY);
    void drawRect(float left, float top, float right, float bottom);
    void drawOval(float left, float top, float right, float bottom);
    void drawCircle(float cx, float cy, float radius);
    void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter);
    void drawBitmap(@NonNull Bitmap bitmap, float left, float top);
    void setBitmap(@Nullable Bitmap bitmap);
    void setViewport(int width, int height);
    int getWidth();
    int getHeight();
    int save();
    void restore();
    void translate(float dx, float dy);
    void scale(float sx, float sy);
    void rotate(float degrees);
    void skew(float sx, float sy);
    void setMatrix(@Nullable Matrix matrix);
    void drawBitmapMesh(@NonNull Bitmap bitmap, int meshWidth, int meshHeight,
                        @NonNull float[] verts, int vertOffset, @Nullable int[] colors, int colorOffset);

}
