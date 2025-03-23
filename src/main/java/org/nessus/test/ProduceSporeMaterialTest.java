package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.Shroom;
import org.nessus.model.ShroomBody;
import org.nessus.model.Tecton;

public class ProduceSporeMaterialTest extends Test {
    Shroom shroom;
    ShroomBody body;
    Tecton t1;

    public ProduceSporeMaterialTest(String name) {
        super(name);
    }

    @Override
    public void Init() {
        shroom = new Shroom();
        t1 = new Tecton();
        body = new ShroomBody(shroom, t1);

        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(body, "body");
        Skeleton.AddObject(t1, "t1");
        Skeleton.AddObject(shroom.GetActionPointCatalog(), "actCatalog");

        shroom.SetShroomBody(body);
        body.SetTecton(t1);
        body.SetRemainingThrows(5);
        body.SetSporeMaterials(5);
    }

    @Override
    public void Run() {
        body.ProduceSpore();
    }
}
