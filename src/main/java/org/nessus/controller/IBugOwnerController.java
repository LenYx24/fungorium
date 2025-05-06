package org.nessus.controller;

import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;

public interface IBugOwnerController {
    void Move(Bug bug, Tecton tecton);
    void Eat(Bug bug, Spore spore);
    void CutThread(Bug bug, ShroomThread shroomThread);
    void UpdateBugOwner();
}
