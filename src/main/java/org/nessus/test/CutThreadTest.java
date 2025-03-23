package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.Bug;
import org.nessus.model.Shroom;
import org.nessus.model.ShroomThread;
import org.nessus.model.Tecton;

public class CutThreadTest extends Test {
    Bug bug;

    Tecton t1;
    Tecton t2;

    Shroom shroom;
    ShroomThread thread;

    public CutThreadTest(String name) {
        super(name);
    }

    @Override
    public void Init() {
        bug = new Bug();

        t1 = new Tecton();
        t2 = new Tecton();

        shroom = new Shroom();
        thread = new ShroomThread(shroom, t1, t2);

        Skeleton.AddObject(bug, "bug");
        Skeleton.AddObject(bug.GetActionPointCatalog(), "actCat");

        Skeleton.AddObject(t1, "t1");
        Skeleton.AddObject(t2, "t2");

        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(thread, "thread");

        bug.SetTecton(t2);

        t1.SetNeighbour(t2);
        t1.GrowShroomThread(thread);

        t2.SetNeighbour(t1);
        t2.GrowShroomThread(thread);
    }

    @Override
    public void Run() {bug.CutThread(thread);}
}
