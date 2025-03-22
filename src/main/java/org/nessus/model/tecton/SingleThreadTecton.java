package org.nessus.model.tecton;

import org.nessus.Skeleton;
import org.nessus.model.ShroomThread;
import org.nessus.model.Tecton;

public class SingleThreadTecton extends Tecton {
    public Tecton Copy() {
        return new SingleThreadTecton();
    }

    @Override
    public void GrowShroomThread(ShroomThread thread)
    {
        Skeleton.LogFunctionCall(this, "GrowShroomThread");
        if (this.getThreads().isEmpty())
        {
            shroomThreads.add(thread);
        }
        else
        {
            throw new RuntimeException("Az egyfonalú tektonon csak egy fonal lehet!");
        }
        Skeleton.LogReturnCall(this, "GrowShroomThread");
    }
}
