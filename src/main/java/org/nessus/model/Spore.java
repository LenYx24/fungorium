package org.nessus.model;

import org.nessus.Skeleton;
import org.nessus.model.effect.*;

public class Spore {
    private Shroom shroom;
    private Tecton tecton;

    int nutrient = 3;

    public Spore(Shroom shroom, Tecton tecton) {
        this.shroom = shroom;
        this.tecton = tecton;
    }

    public void EatenBy(Bug bug) {
        Skeleton.LogFunctionCall(this, "EatenBy", bug);
        bug.AddNutrients(nutrient);
        bug.AddEffect(Skeleton.WhichEffect());
        tecton.RemoveSpore(this);
        shroom.RemoveSpore(this);
        Skeleton.LogReturnCall(this, "EatenBy");
    }

    public Shroom GetShroom() {
        return shroom;
    }

    public Tecton GetTecton() {
        Skeleton.LogFunctionCall(this, "GetTecton");
        Skeleton.LogReturnCall(this, "GetTecton", tecton);
        return tecton;
    }

    public void SetTecton(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "SetTecton", tecton);
        this.tecton = tecton;
        Skeleton.LogReturnCall(this, "SetTecton");
    }
}
