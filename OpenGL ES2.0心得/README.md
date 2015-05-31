# 1.着色器基础
对于程序员而言，着色器就是使用GLSL语言编写的一个小型函数。

#####1.变量的声明
命名规范和C语言相同 可以使用 字母 数字 下划线。但是数字，下划线不能放在首位。变量名不能包含连续的下划线（GLSL保留使用）。
包括以下类型： float ，double，int ，uint ，bool，还有
采样器（sample），图像（image），原子计数器（atomic counter）

#####2.变量的作用域
<1>.任何函数外定义的变量拥有全局作用域，相当于全局变量。
<2>.在一组大括号内声明的变量（if语句，循环，函数内），只能在大括号的范围内存在。
<3>for循环中自迭代的变量例如：for(int i=0;i<10;i++)中的i只能在循环体内起作用。

#####3.变量的初始化
所有变量都需要在声明的同时进行初始化。在数字末尾加u或者U 可以表示一个无符号整数，浮点字面必须包括一个小数点，浮点数也可以在数字结尾加一个f或者F，double类型数字末尾必须加一个“lF”或者“LF”。bool变量可以直接用true和false赋值。也可以用一个bool表达式计算之后赋值。

#####4.构造函数 隐式转换
GLSL中只有少量变量类型支持隐式转换。
<1>uint可以通过int隐式转换 uint i=1;
<2>float可以通过int，uint隐式转换
比如float i=1u;
<3>double可以通过int,uint,float隐式转换,double i=3.0f;

上面这几种类型的标量，向量和矩阵也适用于隐式转换。

其他的数值转换需要提供显式的构造函数
它是一个名称与类型名称相同的函数，返回值就是对应类型的值。例如:
float f=10.0;
int ten=int（f）； 使用了int构造函数


#####5.聚合类型
对基本类型进行合并，形成向量。每个向量都支持所有的基本类型。另外也支持float和double类型的矩阵。

| 基本类型 | 2D向量 | 3D向量 | 4D向量 | 矩阵类型 |
| -- | -- | -- | -- | -- |
| float | vec2 | vec3 | vec4 | mat2  mat3   mat4|
| double | dvec2 | dvec3 | dvec4 | dmat2 dmat3  dmat4 |
| int | ivec2 | ivec3 | ivec4 | 4:4 |
| uint| uvec2 | uvec3| uvec4| 4:5 |
| bool | bvec2 | bvec3 | bvec4 | 4:6 |

向量的构造函数可以用来截短或者加长一个向量，例如：
```
vec4 color；
vec3 RGB=vec3（color）；
vec3 white=vec3(1.0);
vec4 tran=vec4(white,0.5);
```


矩阵的构建方式：
m=mat3(4.0)构建了一个对角线元素为4.0的对角矩阵。

可以通过下面的形式初始化矩阵。
```
mat3 M=(1.0,2.0,3.0,
        4.0, 5.0, 6.0,
        7.0，8.0，9.0)
```

```
vec3 column1=vec3（1.0，2.0，3.0）；
vec3 column2=vec3（1.0，2.0，3.0）；
vec3 column3=vec3（1.0，2.0，3.0）；
mat3 M=mat3(column1,column2,column3);

vec3 column1=vec3（1.0，2.0）；
vec3 column2=vec3（4.0，5.0）；
vec3 column3=vec3（7.0，8.0）；
mat3 M=mat3(column1,3.0,
            column2,6.0,
            column3,9.0);


```

#####6.访问向量和矩阵中的元素
<1>向量支持两种类型的元素访问方式：1.使用分量名称。2.使用数组访问的形式。例如：float red=color.r;

向量的分量访问符：

| 分量访问符 | 符号描述 |
| -- | -- |
| （x,y,z,w） | 与位置相关的分量|
| （r,g,b,a）| 与颜色相关的分量 |
| (s,t,p,q) | 与纹理坐标相关的分量 |


<2>矩阵元素的访问可以通过2纬数组标记的方式
```
mat4 m=mat4(2.0);
vec4 zVec=m(2);//获取矩阵的第二列
float yScale=m[1][1];//相当于m[1].y
```

#####7.结构体
逻辑上将不同数据组合到一个结构体中。
自带一个构造函数，结构体重的元素作为输入参数。
```
struct Particle{
    float lifetime;
    vec3 position;
    vec3 velocity;
}

Particle p=Particle(1.0,pos,vel);
```
需要引用结构体中的元素时直接使用点“.”就可以了。

#####8.数组
#####9.存储限制符


