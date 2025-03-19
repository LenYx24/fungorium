package org.nessus;

import java.util.Scanner;

import org.nessus.test.TestsHolder;

public class Skeleton {
    private static final TestsHolder testsHolder = new TestsHolder("testNames.txt");
    private static final Logger logger = new Logger();
    private static boolean running = true;

    public static void Run() {
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("Válassz egy számot: ");
            System.out.println("-".repeat(15));

            System.out.println("0. Kilépés");
            testsHolder.PrintTests();

            System.out.println("-".repeat(15));

            System.out.print("A választott szám: ");
            String line = scanner.nextLine();
            int number = -1;
            try {
                number = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Nem számot adtál meg");
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
}
