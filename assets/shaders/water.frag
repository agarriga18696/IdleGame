#version 120
#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform float u_time;
varying vec2 v_texCoord;

void main() {
    vec2 uv = v_texCoord;
    uv.y += sin(uv.x * 10.0 + u_time) * 0.0012; // Pequeñas olas.
    gl_FragColor = texture2D(u_texture, uv);
}
