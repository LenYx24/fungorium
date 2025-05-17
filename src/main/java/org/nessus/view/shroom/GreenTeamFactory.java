package org.nessus.view.shroom;

import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.view.entities.ShroomBodyView;
import org.nessus.view.entities.ShroomThreadView;
import org.nessus.view.entities.SporeView;

import java.awt.*;

import static org.nessus.utility.ImageReader.GetImage;

public class GreenTeamFactory extends ShroomViewFactory {

    @Override
    public ShroomBodyView CreateShroomBodyView(ShroomBody body) {
        return new ShroomBodyView(body, GetImage("green_shroombody"));
    }

    @Override
    public ShroomThreadView CreateShroomThreadView(ShroomThread shroomThread) {
        return new ShroomThreadView(shroomThread, Color.GREEN);
    }

    @Override
    public SporeView CreateSporeView(Spore spore) {
        return new SporeView(spore, GetImage("green_spore"));
    }
}
