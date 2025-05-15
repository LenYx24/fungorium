package org.nessus.model;

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
        return currentActionPoints >= cost;
    }

    /**
     * Csökkenti a pontok számát.
     * @param cost
     */
    public void DecreasePoints(int cost) {
        currentActionPoints -= cost;
    }
    /**
     * Növeli a pontok számát.
     * @param cost
     */
    public void IncreasePoints(int cost) {
        currentActionPoints += cost;
    }

    /**
     * Alaphelyzetbe állítja a pontokat.
     * @see org.nessus.model.ActionPointCatalog#defaultActionPoints
     */
    public void ResetPoints() {
        currentActionPoints = defaultActionPoints;
    }

    public int GetCurrentPoints() {return currentActionPoints;}

    public int GetDefaultPoints() {return defaultActionPoints;}
}
