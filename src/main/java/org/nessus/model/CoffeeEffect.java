package org.nessus.model;

public class CoffeeEffect extends BugEffect{
    public void Apply(Bug bug){
        bug.AddMoveCost(-2);
        UpdateState(bug);
    }
}
