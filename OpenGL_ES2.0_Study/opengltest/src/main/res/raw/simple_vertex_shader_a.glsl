uniform mat4 u_Matrix;
attribute vec3 a_Position;
varying vec3 vPosition;
void main(){
    gl_Position=u_Matrix * vec4(a_Position,1.0);
    vPosition=a_Position;
    gl_PointSize=10.0;
}