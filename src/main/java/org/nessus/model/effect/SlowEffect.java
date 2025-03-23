package org.nessus.model.effect;

import org.nessus.model.Bug;

/**
 * Az osztály a "lassítás" hatását reprezentálja.
 * A "lassítás" hatására a rovarok mozgási költsége nő 2-vel.
 * A hatás három alkalommal alkalmazható.
 * A {@link BugEffect} osztályból származik le.
 * @see org.nessus.model.Bug
 * @see org.nessus.model.effect.BugEffect
 */
public class SlowEffect extends BugEffect {
    /**
     * A hatás alkalmazása a rovarokra.
     * @param bug
     */
    public void Apply(Bug bug) {
        bug.AddMoveCost(+2);
        UpdateState(bug);
    }
}