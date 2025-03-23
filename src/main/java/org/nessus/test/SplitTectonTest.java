package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.*;
import org.nessus.test.base.Test;

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
        bug = new Bug(t1);

        Skeleton.AddObject(t1, "t1");
        Skeleton.AddObject(t2, "t2");
        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(shroom.GetActionPointCatalog(), "shroomCat");
        Skeleton.AddObject(body, "body");
        Skeleton.AddObject(thread, "thread");
        Skeleton.AddObject(spore, "spore");
        Skeleton.AddObject(bug, "bug");
        Skeleton.AddObject(bug.GetActionPointCatalog(), "bugCat");

        t1.SetShroomBody(body);
        t1.SetNeighbour(t2);
        t1.GrowShroomThread(thread);
        
        t2.SetNeighbour(t1);
        t2.GrowShroomThread(thread);

        t1.AddBug(bug);
        t1.ThrowSpore(spore);

        shroom.SetSpore(spore);
        shroom.SetShroomBody(body);
        shroom.SetShroomThread(thread);
    }

    @Override
    public void Run() {
        t1.Split();
    }
}
