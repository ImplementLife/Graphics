package com.impllife.my;

import com.impllife.my.control.ControlService;
import com.impllife.my.lib.draw.Texture;
import com.impllife.my.lib.draw.TextureFabric;
import com.impllife.my.lib.math.V2d;
import com.impllife.my.mouse.Mouse;
import com.impllife.my.mouse.MouseFabric;
import org.joml.Vector2f;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

@Component
@Scope("singleton")
public class DrawService {
    @Autowired
    private ControlService controlService;
    public Window window;
    public Mouse mouse;
    public Vector2f pos = new Vector2f();

    public void start() {
        if (!glfwInit()) {
            throw new IllegalStateException("Fail init GJFW");
        }
        window = new Window();
        glEnable(GL_TEXTURE_2D);

        TextureFabric textureFabric = new TextureFabric();
        Texture texture = textureFabric.get("C:\\Users\\ImplementLife\\Desktop\\temp\\save matvey desktop\\Projects\\1F EDU\\LWJGL edu\\textures\\00.png");

        mouse = MouseFabric.getInstance().getMouse(window.getId());
        while (!glfwWindowShouldClose(window.getId())) {
            controlService.checkKey();
            glfwPollEvents();

//            updateMousePos();
            glClear(GL_COLOR_BUFFER_BIT);
            texture.bind();
            glBegin(GL_QUADS);
                glTexCoord2f(0,0);
                glVertex2f(-0.5f + pos.x, 0.5f + pos.y);

                glTexCoord2f(1,0);
                glVertex2f(0.5f + pos.x, 0.5f + pos.y);

                glTexCoord2f(1,1);
                glVertex2f(0.5f + pos.x, -0.5f + pos.y);

                glTexCoord2f(0,1);
                glVertex2f(-0.5f + pos.x, -0.5f + pos.y);
            glEnd();

            glfwSwapBuffers(window.getId());
        }

        glfwTerminate();
    }

    private void updateMousePos() {
        V2d pos = mouse.getPos();
        System.err.println("x=" + pos.x + " y=" + pos.y);
        glfwPollEvents();
    }
}
