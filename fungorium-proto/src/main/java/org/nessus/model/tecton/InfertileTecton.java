package org.nessus.model.tecton;

import org.nessus.model.shroom.ShroomBody;

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
        return new InfertileTecton();
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
