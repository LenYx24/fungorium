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
        builder.append("\t".repeat(Math.max(0, indentLevel)));
        builder.append(objects.get(object)).append(".").append(funcName).append("(");
        for (Object arg : args) {
            if (objects.containsKey(arg))
                builder.append(objects.get(arg)).append(", ");
            else
                builder.append(arg).append(", ");
        }
        // Az utolsó ", " sztringet ki kell szedni, mert így nézne ki a kiírás: func(a1, a2, )
        builder.replace(builder.length() - 2, builder.length(), ")");
        indentLevel++;
        System.out.println(builder);
    }

    public void LogReturnCall(Object object, String funcName, Object... args) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t".repeat(Math.max(0, indentLevel)));
        builder.append("return ");
        if (args.length != 0) {
            Object arg = args[0];
            if (objects.containsKey(arg))
                builder.append(objects.get(arg));
            else
                builder.append(arg);
        }
        indentLevel--;
        System.out.println(builder);
    }
}
