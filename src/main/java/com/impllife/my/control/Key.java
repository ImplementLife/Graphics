package com.impllife.my.control;

public class Key {
    private int id;
    private String name;
    private boolean isPress;
    private boolean statusChanged;

    Key(int id, String name) {
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
