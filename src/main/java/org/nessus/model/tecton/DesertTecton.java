package org.nessus.model.tecton;

import org.nessus.Skeleton;
import org.nessus.model.*;
import java.util.*;

public class DesertTecton extends Tecton {
    private HashMap<ShroomThread, Integer> decayTimers;

    public DesertTecton() {
        decayTimers = new HashMap<>();
    }

    @Override
    public Tecton Copy() {
        return new DesertTecton();
    }

    @Override
    public boolean GrowShroomThread(ShroomThread thread)
    {
        Skeleton.LogFunctionCall(this, "GrowShroomThread");
        this.shroomThreads.add(thread);
        this.decayTimers.put(thread, 2);
        Skeleton.LogReturnCall(this, "GrowShroomThread", true);
        return true;
    }

    @Override
    public void UpdateTecton()
    {
        Skeleton.LogFunctionCall(this, "UpdateTecton");

        for (ShroomThread thread : List.copyOf(shroomThreads)) {
            var decayTimer = decayTimers.get(thread);

            if (decayTimer <= 0)
            {
                thread.Remove();
                thread.GetTecton1().RemoveShroomThread(thread);
                thread.GetTecton2().RemoveShroomThread(thread);
                thread.GetShroom().RemoveShroomThread(thread);

                decayTimers.remove(thread);
            }
            else
            {
                decayTimers.replace(thread, decayTimer - 1);
            }
        }

        Skeleton.LogReturnCall(this, "UpdateTecton");
    }
}
