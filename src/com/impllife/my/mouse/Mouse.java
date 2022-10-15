package com.impllife.my.mouse;

import com.impllife.my.lib.math.V2d;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class Mouse {
    private final DoubleBuffer xBuffer;
    private final DoubleBuffer yBuffer;
    private final long windowID;

    Mouse(long windowID) {
        this.windowID = windowID;
        xBuffer = BufferUtils.createDoubleBuffer(1);
        yBuffer = BufferUtils.createDoubleBuffer(1);
    }

    public V2d getPos() {
        glfwGetCursorPos(windowID, xBuffer, yBuffer);
        return new V2d(xBuffer.get(0), yBuffer.get(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mouse mouse = (Mouse) o;
        return windowID == mouse.windowID;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(windowID);
    }
}
