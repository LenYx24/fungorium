package org.nessus.test;

import org.nessus.test.base.ShroomLargeTest;

public class PlaceShroomBodyTest extends ShroomLargeTest {
    protected PlaceShroomBodyTest(String name) {
        super(name);
    }
    
        @Override
    public void Run() {
        shroom.PlaceShroomBody(t2);
    }
}
