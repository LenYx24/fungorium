package org.nessus.test.base;

import org.nessus.Skeleton;
import org.nessus.model.Bug;
import org.nessus.model.Shroom;
import org.nessus.model.ShroomThread;
import org.nessus.model.Spore;
import org.nessus.model.Tecton;

/**
 * Alap osztály a bogarak teszteléséhez.
 * A teszteknek implementálniuk kell a Run() metódust.
 * Minden tesztnek van egy neve, amely megegyezik a szekvenciadiagrammok neveivel. Mindegyik teszt egy szekvenciadiagrammot valósít meg.
 * A Test osztály alapján, a rovaros tesztek alapjait adja.
 */
public abstract class BugsTest extends Test {
    protected Tecton t1; // Az egyik tekton
    protected Tecton t2; // A másik tekton
    protected Bug bug; // A rovar
    protected Shroom shroom; // A gomba
    protected ShroomThread thread; // A gomba fonala
    protected Spore spore; // A spóra

    /**
     * Konstruktor
     * @param name - A teszt neve
     * @return - A teszt objektum
     */
    protected BugsTest(String name) {
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
        bug = new Bug(t1);
        thread = new ShroomThread(shroom, t1, t2);
        spore = new Spore(shroom, t1);

        Skeleton.AddObject(bug, "bug");
        Skeleton.AddObject(t1, "t1");
        Skeleton.AddObject(t2, "t2");
        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(thread, "thread");
        Skeleton.AddObject(spore, "spore");

        t1.SetNeighbour(t2);
        t2.SetNeighbour(t1);
        
        t1.ThrowSpore(spore);
        
        t1.AddBug(bug);
        
        t2.GrowShroomThread(thread);
        t1.GrowShroomThread(thread);

        shroom.SetSpore(spore);
        shroom.SetShroomThread(thread);
    }

    /**
     * A teszt végrehajtása
     * @return - void
     */
    @Override
    public abstract void Run();
}
