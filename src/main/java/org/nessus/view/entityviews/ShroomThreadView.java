package org.nessus.view.entityviews;

import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.tecton.Tecton;
import org.nessus.utility.EntitySelector;

import java.awt.*;

public class ShroomThreadView implements IEntityView {
    private ShroomThread model;

    public ShroomThreadView(Shroom s, Tecton t1, Tecton t2)
    {
        this.model = new ShroomThread(s, t1, t2);
    }

    @Override
    public void Draw(Graphics2D g2d) {

    }

    @Override
    public int GetLayer() {
        return 0;
    }

    @Override
    public boolean ContainsPoint(int x, int y) {
        return false;
    }

    @Override
    public boolean IsViewing(Object obj) {
        return false;
    }

    @Override
    public String GetEntityInfo() {
        return "";
    }

    public ShroomThread GetModel() {
        return model;
    }

    @Override
    public void Accept(EntitySelector selector) {
        selector.Visit(this);
    }
}
