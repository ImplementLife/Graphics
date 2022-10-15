package com.impllife.my.lib.draw;

import com.impllife.my.lib.disposed.DisposeManager;

public class TextureFabric {
    private DisposeManager disposeManager;

    public TextureFabric(DisposeManager disposeManager) {
        this.disposeManager = disposeManager;
    }

    public Texture get(String name) {
        Texture texture = new Texture();
        texture.load(name);
        if (disposeManager != null) disposeManager.put(texture);
        return texture;
    }
}
