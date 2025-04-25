package org.nessus.view;

import java.util.*;

import org.nessus.model.shroom.*;
import org.nessus.controller.Controller;
import org.nessus.controller.IRandomProvider;
import org.nessus.model.bug.*;

import static java.lang.System.in;

public class View implements IGameObjectStore {
    private static View instance;

    private boolean running = true; // A program futását jelző változó
    
    private String pendingObjectName;
    private Map<String,Object> objects = new LinkedHashMap<>();
    private Map<String,Object> pendingObjects = new LinkedHashMap<>();

    private Controller controller = new Controller(this);

    /**
     * A {@code View} osztály konstruktora.
     * A konstruktor privát, mert nem szükséges példányosítani az osztályt.
     */
    private View() {}

    static View GetInstance() {
        if (instance == null)
            instance = new View();
        return instance;
    }

    public static IGameObjectStore GetObjectStore() {
        return GetInstance();
    }

    public void Stop() {
        running = false;
    }

    public Set<String> GetObjects() {
        return objects.keySet();
    }
    
    /**
     * A program futását megvalósító metódus.
     * @return void
     */
    public void Run() {
        Scanner scanner = new Scanner(in);
        System.out.println("Üdv a prototípus fázisban");

        while (running) {
            String prompt = controller.GetPrompt();
            System.out.print(prompt + ">");

            String cmd = scanner.nextLine();
            controller.ProcessCommand(cmd);
        }

        scanner.close();
    }

    /**
     * Ezzel a metódussal adhatunk hozzá objektumot a loghoz.
     * @param name
     * @param object
     */
    public void AddObject(String name, Object object) {
        if (pendingObjectName != null) {
            pendingObjects.put(name, object);
        }
        else {
            if (object instanceof BugOwner bugOwner)
                controller.AddBugOwner(bugOwner);
            else if (object instanceof Shroom shroom)
                controller.AddShroom(shroom);
                
            objects.put(name, object);
        }
    }

    /**
     * Visszaadja a paraméterként kapott objektum nevét.
     * @param object
     * @return String - Az objektum neve
     */
    public String GetName(Object object) {
        for (Map.Entry<String, Object> entry : objects.entrySet()) {
            if (entry.getValue() == object) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Object GetObject(String name) {
        return objects.get(name);
    }

    @Override
    public void SetPending(String name) {
        pendingObjectName = name;
    }

    @Override
    public void EndPending(Object object) {
        // Belerakjuk a legelső objektumot amely létre lett hozva a lista elé
        objects.put(pendingObjectName, object);
        objects.putAll(pendingObjects);
        pendingObjects.clear();
        pendingObjectName = null;
    }
    
    @Override
    public String GetPendingObjectName() {
        return pendingObjectName;
    }
    
    @Override
    public IRandomProvider GetRandomProvider() {
        return controller;
    }

    public static void main(String[] args) {
        View view = View.GetInstance();
        view.Run();
    }
}