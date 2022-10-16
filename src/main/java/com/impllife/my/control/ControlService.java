package com.impllife.my.control;

import com.impllife.my.DrawService;
import com.impllife.my.Window;
import com.impllife.my.lib.draw.Camera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

@Component
@Scope("singleton")
public class ControlService {
    @Autowired
    public DrawService drawService;
    @Autowired
    private Camera camera;
    @Autowired
    private Window window;

    private final LinkedList<Action> actions = new LinkedList<>();
    private final HashMap<Integer, Key> keys = new HashMap<>();

    @PostConstruct
    private void init() {
        registerAction(new Action(new int[][]{
            {GLFW_KEY_UP, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_W},
        }, () -> camera.getPos().y += 0.05f, "move up", true, false, keys));
        registerAction(new Action(new int[][]{
            {GLFW_KEY_DOWN, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_S},
        }, () -> camera.getPos().y -= 0.05f, "move down", true, false, keys));
        registerAction(new Action(new int[][]{
            {GLFW_KEY_LEFT, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_A},
        }, () -> camera.getPos().x -= 0.05f, "move left", true, false, keys));
        registerAction(new Action(new int[][]{
            {GLFW_KEY_RIGHT, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_D},
        }, () -> camera.getPos().x += 0.05f, "move right", true, false, keys));
        registerUsedKeys();
    }
    private void registerUsedKeys() {
        for (Action action : actions) {
            for (int[] orCases : action.getKeysId()) {
                for (int andKeys : orCases) {
                    registerKey(andKeys);
                }
            }
        }
    }
    private void registerAction(Action action) {
        actions.add(action);
    }
    private void registerKey(int keyId) {
        int scancode = glfwGetKeyScancode(keyId);
        String name = glfwGetKeyName(keyId, scancode);
        Key key = new Key(keyId, name);
        keys.put(key.getId(), key);
    }

    public void checkKey() {
        for (Key key : keys.values()) {
            key.setPress(glfwGetKey(window.getId(), key.getId()) == GL_TRUE);
        }
        for (Action action : actions) {
            action.execute();
        }
        for (Key key : keys.values()) {
            key.resetStatusChanged();
        }
    }
}
