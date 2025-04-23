package org.nessus;

import java.util.HashMap;
import java.util.Scanner;

import org.nessus.model.shroom.*;
import org.nessus.model.tecton.*;
import org.nessus.model.bug.*;

public class View {
    private static final CommandProcessor commandProcessor = new CommandProcessor(); // Logger osztály példányosítása
    private static boolean running = true; // A program futását jelző változó

    /**
     * A {@code View} osztály konstruktora.
     * A konstruktor privát, mert nem szükséges példányosítani az osztályt.
     */
    private View() {}
    
    /**
     * A program futását megvalósító metódus.
     * @return void
     */
    public static void Run() {
        Scanner scanner = new Scanner(System.in);
        
        while (running) {
            // TODO
        }
        scanner.close();
    }

    /**
     * Ezzel a metódussal adhatunk hozzá objektumot a loghoz.
     * @param object
     * @param name
     */
    public static void AddObject(Object object, String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visszaadja a paraméterként kapott objektum nevét.
     * @param o
     * @return String - Az objektum neve
     */
    public static String GetName(Object o) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {

    }
}