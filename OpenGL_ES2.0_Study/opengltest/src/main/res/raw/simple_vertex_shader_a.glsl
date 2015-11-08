uniform mat4 u_MVPMatrix;
uniform mat4 uMMatrix;
uniform vec3 uLightLocation;
uniform vec3 uCamera;
attribute vec3 a_Position;
attribute vec3 a_Normal;
varying vec3 vPosition;
varying vec4 vAmbient;//环境光强度
varying vec4 vDiffuse;//散射光强度
varying vec4 vSpecular;//镜面光强度

void pointLight(in vec3 normal,inout vec4 ambient ,inout vec4 diffuse,inout vec4 specular,
                in vec3 lightLocation,in vec4 lightAmbient,in vec4 lightDiffuse,
                in vec4 lightSpecular){
    ambient=lightAmbient;
    vec3 normalTarget=a_Position+normal;
    vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(a_Position,1)).xyz;//计算出变换后的法向量

    newNormal=normalize(newNormal);//归一化向量
    vec3 eye =normalize(uCamera-(uMMatrix*vec4(a_Position,1)).xyz);
    //计算表面点到光源的向量
    vec3 vp=normalize(lightLocation-(uMMatrix*vec4(a_Position,1)).xyz);
    vp=normalize(vp);
    vec3 halfVector=normalize(vp+eye);
    float shininess = 50.0;
    float nDotViewPosition=max(0.0,dot(newNormal,vp));
    diffuse=lightDiffuse*nDotViewPosition;
    float nDotViewHalfVector=dot(newNormal,halfVector);
    float powerFactor=max(0.0,pow(nDotViewHalfVector,shininess));
    specular=lightSpecular*powerFactor;
}
void main(){
    gl_Position=u_MVPMatrix * vec4(a_Position,1.0);
    vec4 ambientTemp,diffuseTemp,specularTemp;
//    vec4 diffuseTemp=vec4(0.0,0.0,0.0,0.0);
//    pointLight(normalize(a_Normal));
    pointLight(normalize(a_Normal),ambientTemp,diffuseTemp,specularTemp,uLightLocation,vec4(0.15,0.15,0.15,1.0),
    vec4(0.8,0.8,0.8,1.0),vec4(0.7,0.7,0.7,1.0));
    vAmbient=ambientTemp;
    vDiffuse=diffuseTemp;
    vSpecular=specularTemp;
    vPosition=a_Position;
}