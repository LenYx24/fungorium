package org.nessus.view;

import org.nessus.controller.IBugOwnerController;
import org.nessus.controller.IRandomProvider;
import org.nessus.controller.IShroomController;
import org.nessus.model.bug.Bug;
import org.nessus.model.bug.BugOwner;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.entityviews.IEntityView;
import org.nessus.view.entityviews.TectonView;

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
    
    void AddShroomBody(ShroomBody shroomBody);
    void AddShroomThread(ShroomThread shroomThread);
    void AddSpore(Spore spore);
    void AddBug(Bug bug);
    void AddTecton(Tecton tecton);
    TectonView FindTectonView(Tecton tecton);
    IEntityView FindEntityView(Object obj);

    void RemoveSpore(Spore spore);
    String GetBugOwnerName(IBugOwnerController bugOwner);
    String GetShroomName(IShroomController shroom);
}
