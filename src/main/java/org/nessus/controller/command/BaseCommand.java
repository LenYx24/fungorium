package org.nessus.controller.command;

import org.nessus.view.View;

import java.util.Optional;

/**
 * Ez az osztály felelős a parancsok végrehajtásáért.
 * Absztrakt osztály, amelyet a különböző parancsok örökölnek.
 * A parancsok végrehajtásához szükséges metódusokat tartalmaz.
 */
public abstract class BaseCommand {
    public abstract void Run(String[] args);

    /**
     * Ezzel a metódussal konvertálhatunk egy String-et Integer típusúvá.
     * Ha a konverzió sikeres, akkor egy Optional<Integer> objektumot ad vissza,
     * amely tartalmazza az Integer értéket.
     * @param arg A konvertálni kívánt String érték.
     * @return Egy Optional<Integer> objektum, amely tartalmazza az Integer értéket,
     *         vagy üres Optional-t, ha a konverzió nem sikerült.
     */
    protected Optional<Integer> ConvertToInteger(String arg){
        try {
            return Optional.of(Integer.parseInt(arg));
        } catch (NumberFormatException e) {
            System.out.println("Rossz paraméter");
            return Optional.empty();
        }
    }

    /**
     * Ez a metódus konvertál egy String-et a megfelelő típusra.
     * Ha a String egy szám, akkor Integer vagy Double típusra konvertálja.
     * Ha a String egy objektum neve, akkor visszaadja az objektumot.
     * @param s A konvertálni kívánt String érték.
     * @return Az átkonvertált érték, amely lehet Integer, Double vagy String.
     */
    protected Object InferType(String s) {
        // Try Integer
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ignored) {}

        // Try Double
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ignored) {}

        var view = View.GetObjectStore();
        Object obj = view.GetObject(s);
        if(obj != null)
            return obj;

        // Default: String
        return s;
    }

    /**
     * Ez a metódus megpróbálja megállapítani egy objektum típusát.
     * Jelenleg csak visszaadja az objektum osztályát.
     * @param o Az objektum, amelynek a típusát meg akarjuk állapítani.
     * @return Az objektum típusa (osztálya).
     */
    protected static Class<?> GetTypeClass(Object o) {
        // TODO
        return o.getClass();
    }

    /**
     * Ez a metódus ellenőrzi, hogy elegendő paramétert adtak-e meg.
     * Ha nem, akkor kiír egy hibaüzenetet.
     * @param args A parancs argumentumai.
     * @param minlength A minimális elvárt paraméterek száma.
     * @return true, ha nem elegendő paramétert adtak meg, false egyébként.
     */
    protected boolean NotEnoughArgs(Object[] args, int minlength){
        if(args.length < minlength) {
            System.out.println("Nincs elegendő számú paraméter");
            return true;
        }
        return false;
    }
}
