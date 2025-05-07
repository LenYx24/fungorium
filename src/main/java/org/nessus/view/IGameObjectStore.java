package org.nessus.view;

import org.nessus.controller.Controller;
import org.nessus.controller.IRandomProvider;

/**
 * Az objektumkatalógus interfésze.
 * Az objektumkatalógus egy olyan osztály, amely tárolja a játékban használt objektumokat.
 * Az objektumkatalógusban tárolt objektumok nevei egyedi azonosítókként szolgálnak.
 */
public interface IGameObjectStore {
    /**
     * Egy objektum hozzáadása az objektumkatalógushoz.
     * @param name - Az objektum neve
     * @param object - Az objektum, amelyet hozzá szeretnénk adni
     * @return void
     */
    public void AddObject(String name, Object object);

    /**
     * Visszaadja a paraméterként kapott objektum nevét.
     * @param object
     * @return String - Az objektum neve
     */
    public String GetName(Object object);

    /**
     * Visszaadja azt az objektumot, amelynek a neve megegyezik a paraméterként kapott névvel.
     * @param name - Az objektum neve
     * @return Object - Az objektum, amelynek a neve megegyezik a paraméterként kapott névvel
     */
    public Object GetObject(String name);

    /**
     * Lekér egy random generátort.
     * @return IRandomProvider - A random generátor
     */
    public IRandomProvider GetRandomProvider();
}
