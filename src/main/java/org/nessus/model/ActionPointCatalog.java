package org.nessus.model;

public class ActionPointCatalog {
    int defaultActionPoints = 5;
    int currentActionPoints = 5;
    public boolean HasEnoughPoints(int cost){
        return currentActionPoints >= cost;
    }
    public void DecreasePoints(int cost){
        currentActionPoints -= cost;
    }
    public void ResetPoints(){
        currentActionPoints = defaultActionPoints;
    }
}
