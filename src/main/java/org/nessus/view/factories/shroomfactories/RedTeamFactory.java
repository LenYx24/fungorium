package org.nessus.view.factories.shroomfactories;

import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.view.entityviews.ShroomBodyView;
import org.nessus.view.entityviews.ShroomThreadView;
import org.nessus.view.entityviews.SporeView;
import org.nessus.view.factories.ShroomViewFactory;

public class RedTeamFactory extends ShroomViewFactory {


    @Override
    public ShroomBodyView CreateShroomBodyView(ShroomBody body) {
        return null;
    }

    @Override
    public ShroomThreadView CreateShroomThreadView(ShroomThread shroomThread) {
        return null;
    }

    @Override
    public SporeView CreateSporeView(Spore spore) {
        return null;
    }
}
