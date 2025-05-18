package org.nessus.view.entities;

import org.nessus.model.shroom.ShroomBody;
import org.nessus.utility.EntitySelector;
import org.nessus.view.View;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShroomBodyView extends EntitySpriteView{
    private ShroomBody model;

    public ShroomBodyView(ShroomBody sb, BufferedImage sprite) {
        this.model = sb;
        this.size = 50;
        this.image = sprite;
    }

    @Override
    public void Draw(Graphics2D g2d) {
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
        String info = "Tulajdonos: ";
        
        info = info.concat(View.GetGameObjectStore().GetShroomName(model.GetShroom()) + "\n");
        info = info.concat("Szint: " + model.GetLevel() + "\n");
        info = info.concat("Spóraanyag: " + model.GetSporeMaterials() + "\n");
        info = info.concat("Hátralevő köpések: " + model.GetRemainingThrows() + "\n");

        return info;
    }

    public ShroomBody GetModel() {
        return model;
    }

    @Override
    public void Accept(EntitySelector selector) {
        selector.Visit(this);
    }
}
