#version 120

uniform sampler2D sampler;

varying vec2 text_coords;

void main() {
    gl_FragColor = texture2D(sampler, text_coords);
}