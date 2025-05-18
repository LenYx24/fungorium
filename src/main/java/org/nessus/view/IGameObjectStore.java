package org.nessus.view;

import org.nessus.controller.IBugOwnerController;
import org.nessus.controller.IShroomController;
import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.entities.IEntityView;
import org.nessus.view.entities.TectonView;

/**
 * Az objektumkatalógus interfésze.
 * Az objektumkatalógus egy olyan osztály, amely tárolja a játékban használt objektumokat.
 * Az objektumkatalógusban tárolt objektumok nevei egyedi azonosítókként szolgálnak.
 */
public interface IGameObjectStore {
    /**
     * Gombatest hozzáadása az objektumkatalógushoz.
     * 
     * @param shroomBody A hozzáadandó gombatest
     * @return void
     */
    void AddShroomBody(ShroomBody shroomBody);
    
    /**
     * Gombafonal hozzáadása az objektumkatalógushoz.
     * 
     * @param shroomThread A hozzáadandó gombafonal
     * @return void
     */
    void AddShroomThread(ShroomThread shroomThread);
    
    /**
     * Spóra hozzáadása az objektumkatalógushoz.
     * 
     * @param spore A hozzáadandó spóra
     * @return void
     */
    void AddSpore(Spore spore);
    
    /**
     * Rovar hozzáadása az objektumkatalógushoz.
     * 
     * @param bug A hozzáadandó rovar
     * @return void
     */
    void AddBug(Bug bug);
    
    /**
     * Tekton hozzáadása az objektumkatalógushoz.
     * 
     * @param tecton A hozzáadandó tekton
     * @return void
     */
    void AddTecton(Tecton tecton);
    
    /**
     * Új tekton hozzáadása egy meglévő tekton helyére.
     * 
     * @param at A meglévő tekton, amelynek a helyére az újat helyezzük
     * @param newTecton Az új tekton
     * @return void
     */
    void AddTectonAt(Tecton at, Tecton newTecton);

    /**
     * Megkeresi egy tekton nézetét a modell alapján.
     * 
     * @param tecton A keresett tekton modell
     * @return TectonView - A tekton nézete, vagy null, ha nem található
     */
    TectonView FindTectonView(Tecton tecton);
    
    /**
     * Megkeresi egy entitás nézetét a modell alapján.
     * 
     * @param obj A keresett entitás modell
     * @return IEntityView - Az entitás nézete, vagy null, ha nem található
     */
    IEntityView FindEntityView(Object obj);
    
    /**
     * Eltávolít egy entitást az objektumkatalógusból.
     * 
     * @param model Az eltávolítandó entitás modell
     * @return void
     */
    void RemoveEntity(Object model);

    /**
     * Visszaadja egy rovarász nevét.
     * 
     * @param bugOwner A rovarász, amelynek a nevét keressük
     * @return String - A rovarász neve
     */
    String GetBugOwnerName(IBugOwnerController bugOwner);
    
    /**
     * Visszaadja egy gombász nevét.
     * 
     * @param shroom A gombász, amelynek a nevét keressük
     * @return String - A gombász neve
     */
    String GetShroomName(IShroomController shroom);
}
