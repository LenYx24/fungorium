package org.nessus.view.entities;

import org.nessus.model.bug.Bug;
import org.nessus.utility.EffectInfoReader;
import org.nessus.utility.EntitySelector;
import org.nessus.view.View;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BugView extends EntitySpriteView{
    private Bug model;
    private static EffectInfoReader effectInfoReader = new EffectInfoReader();

    public BugView(Bug t, BufferedImage sprite) {
        this.model = t;
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
    public String GetEntityInfo()
    {
        StringBuilder info = new StringBuilder();
        
        info.append("Tulajdonos: ");
        info.append(View.GetGameObjectStore().GetBugOwnerName(model.GetOwner()) + "\n");
        info.append("Begyűjtött tápanyag: " + model.GetCollectedNutrients() + "\n");
        
        for (var effect : model.GetEffects()) {
            effect.Accept(effectInfoReader);
            info.append(effectInfoReader.GetInfo() + "\n");
        }

        return info.toString();
    }

    public Bug GetModel() {
        return model;
    }

    @Override
    public void Accept(EntitySelector selector) {
        selector.Visit(this);
    }
}
