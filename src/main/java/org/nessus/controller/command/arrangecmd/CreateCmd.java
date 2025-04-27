package org.nessus.controller.command.arrangecmd;

import org.nessus.controller.command.BaseCommand;
import org.nessus.view.View;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Ez az osztály felelős a különböző objektumok létrehozásáért a megadott paraméterek alapján.
 * A parancs formátuma: create <osztálynév> <objektumnév> <paraméterek>
 * Az osztálynév a létrehozandó objektum osztályának teljes neve, az objektumnév pedig az új objektum neve.
 * 
 * A parancs végrehajtásához a következő lépések szükségesek:
 * 1. Ellenőrizzük, hogy elegendő paramétert adtak-e meg.
 * 2. Ellenőrizzük, hogy a megadott osztály létezik-e.
 * 3. Ellenőrizzük, hogy a megadott paraméterek típusa megfelelő-e az osztály konstruktorához.
 * 4. Létrehozzuk az objektumot a megadott paraméterekkel.
 * 5. Hozzáadjuk az objektumot a View-hoz.
 * 6. Visszaadjuk a létrehozott objektumot.
 */
public class CreateCmd extends BaseCommand {
    private Map<String, String> classNameFQNs = new HashMap<>(); // A parancsokhoz tartozó osztálynevek és azok teljes nevei
    private String baseprefix = "org.nessus.model."; // Az osztályok alapértelmezett prefixe

    /**
     * A konstruktorban inicializáljuk a classNameFQNs map-et, amely a parancsokhoz tartozó osztályneveket és azok teljes neveit tárolja.
     * Az osztálynevek a következő formátumban vannak megadva: <osztálynév>.<osztálynév>
     * A map kulcsai a parancsok nevei, az értékek pedig a teljes osztálynevek.
     * A map feltöltése során a baseprefix változó értékét is figyelembe vesszük.
     * 
     * A feltöltés során a következő osztályokat adjuk hozzá:
     * - Bug: Bug és BugOwner
     * - Effect: BugEffect, CoffeeEffect, CripplingEffect, DivisionEffect, JawLockEffect, SlowEffect
     * - Shroom: Shroom, ShroomBody, ShroomThread, Spore
     * - Tecton: Tecton, DesertTecton, InfertileTecton, SingleThreadTecton, ThreadSustainerTecton
     * - Base: ActionPointCatalog
     */
    public CreateCmd(){
        String prefix_plus = "";
        // bug
        prefix_plus = "bug.";
        classNameFQNs.put("Bug",baseprefix+prefix_plus+"Bug");
        classNameFQNs.put("BugOwner",baseprefix+prefix_plus+"BugOwner");
        // effect
        prefix_plus = "effect.";
        classNameFQNs.put("BugEffect",baseprefix+prefix_plus+"BugEffect");
        classNameFQNs.put("CoffeeEffect",baseprefix+prefix_plus+"CoffeeEffect");
        classNameFQNs.put("CripplingEffect",baseprefix+prefix_plus+"CripplingEffect");
        classNameFQNs.put("DivisionEffect",baseprefix+prefix_plus+"DivisionEffect");
        classNameFQNs.put("JawLockEffect",baseprefix+prefix_plus+"JawLockEffect");
        classNameFQNs.put("SlowEffect",baseprefix+prefix_plus+"SlowEffect");
        // shroom
        prefix_plus = "shroom.";
        classNameFQNs.put("Shroom",baseprefix+prefix_plus+"Shroom");
        classNameFQNs.put("ShroomBody",baseprefix+prefix_plus+"ShroomBody");
        classNameFQNs.put("ShroomThread",baseprefix+prefix_plus+"ShroomThread");
        classNameFQNs.put("Spore",baseprefix+prefix_plus+"Spore");
        // tecton
        prefix_plus = "tecton.";
        classNameFQNs.put("Tecton",baseprefix+prefix_plus+"Tecton");
        classNameFQNs.put("DesertTecton",baseprefix+prefix_plus+"DesertTecton");
        classNameFQNs.put("InfertileTecton",baseprefix+prefix_plus+"InfertileTecton");
        classNameFQNs.put("SingleThreadTecton",baseprefix+prefix_plus+"SingleThreadTecton");
        classNameFQNs.put("ThreadSustainerTecton",baseprefix+prefix_plus+"ThreadSustainerTecton");
        // base
        classNameFQNs.put("ActionPointCatalog",baseprefix+"ActionPointCatalog");
    }

    /**
     * A parancs végrehajtásáért felelős metódus.
     * A metódus ellenőrzi a paramétereket, és létrehozza az objektumot a megadott paraméterekkel.
     * 
     * @param args A parancs paraméterei
     *            args[0] - Az osztály neve
     *           args[1] - Az objektum neve
     *          args[2] - A paraméterek
     * @return void
     */
    @Override
    public void Run(String[] args) {
        if(NotEnoughArgs(args,2)) {return;}
        String className = args[0];
        String objectName = args[1]; // can be used later for storage

        Object[] inferredArgs = Arrays.stream(Arrays.copyOfRange(args, 2, args.length))
                .map(this::InferType)
                .toArray();

        // Get the exact parameter types for constructor lookup
        Class<?>[] paramTypes = Arrays.stream(inferredArgs)
                .map(CreateCmd::GetTypeClass)
                .toArray(Class<?>[]::new);

        try {
            // Load class
            Class<?> clazz = Class.forName(classNameFQNs.get(className));

            // Get matching constructor
            Constructor<?> constructor = clazz.getConstructor(paramTypes);

            // Beállítjuk hogy a viewhoz olyan objektumok adódnak most hozzá amelyek egy creation alatt álló objektum
            // által lesznek hozzáadva, erre a sorrend megtartása miatt van szükség

            var view = View.GetObjectStore();
            view.SetPending(objectName);

            // Instantiate the object
            Object instance = constructor.newInstance(inferredArgs);

            view.EndPending(instance);

        } catch (Exception e) {
            System.out.println("Rossz parancsformátum: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
