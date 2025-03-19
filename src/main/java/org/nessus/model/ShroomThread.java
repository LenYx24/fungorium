package org.nessus.model;

public class ShroomThread {
    Tecton tecton1 = null;
    Tecton tecton2 = null;

    public ShroomThread(Tecton tecton1, Tecton tecton2) {
        this.tecton1 = tecton1;
        this.tecton2 = tecton2;
    }

    int evolution = 0;
    int decayCounter = 0;
    boolean decaying = false;

    public void ValideLife(){}
    public void Remove(){

    }
    public void Decay(){}
}
