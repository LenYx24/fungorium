package org.nessus.model.effect;

import org.nessus.model.Bug;

/**
 * Absztrakt osztály, mely a rovarok hatásait reprezentálja.
 * A hatásoknak három használati lehetőségük van, melyek után eltűnnek.
 * A hatásokat a rovarokra lehet alkalmazni.
 * 
 * @see org.nessus.model.Bug
 */
public abstract class BugEffect {
    int remainingUses = 3;

    protected void UpdateState(Bug bug) {
        remainingUses--;
        if (remainingUses == 0) {
            bug.ClearEffect(this);
        }
    }

    public abstract void Apply(Bug bug);
}
