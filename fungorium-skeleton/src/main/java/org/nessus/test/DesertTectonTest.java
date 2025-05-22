package org.nessus.test;

import org.nessus.test.base.TectonTypeTest;

public class DesertTectonTest extends TectonTypeTest{
    public DesertTectonTest(String name) {
        super(name);
    }

    @Override
    public void Run() {
        for (int i = 0; i < 3; i++)
            dt.UpdateTecton();
    }
}
