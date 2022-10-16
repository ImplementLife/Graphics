package com.impllife.my.lib.draw;

import com.impllife.my.lib.disposed.DisposeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextureFabric {
    @Autowired
    private DisposeManager disposeManager;

    public Texture get(String name) {
        Texture texture = new Texture();
        texture.load("resources/textures/" + name);
        return texture;
    }
}
