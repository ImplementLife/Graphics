package com.impllife.my.lib.math;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import org.joml.Vector2fc;
import org.joml.Vector2ic;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

/**
 * Vector 2 double
 */
public class V2d extends Vector2d {

    //region Constructors

    public V2d() {
    }

    public V2d(double d) {
        super(d);
    }

    public V2d(double x, double y) {
        super(x, y);
    }

    public V2d(Vector2dc v) {
        super(v);
    }

    public V2d(Vector2fc v) {
        super(v);
    }

    public V2d(Vector2ic v) {
        super(v);
    }

    public V2d(double[] xy) {
        super(xy);
    }

    public V2d(float[] xy) {
        super(xy);
    }

    public V2d(ByteBuffer buffer) {
        super(buffer);
    }

    public V2d(int index, ByteBuffer buffer) {
        super(index, buffer);
    }

    public V2d(DoubleBuffer buffer) {
        super(buffer);
    }

    public V2d(int index, DoubleBuffer buffer) {
        super(index, buffer);
    }

    //endregion

    public V2d x(float x) {
        super.x = x;
        return this;
    }

    public V2d y(float y) {
        super.y = y;
        return this;
    }

    public V2d x(double x) {
        super.x = x;
        return this;
    }

    public V2d y(double y) {
        super.y = y;
        return this;
    }

}
