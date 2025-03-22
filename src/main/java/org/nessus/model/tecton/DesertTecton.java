package org.nessus.model.tecton;

import org.nessus.Skeleton;
import org.nessus.model.ShroomThread;
import org.nessus.model.Tecton;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DesertTecton extends Tecton {
    private HashMap<ShroomThread, Integer> decayTimers;

    public DesertTecton() {
        decayTimers = new HashMap<>();
    }

    public Tecton Copy() {
        return new DesertTecton();
    }

    @Override
    public boolean GrowShroomThread(ShroomThread thread)
    {
        Skeleton.LogFunctionCall(this, "GrowShroomThread");
        this.shroomThreads.add(thread);
        this.decayTimers.put(thread, 3);
        Skeleton.LogReturnCall(this, "GrowShroomThread", true);
        return true;
    }

    @Override
    public void UpdateTecton()
    {
        Skeleton.LogFunctionCall(this, "UpdateTecton");

        var iter = decayTimers.entrySet().iterator();
        while (iter.hasNext())
        {
            var entry = iter.next();
            var key = entry.getKey();

            if (entry.getValue()-1 <= 0)
            {
                key.Remove();
                key.GetTecton1().RemoveShroomThread(key);
                key.GetTecton2().RemoveShroomThread(key);
                key.GetShroom().RemoveShroomThread(key);

                iter.remove();
            }
            else
            {
                entry.setValue(entry.getValue()-1);
            }
        }

        Skeleton.LogReturnCall(this, "UpdateTecton");
        // TODO: Megnézni hogy decayelt-e egy thread és megölni
    }
}
