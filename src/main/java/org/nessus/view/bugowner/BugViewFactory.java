package org.nessus.view.bugowner;

import org.nessus.model.bug.Bug;
import org.nessus.model.bug.BugOwner;
import org.nessus.view.entities.BugView;

public abstract class BugViewFactory {
    protected BugOwner bugOwner;
    public abstract BugView CreateBugView(Bug bug);
}
