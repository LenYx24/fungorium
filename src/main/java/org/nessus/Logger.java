package org.nessus;

import java.util.Arrays;
import java.util.HashMap;

public class Logger {
    private int indentLevel = 0;
    private final HashMap<Object, String> objects = new HashMap<Object, String>();

    public void AddObject(Object object, String name) {
        objects.put(object, name);
    }

    public void LogFunctionCall(Object object, String funcName, Object... args) {
        StringBuilder builder = new StringBuilder();
        builder.append(objects.get(object)).append(".").append(funcName);
        for (Object arg : args) {
            builder.append(", ").append(arg);
        }
        indentLevel++;
        System.out.println(builder);
    }
}
