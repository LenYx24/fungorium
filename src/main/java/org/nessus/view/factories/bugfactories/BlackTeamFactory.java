package org.nessus.view.factories.bugfactories;

import org.nessus.model.bug.Bug;
import org.nessus.view.entityviews.BugView;
import org.nessus.view.factories.BugViewFactory;

import static org.nessus.utility.ImageReader.GetImage;

public class BlackTeamFactory extends BugViewFactory {
    @Override
    public BugView CreateBugView(Bug bug) {
        return new BugView(bug,GetImage("grey_bug"));
    }
}
