package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.Bug;
import org.nessus.model.Shroom;
import org.nessus.model.ShroomThread;
import org.nessus.model.Spore;
import org.nessus.model.Tecton;
import org.nessus.test.base.Test;

public class CutThreadTest extends Test {
    Tecton t1;
    Tecton t2;
    
    Shroom shroom;
    Bug bug;
    
    ShroomThread thread;
    Spore spore;

    public CutThreadTest(String name) {
        super(name);
    }

    @Override
    public void Init() {
        t1 = new Tecton();
        t2 = new Tecton();
        shroom = new Shroom();
        bug = new Bug(t1);
        thread = new ShroomThread(shroom, t1, t2);
        spore = new Spore(shroom, t1);

        Skeleton.AddObject(bug, "bug");
        Skeleton.AddObject(bug.GetActionPointCatalog(), "actCat");
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

        // A komm diagramon van ilyen hívás, viszont fent meg a konstruktornál
        // már beállítjuk, hogy melyik tektonon legyen. Melyik marad?
        spore.SetTecton(t1);
        shroom.SetSpore(spore);

        // ShoomThreadnél ugyanez, ott most kellenek a SetTecton hívások???
        // Ezeket a komm diagramon is javítani kell!!!!!
        thread.SetTecton1(t1);
        thread.SetTecton2(t2);
    }

    @Override
    public void Run() {
        bug.CutThread(thread);
    }
}
