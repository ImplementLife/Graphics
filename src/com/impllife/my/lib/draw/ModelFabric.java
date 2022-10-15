package com.impllife.my.lib.draw;

import com.impllife.my.lib.disposed.DisposeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelFabric {
    @Autowired
    private DisposeManager disposeManager;

    private final float[] vertices = new float[] {
        -1f, 1f, 0, //TOP LEFT     0
        1f, 1f, 0,  //TOP RIGHT    1
        1f, -1f, 0, //BOTTOM RIGHT 2
        -1f, -1f, 0,//BOTTOM LEFT  3
    };

    private final float[] texture = new float[] {
        0,0,
        1,0,
        1,1,
        0,1,
    };

    private final int[] indices = new int[] {
        0,1,2,
        2,3,0
    };

    public Model createModel() {
        Model model = new Model(vertices, texture, indices);
        disposeManager.put(model);
        return model;
    }
}
