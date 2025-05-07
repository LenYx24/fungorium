package org.nessus.view.factories;

import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.view.entityviews.ShroomBodyView;
import org.nessus.view.entityviews.ShroomThreadView;
import org.nessus.view.entityviews.SporeView;

public abstract class ShroomViewFactory {
    protected Shroom shroom;
    public abstract ShroomBodyView CreateShroomBodyView(ShroomBody body);
    public abstract ShroomThreadView CreateShroomThreadView(ShroomThread shroomThread);
    public abstract SporeView CreateSporeView(Spore spore);
}
