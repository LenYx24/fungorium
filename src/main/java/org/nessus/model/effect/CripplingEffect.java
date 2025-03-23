package org.nessus.model.effect;

import org.nessus.Skeleton;
import org.nessus.model.Bug;

public class CripplingEffect extends BugEffect {
    public void ApplyOn(Bug bug) {
        Skeleton.LogFunctionCall(this, "ApplyOn", bug);
        bug.SetCanMove(false);
        UpdateState(bug);
        Skeleton.LogReturnCall(this, "ApplyOn");
    }
}
