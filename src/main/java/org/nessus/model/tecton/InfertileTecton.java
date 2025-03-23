package org.nessus.model.tecton;

import org.nessus.Skeleton;
import org.nessus.model.ShroomBody;
import org.nessus.model.Tecton;

public class InfertileTecton extends Tecton {
    @Override
    public Tecton Copy() {
        return new InfertileTecton();
    }

    @Override
    public boolean GrowShroomBody(ShroomBody body)
    {
        Skeleton.LogFunctionCall(this, "GrowShroomBody", body);
        Skeleton.LogReturnCall(this, "GrowShroomBody", false);
        return false;
    }
}
