package com.impllife.my.lib.disposed;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
@Scope("singleton")
public class DisposeManager {
    private final LinkedList<Disposable> disposableList = new LinkedList<>();

    public void put(Disposable disposable) {
        disposableList.push(disposable);
    }

    public void disposeAll() {
        disposableList.forEach(Disposable::dispose);
        disposableList.clear();
    }
}
