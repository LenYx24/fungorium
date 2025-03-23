package org.nessus.test;

import org.nessus.test.base.BugsTest;

public class BugEatTest extends BugsTest {

    protected BugEatTest(String name) {
        super(name);
    }

    @Override
    public void Run() {
        bug.Eat(spore);
        bug.UpdateBug();
    }
}
