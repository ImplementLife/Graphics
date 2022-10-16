package com.impllife.my.lib.draw;

import com.impllife.my.lib.disposed.DisposeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShaderFabric {
    @Autowired
    private DisposeManager disposeManager;

    public Shader getShader() {
        Shader shader = new Shader("shader");
        disposeManager.put(shader);
        return shader;
    }
}
