package org.nessus.model.effect;

import org.nessus.model.Bug;

/**
 * Az osztály a "bénítás" hatását reprezentálja.
 * A "bénítás" hatására a rovarok nem tudnak mozogni.
 * A hatás három alkalommal alkalmazható.
 * A {@link BugEffect} osztályból származik le.
 * @see org.nessus.model.Bug
 * @see org.nessus.model.effect.BugEffect
 */
public class CripplingEffect extends BugEffect {
    /**
     * A hatás alkalmazása a rovarokra.
     * @param bug
     */
    public void Apply(Bug bug) {
        bug.SetCanMove(false);
        UpdateState(bug);
    }
}
