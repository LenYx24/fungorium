package org.nessus.view.factories.bugfactories;

import org.nessus.model.bug.Bug;
import org.nessus.view.entityviews.BugView;
import org.nessus.view.factories.BugViewFactory;

public class WhiteTeamFactory extends BugViewFactory {
    @Override
    public BugView CreateBugView(Bug bug) {
        return null;
    }
}
