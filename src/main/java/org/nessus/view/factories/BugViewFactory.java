package org.nessus.view.factories;

import org.nessus.model.bug.Bug;
import org.nessus.model.bug.BugOwner;
import org.nessus.view.entityviews.BugView;

public abstract class BugViewFactory {
    protected BugOwner bugOwner;
    public abstract BugView CreateBugView(Bug bug);
}
