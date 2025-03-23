package org.nessus.test.base;

import org.nessus.Skeleton;
import org.nessus.model.Bug;
import org.nessus.model.Shroom;
import org.nessus.model.ShroomThread;
import org.nessus.model.Spore;
import org.nessus.model.Tecton;

public abstract class BugsTest extends Test {
    protected Tecton t1;
    protected Tecton t2;
    protected Bug bug;
    protected Shroom shroom;
    protected ShroomThread thread;
    protected Spore spore;

    protected BugsTest(String name) {
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

        shroom.SetSpore(spore);
        shroom.SetShroomThread(thread);
    }
    
    @Override
    public abstract void Run();
}
