package org.nessus.controller.command.arrangecmd;

import org.nessus.controller.command.BaseCommand;
import org.nessus.view.View;

import java.lang.reflect.Field;

public class SetRefCmd extends BaseCommand {
    @Override
    public void Run(String[] args) {
        if (NotEnoughArgs(args,3)) {
            return;
        }

        String targetName = args[0];
        String fieldName = args[1];
        String referenceName = args[2];

        var view = View.GetObjectStore();
        Object targetObject = view.GetObject(targetName);
        Object referenceObject = view.GetObject(referenceName);

        if (targetObject == null) {
            System.out.println("Nincs ilyen célobjektum: " + targetName);
            return;
        }
        if (referenceObject == null) {
            System.out.println("Nincs ilyen hivatkozott objektum: " + referenceName);
            return;
        }

        Field field = null;
        try {
            field = targetObject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (!field.getType().isAssignableFrom(referenceObject.getClass())) {
                System.out.println("Típusinkompatibilis hivatkozás: " + field.getType().getSimpleName() +
                        " <- " + referenceObject.getClass().getSimpleName());
                return;
            }

            field.set(targetObject, referenceObject);
            System.out.println("Referencia sikeresen beállítva.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Hiba a referencia beállításánál: " + e.getMessage());
        } finally {
            if (field != null)
                field.setAccessible(false);
        }
    }
}
