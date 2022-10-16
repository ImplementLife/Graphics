#version 120

attribute vec3 vertices;
attribute vec2 textures;

varying vec2 text_coords;

void main() {
    text_coords = textures;
    gl_Position = vec4(vertices, 1);
}