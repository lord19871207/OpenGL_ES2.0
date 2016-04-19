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

    /**
     * 绘制矩形
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    void drawRect(float left, float top, float right, float bottom);

    /**
     * 绘制椭圆
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    void drawOval(float left, float top, float right, float bottom);

    /**
     * 绘制圆形
     * @param cx
     * @param cy
     * @param radius
     */
    void drawCircle(float cx, float cy, float radius);

    /**
     * 绘制圆弧
     * @param oval
     * @param startAngle
     * @param sweepAngle
     * @param useCenter
     */
    void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter);

    /**
     * 绘制bitmap
     * @param bitmap
     * @param left
     * @param top
     */
    void drawBitmap(@NonNull Bitmap bitmap, float left, float top);

    /**
     * 设置图片
     * @param bitmap
     */
    void setBitmap(@Nullable Bitmap bitmap);

    /**
     * 设置绘制区域大小
     * @param width
     * @param height
     */
    void setViewport(int width, int height);

    /**
     * 获取宽度
     * @return
     */
    int getWidth();

    /**
     * 获取高度
     * @return
     */
    int getHeight();

    /**
     * 保存当前状态
     * @return
     */
    int save();

    /**
     * 恢复上一次保存的状态
     */
    void restore();

    /**
     * 平移
     * @param dx x轴上平移的距离
     * @param dy y轴上平移的距离
     */
    void translate(float dx, float dy);

    /**
     * 放大缩小
     * @param sx x轴上放大的比例
     * @param sy y轴上放大的比例
     */
    void scale(float sx, float sy);

    /**
     * 旋转
     * @param degrees 角度
     */
    void rotate(float degrees);

    /**
     * 错切
     * @param sx x轴上倾斜的比例
     * @param sy y轴上倾斜的比例
     */
    void skew(float sx, float sy);

    /**
     * 设置矩阵
     * @param matrix
     */
    void setMatrix(@Nullable Matrix matrix);

    /**
     * 绘制网格图片
     * @param bitmap
     * @param meshWidth
     * @param meshHeight
     * @param verts
     * @param vertOffset
     * @param colors
     * @param colorOffset
     */
    void drawBitmapMesh(@NonNull Bitmap bitmap, int meshWidth, int meshHeight,
                        @NonNull float[] verts, int vertOffset, @Nullable int[] colors, int colorOffset);

    /**
     * 刷新当前的图片
     */
    void requestRender();
}
