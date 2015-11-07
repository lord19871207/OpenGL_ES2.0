uniform mat4 u_Matrix;
uniform mat4 uMMatrix;
uniform vec3 uLightLocation;
attribute vec3 a_Normal;
varying vec4 vDiffuse;
attribute vec3 a_Position;
varying vec3 vPosition;
varying vec4 vAmbient;

void pointLight(in vec3 normal,inout vec4 diffuse,in vec3 lightLocation,in vec4 lightDiffuse){
    vec3 normalTarget=a_Position+normal;
    vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(a_Position,1)).xyz;//计算出变换后的法向量

    newNormal=normalize(newNormal);//归一化向量
    //计算表面点到光源的向量
    vec3 vp=normalize(lightLocation-(uMMatrix*vec4(a_Position,1)).xyz);
    vp=normalize(vp);
    float nDotViewPosition=max(0.0,dot(newNormal,vp));
    diffuse=lightDiffuse*nDotViewPosition;
}
void main(){
    gl_Position=u_Matrix * vec4(a_Position,1.0);
    vec4 diffuseTemp=vec4(0.0,0.0,0.0,0.0);
    pointLight(normalize(a_Normal),diffuseTemp,uLightLocation,vec4(0.8,0.8,0.8,1.0));
    vDiffuse=diffuseTemp;
    vPosition=a_Position;
}