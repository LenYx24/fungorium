package org.nessus.view.entityviews;

import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.tecton.Tecton;
import org.nessus.utility.EntitySelector;
import org.nessus.utility.Point;
import org.nessus.view.IGameObjectStore;
import org.nessus.view.View;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShroomBodyView extends EntitySpriteView{
    private ShroomBody model;
    int size = 50;
    public ShroomBodyView(ShroomBody sb, BufferedImage sprite) {
        this.model = sb;

        BufferedImage newImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();

        g2.drawImage(sprite, 0, 0,size,size, null);
        g2.dispose();
        this.image = newImage;
    }

    @Override
    public void Draw(Graphics2D g2d)
    {
        if(model.GetTecton() == null){
            return;
        }
        View.GetGameObjectStore().FindTectonView(model.GetTecton()).InsertEntity(this);
        this.DrawSprite(g2d);
    }

    @Override
    public int GetLayer() {
        return 2;
    }

    @Override
    public boolean IsViewing(Object obj) {
        return model == obj;
    }

    @Override
    public String GetEntityInfo() {
        return "";
    }

    public ShroomBody GetModel() {
        return model;
    }

    @Override
    public void Accept(EntitySelector selector) {
        selector.Visit(this);
    }
}
