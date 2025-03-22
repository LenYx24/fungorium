package org.nessus.model;

import org.nessus.Skeleton;

public class ShroomThread {
    private Shroom shroom;
    private Tecton tecton1;
    private Tecton tecton2;

    private boolean growthBoost1;
    private boolean growthBoost2;

    int evolution = 0;
    int isolationCounter = 0;
    boolean connectedToShroomBody = false;

    public ShroomThread(Shroom s, Tecton tecton1, Tecton tecton2) {
        this.shroom = s;
        this.tecton1 = tecton1;
        this.tecton2 = tecton2;
    }

    public void ValidateLife()
    {
        Skeleton.LogFunctionCall(this, "ValidateLife");
        if (tecton1.HasSporeOfShroom(this.shroom))
        {
            growthBoost1 = true;
        }

        if (tecton2.HasSporeOfShroom(this.shroom))
        {
            growthBoost2 = true;
        }

        boolean decayed = Skeleton.YesNoQuestion("Halott ez a fonál?");

        if (decayed)
        {
            this.Remove();
            tecton1.RemoveShroomThread(this);
            tecton2.RemoveShroomThread(this);
            shroom.RemoveShroomThread(this);
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
