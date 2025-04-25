package org.nessus.model.tecton;

import org.nessus.View;
import org.nessus.model.shroom.ShroomThread;

public class ThreadSustainerTecton extends Tecton {
    @Override
    public Tecton Copy() {
        Tecton copyTecton = new ThreadSustainerTecton();
        View.AddObject(copyTecton, "copyTecton");
        SpreadEntities(copyTecton);
        return copyTecton;
    }

    @Override
    public boolean GrowShroomThread(ShroomThread thread) {
        thread.SetSustained();
        return super.GrowShroomThread(thread);
    }
}
