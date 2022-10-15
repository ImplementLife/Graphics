package com.impllife.my.lib.math;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector2ic;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * Vector 2 float
 */
public class V2f extends Vector2f {

    //region Constructors

    public V2f() {
    }

    public V2f(float d) {
        super(d);
    }

    public V2f(float x, float y) {
        super(x, y);
    }

    public V2f(Vector2fc v) {
        super(v);
    }

    public V2f(Vector2ic v) {
        super(v);
    }

    public V2f(float[] xy) {
        super(xy);
    }

    public V2f(ByteBuffer buffer) {
        super(buffer);
    }

    public V2f(int index, ByteBuffer buffer) {
        super(index, buffer);
    }

    public V2f(FloatBuffer buffer) {
        super(buffer);
    }

    public V2f(int index, FloatBuffer buffer) {
        super(index, buffer);
    }


    //endregion

    public V2f x(float x) {
        super.x = x;
        return this;
    }

    public V2f y(float y) {
        super.y = y;
        return this;
    }

    public V2f x(double x) {
        return this.x((float) x);
    }

    public V2f y(double y) {
        return this.y((float) y);
    }

}
