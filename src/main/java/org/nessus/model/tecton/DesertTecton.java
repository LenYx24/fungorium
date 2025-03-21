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
    public void GrowThread(ShroomThread thread) {
        System.out.println("Grow thread");
    }
}
