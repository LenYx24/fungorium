package org.nessus.controller;

import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.tecton.Tecton;

public interface IShroomController {
    void PlaceShroomThread(Tecton tecton1, Tecton tecton2);
    void PlaceShroomBody(Tecton tecton);
    void UpgradeShroomBody(ShroomBody body);
    void ThrowSpore(ShroomBody body, Tecton tecton);
    void ShroomThreadDevourBug(ShroomThread thread, Bug bug);
    void UpdateShroom();
}
