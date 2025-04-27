package org.nessus.controller.command.arrangecmd;

import org.nessus.controller.command.BaseCommand;
import org.nessus.view.View;

import java.lang.reflect.Field;

/**
 * Ez az osztály felelős a különböző objektumok mezőinek értékének beállításáért a megadott paraméterek alapján.
 * A parancs formátuma: setval <objektumnév> <mezőnév> <érték>
 * Az objektumnév a mező, amelybe az értéket beállítjuk, a mezőnév pedig az objektum mezőjének neve.
 * Az érték a mezőbe beállítandó érték.
 * 
 * A parancs végrehajtásához a következő lépések szükségesek:
 * 1. Ellenőrizzük, hogy elegendő paramétert adtak-e meg.
 * 2. Ellenőrizzük, hogy a megadott objektum létezik-e.
 * 3. Ellenőrizzük, hogy a megadott mező létezik-e az objektumban.
 * 4. Ellenőrizzük, hogy a megadott mező típusa kompatibilis-e az érték típusával.
 * 5. Beállítjuk az értéket az objektum mezőjében.
 * 6. Visszaadjuk a beállított értéket.
 */
public class SetValCmd extends BaseCommand {
    /**
     * Ez a metódus felelős az értékek típusának konvertálásáért.
     * A metódus bemeneti paraméterei a konvertálni kívánt érték és a cél típus.
     * @param value - a konvertálni kívánt érték
     * @param type - a cél típus
     * @return Object - a konvertált érték
     */
    private Object convertStringToType(String value, Class<?> type) {
        try {
            if (type == int.class || type == Integer.class) return Integer.parseInt(value);
            if (type == double.class || type == Double.class) return Double.parseDouble(value);
            if (type == char.class || type == Character.class) return value.charAt(0);
            if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
            if (type == String.class) return value;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Ez a metódus felelős az objektum mezőjének értékének beállításáért.
     * A parancs formátuma: setval <objektumnév> <mezőnév> <érték>
     * A célobjektum a mező, amelybe az értéket beállítjuk, a mezőnév pedig az objektum mezőjének neve.
     * Az érték a mezőbe beállítandó érték.
     * 
     * @param args A parancs paraméterei, amelyek tartalmazzák az objektum nevét, a mező nevét és az értéket.
     * @return Nincs visszatérési érték, a metódus csak végrehajtja az érték beállítását.
     */
    @Override
    public void Run(String[] args) {
        if (NotEnoughArgs(args,3)) {
            return;
        }

        String objectName = args[0];
        String fieldName = args[1];
        String valueString = args[2];

        var view = View.GetObjectStore();
        Object targetObject = view.GetObject(objectName);
        if (targetObject == null) {
            System.out.println("Nincs ilyen objektum: " + objectName);
            return;
        }

        Class<?> clazz = targetObject.getClass();

        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            // Típusdetektálás és konvertálás
            Object value = convertStringToType(valueString, field.getType());
            if (value == null) {
                System.out.println("Konvertálás sikertelen");
                return;
            }

            field.set(targetObject, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Hiba: " + e.getMessage());
        }
    }
}
