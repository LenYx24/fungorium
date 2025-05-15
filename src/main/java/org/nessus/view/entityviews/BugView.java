package org.nessus.view.entityviews;

import org.nessus.model.bug.Bug;
import org.nessus.model.tecton.Tecton;
import org.nessus.utility.EntitySelector;
import org.nessus.view.IGameObjectStore;
import org.nessus.view.View;
import org.nessus.utility.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BugView extends EntitySpriteView{
    private Bug model;
    int size = 50;

    public BugView(Bug t, BufferedImage sprite) {
        this.model = t;

        BufferedImage newImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();

        g2.drawImage(sprite, 0, 0,size,size, null);
        g2.dispose();
        this.image = newImage;
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
        return false;
    }

    @Override
    public String GetEntityInfo()
    {
        String info = "";

        info.concat("Tulajdonos: " + model.GetOwner() + "\n");

        if (model.GetCanMove())
        {
            info.concat("Bénult: " + "nem\n");
        }
        else
        {
            info.concat("Bénult: " + "igen\n");
        }

        info.concat("Begyűjtött tápanyag: " + model.GetCollectedNutrients() + "\n");

        return info;
    }

    public Bug GetModel() {
        return model;
    }

    @Override
    public void Accept(EntitySelector selector) {
        selector.Visit(this);
    }
}
