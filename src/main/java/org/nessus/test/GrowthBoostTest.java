package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.*;

public class GrowthBoostTest extends Test
{
    Tecton t1;
    Tecton t2;
    Shroom shroom;
    ShroomBody body;
    ShroomThread thread;
    Spore spore;

    public GrowthBoostTest(String name) { super(name); }

    @Override
    public void Init()
    {
        t1 = new Tecton();
        t2 = new Tecton();
        shroom = new Shroom();
        body = new ShroomBody(shroom, t1);
        thread = new ShroomThread(shroom, t1, t2);
        spore = new Spore(shroom, t1);

        Skeleton.AddObject(t1, "tekton1");
        Skeleton.AddObject(t2, "tekton2");
        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(shroom.GetActionPointCatalog(), "actCatalog");
        Skeleton.AddObject(body, "shroombody");
        Skeleton.AddObject(thread, "shroomthread");
        Skeleton.AddObject(spore, "spore");

        t1.GrowShroomBody(body);
        t1.ThrowSpore(spore);
        t1.GrowShroomThread(thread);
        t2.GrowShroomThread(thread);
        t1.AddNeighbour(t2);
        t2.AddNeighbour(t1);
    }

    @Override
    public void Run() {
        thread.ValidateLife();
    }
}
