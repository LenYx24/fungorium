package org.nessus.model.tecton;

import org.nessus.view.View;
import org.nessus.model.shroom.ShroomThread;

public class ThreadSustainerTecton extends Tecton {
    @Override
    public Tecton Copy() {
        Tecton copyTecton = new ThreadSustainerTecton();
        View.GetObjectStore().AddObject("copyTecton", copyTecton);
        return copyTecton;
    }

    @Override
    public boolean GrowShroomThread(ShroomThread thread) {
        thread.SetSustained();
        return super.GrowShroomThread(thread);
    }
}
