package org.nessus.model.effect;

import org.nessus.Skeleton;
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
    public void ApplyOn(Bug bug) {
        Skeleton.LogFunctionCall(this, "ApplyOn", bug);
        bug.SetCanCut(false);
        UpdateState(bug);
        Skeleton.LogReturnCall(this, "ApplyOn");
    }
}
