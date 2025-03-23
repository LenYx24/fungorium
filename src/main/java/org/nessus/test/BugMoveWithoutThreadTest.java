package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.Bug;
import org.nessus.model.Shroom;
import org.nessus.model.ShroomThread;
import org.nessus.model.Tecton;

public class BugMoveWithoutThreadTest extends Test {
    Bug bug;
    Tecton t1;
    Tecton t2;
    Shroom shroom;
    ShroomThread thread;

    public BugMoveWithoutThreadTest(String name) {
        super(name);
    }

    @Override
    public void Init() {
        bug = new Bug();
        t1 = new Tecton();
        t2 = new Tecton();
        shroom = new Shroom();

        Skeleton.AddObject(bug, "bug");
        Skeleton.AddObject(bug.GetActionPointCatalog(), "actCat");
        Skeleton.AddObject(t1, "tecton1");
        Skeleton.AddObject(t2, "tecton2");
        Skeleton.AddObject(shroom, "shroom");

        bug.SetTecton(t1);
        t1.SetNeighbour(t2);
        t2.SetNeighbour(t1);

    }

    @Override
    public void Run() {
        bug.Move(t2);
    }
}
