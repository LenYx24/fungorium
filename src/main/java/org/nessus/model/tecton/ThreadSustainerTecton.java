package org.nessus.model.tecton;

import org.nessus.model.shroom.ShroomThread;
import org.nessus.view.TectonTexturer;

/**
 * A {@link Tecton} osztályból származó osztály, amely a fonál fenntartására szolgál.
 * A {@link ThreadSustainerTecton} osztály a fonál fenntartására szolgál.
 * A {@link Tecton} osztályból származik le.
 * @see org.nessus.model.tecton.Tecton
 */
public class ThreadSustainerTecton extends Tecton {
    /**
     * Az osztály másoló konstruktora.
     * @return Az új példány.
     * @see org.nessus.model.tecton.Tecton#Copy()
     */
    @Override
    public Tecton Copy() {
        return new ThreadSustainerTecton();
    }

    /**
     * A fonál növesztése a tektontípuson.
     * @param thread - A növesztendő fonál.
     * @return Sikeres volt-e a növesztés.
     */
    @Override
    public boolean GrowShroomThread(ShroomThread thread) {
        thread.SetSustained();
        return super.GrowShroomThread(thread);
    }

    @Override
    public void accept(TectonTexturer texturer) {
        texturer.visit(this);
    }
}
