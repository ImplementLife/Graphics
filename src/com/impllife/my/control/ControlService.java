package com.impllife.my.control;

import com.impllife.my.Boot;
import com.impllife.my.DrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

@Component
@Scope("singleton")
public class ControlService {
    @Autowired
    public DrawService drawService;
    private final LinkedList<Action> actions = new LinkedList<>();
    private final HashMap<Integer, Key> keys = new HashMap<>();
    public ControlService() {
        addKey(new Key(GLFW_KEY_W, "W"));
        addKey(new Key(GLFW_KEY_A, "A"));
        addKey(new Key(GLFW_KEY_S, "S"));
        addKey(new Key(GLFW_KEY_D, "D"));
        addKey(new Key(GLFW_KEY_LEFT_CONTROL, "Ctrl"));
        addKey(new Key(GLFW_KEY_UP, "up"));
        addKey(new Key(GLFW_KEY_DOWN, "DOWN"));
        addKey(new Key(GLFW_KEY_LEFT, "LEFT"));
        addKey(new Key(GLFW_KEY_RIGHT, "RIGHT"));

        addAction(new Action(new int[][]{
            {GLFW_KEY_UP, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_W},
        }, () -> drawService.pos.y += 0.05f, "move up", true, false, keys));
        addAction(new Action(new int[][]{
            {GLFW_KEY_DOWN, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_S},
        }, () -> drawService.pos.y -= 0.05f, "move down", true, false, keys));
        addAction(new Action(new int[][]{
            {GLFW_KEY_LEFT, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_A},
        }, () -> drawService.pos.x -= 0.05f, "move left", true, false, keys));
        addAction(new Action(new int[][]{
            {GLFW_KEY_RIGHT, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_D},
        }, () -> drawService.pos.x += 0.05f, "move right", true, false, keys));
    }

    private void addAction(Action action) {
        actions.add(action);
    }

    private void addKey(Key key) {
        keys.put(key.getId(), key);
    }

    public void checkKey() {
        for (Key key : keys.values()) {
            key.setPress(glfwGetKey(drawService.window.getId(), key.getId()) == GL_TRUE);
        }
        for (Action action : actions) {
            action.execute();
        }
        for (Key key : keys.values()) {
            key.resetStatusChanged();
        }
    }
}
