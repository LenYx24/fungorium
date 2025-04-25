package org.nessus.controller;

import org.nessus.model.effect.BugEffect;

public interface IRandomProvider {
    int RandomNumber(int min, int max);
    BugEffect RandomBugEffect();
    boolean RandomBoolean();
    void SetSeed(long seed);
}
