package com.impllife.my;

import com.impllife.my.control.ControlService;
import com.impllife.my.lib.draw.*;
import com.impllife.my.lib.math.V2d;
import com.impllife.my.mouse.Mouse;
import com.impllife.my.mouse.MouseFabric;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
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
    @Autowired
    private DrawFabric drawFabric;
    @Autowired
    private Window window;
    @Autowired
    private Camera camera;

    public Mouse mouse;
    public Vector2f pos = new Vector2f();
    public Vector2f mousePos = new Vector2f();

    public DrawService() {
        if (!glfwInit()) {
            throw new IllegalStateException("Fail init GJFW");
        }
    }

    public void start() {
        window.createWindow(new Vector2i(600, 400));
        camera.init();
        glEnable(GL_TEXTURE_2D);

        Model model = drawFabric.createModel();
        Shader shader = drawFabric.getShader();
        Texture texture = drawFabric.getTexture("01.png");
        Matrix4f pos = new Matrix4f().translate(new Vector3f(5,5,0));

        mouse = MouseFabric.getInstance().getMouse(window.getId());
        while (!glfwWindowShouldClose(window.getId())) {
            controlService.checkKey();
            updateMousePos();
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);

            shader.bind();
            shader.setUniform("sampler", 0);
            shader.setUniform("projection", camera.proj().mul(pos));
            texture.bind(0);
            model.render();

            glfwSwapBuffers(window.getId());
        }
        glfwTerminate();
    }

    private void updateMousePos() {
        mousePos.set(mouse.getPos());
        glfwPollEvents();
    }
}
