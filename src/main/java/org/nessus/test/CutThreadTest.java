package org.nessus.test;

import org.nessus.test.base.BugsTest;

public class CutThreadTest extends BugsTest {
    public CutThreadTest(String name) {
        super(name);
    }

    @Override
    public void Run() {
        bug.CutThread(thread);
    }
}
