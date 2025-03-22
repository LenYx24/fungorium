package org.nessus.model;

import org.nessus.Skeleton;

public class ShroomBody {
    private Shroom shroom;
    private Tecton tecton = null;

    private int level = 1;
    private int remainingThrows = 5;
    private int sporeMaterials = 0;

    public ShroomBody(Shroom shroom, Tecton tecton) {
        this.shroom = shroom;
        this.tecton = tecton;
    }

    public Tecton GetTecton() {
        return tecton;
    }
  
    public Shroom GetShroom() {
        return shroom;
    }

    public void Upgrade(){
        Skeleton.LogFunctionCall(this, "Upgrade");
        level++;
        Skeleton.LogReturnCall(this, "Upgrade");
    }

    public boolean InRange(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "InRange", tecton);
        boolean inRange = Skeleton.YesNoQuestion("Elég közel van a tekton a spóraköpéshez?");
        Skeleton.LogReturnCall(this, "InRange", inRange);
        return inRange;
    }

    public Spore FormSpore(Tecton tecton){
        Skeleton.LogFunctionCall(this, "FormSpore", tecton);
        
        if (!InRange(tecton)) {
            Skeleton.LogReturnCall(this, "FormSpore", "null");
            return null;
        }

        sporeMaterials -= 2;
        remainingThrows--;
        
        Spore spore = new Spore(shroom, tecton);
        Skeleton.AddObject(spore, "spore");
        tecton.ThrowSpore(spore);
        
        if (remainingThrows <= 0) {
            tecton.ClearShroomBody();
            shroom.RemoveShroomBody(this);
        }

        Skeleton.LogReturnCall(this, "FormSpore", spore);
        return spore;
    }

    public void ProduceSpore(){}

    public void SetTecton(Tecton tecton) {
        this.tecton = tecton;
    }
  
    public void SetRemainingThrows(int remainingThrows) {
        Skeleton.LogFunctionCall(this, "SetRemainingThrows", remainingThrows);
        this.remainingThrows = remainingThrows;
        Skeleton.LogReturnCall(this, "SetRemainingThrows");
    }

    public void SetSporeMaterials(int sporeMaterials) {
        Skeleton.LogFunctionCall(this, "SetSporeMaterials", sporeMaterials);
        this.sporeMaterials = sporeMaterials;
        Skeleton.LogReturnCall(this, "SetSporeMaterials");
    }
}
