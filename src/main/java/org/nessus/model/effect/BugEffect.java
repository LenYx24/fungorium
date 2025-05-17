package org.nessus.model.effect;

import org.nessus.model.bug.Bug;
import org.nessus.utility.EffectInfoReader;

/**
 * Absztrakt osztály, mely a rovarok hatásait reprezentálja.
 * A hatásoknak három használati lehetőségük van, melyek után eltűnnek.
 * A hatásokat a rovarokra lehet alkalmazni.
 * 
 * @see org.nessus.model.bug.Bug
 */
public abstract class BugEffect {
    int remainingUses = 3; // A hatás hátralévő használatainak száma.

    /**
     * A hatások alkalmazása után a hatások állapotának frissítése.
     * Ha a hatások használata elfogyott, akkor a hatások eltűnnek.
     * @param bug
     */
    protected void UpdateState(Bug bug) {
        if (remainingUses == 0) {
            bug.ClearEffect(this);
            bug.UpdateBug();
        } else {
            remainingUses--;
        }
    }

    public int GetRemainingUses() {
        return remainingUses;
    }

    /**
     * A hatások alkalmazása a rovarokra.
     * @param bug
     */
    public abstract void ApplyOn(Bug bug);


    public abstract void Accept(EffectInfoReader reader);
}
