package org.nessus.model.tecton;

import org.nessus.model.shroom.ShroomThread;
import org.nessus.utility.ITectonVisitor;

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
        return new SingleThreadTecton();
    }

    /**
     * A fonál növesztése a tektontípuson.
     * @param thread
     * @return Sikeres volt-e a növesztés.
     */
    @Override
    public boolean GrowShroomThread(ShroomThread thread) {
        if (threads.isEmpty()) {
            threads.add(thread);
            return true;
        }
        
        return false;
    }

    @Override
    public void Accept(ITectonVisitor visitor) {
        visitor.Visit(this);
    }
}
