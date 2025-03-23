package org.nessus.model.tecton;

import org.nessus.Skeleton;
import org.nessus.model.ShroomThread;
import org.nessus.model.Tecton;

/**
 * Egyfonál tekton, azaz ezen a tektonfajtán csak egy fonál lehet.
 * Ha már van fonál, akkor nem lehet újat hozzáadni.
 * Ha nincs fonál, akkor a hozzáadott fonál bekerül a tektonba.
 * A {@link Tecton} osztályból származik le.
 * @see org.nessus.model.Tecton
 * @see org.nessus.model.ShroomThread
 */
public class SingleThreadTecton extends Tecton {
    /**
     * Az osztály konstruktora.
     * @return Az új példány.
     */
    @Override
    public Tecton Copy() {
        Skeleton.LogFunctionCall(this, "Copy");

        Tecton copyTecton = new SingleThreadTecton();
        Skeleton.AddObject(copyTecton, "copyTecton");
        SpreadEntities(copyTecton);

        Skeleton.LogReturnCall(this, "Copy");
        return copyTecton;
    }

    /**
     * A fonál növesztése a tektontípuson.
     * @param thread
     * @return Sikeres volt-e a növesztés.
     */
    @Override
    public boolean GrowShroomThread(ShroomThread thread) {
        Skeleton.LogFunctionCall(this, "GrowShroomThread", thread);
        if (this.getThreads().isEmpty()) {
            shroomThreads.add(thread);
            Skeleton.LogReturnCall(this, "GrowShroomThread", true);
            return true;
        } else {
            Skeleton.LogReturnCall(this, "GrowShroomThread", false);
            return false;
        }
    }
}
