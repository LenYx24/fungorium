package org.nessus.view.factories.shroomfactories;

import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.view.entityviews.ShroomBodyView;
import org.nessus.view.entityviews.ShroomThreadView;
import org.nessus.view.entityviews.SporeView;
import org.nessus.view.factories.ShroomViewFactory;

import java.awt.*;

import static org.nessus.utility.ImageReader.GetImage;

public class RedTeamFactory extends ShroomViewFactory {


    @Override
    public ShroomBodyView CreateShroomBodyView(ShroomBody body) {
        return new ShroomBodyView(body, GetImage("red_shroombody"));
    }

    @Override
    public ShroomThreadView CreateShroomThreadView(ShroomThread shroomThread) {
        return new ShroomThreadView(shroomThread, Color.RED);
    }

    @Override
    public SporeView CreateSporeView(Spore spore) {
        return new SporeView(spore, GetImage("red_spore"));
    }
}
