package org.nessus.test;

import org.nessus.model.Bug;
import org.nessus.model.ShroomThread;
import org.nessus.model.Tecton;

public class BugEatTest extends Test {
    Bug bug;
    Tecton tecton1;
    Tecton tecton2;
    ShroomThread thread;

    public BugEatTest(String name) {
        super(name);
    }

    @Override
    public void Init() {
        bug = new Bug();
        tecton1 = new Tecton();
        tecton2 = new Tecton();
        thread = new ShroomThread(tecton1, tecton2);

        tecton1.AddNeighbour(tecton2);
        tecton2.AddNeighbour(tecton1);
    }

    @Override
    public void Run() {
    }
}
