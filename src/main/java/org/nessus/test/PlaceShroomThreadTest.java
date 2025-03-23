package org.nessus.test;


import org.nessus.Skeleton;
import org.nessus.model.Shroom;
import org.nessus.model.ShroomBody;
import org.nessus.model.Tecton;

public class PlaceShroomThreadTest extends Test {

    Tecton t1;
    Tecton t2;

    Shroom shroom;
    ShroomBody body;

    public PlaceShroomThreadTest(String name) { super(name); }

    // A kommunikációs
    @Override
    public void Init() {
        t1 = new Tecton();
        t2 = new Tecton();

        shroom = new Shroom();
        body = new ShroomBody(shroom, t1);


        Skeleton.AddObject(t1, "t1");
        Skeleton.AddObject(t2, "t2");

        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(shroom.GetActionPointCatalog(), "actCat");
        Skeleton.AddObject(body, "body");


        t1.SetShroomBody(body);
        t1.SetNeighbour(t2);

        t2.SetNeighbour(t1);


    }

    @Override
    public void Run() {shroom.PlaceShroomThread(t1, t2);}
}
