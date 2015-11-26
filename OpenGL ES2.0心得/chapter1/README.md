# 我对OpenGL ES2.0的理解
####（1）OpenGL ES2.0是什么？
OpenGL ES2.0是一套针对移动设备的应用程序接口，对于程序员而言他其实就是一个API文档。只不过这套API是专门针对图形硬件的。它在Android平台上的调用是依靠 客户端-服务端的形式体现的。Android系统本身内置的OpenGL实现是服务端，我们自己编写的Android程序是客户端。客户端负责提交命令，由服务端转化为硬件能接受的命令，最终执行并产生图像内容。


####（2）为什么要用OpenGL ES2.0呢？
首先因为android设备本身默认的skia图形绘制只支持2D和少量3D，想要做出绚丽的3D效果必须得借助OpenGL。也许有人会说，在android中使用camera不也能绘制出3D效果么，对此我只能说：Camera只是对OpenGL做了一个封装，它本身仍然是属于OpenGL范畴。
其次OpenGL直接操作native层的内存。在android上会有更高的运行效率。而且由于OpenGL的绘制是在在主线程之外的另一条线程里操作，所以会更加流畅，不会阻塞主线程。

####（3）怎么在android上使用OpenGL ES2.0呢？
为了能适应各种硬件平台，所以OpenGL ES2.0并不包含任何执行窗口任务或者处理用户输入的函数，我们需要通过应用程序本身所运行的窗口系统来提供相应操作才能处理这些操作。比如说：android手机上，我们想要调用OpenGL的话就必须得使用GLSurfaceView来渲染图形。

Android为我们提供了一个专门的用在3D画图上的 GLSurfaceView。这个类被放在一个单独的包android.opengl里面，其中实现了其他View所不具备的操作：

(1) 具有OpenGL|ES调用过程中的错误跟踪，检查工具，这样就方便了Opengl编程过程的debug ；

(2) 所有的画图是在一个专门的Surface上进行，这个Surface可以最后被组合到android的View体系中 ；

(3) 它可以根据EGL的配置来选择自己的buffer类型，比如RGB565，depth＝16 。

(4) 所有画图的操作都通过render来提供，而且render对Opengl的调用是在一个单独的线程中，可以获得更加平滑的动画效果。

(5) Opengl的运行周期与Activity的生命周期可以协调

因为GLSurfaceView可以为自己创建一个窗口，并在视图（View Hierarchy）层次上穿个洞让底层的OpenGL
surface显示出来。对于大多数情况下这就足够了。（但是由于GLSurfaceView本身就是window的一部分。所以它没法像其他的View一样进行动画和变形操作。）另外 OpenGL ES2.0也不包含任何表达三维模型，读取图像文件的操作，这个时候，我们需要通过一系列的几何图元（点，线，三角形）来创建三维空间的物体。GLSurfaceView和图元（点线面）将是我们后面讲述的重点。




下面我们再看看利用GLSurface画3D图形的一个典型的过程

(1)  选择你的EGL配置(就是你画图需要的buffer类型) [optional] ：
            setEGLConfigChooser(boolean)
            setEGLConfigChooser(EGLConfigChooser)
            setEGLConfigChooser(int, int, int, int, int, int)

(2) 选择是否需要Debug信息 [optional] :
           setDebugFlags(int)
           setGLWrapper(GLSurfaceView.GLWrapper).

(3) 为GLSurfaceView注册一个画图的renderer ： setRenderer(GLSurfaceView.Renderer)

(4) 设置reander mode，可以为持续渲染或者根据命令   来渲染,默认是continuous rendering [optional]： setRenderMode(int)
      这里有一个要注意的地方就是必须将Opengl的运行和Activity的生命周期绑定在一起，也就是说Activity pause的时候，opengl的渲染也必须pause。

另外GLSurfaceView还提供了一个非常实用的线程间交互的函数 queueEvent(Runnable)，可以用在主线程和render线程之间的交互，下面就是SDK提供的范例：

     class MyGLSurfaceView extends GLSurfaceView {
     private MyRenderer mMyRenderer;
     public void start() {
         mMyRenderer = ...;
         setRenderer(mMyRenderer);
     }
     public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
             queueEvent(new Runnable() {
                 // This method will be called on the rendering
                 // thread:
                 public void run() {
                     mMyRenderer.handleDpadCenter();
                 }});
             return true;
         }
         return super.onKeyDown(keyCode, event);
      }
      }


 GLSurfaceView是Android提供的一个非常值得学习   的类，它实际上是一个如何在View中添加画图线程的例子，如何在Java   中使用线程的例子，如何添加事件队列的例子，一个使用SurfaceView画图的经典Sequence，一个如何定义Debug信息的例子，觉得把它看懂了可以学到很多知识   ，具体的源码在：/framworks/base/opengl/java/android/opengl/GLSurfaceView.java 。

