package com.impllife.my.lib.draw;

import com.impllife.my.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Camera {
    @Autowired
    private Window window;

    @Value("${implement-life.camera.init-scale}")
    private float scale;
    private final Vector3f position = new Vector3f();
    private Matrix4f baseProjection;
    private Matrix4f projection;

    public void init() {
        updateSize();
        setScale(scale);
    }

    public void updateSize() {
        baseProjection = new Matrix4f().ortho2D(-window.getSize().x/2f, window.getSize().x/2f, -window.getSize().y/2f, window.getSize().y/2f);
        projection = new Matrix4f(baseProjection);
    }

    private void setScale(float scale) {
        this.scale = scale;
        projection = new Matrix4f(baseProjection).scale(scale);
    }
    private void addScale(float scale) {
        setScale(this.scale + scale);
    }

    public Matrix4f proj() {
        Matrix4f pos = new Matrix4f().setTranslation(position);
        return projection.mul(pos, new Matrix4f());
    }

    public void setPos(Vector3f pos) {
        position.set(pos);
    }
    public void addPos(Vector3f pos) {
        position.add(pos);
    }
    public void moveToPos(Vector3f pos) {
        position.set(pos);
    }

    public Vector3f getPos() {
        return position;
    }
    public Matrix4f getProj() {
        return projection;
    }

}
