package org.nessus.test.base;

import org.nessus.Skeleton;
import org.nessus.model.Shroom;
import org.nessus.model.ShroomBody;
import org.nessus.model.ShroomThread;
import org.nessus.model.Spore;
import org.nessus.model.Tecton;

/**
 * Alap osztály a gomba teszteléséhez.
 * A teszteknek implementálniuk kell a Run() metódust.
 * Minden tesztnek van egy neve, amely megegyezik a szekvenciadiagrammok neveivel. Mindegyik teszt egy szekvenciadiagrammot valósít meg.
 * A Test osztály alapján, a gombás tesztek alapjait adja.
 */
public abstract class ShroomLargeTest extends Test {
    protected Tecton t1; // Az egyik tekton
    protected Tecton t2; // A másik tekton

    protected Shroom shroom; // A gomba
    protected ShroomBody body; // A gomba teste
    protected ShroomThread thread; // A gomba fonala

    protected Spore spore1; // Az egyik spóra
    protected Spore spore2; // A másik spóra
    protected Spore spore3; // A harmadik spóra
    protected Spore spore4; // A negyedik spóra

    /**
     * Konstruktor
     * @param name - A teszt neve
     * @return - A teszt objektum
     */
    protected ShroomLargeTest(String name) {
        super(name);
    }

    /**
     * A teszt inicializálása
     * A teszteléshez szükséges objektumok inicializálása
     * @return - void
     */
    @Override
    public void Init() {
        t1 = new Tecton();
        t2 = new Tecton();

        shroom = new Shroom();
        body = new ShroomBody(shroom, t1);
        thread = new ShroomThread(shroom, t1, t2);

        spore1 = new Spore(shroom, t2);
        spore2 = new Spore(shroom, t2);
        spore3 = new Spore(shroom, t1);
        spore4 = new Spore(shroom, t1);

        Skeleton.AddObject(t1, "t1");
        Skeleton.AddObject(t2, "t2");

        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(body, "body");
        Skeleton.AddObject(thread, "thread");

        Skeleton.AddObject(spore1, "spore1");
        Skeleton.AddObject(spore2, "spore2");
        Skeleton.AddObject(spore3, "spore3");
        Skeleton.AddObject(spore4, "spore4");

        t1.SetShroomBody(body);
        t1.GrowShroomThread(thread);
        t1.SetNeighbour(t2);

        t2.SetNeighbour(t1);
        t2.GrowShroomThread(thread);

        t1.ThrowSpore(spore4);
        t1.ThrowSpore(spore3);

        t2.ThrowSpore(spore2);
        t2.ThrowSpore(spore1);

        shroom.SetShroomBody(body);
        shroom.SetShroomThread(thread);
        shroom.SetSpore(spore1);
        shroom.SetSpore(spore2);
        shroom.SetSpore(spore3);
        shroom.SetSpore(spore4);
    }

    /**
     * A teszt végrehajtása
     * @return - void
     */
    @Override
    public abstract void Run();
}
