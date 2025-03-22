package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.*;
import org.nessus.model.tecton.*;

public class SingleThreadTectonTest extends Test
{
    DesertTecton dt;
    SingleThreadTecton stt;
    InfertileTecton it;
    Shroom shroom;
    ShroomThread thread1;
    ShroomThread thread2;

    public SingleThreadTectonTest(String name) { super(name); }

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
        Skeleton.AddObject(stt, "stt");
        Skeleton.AddObject(it, "it");
        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(shroom.GetActionPointCatalog(), "actCatalog");
        Skeleton.AddObject(thread1, "thread1");
        Skeleton.AddObject(thread2, "thread2");

        dt.GrowShroomThread(thread1);
        it.GrowShroomThread(thread1);
        it.GrowShroomThread(thread2);
        stt.GrowShroomThread(thread2);

        dt.AddNeighbour(it);
        it.AddNeighbour(dt);

        it.AddNeighbour(stt);
        stt.AddNeighbour(it);

        dt.AddNeighbour(stt);
        stt.AddNeighbour(dt);
    }

    @Override
    public void Run()
    {
        shroom.PlaceShroomThread(it, stt);
    }
}
