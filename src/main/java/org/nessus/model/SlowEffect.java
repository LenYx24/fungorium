package org.nessus.model;

public class SlowEffect extends BugEffect{
    public void Apply(Bug bug){
        bug.AddMoveCost(+2);
        UpdateState(bug);
    }
}