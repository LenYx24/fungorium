package org.nessus;

import java.util.Optional;
import java.util.Scanner;

import org.nessus.model.effect.*;
import org.nessus.test.TestsHolder;

/**
 * A {@code Skeleton} osztály a program belépési pontja.
 * A {@code Logger} és a {@code TestsHolder} osztályokat használja.
 * A {@code Logger} osztály segítségével logolja a program futását.
 * A {@code TestsHolder} osztály segítségével a teszteket tárolja és futtatja.
 * A {@code Skeleton} osztály a következő funkciókat valósítja meg:
 * - A felhasználó választhat a tesztek közül.
 * - A felhasználó kiléphet a programból.
 */
public class Skeleton {
    private static final Logger logger = new Logger(); // Logger osztály példányosítása
    private static final TestsHolder testsHolder = new TestsHolder("testNames.txt", logger); // TestsHolder osztály példányosítása
    private static boolean running = true; // A program futását jelző változó

    /**
     * A {@code Skeleton} osztály konstruktora.
     * A konstruktor privát, mert nem szükséges példányosítani az osztályt.
     */
    private Skeleton() {}

    /**
     * Visszaadja a paraméterként kapott objektum nevét.
     * @param o
     * @return String - Az objektum neve
     */
    public static String GetName(Object o) {
        return logger.GetName(o);
    }

    /**
     * A program futását megvalósító metódus.
     * @return void
     */
    public static void Run() {
        Scanner scanner = new Scanner(System.in);

        while (running) {
            logger.Log("Válassz egy számot: ");
            logger.Log("-".repeat(15));

            logger.Log("0. Kilépés");
            testsHolder.PrintTests();

            logger.Log("-".repeat(15));

            logger.LogNoNewLine("A választott szám: ");
            String line = scanner.nextLine();
            int number = -1;
            try {
                number = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                logger.Log("Nem számot adtál meg");
                continue;
            }

            if (number == 0) {
                running = false;
            } else {
                testsHolder.DoTest(number);
            }
        }
        scanner.close();
    }

    /**
     * Ezzel a metódussal logolhatjuk a függvényhívásokat.
     * @param object
     * @param method
     * @param args
     */
    public static void LogFunctionCall(Object object, String method, Object... args) {
        logger.LogFunctionCall(object, method, args);
    }

    /**
     * Ezzel a metódussal logolhatjuk a függvények visszatérését.
     * @param object
     * @param method
     * @param args
     */
    public static void LogReturnCall(Object object, String method, Object... args) {
        logger.LogReturnCall(object, method, args);
    }

    /**
     * Ezzel a metódussal adhatunk hozzá objektumot a loghoz.
     * @param object
     * @param name
     */
    public static void AddObject(Object object, String name) {
        logger.AddObject(object, name);
    }

    /**
     * Ezzel a metódussal kérdezhetünk a felhasználótól.
     * @return BugEffect - A választott effekt
     */
    public static BugEffect WhichEffect() {
        Optional<Integer> ans = Optional.empty();
        while (ans.isEmpty()) {
            ans = logger.AskQuestion("Melyik effektet rakjuk a rovarra?", "Kávé", "Bénító", "Lassító", "Szájzár");
        }
        return switch (ans.get()) {
            case 1 -> new CoffeeEffect();
            case 2 -> new CripplingEffect();
            case 3 -> new SlowEffect();
            case 4 -> new JawLockEffect();
            default -> null;
        };
    }

    /**
     * Ezzel a metódussal kérdezhetünk a felhasználótól.
     * @param message
     * @return boolean - A válasz
     */
    public static boolean YesNoQuestion(String message) {
        Optional<Boolean> ans = Optional.empty();
        while (ans.isEmpty()) {
            ans = logger.AskYesNoQuestion(message);
        }
        return ans.get();
    }
}