package org.nessus.model;

public abstract class BugEffect {
    int remainingUses = 3;
    protected void UpdateState(Bug bug){
        remainingUses--;
        if(remainingUses == 0){
            bug.ClearEffect(this);
        }
    }
    public abstract void Apply(Bug bug);
}
