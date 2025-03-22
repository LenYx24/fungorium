package org.nessus.test;

import org.nessus.Skeleton;
import org.nessus.model.*;

public class ShroomBodyDeathTest extends Test {
    Tecton t1;
    Shroom shroom;
    ShroomBody body;

    public ShroomBodyDeathTest(String name) {
        super(name);
    }
    
        @Override
    public void Init() {
        t1 = new Tecton();
        shroom = new Shroom();
        body = new ShroomBody(shroom, t1);

        Skeleton.AddObject(t1, "t1");
        Skeleton.AddObject(shroom, "shroom");
        Skeleton.AddObject(body, "body");

        t1.SetShroomBody(body);
        shroom.SetShroomBody(body);
        body.SetRemainingThrows(1);
        body.SetSporeMaterials(2);
    }

    @Override
    public void Run() {
       shroom.ThrowSpore(body, t1);
    }
    
}
