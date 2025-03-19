package org.nessus.test;

public abstract class Test {
    private String name;
    public Test(String name) {
        this.name = name;
    }
    public abstract void Run();
}
