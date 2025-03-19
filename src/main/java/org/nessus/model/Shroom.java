package org.nessus.model;

import java.util.ArrayList;
import java.util.List;

public class Shroom {
    private List<Spore> spores = new ArrayList<>();
    private List<ShroomBody> shroomBodies = new ArrayList<>();
    private List<ShroomThread> threads = new ArrayList<>();
    private ActionPointCatalog actCatalog = new ActionPointCatalog();

    private int grownShroomBodies = 0;

    private int shroomThreadCost;
    private int shroomBodyCost;
    private int shroomUpgradeCost;
    private int sporeCost;
    public Shroom(){
        ResetPoints();
    }

    public void PlaceThread(Tecton tecton1, Tecton tecton2){
        if(actCatalog.HasEnoughPoints(shroomThreadCost)){
            ShroomThread thread = new ShroomThread(tecton1, tecton2);
            threads.add(thread);
            actCatalog.DecreasePoints(shroomThreadCost);
        }

    }
    public void PlaceShroomBody(Tecton tecton){
        if(actCatalog.HasEnoughPoints(shroomBodyCost)){
            ShroomBody shroomBody = new ShroomBody(tecton);
            boolean success = tecton.GrowShroomBody(shroomBody);
            if(success){
                grownShroomBodies++;
                shroomBodies.add(shroomBody);
            }
        }
    }
    public void UpgradeShroomBody(ShroomBody body){
        body.Upgrade();
    }
    public void ThrowSpore(ShroomBody body){

    }

    public void AddUpgradeCost(int value){

    }

    public void RemoveSpore(Spore spore){}
    public void RemoveShroomBody(ShroomBody body){}
    public void RemoveShroomThread(ShroomThread thread){}

    public void UpdateShroom(){}
    public void LoadDefaultCosts(){
        shroomThreadCost = 3;
        shroomBodyCost = 3;
        shroomUpgradeCost = 3;
        sporeCost = 3;
    }
    public void ResetPoints(){
        actCatalog.ResetPoints();
    }

    public int getGrownShroomBodies(){
        return grownShroomBodies;
    }
}
