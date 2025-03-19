package org.nessus.model;

public class InfertileTecton extends Tecton{
    public Tecton Copy(){return new InfertileTecton();}
    @Override
    public boolean GrowShroomBody(ShroomBody body){return true;}
}
