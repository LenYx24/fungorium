package org.nessus;

import java.util.HashMap;
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
     * Objektum nevének lekérdezése.
     * @param o
     * @return String - Az objektum neve
     */
    public String GetName(Object o) {
        return objects.get(o);
    }
}