package org.nessus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

/**
 * A logolást végző osztály.
 * A logolás során a program minden függvényhívás előtt és után kiírja a függvény nevét és a paramétereit.
 * A logolás során lehetőség van a felhasználónak kérdéseket feltenni, amikre a program válaszol.
 */
public class Logger {
    private int indentLevel = 0; // A behúzás szintje
    private final HashMap<Object, String> objects = new HashMap<>(); // Az objektumok nevei
    private final Scanner scanner = new Scanner(System.in); // A bemeneti adatok beolvasására szolgáló Scanner

    /**
     * Az aktuális behúzás szintjének megfelelő behúzást ad vissza.
     * @return String - A behúzás
     */
    private String GetIndentation() {
        return "\t".repeat(Math.max(0, indentLevel));
    }

    /**
     * Behúzást ad a megadott StringBuilderhez.
     * @param builder - A StringBuilder, amelyhez a behúzást hozzá kell adni
     * @return void
     */
    private void Indent(StringBuilder builder) {
        builder.append(GetIndentation());
    }

    /**
     * Objektum hozzáadása a logoláshoz.
     * @param object - Az objektum, amelyet hozzá kell adni
     * @param name - Az objektum neve
     * @return void
     */
    public void AddObject(Object object, String name) {
        objects.put(object, name);
    }

    /**
     * Függvényhívás logolása.
     * @param object - Az objektum, amelyen a függvény meghívódik
     * @param funcName - A függvény neve
     * @param args - A függvény paraméterei
     * @return void
     */
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

    /**
     * Függvényhívás visszatérésének logolása.
     * @param object
     * @param funcName
     * @param args
     * @return void
     */
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

    /**
     * Logolás.
     * @param message
     * @return void
     */
    public void Log(String message) {
        System.out.println(message);
    }

    /**
     * Logolás új sor nélkül.
     * @param message
     * @return void
     */
    public void LogNoNewLine(String message) {
        System.out.print(message);
    }

    /**
     * Kérdés feltevése a felhasználónak.
     * @param question
     * @param args
     * @return Optional<Integer> - A válasz
     */
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

    /**
     * Igen-nem kérdés feltevése a felhasználónak.
     * @param question
     * @return Optional<Boolean> - A válasz
     */
    public Optional<Boolean> AskYesNoQuestion(String question) {
        Optional<Integer> ans = AskQuestion(question, "nem", "igen");
        return ans.flatMap(num -> switch (num) {
            case 1 -> Optional.of(false);
            case 2 -> Optional.of(true);
            default -> Optional.empty();
        });
    }

    /**
     * Objektum nevének lekérdezése.
     * @param o
     * @return String - Az objektum neve
     */
    public String GetName(Object o) {
        return objects.get(o);
    }
}