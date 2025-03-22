package org.nessus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class Logger {
    private int indentLevel = 0;
    private final HashMap<Object, String> objects = new HashMap<Object, String>();
    private final Scanner scanner = new Scanner(System.in);

    private String GetIndentation() {
        return "\t".repeat(Math.max(0, indentLevel));
    }

    private void Indent(StringBuilder builder) {
        builder.append(GetIndentation());
    }

    public void AddObject(Object object, String name) {
        objects.put(object, name);
    }

    public void LogFunctionCall(Object object, String funcName, Object... args) {
        StringBuilder builder = new StringBuilder();
        Indent(builder);
        builder.append(objects.get(object)).append(".").append(funcName).append("(");

        var objNames = new ArrayList<String>();
        for (Object arg : args) {
            if (objects.containsKey(arg))
                objNames.add(objects.get(arg));
            else
                objNames.add(arg.toString());
        }

        builder.append(String.join(", ", objNames));
        builder.append(")");

        indentLevel++;
        Log(builder.toString());
    }

    public void LogReturnCall(Object object, String funcName, Object... args) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t".repeat(Math.max(0, indentLevel)));
        builder.append(objects.get(object)).append(".").append(funcName).append(" return ");
        if (args.length != 0) {
            Object arg = args[0];
            if (objects.containsKey(arg))
                builder.append(objects.get(arg));
            else
                builder.append(arg);
        }
        indentLevel--;
        Log(builder.toString());
    }

    public void Log(String message) {
        System.out.println(message);
    }

    public void LogNoNewLine(String message) {
        System.out.print(message);
    }

    public Optional<Integer> AskQuestion(String question, Object... args) {
        StringBuilder builder = new StringBuilder();
        builder.append(GetIndentation()).append("> ").append(question).append("\n");
        for (int i = 0; i < args.length; i++) {
            int num = i + 1;
            builder.append(GetIndentation()).append(num).append(". ").append(args[i]).append("\n");
        }
        builder.append(GetIndentation()).append("Válasz: ");
        LogNoNewLine(builder.toString());
        String answer = scanner.nextLine();
        Optional<Integer> ans = Optional.empty();
        try {
            ans = Optional.of(Integer.parseInt(answer));
            // Ha túllóg a választott szám a lehetséges opciók számán, akkor is legyen érvénytelen a válasz

        } catch (NumberFormatException exc) {
            Log("Nem számot adtál meg!");
        }
        if (ans.isPresent() && ans.get() > args.length) return Optional.empty();
        return ans;
    }

    public Optional<Boolean> AskYesNoQuestion(String question) {
        Optional<Integer> ans = AskQuestion(question, "nem", "igen");
        return ans.flatMap(num -> switch (num) {
            case 1 -> Optional.of(false);
            case 2 -> Optional.of(true);
            default -> Optional.empty();
        });
    }

    public String GetName(Object o)
    {
        return objects.get(o);
    }
}
