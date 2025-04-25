package org.nessus.controller.command.arrangecmd;

import org.nessus.controller.command.BaseCommand;
import org.nessus.view.View;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateCmd extends BaseCommand {
    private Map<String, String> classNameFQNs = new HashMap<>();
    private String baseprefix = "org.nessus.model.";
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
            view.SetPending();

            // Instantiate the object
            Object instance = constructor.newInstance(inferredArgs);

            view.EndPending(objectName, instance);

        } catch (Exception e) {
            System.out.println("Rossz parancsformátum: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
