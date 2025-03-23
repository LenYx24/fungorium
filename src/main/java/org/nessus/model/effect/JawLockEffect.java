package org.nessus.model.effect;

import org.nessus.Skeleton;
import org.nessus.model.Bug;

public class JawLockEffect extends BugEffect {
    public void ApplyOn(Bug bug) {
        Skeleton.LogFunctionCall(this, "ApplyOn", bug);
        bug.SetCanCut(false);
        UpdateState(bug);
        Skeleton.LogReturnCall(this, "ApplyOn");
    }
}
