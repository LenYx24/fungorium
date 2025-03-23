package org.nessus.test;

import org.nessus.test.base.TectonTypeTest;

public class SingleThreadTectonTest extends TectonTypeTest {
    public SingleThreadTectonTest(String name) {
        super(name);
    }

    @Override
    public void Run() {
        shroom.PlaceShroomThread(it, stt);
    }
}
