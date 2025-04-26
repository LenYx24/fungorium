package org.nessus.model.effect;

import org.nessus.model.bug.Bug;

public class DivisionEffect extends BugEffect {
    public DivisionEffect() {
        remainingUses = 0;
    }

    public void ApplyOn(Bug bug) {
        bug.ClearEffect(this);
        bug.Split();
    }
}
