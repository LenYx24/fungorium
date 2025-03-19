package org.nessus.model;

public class JawLockEffect extends BugEffect{
    public void Apply(Bug bug){
        bug.SetCanCut(false);
        UpdateState(bug);
    }
}
