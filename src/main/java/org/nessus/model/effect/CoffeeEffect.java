package org.nessus.model.effect;

import org.nessus.Skeleton;
import org.nessus.model.Bug;

public class CoffeeEffect extends BugEffect {
    public void ApplyOn(Bug bug) {
        Skeleton.LogFunctionCall(this, "ApplyOn", bug);
        bug.AddMoveCost(-2);
        UpdateState(bug);
        Skeleton.LogReturnCall(this, "ApplyOn");
    }
}
