# 4.图像的本质
静态图像的本质其实是 位置与颜色的结合。动态图像需要增加方向向量和变换矩阵

可以以一些简单的建筑块构建高度复杂和漂亮优雅的结构。

模型：
<1>点 point

![](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/05/20110530007.png)

顶点是3D建模时用到的最小构成元素，顶点定义为两条或是多条边交会的地方。在3D模型中一个顶点可以为多条边，面或是多边形所共享。一个顶点也可以代表一个点光源或是Camera的位置。
在Android系统中可以使用一个浮点数数组来定义一个顶点，浮点数数组通常放在一个Buffer（java.nio)中来提高性能。

<2>线 line
![](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/05/20110530010.png)

连续线条line_strip
![](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/05/20110530008.png)

封闭线条line_loop
![](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/05/20110530009.png)

定义为两个顶点之间的线段。边是面和多边形的边界线。在3D模型中，边可以被相邻的两个面或是多边形形共享。对一个边做变换将影响边相接的所有顶点，面或多边形。在OpenGL中，通常无需直接来定义一个边，而是通过顶点定义一个面，从而由面定义了其所对应的三条边。可以通过修改边的两个顶点来更改一条边

<3>三角形 triangles   共用一个条带上的顶点的三角形triangle_strip  ，扇形排列共用相邻顶点的一组三角形triangle_fan

在OpenGL ES中，面特指一个三角形，由三个顶点和三条边构成，对一个面所做的变化影响到连接面的所有顶点和边，面

位置变换：
<1>平移
<2>旋转
<3>缩放
<4>投影  正交投影  透视投影

RGB颜色空间

显示器使用了红绿蓝三种颜色。每一个像素按照不同的强度来显示三种颜色，人眼无法分清混在一起的 单色，看起来就像是混合之后的颜色值。

累加RGB颜色模型    累加起来颜色会更明亮
减色绘画模型           使用的颜色越多 就越暗淡


位置  向量和矩阵



正面和背面剔除，深度测试，混合颜色

Render (渲染）

我们已定义好了多边形，下面就要了解如和使用OpenGL ES的API来绘制（渲染）这个多边形了。OpenGL ES提供了两类方法来绘制一个空间几何图形：

  public abstract void glDrawArrays(int mode, int first, int count)   使用VetexBuffer 来绘制，顶点的顺序由vertexBuffer中的顺序指定。
    public abstract void glDrawElements(int mode, int count, int type, Buffer indices)  ，可以重新定义顶点的顺序，顶点的顺序由indices Buffer 指定。
