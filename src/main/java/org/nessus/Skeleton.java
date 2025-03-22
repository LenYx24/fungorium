package org.nessus;

import java.util.Optional;
import java.util.Scanner;

import org.nessus.model.effect.*;
import org.nessus.test.TestsHolder;

public class Skeleton {
    private static final Logger logger = new Logger();
    private static final TestsHolder testsHolder = new TestsHolder("testNames.txt", logger);
    private static boolean running = true;

    public static String GetName(Object o)
    {
        return logger.GetName(o);
    }

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

    public static void LogFunctionCall(Object object, String method, Object... args) {
        logger.LogFunctionCall(object, method, args);
    }

    public static void LogReturnCall(Object object, String method, Object... args) {
        logger.LogReturnCall(object, method, args);
    }

    public static void AddObject(Object object, String name) {
        logger.AddObject(object, name);
    }

    public static BugEffect WhichEffect() {
        Optional<Integer> ans = Optional.empty();
        //String[] args = {"Kávé", "Bénító", "Lassító", "Szájzár"};
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

    public static boolean YesNoQuestion(String message) {
        Optional<Boolean> ans = Optional.empty();
        while (ans.isEmpty()) {
            ans = logger.AskYesNoQuestion(message);
        }
        return ans.get();
    }
}
