package org.nessus.model;

public class ShroomBody {
    private Tecton tecton = null;

    private int level = 1;
    private int remainingThrows = 5;
    private int SporeMaterials = 0;

    public ShroomBody(Tecton tecton) {this.tecton = tecton;}

    public void Upgrade(){
        level++;
    }
    public boolean IsTectonReachable(Tecton tecton){
        return false;
    }
    public void FormSpore(Tecton tecton){
        SporeMaterials -= 2;
        tecton.ThrowSpore(new Spore(tecton));
    }
    public void ProduceSpore(){}
}
