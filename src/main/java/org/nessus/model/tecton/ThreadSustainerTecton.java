package org.nessus.model.tecton;

import org.nessus.model.shroom.ShroomThread;

public class ThreadSustainerTecton extends Tecton {
    @Override
    public Tecton Copy() {
        return new ThreadSustainerTecton();
    }

    @Override
    public boolean GrowShroomThread(ShroomThread thread) {
        thread.SetSustained();
        return super.GrowShroomThread(thread);
    }
}
