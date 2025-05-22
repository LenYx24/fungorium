package org.nessus.controller;

import org.nessus.model.effect.BugEffect;

/**
 * Véletlenszám-generátor interfész.
 * A játék során használt véletlenszerű értékek generálásáért felelős.
 */
public interface IRandomProvider {
    /**
     * Véletlenszám generálása egy adott tartományban.
     * @param min A tartomány alsó határa (beleértve)
     * @param max A tartomány felső határa (beleértve)
     * @return int - Egy véletlenszám a megadott tartományban
     */
    int RandomNumber(int min, int max);
    
    /**
     * Véletlenszerű rovar-hatás generálása.
     * @return BugEffect - Egy véletlenszerűen kiválasztott rovar-hatás
     */
    BugEffect RandomBugEffect();
    
    /**
     * Véletlenszerű logikai érték generálása.
     * @return Boolean - Véletlenszerű igaz vagy hamis érték
     */
    boolean RandomBoolean();
    
    /**
     * A véletlenszám-generátor magjának beállítása.
     * Ez lehetővé teszi a reprodukálható véletlenszám-sorozatok generálását.
     * @param seed A beállítandó mag érték
     * @return void
     */
    void SetSeed(long seed);
}
