package org.nessus.model.effect;

import org.nessus.model.Bug;

public class SlowEffect extends BugEffect {
    public void Apply(Bug bug) {
        bug.AddMoveCost(+2);
        UpdateState(bug);
    }
}