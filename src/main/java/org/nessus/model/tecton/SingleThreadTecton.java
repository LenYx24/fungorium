package org.nessus.model.tecton;

import org.nessus.View;
import org.nessus.model.shroom.ShroomThread;

/**
 * Egyfonál tekton, azaz ezen a tektonfajtán csak egy fonál lehet.
 * Ha már van fonál, akkor nem lehet újat hozzáadni.
 * Ha nincs fonál, akkor a hozzáadott fonál bekerül a tektonba.
 * A {@link Tecton} osztályból származik le.
 * @see org.nessus.model.tecton.Tecton
 * @see org.nessus.model.shroom.ShroomThread
 */
public class SingleThreadTecton extends Tecton {
    /**
     * Az osztály konstruktora.
     * @return Az új példány.
     */
    @Override
    public Tecton Copy() {
        Tecton copyTecton = new SingleThreadTecton();
        View.AddObject( "copyTecton", copyTecton);
        SpreadEntities(copyTecton);
        return copyTecton;
    }

    /**
     * A fonál növesztése a tektontípuson.
     * @param thread
     * @return Sikeres volt-e a növesztés.
     */
    @Override
    public boolean GrowShroomThread(ShroomThread thread) {
        if (shroomThreads.isEmpty()) {
            shroomThreads.add(thread);
            return true;
        } else {
            return false;
        }
    }
}
