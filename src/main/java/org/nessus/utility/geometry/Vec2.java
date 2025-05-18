package org.nessus.utility.geometry;

/**
 * A Vec2 osztály egy 2D vektort reprezentál.
 * Tartalmazza az x és y komponenseket, valamint különböző műveleteket a vektorokkal való számításokhoz.
 */
public class Vec2 {
    public double x; // Az x komponens

    public double y; // Az y komponens

    /**
     * Létrehoz egy új vektort a megadott komponensekkel.
     * 
     * @param x Az x komponens
     * @param y Az y komponens
     * @return void
     */
    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Létrehoz egy új vektort két pont között.
     * 
     * @param origin A kezdőpont
     * @param target A célpont
     */
    public Vec2(Point2 origin, Point2 target) {
        x = target.x - origin.x;
        y = target.y - origin.y;
    }

    /**
     * Kiszámítja a skaláris szorzatot egy másik vektorral.
     * 
     * @param u A másik vektor
     * @return double - A skaláris szorzat eredménye
     */
    public double Dot(Vec2 u) {
        return u.x * x + u.y * y;
    }

    /**
     * Skálázza a vektort egy skalárral.
     * 
     * @param scalar A skálázó tényező
     * @return Vec2 - Az új, skálázott vektor
     */
    public Vec2 Scale(double scalar) {
        return new Vec2(x * scalar, y * scalar);
    }

    /**
     * Kiszámítja a vektor hosszát.
     * 
     * @return double - A vektor hossza
     */
    public double Length() {
        return Math.sqrt(this.Dot(this));
    }

    /**
     * Elforgatja a vektort a megadott szöggel.
     * 
     * @param angleRad A forgatási szög radiánban
     * @return Vec2 - Az új, elforgatott vektor
     */
    public Vec2 Rotate(double angleRad) {
        var _x = x * Math.cos(angleRad) - y * Math.sin(angleRad);
        var _y = x * Math.sin(angleRad) + y * Math.cos(angleRad);
        return new Vec2(_x, _y);
    }

    /**
     * Normalizálja a vektort (egységvektorrá alakítja).
     * 
     * @return Vec2 - Az új, normalizált vektor
     */
    public Vec2 Normalize() {
        var length = Length();
        return new Vec2(x / length, y / length);
    }
    
    /**
     * Kivonja a másik vektort ebből a vektorból.
     * 
     * @param other A kivonandó vektor
     * @return Vec2 - Az eredmény vektor
     */
    public Vec2 Subtract(Vec2 other){
        return new Vec2(x - other.x, y - other.y);
    }
}
