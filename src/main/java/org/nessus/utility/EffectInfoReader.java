package org.nessus.utility;

import org.nessus.model.effect.BugEffect;
import org.nessus.model.effect.CoffeeEffect;
import org.nessus.model.effect.CripplingEffect;
import org.nessus.model.effect.DivisionEffect;
import org.nessus.model.effect.JawLockEffect;
import org.nessus.model.effect.SlowEffect;

public class EffectInfoReader {
    private String effectInfo;

    private void SetInfo(String effectName, BugEffect effect) {
        effectInfo = effectName + ": " + effect.GetRemainingUses() + " kör";
    }

    public String GetInfo() {
        return effectInfo;
    }

    public void Visit(CoffeeEffect effect) {
        SetInfo("Gyorsítva", effect);
    }

    public void Visit(CripplingEffect effect) {
        SetInfo("Bénítva", effect);
    }

    public void Visit(JawLockEffect effect) {
        SetInfo("Szájzár", effect);
    }
    
    public void Visit(SlowEffect effect) {
        SetInfo("Lassítva", effect);
    }
    
    public void Visit(DivisionEffect effect) { /* A kettéosztódás egyszeri effekt, nincs értelme megjeleníteni */ }
}
