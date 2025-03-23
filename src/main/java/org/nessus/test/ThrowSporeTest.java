package org.nessus.test;

import org.nessus.test.base.ShroomLargeTest;

public class ThrowSporeTest extends ShroomLargeTest {
    public ThrowSporeTest(String name) {
        super(name);
    }

    @Override
    public void Run() {
        shroom.ThrowSpore(body, t1);
    }
}
