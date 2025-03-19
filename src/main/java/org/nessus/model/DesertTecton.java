package org.nessus.model;

public class DesertTecton extends Tecton{
    public Tecton Copy(){return new DesertTecton();}
    @Override
    public void GrowThread(ShroomThread thread){
        System.out.println("Grow thread");
    }
}
