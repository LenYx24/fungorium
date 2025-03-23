package org.nessus.model;

import org.nessus.Skeleton;

/**
 * A cselekvési pontokat reprezentáló osztály.
 * A cselekvési pontokat a játékosok használják a cselekvéseik végrehajtásához.
 * @see org.nessus.model.ActionPointCatalog
 */
public class ActionPointCatalog {
    int defaultActionPoints = 5; // alapértelmezett cselekvési pontok száma
    int currentActionPoints = 5; // jelenlegi cselekvési pontok száma

    /**
     * Visszaadja, hogy van-e elég pont a cselekvéshez.
     * @param cost
     * @return Van-e elég pont.
     */
    public boolean HasEnoughPoints(int cost) {
        Skeleton.LogFunctionCall(this, "HasEnoughPoints", cost);
        boolean hasEnoughPoints = Skeleton.YesNoQuestion("Van elég pont erre a cselekvésre?");
        Skeleton.LogReturnCall(this, "HasEnoughPoints", hasEnoughPoints);
        return hasEnoughPoints;
    }

    /**
     * Csökkenti a pontok számát.
     * @param cost
     */
    public void DecreasePoints(int cost) {
        Skeleton.LogFunctionCall(this, "DecreasePoints", cost);
        currentActionPoints -= cost;
        Skeleton.LogReturnCall(this, "DecreasePoints");
    }

    /**
     * Alaphelyzetbe állítja a pontokat.
     * @see org.nessus.model.ActionPointCatalog#defaultActionPoints
     */
    public void ResetPoints() {
        Skeleton.LogFunctionCall(this, "ResetPoints");
        currentActionPoints = defaultActionPoints;
        Skeleton.LogReturnCall(this, "ResetPoints");
    }
}
