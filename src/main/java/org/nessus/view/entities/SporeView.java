package org.nessus.view.entities;

import org.nessus.model.shroom.Spore;
import org.nessus.utility.EntitySelector;
import org.nessus.view.View;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SporeView extends EntitySpriteView{
    private Spore model;

    public SporeView(Spore spore, BufferedImage sprite) {
        this.model = spore;
        this.size = 50;
        this.image = sprite;
    }

    @Override
    public void Draw(Graphics2D g2d) {
        if(model.GetTecton() == null)
            return;

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
        StringBuilder info = new StringBuilder();
        
        info.append("Tulajdonos: ");
        info.append(View.GetGameObjectStore().GetShroomName(model.GetShroom()) + "\n");
        info.append("Tápanyagtartalom: " + model.GetNutrient() + "\n");

        return info.toString();
    }

    public Spore GetModel() {
        return model;
    }

    @Override
    public void Accept(EntitySelector selector) {
        selector.Visit(this);
    }
}
