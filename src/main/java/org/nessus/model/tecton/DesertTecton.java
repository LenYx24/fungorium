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
    public void GrowShroomThread(ShroomThread thread)
    {
        Skeleton.LogFunctionCall(this, "GrowShroomThread");
        this.shroomThreads.add(thread);
        this.decayTimers.put(thread, 3);
        Skeleton.LogReturnCall(this, "GrowShroomThread");
    }

    @Override
    public void UpdateTecton()
    {
        Skeleton.LogFunctionCall(this, "UpdateTecton");

        Iterator<Map.Entry<ShroomThread, Integer>> iter = decayTimers.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry<ShroomThread, Integer> entry = iter.next();

            if (entry.getValue()-1 <= 0)
            {
                entry.getKey().Remove();
                entry.getKey().GetTecton1().RemoveShroomThread(entry.getKey());
                entry.getKey().GetTecton2().RemoveShroomThread(entry.getKey());
                entry.getKey().GetShroom().RemoveShroomThread(entry.getKey());

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
