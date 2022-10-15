package com.impllife.my.mouse;

import java.util.HashMap;

public final class MouseFabric {
    private static MouseFabric instance;
    public static MouseFabric getInstance() {
        if (instance == null) instance = new MouseFabric();
        return instance;
    }
    private MouseFabric() {}

    private final HashMap<Long, Mouse> mousses = new HashMap<>();

    public Mouse getMouse(long windowID) {
        Mouse mouse = mousses.get(windowID);
        if (mouse == null) {
            mouse = new Mouse(windowID);
            mousses.put(windowID, mouse);
        }
        return mouse;
    }
}
