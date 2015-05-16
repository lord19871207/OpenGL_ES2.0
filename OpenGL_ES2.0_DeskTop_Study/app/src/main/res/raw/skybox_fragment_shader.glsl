precision mediump float;

uniform samplerCube u_TextureUnit;
 varying vec3 v_Position;

void main() {
    gl_fragColor =textureCube(u_TextureUnit,v_Position);
}