package org.nessus.view.entityviews;

import org.nessus.model.shroom.ShroomBody;

import java.awt.*;

public class ShroomBodyView extends EntitySpriteView{
    private ShroomBody model;
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
}
