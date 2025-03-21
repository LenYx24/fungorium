package org.nessus.model.effect;

import org.nessus.model.Bug;

public class CoffeeEffect extends BugEffect {
    public void Apply(Bug bug) {
        bug.AddMoveCost(-2);
        UpdateState(bug);
    }
}
