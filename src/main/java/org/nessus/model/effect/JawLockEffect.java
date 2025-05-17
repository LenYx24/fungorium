package org.nessus.model.effect;

import org.nessus.model.bug.Bug;
import org.nessus.utility.EffectInfoReader;

/**
 * Az osztály a "állkapocs zár" hatását reprezentálja.
 * A "állkapocs zár" hatására a rovarok nem tudnak vágni.
 * A hatás három alkalommal alkalmazható.
 * A {@link BugEffect} osztályból származik le.
 * @see org.nessus.model.bug.Bug
 * @see org.nessus.model.effect.BugEffect
 */
public class JawLockEffect extends BugEffect {
    /**
     * A hatás alkalmazása a rovarokra.
     * @param bug
     */
    public void ApplyOn(Bug bug) {
        bug.SetCanCut(false);
        UpdateState(bug);
    }

    @Override
    public void Accept(EffectInfoReader reader) {
        reader.Visit(this);
    }
}
