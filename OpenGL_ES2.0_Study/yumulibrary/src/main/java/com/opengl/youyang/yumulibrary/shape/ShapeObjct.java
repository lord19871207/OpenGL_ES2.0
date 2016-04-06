package com.opengl.youyang.yumulibrary.shape;

import com.opengl.youyang.yumulibrary.programs.ColorShaderProgram;

/**
 * Created by youyang on 16/3/30.
 */
public interface ShapeObjct {
    void draw();
    void generateVertices();
    void bindData(ColorShaderProgram colorShaderProgram);
}
