package org.nessus.view.entityviews;

import org.nessus.model.shroom.Spore;
import org.nessus.utility.EntitySelector;

import java.awt.*;

public class SporeView extends EntitySpriteView{
    private Spore model;
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

    public Spore GetModel() {
        return model;
    }

    @Override
    public void Accept(EntitySelector selector) {
        selector.Visit(this);
    }
}
