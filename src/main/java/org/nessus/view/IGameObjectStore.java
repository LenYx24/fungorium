package org.nessus.view;

public interface IGameObjectStore {
    public void AddObject(String name, Object object);

    /**
     * Visszaadja a paraméterként kapott objektum nevét.
     * @param object
     * @return String - Az objektum neve
     */
    public String GetName(Object object);

    public Object GetObject(String name);
}
