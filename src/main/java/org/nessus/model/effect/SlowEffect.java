package org.nessus.model.effect;

import org.nessus.model.bug.Bug;
import org.nessus.utility.EffectInfoReader;

/**
 * Az osztály a "lassítás" hatását reprezentálja.
 * A "lassítás" hatására a rovarok mozgási költsége nő 2-vel.
 * A hatás három alkalommal alkalmazható.
 * A {@link BugEffect} osztályból származik le.
 * @see org.nessus.model.bug.Bug
 * @see org.nessus.model.effect.BugEffect
 */
public class SlowEffect extends BugEffect {
    /**
     * A hatás alkalmazása a rovarokra.
     * @param bug
     */
    public void ApplyOn(Bug bug) {
        bug.AddMoveCost(+2);
        UpdateState(bug);
    }

    @Override
    public void Accept(EffectInfoReader reader) {
        reader.Visit(this);
    }
}