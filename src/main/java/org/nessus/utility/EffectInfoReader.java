package org.nessus.utility;

import org.nessus.model.effect.BugEffect;
import org.nessus.model.effect.CoffeeEffect;
import org.nessus.model.effect.CripplingEffect;
import org.nessus.model.effect.DivisionEffect;
import org.nessus.model.effect.JawLockEffect;
import org.nessus.model.effect.SlowEffect;

/**
 * Az EffectInfoReader osztály felelős a rovar-hatások információinak olvasásáért és formázásáért.
 * A látogatóminta (Visitor Pattern) implementációját használja a különböző hatástípusok kezelésére.
 */
public class EffectInfoReader {
    private String effectInfo; // A hatás információi

    /**
     * Beállítja a hatás információit a megadott névvel és hatással.
     * 
     * @param effectName A hatás neve
     * @param effect A hatás objektum
     * @return void
     */
    private void SetInfo(String effectName, BugEffect effect) {
        effectInfo = effectName + ": " + effect.GetRemainingUses() + " kör";
    }

    /**
     * Visszaadja a hatás információit.
     * 
     * @return String - A hatás információi
     */
    public String GetInfo() {
        return effectInfo;
    }

    /**
     * Feldolgozza a CoffeeEffect típusú hatást.
     * 
     * @param effect A feldolgozandó CoffeeEffect
     * @return void
     */
    public void Visit(CoffeeEffect effect) {
        SetInfo("Gyorsítva", effect);
    }

    /**
     * Feldolgozza a CripplingEffect típusú hatást.
     * 
     * @param effect A feldolgozandó CripplingEffect
     * @return void
     */
    public void Visit(CripplingEffect effect) {
        SetInfo("Bénítva", effect);
    }

    /**
     * Feldolgozza a JawLockEffect típusú hatást.
     * 
     * @param effect A feldolgozandó JawLockEffect
     * @return void
     */
    public void Visit(JawLockEffect effect) {
        SetInfo("Szájzár", effect);
    }
    
    /**
     * Feldolgozza a SlowEffect típusú hatást.
     * 
     * @param effect A feldolgozandó SlowEffect
     * @return void
     */
    public void Visit(SlowEffect effect) {
        SetInfo("Lassítva", effect);
    }
    
    /**
     * Feldolgozza a DivisionEffect típusú hatást.
     * A kettéosztódás egyszeri effekt, nincs értelme megjeleníteni.
     * 
     * @param effect A feldolgozandó DivisionEffect
     * @return void
     */
    public void Visit(DivisionEffect effect) { /* A kettéosztódás egyszeri effekt, nincs értelme megjeleníteni */ }
}
