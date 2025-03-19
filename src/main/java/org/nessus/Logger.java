package org.nessus;

public class Logger {
    private int indentLevel = 0;

    public void LogFunctionCall(Object object) {
        System.out.println(object);
    }
}
