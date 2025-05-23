package org.nessus.model.effect;

import org.nessus.Skeleton;
import org.nessus.model.Bug;

/**
 * Az osztály a "kávé" hatását reprezentálja.
 * A "kávé" hatására a rovarok mozgási költsége csökken 2-vel.
 * A hatás három alkalommal alkalmazható.
 * A {@link BugEffect} osztályból származik le.
 * @see org.nessus.model.Bug
 * @see org.nessus.model.effect.BugEffect
 */
public class CoffeeEffect extends BugEffect {
    /**
     * A hatás alkalmazása a rovarokra.
     * @param bug
     */
    public void ApplyOn(Bug bug) {
        Skeleton.LogFunctionCall(this, "ApplyOn", bug);
        bug.AddMoveCost(-2);
        UpdateState(bug);
        Skeleton.LogReturnCall(this, "ApplyOn");
    }
}
