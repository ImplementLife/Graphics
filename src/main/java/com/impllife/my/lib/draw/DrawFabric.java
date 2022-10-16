package com.impllife.my.lib.draw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DrawFabric {
    @Autowired
    private ModelFabric modelFabric;
    @Autowired
    private ShaderFabric shaderFabric;
    @Autowired
    private TextureFabric textureFabric;

    public Texture getTexture(String name) {
        return textureFabric.get(name);
    }

    public Shader getShader() {
        return shaderFabric.getShader();
    }

    public Model createModel() {
        return modelFabric.createModel();
    }
}
