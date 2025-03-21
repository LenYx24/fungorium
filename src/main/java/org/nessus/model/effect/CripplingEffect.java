package org.nessus.model.effect;

import org.nessus.model.Bug;

public class CripplingEffect extends BugEffect {
    public void Apply(Bug bug) {
        bug.SetCanMove(false);
        UpdateState(bug);
    }
}
