package org.nessus.controller;

import org.nessus.model.tecton.Tecton;

public interface ITectonController {
    void UpdateTecton();
    void Split();
    void SetNeighbour(Tecton tecton);
}
