package org.nessus.model.tecton;

import org.nessus.Skeleton;
import org.nessus.model.ShroomBody;
import org.nessus.model.Tecton;

/**
 * Ezen a tektontípuson nem lehet gombatestet növeszteni.
 * A {@link Tecton} osztályból származik le.
 */
public class InfertileTecton extends Tecton {
    /**
     * Az osztály másoló konstruktora.
     * @return Az új példány.
     */
    @Override
    public Tecton Copy() {
        Skeleton.LogFunctionCall(this, "Copy");

        Tecton copyTecton = new InfertileTecton();
        Skeleton.AddObject(copyTecton, "copyTecton");
        SpreadEntities(copyTecton);

        Skeleton.LogReturnCall(this, "Copy");
        return copyTecton;
    }

    /**
     * A gombatest növesztése a tektontípuson.
     * @param body
     * @return Sikeres volt-e a növesztés.
     */
    @Override
    public boolean GrowShroomBody(ShroomBody body) {
        Skeleton.LogFunctionCall(this, "GrowShroomBody");
        Skeleton.LogFunctionCall(this, "GrowShroomBody", body);
        Skeleton.LogReturnCall(this, "GrowShroomBody", false);
        return false;
    }
}
