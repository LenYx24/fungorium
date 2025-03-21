package org.nessus.model;

import org.nessus.Skeleton;

public class ActionPointCatalog {
    int defaultActionPoints = 5;
    int currentActionPoints = 5;

    public boolean HasEnoughPoints(int cost) {
        Skeleton.LogFunctionCall(this, "HasEnoughPoints", cost);
        boolean hasEnoughPoints = Skeleton.YesNoQuestion("Van elég pontja a rovarásznak a rovar mozgatásához?");
        Skeleton.LogReturnCall(this, "HasEnoughPoints", hasEnoughPoints);
        return hasEnoughPoints;
    }

    public void DecreasePoints(int cost) {
        Skeleton.LogFunctionCall(this, "DecreasePoints", cost);
        currentActionPoints -= cost;
        Skeleton.LogReturnCall(this, "DecreasePoints");
    }

    public void ResetPoints() {
        currentActionPoints = defaultActionPoints;
    }
}
