# 分形及其在OpenGL中的简单运用
1.分形的概念
首先需要理解分形究竟是什么。我们往往称呼分形曲线，但是分形又不完全是曲线。因为我们意识中的曲线都是一维，平面是二维的。但分形却是介于一维和二维之间，所以想要介绍这个东西还真不是一件容易的事。一般对于这种无法用经验和直觉来描述的东西都需要用数学公式来解释。在此之前需要先了解维度的概念。只有了解什么是维度了，才能理解一维和二维之间的维度是个什么样的东西。



2.曼德布罗集 和 朱利亚集
基于复数平面产生的递归迭代

曼德布罗集合可以用复二次多项式来定义：

    f_c(z) = z^2 + c ,

其中 c 是一个复数参数。

从 z = 0 开始对 f_c(z) 进行迭代：

    z_{n+1} = z_n^2 + c, n=0,1,2,...

    z_0 = 0 ,

    z_1 = z_0^2 + c = c ,

    z_2 = z_1^2 + c = c^2 + c ,

每次迭代的值依序如以下序列所示：

(0, f_c(0), f_c(f_c(0)), f_c(f_c(f_c(0))), .....)

不同的参数 c 可能使序列的绝对值逐渐发散到无限大，也可能收敛在有限的区域内。

曼德博集合 M 就是使序列不延伸至无限大的所有复数 c 的集合。

![](https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Mandel_zoom_00_mandelbrot_set.jpg/1920px-Mandel_zoom_00_mandelbrot_set.jpg)

![](https://upload.wikimedia.org/wikipedia/commons/thumb/e/ee/Mandel_zoom_01_head_and_shoulder.jpg/1920px-Mandel_zoom_01_head_and_shoulder.jpg)

![](https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Mandel_zoom_02_seehorse_valley.jpg/1920px-Mandel_zoom_02_seehorse_valley.jpg)

![](https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Mandel_zoom_03_seehorse.jpg/1920px-Mandel_zoom_03_seehorse.jpg)

![](https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/Mandel_zoom_04_seehorse_tail.jpg/1920px-Mandel_zoom_04_seehorse_tail.jpg)

![](https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Mandel_zoom_12_satellite_spirally_wheel_with_julia_islands.jpg/1920px-Mandel_zoom_12_satellite_spirally_wheel_with_julia_islands.jpg)




朱利亚集合可以由下式进行反复迭代得到：

    f_c(z)= z^2 + c

对于固定的复数c，取某一z值（如z=z_0），可以得到序列

    z_0, f_c(z_0), f_c(f_c(z_0)), f_c(f_c(f_c(z_0))), \ldots .

这一序列可能发散于无穷大或始终处于某一范围之内并收敛于某一值。我们将使其不扩散的z值的集合称为朱利亚集合。

![](https://upload.wikimedia.org/wikipedia/commons/9/9a/Julia_set_%28Rev_formula_02%29.jpg)

![](https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Julia_set_%28highres_02%29.jpg/1280px-Julia_set_%28highres_02%29.jpg)
