package com.impllife.my;

import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Window {
    private long windowId;
    public Window() {
        createWindow();
    }

    public long getId() {
        return windowId;
    }

    private void createWindow() {
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

//        windowId = glfwCreateWindow(1920, 1080, "MyStrategy", glfwGetPrimaryMonitor(), NULL);
        windowId = glfwCreateWindow(600, 300, "MyStrategy", 0, 0);
        if (windowId == 0) {
            throw new IllegalStateException("Failed to create Window");
        }
        glfwMakeContextCurrent(windowId);
        glfwSetKeyCallback(windowId, new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action != GLFW_RELEASE) return;
                if (key == GLFW_KEY_ESCAPE) glfwSetWindowShouldClose(window, true);
            }
        });
        createCapabilities();
        setIcon();
    }
    private void setIcon() {
        GLFWImage image = GLFWImage.malloc();
        GLFWImage.Buffer imageBuffer = GLFWImage.malloc(1);
        ImageParser imageParser = new ImageParser("resources/IL.png");
        image.set(imageParser.getWidth(), imageParser.getHeight(), imageParser.getImage());
        imageBuffer.put(0, image);
        glfwSetWindowIcon(windowId, imageBuffer);
    }
    private static class ImageParser {
        private ByteBuffer image;
        private int width, height;

        ImageParser(String path) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer comp = stack.mallocInt(1);
                IntBuffer w = stack.mallocInt(1);
                IntBuffer h = stack.mallocInt(1);

                image = stbi_load(path, w, h, comp, 4);
                if (image == null) {
                    throw new IllegalStateException();
                }
                width = w.get();
                height = h.get();
            }
        }

        public ByteBuffer getImage() {
            return image;
        }
        public int getWidth() {
            return width;
        }
        public int getHeight() {
            return height;
        }
    }
}
