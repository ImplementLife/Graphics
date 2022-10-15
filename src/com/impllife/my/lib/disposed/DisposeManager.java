package com.impllife.my.lib.disposed;

import java.util.LinkedList;

public class DisposeManager {
    private static DisposeManager disposeManager;
    public static DisposeManager getDisposeManager() {
        if (disposeManager == null) disposeManager = new DisposeManager();
        return disposeManager;
    }
    private DisposeManager() {}

    private final LinkedList<Disposable> disposableList = new LinkedList<>();

    public void disposeAll() {
        disposableList.forEach(Disposable::dispose);
        disposableList.clear();
    }

    public void put(Disposable disposable) {
        disposableList.push(disposable);
    }
}
