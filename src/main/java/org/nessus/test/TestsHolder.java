package org.nessus.test;

import org.nessus.Logger;
import org.nessus.Skeleton;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TestsHolder {
    private ArrayList<Test> tests = new ArrayList<Test>();
    private Logger logger;

    public TestsHolder(String filename, Logger logger) {
        this.logger = logger;
        try {
            LoadTests(filename);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void DoTest(int number) {
        int index = number - 1;
        if (0 <= index && index < tests.size()) {
            String separator = "-".repeat(10);
            logger.Log("A(Z) " + number + ". TESZT INICIALIZÁLÁSA");
            tests.get(index).Init();
            logger.Log(separator);
            logger.Log("A(Z) " + number + ". TESZT FUTTATÁSA");
            tests.get(index).Run();
            logger.Log(separator);
        }
    }

    /*
     * A függvény a java Reflection API segítségével egy fájlból betölteni a teszteket,
     * ezért új teszt hozzáadása esetén, elég egy helyen, a "testNames.txt" fájlban hozzáadni a tesztet
     */
    private void LoadTests(String filename) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new FileNotFoundException(filename + " not found");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        // Az első header sor átugrása
        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length == 2) {
                String testName = parts[0];
                String className = parts[1];

                // Use Reflection to instantiate the class
                Class<?> clazz = Class.forName(className);
                Test testInstance = (Test) clazz.getDeclaredConstructor(String.class).newInstance(testName);
                // Store in the HashMap
                tests.add(testInstance);
            }
        }
        reader.close();
    }

    public void PrintTests() {
        for (int i = 0; i < tests.size(); i++) {
            Test test = tests.get(i);
            int order = i + 1;
            System.out.println(order + ". " + test);
        }
    }
}
