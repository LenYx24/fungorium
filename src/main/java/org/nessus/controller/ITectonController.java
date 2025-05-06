package org.nessus.controller;

import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;

public interface ITectonController {
    void UpdateTecton();
    void Split();
    void SetNeighbour(Tecton tecton);
    void SetShroomBody(ShroomBody body);
    void AddBug(Bug bug);
    void ThrowSpore(Spore spore);
    boolean GrowShroomThread(ShroomThread thread);
}
