package org.nessus.model.effect;

import org.nessus.model.Bug;

public class JawLockEffect extends BugEffect {
    public void Apply(Bug bug) {
        bug.SetCanCut(false);
        UpdateState(bug);
    }
}
