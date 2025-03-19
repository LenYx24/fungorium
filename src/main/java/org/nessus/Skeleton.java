package org.nessus;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;

import org.nessus.test.BugEatTest;
import org.nessus.test.Test;

public class Skeleton {
    private final HashMap<Test, String> tests = new HashMap<>();

    public Skeleton() {
        try{
            LoadTests();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /*
    * A függvény a java Reflection API segítségével egy fájlból betölteni a teszteket,
    * ezért új teszt hozzáadása esetén, elég egy helyen, a "testNames.txt" fájlban hozzáadni a tesztet
     */
    private void LoadTests() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testNames.txt");
        if (inputStream == null) {
            throw new FileNotFoundException("tests.txt not found");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                String testName = parts[0];
                String className = parts[1];

                // Use Reflection to instantiate the class
                Class<?> clazz = Class.forName(className);
                Test testInstance = (Test) clazz.getDeclaredConstructor().newInstance();

                // Store in the HashMap
                tests.put(testInstance, testName);
            }
        }
        reader.close();
    }
    public void PrintTests(){
        for (Test test : tests.keySet()) {
            System.out.println(test);
        }
    }
    public static void main(String[] args) {
        System.out.println("Üdvözlünk a szkeleton programban");

        Skeleton skeleton = new Skeleton();

        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while(running){
            System.out.println("Válassz egy számot: ");

            skeleton.PrintTests();

            String line = scanner.nextLine();
            int number = -1;
            try{
                 number = Integer.parseInt(line);
            }catch(NumberFormatException e){
                System.out.println("Nem számot adtál meg");
                continue;
            }

            switch(number){
                case 0:{
                    running = false;
                    break;
                }
            }
        }
        scanner.close();
        System.out.println("Viszlát");
    }
}
