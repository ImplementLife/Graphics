package com.impllife.my;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

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
    }
}
