package org.nessus.view;

import java.util.*;

import org.nessus.model.shroom.*;
import org.nessus.controller.Controller;
import org.nessus.controller.IRandomProvider;
import org.nessus.controller.NameGenerator;
import org.nessus.model.bug.*;

import static java.lang.System.in;

/**
 * Ez a singleton View osztály felelős a program futtatásáért.
 * A View osztály a Controller osztályt használja a parancsok feldolgozására.
 * A View osztály a parancsok végrehajtásáért és a felhasználói interakcióért felelős.
 */
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

    /**
     * Ez a metódus visszaadja a {@code View} osztály egy példányát.
     * Ha a példány még nem létezik, akkor létrehozza azt.
     * @return A {@code View} osztály egy példánya.
     */
    static View GetInstance() {
        if (instance == null)
            instance = new View();
        return instance;
    }

    /**
     * Ezzela metódussal kaphatunk vissza egy ObjectStore-t.
     * @return IGameObjectStore - Az objektumok tárolására szolgáló objektum.
     */
    public static IGameObjectStore GetObjectStore() {
        return GetInstance();
    }

    /**
     * Ezzel a metódussal állíthatjuk le a program futását.
     * @return void
     */
    public void Stop() {
        running = false;
    }

    /**
     * Ezzel a metódussal kérhetjük le a programban tárolt objektumokat.
     * @return Set<String> - Az objektumok tárolására szolgáló térkép.
     */
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
            System.out.print(prompt);
            
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
            NameGenerator.AddName(name);
        }
    }

    /**
     * Ezzel a metódussal adhatunk hozzá objektumot a loghoz.
     * @param prefix
     * @param object
     */
    public void AddObjectWithNameGen(String prefix, Object object) {
        String name = NameGenerator.GenerateName(prefix);
        if (pendingObjectName != null) {
            pendingObjects.put(name, object);
        }
        else {
            objects.put(name, object);
        }
    }

    /**
     * Ezzel a metódussal törölhetjük az összes objektumot a tárolókból.
     * @return void
     */
    public void ResetObjects() {
        objects.clear();
        pendingObjects.clear();
        NameGenerator.ResetNames();
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

    /**
     * Visszaadja azt az objektumot, amelynek a neve megegyezik a paraméterként kapott névvel.
     * @param name - Az objektum neve
     * @return Object - Az objektum, amelynek a neve megegyezik a paraméterként kapott névvel
     */
    public Object GetObject(String name) {
        return objects.get(name);
    }

    /**
     * Beállítja a várakozó objektum nevét.
     * @param name - Az objektum neve
     * @return void
     */
    @Override
    public void SetPending(String name) {
        pendingObjectName = name;
    }

    @Override
    public void EndPending(Object object) {
        // Belerakjuk a legelső objektumot amely létre lett hozva a lista elé
        var tmp = pendingObjectName;
        pendingObjectName = null;
        
        AddObject(tmp, object);
        pendingObjects.forEach(this::AddObject);

        pendingObjects.clear();
    }

    /**
     * Visszaadja a még várakozó objektum nevét.
     * @return String - A várakozó objektum neve
     */
    @Override
    public String GetPendingObjectName() {
        return pendingObjectName;
    }

    /**
     * Lekér egy random generátort.
     * @return IRandomProvider - A random generátor
     */
    @Override
    public IRandomProvider GetRandomProvider() {
        return controller;
    }

    /**
     * A program belépési pontja.
     * @param args - A parancssori argumentumok
     */
    public static void main(String[] args) {
        View view = View.GetInstance();
        view.Run();
    }
}