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
    public void SetPending();
    // Amikor egy objektumot létrehozunk, akkor először muszáj a konstruktornak lefutni először
    // tehát a benne létrejövő objektumok előbb adódnak hozzá az objektumkatalógushoz, mint maga az objektum
    public void EndPending(String name, Object object);
}
