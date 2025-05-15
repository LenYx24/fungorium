package org.nessus.utility;

public class Vec2 {
    public double x;
    public double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(Point origin, Point target) {
        x = target.x - origin.x;
        y = target.y - origin.y;
    }

    public double Dot(Vec2 u) {
        return u.x * x + u.y * y;
    }

    public Vec2 Scale(double scalar) {
        return new Vec2(x * scalar, y * scalar);
    }

    public double Length() {
        return Math.sqrt(this.Dot(this));
    }

    public Vec2 Rotate(double angleRad) {
        var _x = x * Math.cos(angleRad) - y * Math.sin(angleRad);
        var _y = x * Math.sin(angleRad) + y * Math.cos(angleRad);
        return new Vec2(_x, _y);
    }

    public Vec2 Normalize() {
        var length = Length();
        return new Vec2(x / length, y / length);
    }
}
