package org.nessus.model.effect;

import org.nessus.model.Bug;

public abstract class BugEffect {
    int remainingUses = 3;

    protected void UpdateState(Bug bug) {
        remainingUses--;
        if (remainingUses == 0) {
            bug.ClearEffect(this);
        }
    }

    public abstract void ApplyOn(Bug bug);
}
