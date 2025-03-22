package org.nessus.model.tecton;

import org.nessus.Skeleton;
import org.nessus.model.ShroomThread;
import org.nessus.model.Tecton;

public class SingleThreadTecton extends Tecton {
    public Tecton Copy() {
        return new SingleThreadTecton();
    }

    @Override
    public boolean GrowShroomThread(ShroomThread thread)  {
        Skeleton.LogFunctionCall(this, "GrowShroomThread");
        if (this.getThreads().isEmpty())
        {
            shroomThreads.add(thread);
            Skeleton.LogReturnCall(this, "GrowShroomThread", true);
            return true;
        }

        else
        {
            Skeleton.LogReturnCall(this, "GrowShroomThread", false);
            return false;
        }
    }
}
