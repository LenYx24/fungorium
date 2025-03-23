package org.nessus.test.base;

import org.nessus.Skeleton;
import org.nessus.model.Shroom;
import org.nessus.model.ShroomThread;
import org.nessus.model.tecton.DesertTecton;
import org.nessus.model.tecton.InfertileTecton;
import org.nessus.model.tecton.SingleThreadTecton;

public abstract class TectonTypeTest extends Test {
    protected DesertTecton dt;
    protected SingleThreadTecton stt;
    protected InfertileTecton it;
    protected Shroom shroom;
    protected ShroomThread thread1;
    protected ShroomThread thread2;
    
    protected TectonTypeTest(String name) {
        super(name);
    }

    @Override
    public void Init()
    {
        dt = new DesertTecton();
        stt = new SingleThreadTecton();
        it = new InfertileTecton();
        shroom = new Shroom();
        thread1 = new ShroomThread(shroom, dt, it);
        thread2 = new ShroomThread(shroom, stt, it);

        Skeleton.AddObject(dt, "dt");
        Skeleton.AddObject(stt, "sst");
        Skeleton.AddObject(it, "it");
        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(shroom.GetActionPointCatalog(), "actCatalog");
        Skeleton.AddObject(thread1, "thread1");
        Skeleton.AddObject(thread2, "thread2");

        dt.GrowShroomThread(thread1);
        it.GrowShroomThread(thread1);
        it.GrowShroomThread(thread2);
        stt.GrowShroomThread(thread2);

        dt.SetNeighbour(it);
        it.SetNeighbour(dt);

        it.SetNeighbour(stt);
        stt.SetNeighbour(it);

        dt.SetNeighbour(stt);
        stt.SetNeighbour(dt);
    }

    @Override
    public abstract void Run();
}
