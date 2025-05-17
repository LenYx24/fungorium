package org.nessus.view.bugowner;

import org.nessus.model.bug.Bug;
import org.nessus.view.entities.BugView;

import static org.nessus.utility.ImageReader.GetImage;

public class WhiteTeamFactory extends BugViewFactory {
    @Override
    public BugView CreateBugView(Bug bug) {
        return new BugView(bug, GetImage("white_bug"));
    }
}
