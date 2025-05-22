package org.nessus.controller;

import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;

/**
 * A rovar tulajdonosok vezérlőjének interfésze.
 * Definiálja azokat a műveleteket, amelyeket egy rovar tulajdonos végrehajthat.
 */
public interface IBugOwnerController {
    /**
     * Rovar mozgatása egy adott tektonra.
     * @param bug A mozgatandó rovar
     * @param tecton A célpont tekton
     * @return void
     */
    void Move(Bug bug, Tecton tecton);
    
    /**
     * Spóra megevése egy rovarral.
     * @param bug A rovar, amely megeszi a spórát
     * @param spore A megevendő spóra
     * @return void
     */
    void Eat(Bug bug, Spore spore);
    
    /**
     * Gombafonal elvágása egy rovarral.
     * @param bug A rovar, amely elvágja a gombafonalat
     * @param shroomThread Az elvágandó gombafonal
     * @return void
     */
    void CutThread(Bug bug, ShroomThread shroomThread);
    
    /**
     * A rovar tulajdonos frissítése.
     * Ez a metódus minden kör elején meghívódik.
     * @return void
     */
    void UpdateBugOwner();

    int GetScore();
}
