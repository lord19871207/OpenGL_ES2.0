# EGL详解
OpenGL ES的javax.microedition.khronos.opengles 包定义了平台无关的GL绘图指令，
EGL(javax.microedition.khronos.egl )
则定义了控制displays ,contexts 以及surfaces 的统一的平台接口。


![](http://www.imobilebbs.com/wordpress/wp-content/uploads/2011/06/20110619002.png)

    Display(EGLDisplay) 是对实际显示设备的抽象。
    Surface（EGLSurface）是对用来存储图像的内存区域FrameBuffer的抽象，包括Color Buffer, Stencil Buffer ,Depth Buffer.
    Context (EGLContext) 存储OpenGL ES绘图的一些状态信息。

使用EGL的绘图的一般步骤：

    获取EGLDisplay对象
    初始化与EGLDisplay 之间的连接。
    获取EGLConfig对象
    创建EGLContext 实例
    创建EGLSurface实例
    连接EGLContext和EGLSurface.
    使用GL指令绘制图形
    断开并释放与EGLSurface关联的EGLContext对象
    删除EGLSurface对象
    删除EGLContext对象
    终止与EGLDisplay之间的连接。

一般来说在Android平台上开发OpenGL ES应用，无需直接使用javax.microedition.khronos.egl 包中的类按照上述步骤来使用OpenGL ES绘制图形，在Android平台中提供了一个android.opengl 包，类GLSurfaceView提供了对Display,Surface,Context 的管理，大大简化了OpenGL ES的程序框架,对应大部分OpenGL ES开发，只需调用一个方法来设置OpenGLView用到的GLSurfaceView.Renderer。
