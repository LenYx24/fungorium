package org.nessus;

import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class Logger {
    private int indentLevel = 0;
    private final HashMap<Object, String> objects = new HashMap<Object, String>();
    private final Scanner scanner = new Scanner(System.in);

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
        Log(builder.toString());
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
        Log(builder.toString());
    }

    public void Log(String message) {
        System.out.println(message);
    }

    public Optional<Integer> AskQuestion(String question, Object... args) {
        Log("> " + question);
        for (int i = 0; i < args.length; i++) {
            int num = i + 1;
            Log(num + ". " + args[i]);
        }
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
        int ans = AskQuestion(question, "nem", "igen");
        return switch (ans) {
            case 1 -> Optional.of(false);
            case 2 -> Optional.of(true);
            default -> Optional.empty();
        };
    }
}
