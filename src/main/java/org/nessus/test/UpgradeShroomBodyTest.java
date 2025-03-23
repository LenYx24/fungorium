package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.*;

public class UpgradeShroomBodyTest extends Test {
    Tecton t1;
    Tecton t2;
    
    Shroom shroom;
    ShroomBody body;
    ShroomThread thread;
    
    Spore spore1;
    Spore spore2;
    Spore spore3;
    Spore spore4;
    
    public UpgradeShroomBodyTest(String name) {
        super(name);
    }

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

        shroom.SetSpore(spore1);
        shroom.SetSpore(spore2);
        shroom.SetSpore(spore3);
        shroom.SetSpore(spore4);

        t1.SetShroomBody(body);
        t1.GrowShroomThread(thread);
        t1.SetNeighbour(t2);

        t2.SetNeighbour(t1);
        t2.GrowShroomThread(thread);

        t1.ThrowSpore(spore4);
        t1.ThrowSpore(spore3);

        t2.ThrowSpore(spore2);
        t2.ThrowSpore(spore1);
    }

    @Override
    public void Run() {
        shroom.UpgradeShroomBody(body);
    }
}
