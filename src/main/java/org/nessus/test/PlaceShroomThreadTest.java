package org.nessus.test;

import org.nessus.test.base.ShroomLargeTest;

public class PlaceShroomThreadTest extends ShroomLargeTest {
    public PlaceShroomThreadTest(String name) {
        super(name);
    }

    @Override
    public void Run() {
        shroom.PlaceShroomThread(t1, t2);
    }
}
