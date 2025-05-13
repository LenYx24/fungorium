package org.nessus.view.entityviews;

import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.Spore;
import org.nessus.utility.EntitySelector;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SporeView extends EntitySpriteView{
    private Spore model;
    int size = 50;
    public SporeView(Spore spore, BufferedImage sprite) {
        this.model = spore;

        BufferedImage newImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();

        g2.drawImage(sprite, 0, 0,size,size, null);
        g2.dispose();
        this.image = newImage;
    }
    @Override
    public void Draw(Graphics2D g2d) {
        if (model.GetTecton() != null) {
            return;
        }
        this.DrawSprite(g2d, size);
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
