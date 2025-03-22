package org.nessus.model;

import org.nessus.Skeleton;

public class ShroomThread {
    private Shroom shroom;
    private Tecton tecton1;
    private Tecton tecton2;

    int evolution = 0;
    int isolationCounter = 0;
    boolean connectedToShroomBody = false;

    private void Evolve(int n)
    {
        evolution = Math.min(evolution+n, 3);
    }

    public ShroomThread(Shroom s, Tecton tecton1, Tecton tecton2) {
        this.shroom = s;
        this.tecton1 = tecton1;
        this.tecton2 = tecton2;
    }
    
    // Ez a konstruktor beállítja a gombafajt is
    public ShroomThread(Shroom shroom, Tecton tecton1, Tecton tecton2) {
        this(tecton1, tecton2);
        this.shroom = shroom;
    }

    public void ValidateLife()
    {
        Skeleton.LogFunctionCall(this, "ValidateLife");
        boolean growthBoost1 = tecton1.HasSporeOfShroom(this.shroom);
        boolean growthBoost2 = tecton2.HasSporeOfShroom(this.shroom);

        Evolve((growthBoost1 || growthBoost2) ? 2 : 1);

        boolean decayed = Skeleton.YesNoQuestion("Felszívódott-e a fonal?");

        if (decayed)
        {
            this.Remove();
        }
        Skeleton.LogReturnCall(this, "ValidateLife");
    }

    public boolean IsTectonReachable(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "IsTectonReachable", tecton);
        boolean isTectonReachable = Skeleton.YesNoQuestion("A t nevű fonal egyik végpontja a t2 tecton?");
        Skeleton.LogReturnCall(this, "IsTectonReachable", isTectonReachable);
        return isTectonReachable;
    }

    public void Remove() {
        Skeleton.LogFunctionCall(this, "Remove");
        tecton1.RemoveShroomThread(this);
        tecton2.RemoveShroomThread(this);
        shroom.RemoveShroomThread(this);
        Skeleton.LogReturnCall(this, "Remove");
    }

    public Shroom GetShroom()
    {
        return shroom;
    }

    public Tecton GetTecton1()
    {
        return tecton1;
    }

    public Tecton GetTecton2()
    {
        return tecton2;
    }
}
