package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.*;

public class SplitTectonTest extends Test
{
    Tecton t1;
    Tecton t2;
    Shroom shroom;
    ShroomBody body;
    ShroomThread thread;
    Spore spore;
    Bug bug1;
    Bug bug2;

    public SplitTectonTest(String name) { super(name); }

    @Override
    public void Init()
    {
        t1 = new Tecton();
        t2 = new Tecton();
        shroom = new Shroom();
        body = new ShroomBody(shroom, t1);
        thread = new ShroomThread(shroom, t1, t2);
        spore = new Spore(shroom, t1);
        bug1 = new Bug();
        bug2 = new Bug();

        Skeleton.AddObject(t1, "tekton1");
        Skeleton.AddObject(t2, "tekton2");
        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(shroom.GetActionPointCatalog(), "shroomCat");
        Skeleton.AddObject(body, "shroombody");
        Skeleton.AddObject(thread, "shroomthread");
        Skeleton.AddObject(spore, "spore");
        Skeleton.AddObject(bug1, "bug1");
        Skeleton.AddObject(bug1.GetActionPointCatalog(), "bugCat");
        Skeleton.AddObject(bug2, "bug2");
        Skeleton.AddObject(bug2.GetActionPointCatalog(), "bugCat");

        t1.AddBug(bug1);
        t1.AddBug(bug2);
        t1.GrowShroomBody(body);
        t1.ThrowSpore(spore);
        t1.GrowShroomThread(thread);
        t2.GrowShroomThread(thread);
        bug1.SetTecton(t1);
        bug2.SetTecton(t1);
        t1.AddNeighbour(t2);
        t2.AddNeighbour(t1);
    }

    @Override
    public void Run() {
        t1.Split();
    }
}
