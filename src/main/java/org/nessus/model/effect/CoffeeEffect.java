package org.nessus.model.effect;

import org.nessus.model.bug.Bug;
import org.nessus.utility.EffectInfoReader;

/**
 * Az osztály a "kávé" hatását reprezentálja.
 * A "kávé" hatására a rovarok mozgási költsége csökken 2-vel.
 * A hatás három alkalommal alkalmazható.
 * A {@link BugEffect} osztályból származik le.
 * @see org.nessus.model.bug.Bug
 * @see org.nessus.model.effect.BugEffect
 */
public class CoffeeEffect extends BugEffect {
    /**
     * A hatás alkalmazása a rovarokra.
     * @param bug
     */
    public void ApplyOn(Bug bug) {
        bug.AddMoveCost(-1);
        UpdateState(bug);
    }

    @Override
    public void Accept(EffectInfoReader reader) {
        reader.Visit(this);
    }
}
