package org.nessus.model.tecton;

import org.nessus.model.ShroomBody;
import org.nessus.model.Tecton;

public class InfertileTecton extends Tecton {
    public Tecton Copy() {
        return new InfertileTecton();
    }

    @Override
    public boolean GrowShroomBody(ShroomBody body) {
        return false;
    }
}
