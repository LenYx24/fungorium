package org.nessus.model.tecton;

import org.nessus.model.ShroomThread;
import org.nessus.model.Tecton;

public class SingleThreadTecton extends Tecton {
    public Tecton Copy() {
        return new SingleThreadTecton();
    }

    @Override
    public void GrowShroomThread(ShroomThread thread) {
        System.out.println("Grow thread 2");
    }
}
