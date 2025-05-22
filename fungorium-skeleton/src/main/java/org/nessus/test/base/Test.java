package org.nessus.test.base;

/**
 * Alap osztály a teszteknek.
 * A teszteknek implementálniuk kell a Run() metódust.
 * Minden tesztnek van egy neve, amely megegyezik a szekvenciadiagrammok neveivel. Mindegyik teszt egy szekvenciadiagrammot valósít meg.
 * A teszteknek van egy Init() metódusa, amely inicializálja a teszteléshez szükséges objektumokat.
 * A teszteknek van egy Run() metódusa, amely végrehajtja a tesztet.
 */
public abstract class Test {
    private String name; // A teszt neve

    /**
     * Konstruktor
     * @param name - A teszt neve
     * @return - A teszt objektum
     */
    protected Test(String name) {
        this.name = name;
    }

    /**
     * A teszt nevének lekérdezése
     * @return - A teszt neve
     */
    public String toString() {
        return name;
    }

    /**
     * A teszt inicializálása
     * A teszteléshez szükséges objektumok inicializálása
     * @return - void
     */
    public abstract void Init();

    /**
     * A teszt végrehajtása
     * A tesztelés végrehajtása
     * @return - void
     */
    public abstract void Run();
}
