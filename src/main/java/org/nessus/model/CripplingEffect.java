package org.nessus.model;

public class CripplingEffect extends BugEffect{
    public void Apply(Bug bug){
        bug.SetCanMove(false);
        UpdateState(bug);
    }
}
