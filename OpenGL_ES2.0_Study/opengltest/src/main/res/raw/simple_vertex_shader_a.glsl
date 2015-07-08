uniform mat4 u_Matrix;
uniform mat4 uMMatrix;
uniform vec3 uLightLocation;
attribute vec4 a_Normal;
varying vec4 vDiffuse;
attribute vec3 a_Position;
varying vec3 vPosition;
varying vec4 vAmbient;

void pointLight(in vec3 normal,inout vec4 diffuse,in vec3 lightLocation,in vec4 lightDiffuse){
    vec3 normalTarget=a_Position+normal;
    
}
void main(){
    gl_Position=u_Matrix * vec4(a_Position,1.0);
    vPosition=a_Position;
    gl_PointSize=10.0;
    vAmbient =vec4(0.8,0.8,0.8,1.0);
}