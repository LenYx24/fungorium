package org.nessus.test.base;

public abstract class Test {
    private String name;

    protected Test(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public abstract void Init();

    public abstract void Run();
}
