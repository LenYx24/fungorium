package org.nessus.model;

public class SingleThreadTecton extends Tecton{
    public Tecton Copy(){return new SingleThreadTecton();}
    @Override
    public void GrowThread(ShroomThread thread){
        System.out.println("Grow thread 2");
    }
}
