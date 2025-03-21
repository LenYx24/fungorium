package org.nessus.model;

public class ShroomThread {
    Tecton tecton1 = null;
    Tecton tecton2 = null;

    int evolution = 0;
    int isolationCounter = 0;
    boolean connectedToShroomBody = false;
    
    public ShroomThread(Tecton tecton1, Tecton tecton2) {
        this.tecton1 = tecton1;
        this.tecton2 = tecton2;
    }

    public void ValidateLife() {
    }

    public boolean IsTectonReachable(Tecton tecton) {
        return true;
    }

    public void Remove() {

    }
}
