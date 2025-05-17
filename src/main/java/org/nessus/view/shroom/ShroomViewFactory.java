package org.nessus.view.shroom;

import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.view.entities.ShroomBodyView;
import org.nessus.view.entities.ShroomThreadView;
import org.nessus.view.entities.SporeView;

public abstract class ShroomViewFactory {
    protected Shroom shroom;
    public abstract ShroomBodyView CreateShroomBodyView(ShroomBody body);
    public abstract ShroomThreadView CreateShroomThreadView(ShroomThread shroomThread);
    public abstract SporeView CreateSporeView(Spore spore);
}
