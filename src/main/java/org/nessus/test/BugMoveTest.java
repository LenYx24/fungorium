package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.Bug;
import org.nessus.model.ShroomThread;
import org.nessus.model.Tecton;

public class BugMoveTest extends Test {
    Bug bug;
    Tecton tecton1;
    Tecton tecton2;
    ShroomThread thread;

    public BugMoveTest(String name) {
        super(name);
    }

    @Override
    public void Init() {
        bug = new Bug();
        tecton1 = new Tecton();
        tecton2 = new Tecton();
        thread = new ShroomThread(tecton1, tecton2);

        Skeleton.AddObject(bug, "bug");
        Skeleton.AddObject(tecton1, "tecton1");
        Skeleton.AddObject(tecton2, "tecton2");
        Skeleton.AddObject(thread, "thread");

        bug.SetTecton(tecton1);
        tecton1.AddNeighbour(tecton2);
        tecton2.AddNeighbour(tecton1);
    }

    @Override
    public void Run() {
        bug.Move(tecton2);
    }
}
