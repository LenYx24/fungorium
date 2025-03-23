package org.nessus.model.effect;

import org.nessus.model.Bug;

/**
 * Az osztály a "állkapocs zár" hatását reprezentálja.
 * A "állkapocs zár" hatására a rovarok nem tudnak vágni.
 * A hatás három alkalommal alkalmazható.
 * A {@link BugEffect} osztályból származik le.
 * @see org.nessus.model.Bug
 * @see org.nessus.model.effect.BugEffect
 */
public class JawLockEffect extends BugEffect {
    /**
     * A hatás alkalmazása a rovarokra.
     * @param bug
     */
    public void Apply(Bug bug) {
        bug.SetCanCut(false);
        UpdateState(bug);
    }
}
