package org.nessus.model.tecton;

import org.nessus.model.shroom.ShroomBody;
import org.nessus.utility.ITectonVisitor;

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

    /**
     * Beállítja a gombatestet a tektontípuson.
     * @param body - A gombatest.
     * @return Boolean - Sikeres volt-e a beállítás. (Ez ebben az esetben mindig false)
     */
    @Override
    public boolean SetShroomBody(ShroomBody body) {
        return false;
    }

    /**
     * Elfogadja a látogatót.
     * A látogató a DesertTecton osztályt látogatja meg (ITectonVisitor).
     * @param visitor - A látogató.
     * @see org.nessus.utility.ITectonVisitor
     * @return void
     */
    @Override
    public void Accept(ITectonVisitor visitor) {
        visitor.Visit(this);
    }
}
