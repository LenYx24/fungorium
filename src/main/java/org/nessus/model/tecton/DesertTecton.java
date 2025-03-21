package org.nessus.model.tecton;

import org.nessus.model.ShroomThread;
import org.nessus.model.Tecton;

import java.util.HashMap;

public class DesertTecton extends Tecton {
    private HashMap<ShroomThread, Integer> decayTimers;

    public DesertTecton() {
        decayTimers = new HashMap<>();
    }

    public Tecton Copy() {
        return new DesertTecton();
    }

    @Override
    public void GrowShroomThread(ShroomThread thread) {
        System.out.println("Grow thread");
    }

    @Override
    public void UpdateTecton() {
        for (ShroomThread thread : decayTimers.keySet()) {
            decayTimers.replace(thread, decayTimers.get(thread) - 1);
        }
        // TODO: Megnézni hogy decayelt-e egy thread és megölni
    }
}
