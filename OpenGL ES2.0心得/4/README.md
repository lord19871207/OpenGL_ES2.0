# 4.图像的本质

####图像的外在表现
在手机上 静态图像的本质其实是 位置与颜色的结合。
因为显示器上的这些图片都是bitmap位图 也叫就是点阵图。它们由一个一个的像素组合而成。每一个像素都有一个位置坐标和颜色值。所有的点集合在一起就成了一副图像。

![](http://image.uisdc.com/wp-content/uploads/2014/04/23.png)



   静态图像在增加方向向量和变换矩阵后，通过矩阵平移，旋转，投影，缩放，就能达到动态的效果。
![](file:///Users/youyang/Library/Containers/com.evernote.Evernote/Data/Library/Application%20Support/com.evernote.Evernote/accounts/app.yinxiang.com/11246382/external-edits/7E8E7A0F-B1A4-4D37-BF49-EAC21BDD3AA3/C96A8F7E-EB75-44E2-BF4F-990F132CF3B0.gif)



物体在图像中的位置与形状 由它们的几何形状，相机位置，以及环境特性等因素决定。

![](https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTm4rH-ZRbIU8YgQTyeoAFGREoeekSEQxCfRU3KUzW25e1K4b5UFA)


而视觉外观则受材料属性，光源，纹理，以及光照模型的影响。
![](http://img.my.csdn.net/uploads/201212/06/1354777683_3315.JPG)


####图像的内在表现
画图都是针对应用程序的一块内存填充数据（包括位置 ，颜色，法线向量等等），android中的 Activity对应着底层的一个surface，所有的绘图操作都是对这块surface内存进行操作。数据发生改变，则图形就会随之改变。



存储顶点的两种格式：
结构数组
在一个缓冲区中存储所有的顶点属性

数组结构
在单独的缓冲区中保存每个顶点属性


图元模型：
<1>点 point

![](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/05/20110530007.png)

顶点是3D建模时用到的最小构成元素，顶点定义为两条或是多条边交会的地方。在3D模型中一个顶点可以为多条边，面或是多边形所共享。一个顶点也可以代表一个点光源或是Camera的位置。
在Android系统中可以使用一个浮点数数组来定义一个顶点，浮点数数组通常放在一个Buffer（java.nio)中来提高性能。

<2>线 line
![](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/05/20110530010.png)

连续线条line_strip
![](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/05/20110530008.png)

封闭线条line_loop
![封闭线条line_loop](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/05/20110530009.png)

定义为两个顶点之间的线段。边是面和多边形的边界线。在3D模型中，边可以被相邻的两个面或是多边形形共享。对一个边做变换将影响边相接的所有顶点，面或多边形。在OpenGL中，通常无需直接来定义一个边，而是通过顶点定义一个面，从而由面定义了其所对应的三条边。可以通过修改边的两个顶点来更改一条边

<3>

三角形 triangles
![三角形 triangles](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/05/20110530011.png)


共用一个条带上的顶点的三角形
triangle_strip
![](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/05/20110530012.png)
，扇形排列共用相邻顶点的一组三角形triangle_fan

在OpenGL ES中，面特指一个三角形，由三个顶点和三条边构成，对一个面所做的变化影响到连接面的所有顶点和边，面


如下图定义了一个正方形：

对应的顶点和buffer 定义代码：
```
1
	private short[] indices = { 0, 1, 2, 0, 2, 3 };
2
	//To gain some performance we also put this ones in a byte buffer.
3
	// short is 2 bytes, therefore we multiply the number if vertices with 2.
4
	ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
5
	ibb.order(ByteOrder.nativeOrder());
6
	ShortBuffer indexBuffer = ibb.asShortBuffer();
7
	indexBuffer.put(indices);
8
	indexBuffer.position(0);
```


定义三角形的顶点的顺序很重要 在拼接曲面的时候，用来定义面的顶点的顺序非常重要，因为顶点的顺序定义了面的朝向（前向或是后向），为了获取绘制的高性能，一般情况不会绘制面的前面和后面，只绘制面的“前面”。虽然“前面”“后面”的定义可以应人而易，但一般为所有的“前面”定义统一的顶点顺序(顺时针或是逆时针方向）。

//可以以一些简单的建筑块构建高度复杂和漂亮优雅的结构。

下面代码设置逆时针方法为面的“前面”：
1
	gl.glFrontFace(GL10.GL_CCW);

打开 忽略“后面”设置：
1
	gl.glEnable(GL10.GL_CULL_FACE);

明确指明“忽略“哪个面的代码如下：
查看源代码
打印
帮助
1
	gl.glCullFace(GL10.GL_BACK);



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
