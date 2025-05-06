package org.nessus.view;

import org.nessus.controller.Controller;

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
     * Egy objektum hozzáadása az objektumkatalógushoz, amelynek neve generált.
     * @param name - Az objektum neve
     * @param object - Az objektum, amelyet hozzá szeretnénk adni
     * @return void
     */
    public void AddObjectWithNameGen(String name, Object object);

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
     * Visszaadja a még várakozó objektum nevét.
     * @return String - A várakozó objektum neve
     */
    public String GetPendingObjectName();

    /** 
     * Amikor egy objektumot létrehozunk, akkor először muszáj a konstruktornak lefutni először
     * tehát a benne létrejövő objektumok előbb adódnak hozzá az objektumkatalógushoz, mint maga az objektum
     * @param name - Az objektum neve
     * @return void
     */
    public void SetPending(String name);

    /**
     * Leállítja a várakozó objektumot.
     * @param object - Az objektum, amelyet le szeretnénk állítani
     * @return void
     */
    public void EndPending(Object object);

    /**
     * Lekér egy random generátort.
     * @return IRandomProvider - A random generátor
     */
    public Controller GetRandomProvider();
}
