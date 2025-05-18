package org.nessus.controller;

import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;

/**
 * A tekton vezérlőjének interfésze.
 * Definiálja azokat a műveleteket, amelyeket egy tekton végrehajthat.
 */
public interface ITectonController {
    /**
     * A tekton frissítése.
     * Ez a metódus minden kör végén meghívódik.
     * @return void
     */
    void UpdateTecton();
    
    /**
     * A tekton kettéhasadása.
     * Új tektont hoz létre, és elosztja a rajta lévő entitásokat.
     * @return void
     */
    void Split();
    
    /**
     * Szomszédos tekton beállítása.
     * @param tecton A szomszédos tekton
     * @return void
     */
    void SetNeighbour(Tecton tecton);
    
    /**
     * Gombatest elhelyezése a tektonon.
     * @param body Az elhelyezendő gombatest
     * @return Boolean - Igaz, ha sikerült elhelyezni a gombatestet, hamis egyébként
     */
    boolean SetShroomBody(ShroomBody body);
    
    /**
     * Rovar hozzáadása a tektonhoz.
     * @param bug A hozzáadandó rovar
     * @return void
     */
    void AddBug(Bug bug);
    
    /**
     * Spóra dobása a tektonra.
     * @param spore A dobott spóra
     * @return void
     */
    void ThrowSpore(Spore spore);
    
    /**
     * Gombafonal növesztése a tektonon.
     * @param thread A növesztendő gombafonal
     * @return Boolean - Igaz, ha sikerült növeszteni a gombafonalat, hamis egyébként
     */
    boolean GrowShroomThread(ShroomThread thread);
}
