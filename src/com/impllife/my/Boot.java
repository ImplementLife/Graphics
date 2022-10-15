package com.impllife.my;

import com.impllife.my.mouse.Mouse;
import com.impllife.my.mouse.MouseFabric;
import com.impllife.my.lib.math.V2d;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Boot {
    static Window window;
    static Mouse mouse;
    static Vector2f pos = new Vector2f();
    static LinkedList<Action> actions = new LinkedList<>();
    static HashMap<Integer, Key> keys = new HashMap<>();
    static {
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
        }, () -> pos.y += 0.05f, "move up", true, false));
        addAction(new Action(new int[][]{
            {GLFW_KEY_DOWN, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_S},
        }, () -> pos.y -= 0.05f, "move down", true, false));
        addAction(new Action(new int[][]{
            {GLFW_KEY_LEFT, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_A},
        }, () -> pos.x -= 0.05f, "move left", true, false));
        addAction(new Action(new int[][]{
            {GLFW_KEY_RIGHT, GLFW_KEY_LEFT_CONTROL},
            {GLFW_KEY_D},
        }, () -> pos.x += 0.05f, "move right", true, false));
    }

    private static void addAction(Action action) {
        actions.add(action);
    }

    static void addKey(Key key) {
        keys.put(key.getId(), key);
    }

    static class Key {
        private int id;
        private String name;
        private boolean isPress;
        private boolean statusChanged;

        public Key(int id, String name) {
            this.name = name;
            this.id = id;
        }
        public Key(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }
        public boolean isPress() {
            return isPress;
        }
        public int getId() {
            return id;
        }

        public boolean isStatusChanged() {
            return statusChanged;
        }
        public void resetStatusChanged() {
            statusChanged = false;
        }

        public void setPress(boolean press) {
            statusChanged = press != isPress;
            isPress = press;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class Action {
        private String name;
        private boolean onHold;
        private boolean onPress;
        private int[][] keysId;
        private Runnable exe;

        public Action(int[][] keysId, Runnable exe, String name, boolean onHold, boolean onPress) {
            this.keysId = Objects.requireNonNull(keysId);
            this.exe = Objects.requireNonNull(exe);
            this.onHold = onHold;
            this.name = name;
        }

        public void execute() {
            boolean conditionsCompleted;
            for (int[] or : keysId) {
                boolean orConditionsCompleted = true;
                for (int and : or) {
                    Key key = keys.get(and);
                    if (onHold) {
                        if (!key.isPress()) {
                            orConditionsCompleted = false;
                        }
                    } else {
                        if (key.isStatusChanged()) {
                            if (key.isPress()) {
//                                System.err.println(keys.get(id) + " pressed");
                            } else {
//                                System.err.println(keys.get(id) + " released");
                            }
                        }
                    }
                }
                if (orConditionsCompleted) {
                    exe.run();
                    break;
                }
            }
        }
    }

    static void checkKey() {
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

    static void addDrawPoint(Vector2f pos) {
        glVertex2f(pos.x, pos.y);
    }

    public static void main(String[] args) {
        if (!glfwInit()) {
            throw new IllegalStateException("Fail init GJFW");
        }
        window = new Window();
        
        mouse = MouseFabric.getInstance().getMouse(window.getId());
        while (!glfwWindowShouldClose(window.getId())) {
            checkKey();
            glfwPollEvents();

//            Boot.updateMousePos();
            glClear(GL_COLOR_BUFFER_BIT);
            glBegin(GL_QUADS);
                glColor4f(1,0,0,0);
                glVertex2f(-0.5f + pos.x, 0.5f + pos.y);

                glColor4f(0,1,0,0);
                glVertex2f(0.5f + pos.x, 0.5f + pos.y);

                glColor4f(0,0,1,0);
                glVertex2f(0.5f + pos.x, -0.5f + pos.y);

                glColor4f(1,1,1,0);
                glVertex2f(-0.5f + pos.x, -0.5f + pos.y);
            glEnd();

            glfwSwapBuffers(window.getId());
        }

        glfwTerminate();
    }

    static void updateMousePos() {
        V2d pos = mouse.getPos();
        System.err.println("x=" + pos.x + " y=" + pos.y);
        glfwPollEvents();
    }
}
