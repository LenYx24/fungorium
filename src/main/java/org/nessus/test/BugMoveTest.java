package org.nessus.test;

import org.nessus.test.base.BugsTest;

public class BugMoveTest extends BugsTest {
    public BugMoveTest(String name) {
        super(name);
    }

    @Override
    public void Run() {
        bug.Move(t2);
    }
}
