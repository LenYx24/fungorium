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
    Bug bug;

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
        bug = new Bug();

        Skeleton.AddObject(t1, "tecton1");
        Skeleton.AddObject(t2, "tecton2");
        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(shroom.GetActionPointCatalog(), "shroomCat");
        Skeleton.AddObject(body, "shroombody");
        Skeleton.AddObject(thread, "shroomthread");
        Skeleton.AddObject(spore, "spore");
        Skeleton.AddObject(bug, "bug");
        Skeleton.AddObject(bug.GetActionPointCatalog(), "bugCat");

        t1.AddBug(bug);
        t1.GrowShroomBody(body);
        t1.ThrowSpore(spore);
        t1.GrowShroomThread(thread);
        t2.GrowShroomThread(thread);
        bug.SetTecton(t1);
        t1.AddNeighbour(t2);
        t2.AddNeighbour(t1);
    }

    @Override
    public void Run() {
        t1.Split();
    }
}
