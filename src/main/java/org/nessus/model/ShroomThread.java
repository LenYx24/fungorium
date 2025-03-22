package org.nessus.model;

import org.nessus.Skeleton;

public class ShroomThread {
    Shroom shroom;
    Tecton tecton1 = null;
    Tecton tecton2 = null;

    int evolution = 0;
    int isolationCounter = 0;
    boolean connectedToShroomBody = false;

    public ShroomThread(Tecton tecton1, Tecton tecton2) {
        this.tecton1 = tecton1;
        this.tecton2 = tecton2;
    }
    
    // Ez a konstruktor beállítja a gombafajt is
    public ShroomThread(Shroom shroom, Tecton tecton1, Tecton tecton2) {
        this(tecton1, tecton2);
        this.shroom = shroom;
    }

    public void ValidateLife() {
    }

    public boolean IsTectonReachable(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "IsTectonReachable", tecton);
        boolean isTectonReachable = Skeleton.YesNoQuestion("A t nevű fonal egyik végpontja a t2 tecton?");
        Skeleton.LogReturnCall(this, "IsTectonReachable", isTectonReachable);
        return isTectonReachable;
    }

    public void Remove() {

    }
}
