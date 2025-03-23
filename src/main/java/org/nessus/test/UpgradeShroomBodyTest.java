package org.nessus.test;

import org.nessus.test.base.ShroomLargeTest;

public class UpgradeShroomBodyTest extends ShroomLargeTest {
    public UpgradeShroomBodyTest(String name) {
        super(name);
    }

    @Override
    public void Run() {
        shroom.UpgradeShroomBody(body);
    }
}
