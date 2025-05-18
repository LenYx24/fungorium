package org.nessus.utility.geometry;

/**
 * A Point osztály egy 2D pontot reprezentál.
 * Tartalmazza az x és y koordinátákat, valamint műveleteket a pontokkal való számításokhoz.
 */
public class Point2 {
    public double x; // Az x koordináta

    public double y; // Az y koordináta

    /**
     * Létrehoz egy új pontot a megadott koordinátákkal.
     * 
     * @param x Az x koordináta
     * @param y Az y koordináta
     */
    public Point2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Eltolja a pontot egy vektor mentén.
     * 
     * @param v Az eltolási vektor
     * @return Point - Az új, eltolt pont
     */
    public Point2 Translate(Vec2 v) {
        return new Point2(x + v.x, y + v.y);
    }
}
