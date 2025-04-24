package org.nessus.controller;

import org.nessus.model.effect.BugEffect;

public interface IRandomProvider {
    int GetNumber(int min, int max);
    BugEffect GetBugEffect();
    void SetSeed(long seed);
}
