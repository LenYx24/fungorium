package org.nessus.test;

public abstract class Test {
    private String name;

    public Test(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public abstract void Init();

    public abstract void Run();
}
