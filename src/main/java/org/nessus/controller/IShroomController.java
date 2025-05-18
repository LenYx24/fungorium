package org.nessus.controller;

import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.tecton.Tecton;

/**
 * A gomba vezérlőjének interfésze.
 * Definiálja azokat a műveleteket, amelyeket egy gomba végrehajthat.
 */
public interface IShroomController {
    /**
     * Gombafonal elhelyezése két tekton között.
     * @param tecton1 Az első tekton
     * @param tecton2 A második tekton
     * @return void
     */
    void PlaceShroomThread(Tecton tecton1, Tecton tecton2);
    
    /**
     * Gombatest elhelyezése egy tektonon.
     * @param tecton A tekton, amelyre a gombatestet helyezzük
     * @return void
     */
    void PlaceShroomBody(Tecton tecton);
    
    /**
     * Gombatest fejlesztése.
     * @param body A fejlesztendő gombatest
     * @return void
     */
    void UpgradeShroomBody(ShroomBody body);
    
    /**
     * Spóra dobása egy gombatestről egy tektonra.
     * @param body A gombatest, amelyről a spórát dobjuk
     * @param tecton A célpont tekton
     * @return void
     */
    void ThrowSpore(ShroomBody body, Tecton tecton);
    
    /**
     * Rovar felfalása egy gombafonallal.
     * @param thread A gombafonal, amely felfalja a rovart
     * @param bug A felfalandó rovar
     * @return void
     */
    void ShroomThreadDevourBug(ShroomThread thread, Bug bug);
    
    /**
     * A gomba frissítése.
     * Ez a metódus minden kör elején meghívódik.
     * @return void
     */
    void UpdateShroom();
}
