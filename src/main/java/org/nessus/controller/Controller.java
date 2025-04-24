package org.nessus.controller;

import java.util.Random;

import org.nessus.model.effect.BugEffect;
import org.nessus.model.effect.CoffeeEffect;
import org.nessus.model.effect.CripplingEffect;
import org.nessus.model.effect.DivisionEffect;
import org.nessus.model.effect.JawLockEffect;
import org.nessus.model.effect.SlowEffect;

public class Controller implements IRandomProvider {
    private static Random rand = new Random();

    @Override
    public int GetNumber(int min, int max) {
        return rand.nextInt(min, max + 1);
    }

    @Override
    public BugEffect GetBugEffect() {
        return switch (GetNumber(1, 5)) {
            case 1 -> new CoffeeEffect();
            case 2 -> new SlowEffect();
            case 3 -> new JawLockEffect();
            case 4 -> new CripplingEffect();
            default -> new DivisionEffect();
        };
    }

    @Override
    public void SetSeed(long seed) {
        rand.setSeed(seed);
    }
}
