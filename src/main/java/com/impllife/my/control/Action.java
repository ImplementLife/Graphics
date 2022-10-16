package com.impllife.my.control;

import java.util.HashMap;
import java.util.Objects;

public class Action {
    private String name;
    private boolean onHold;
    private boolean onPress;
    private int[][] keysId;
    private final Runnable exe;
    private final HashMap<Integer, Key> keys;

    Action(int[][] keysId, Runnable exe, String name, boolean onHold, boolean onPress, HashMap<Integer, Key> keys) {
        this.keysId = Objects.requireNonNull(keysId);
        this.exe = Objects.requireNonNull(exe);
        this.onHold = onHold;
        this.name = name;
        this.keys = keys;
    }

    int[][] getKeysId() {
        return keysId;
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
