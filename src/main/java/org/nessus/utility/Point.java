package org.nessus.utility;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point Translate(Vec2 v) {
        return new Point(x + v.x, y + v.y);
    }
}
