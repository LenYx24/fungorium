package org.nessus.model.effect;

import org.nessus.model.Bug;

/**
 * Az osztály a "állkapocs zár" hatását reprezentálja.
 * A "állkapocs zár" hatására a rovarok nem tudnak vágni.
 * A hatás három alkalommal alkalmazható.
 * 
 * @see org.nessus.model.Bug
 * @see org.nessus.model.effect.BugEffect
 */
public class JawLockEffect extends BugEffect {
    public void Apply(Bug bug) {
        bug.SetCanCut(false);
        UpdateState(bug);
    }
}
