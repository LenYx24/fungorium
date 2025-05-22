package org.nessus.test;

import org.nessus.test.base.ShroomLargeTest;

public class ProduceSporeMaterialTest extends ShroomLargeTest {
    public ProduceSporeMaterialTest(String name) {
        super(name);
    }

    @Override
    public void Run() {
        body.ProduceSpore();
    }
}
