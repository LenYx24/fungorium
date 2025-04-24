package org.nessus.model.tecton;

import org.nessus.model.shroom.ShroomBody;
import org.nessus.view.View;

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
        Tecton copyTecton = new InfertileTecton();
        View.AddObject( "copyTecton", copyTecton);
        SpreadEntities(copyTecton);
        return copyTecton;
    }

    /**
     * A gombatest növesztése a tektontípuson.
     * @param body
     * @return Sikeres volt-e a növesztés.
     */
    @Override
    public boolean GrowShroomBody(ShroomBody body) {
        return false;
    }
}
