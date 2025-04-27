package org.nessus.controller.command.arrangecmd;

import org.nessus.controller.command.BaseCommand;
import org.nessus.view.View;

import java.lang.reflect.Field;

/**
 * Ez az osztály felelős egy referencia beállításáért egy adott objektum mezőjében.
 * A parancs formátuma: setref <célobjektum> <mezőnév> <hivatkozott objektum>
 * A célobjektum a mező, amelybe a hivatkozást beállítjuk, a mezőnév pedig a célobjektum mezőjének neve.
 * A hivatkozott objektum a célobjektum mezőjébe beállítandó objektum.
 * 
 * A parancs végrehajtásához a következő lépések szükségesek:
 * 1. Ellenőrizzük, hogy elegendő paramétert adtak-e meg.
 * 2. Ellenőrizzük, hogy a megadott célobjektum létezik-e.
 * 3. Ellenőrizzük, hogy a megadott hivatkozott objektum létezik-e.
 * 4. Ellenőrizzük, hogy a megadott mező létezik-e a célobjektumban.
 * 5. Ellenőrizzük, hogy a megadott mező típusa kompatibilis-e a hivatkozott objektum típusával.
 * 6. Beállítjuk a hivatkozást a célobjektum mezőjében.
 * 7. Visszaadjuk a beállított hivatkozást.
 */
public class SetRefCmd extends BaseCommand {
    /**
     * Ez a metódus felelős a referencia beállításáért egy adott objektum mezőjében.
     * A parancs formátuma: setref <célobjektum> <mezőnév> <hivatkozott objektum>
     * A célobjektum a mező, amelybe a hivatkozást beállítjuk, a mezőnév pedig a célobjektum mezőjének neve.
     * A hivatkozott objektum a célobjektum mezőjébe beállítandó objektum.
     * 
     * @param args A parancs paraméterei, amelyek tartalmazzák a célobjektum nevét, a mező nevét és a hivatkozott objektum nevét.
     * @return Nincs visszatérési érték, a metódus csak végrehajtja a referencia beállítását.
     */
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
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Hiba a referencia beállításánál: " + e.getMessage());
        } finally {
            if (field != null)
                field.setAccessible(false);
        }
    }
}
