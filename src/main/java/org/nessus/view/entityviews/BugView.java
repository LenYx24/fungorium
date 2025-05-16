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
        return model == obj;
    }

    @Override
    public String GetEntityInfo()
    {
        String info = "Tulajdonos: ";

        info = info.concat(View.GetGameObjectStore().GetBugOwnerName(model.GetOwner()) + "\n");

        info = info.concat("Begyűjtött tápanyag: " + model.GetCollectedNutrients() + "\n");

        if (!model.GetCanMove())
        {
            info = info.concat("BÉNULT\n");
        }

        if (!model.GetCanCut())
        {
            info = info.concat("SZÁJZÁRT\n");
        }

        if (model.GetMoveCost() == 1)
        {
            info = info.concat("GYORSÍTVA\n");
        }

        if (model.GetMoveCost() == 4)
        {
            info = info.concat("LASSÍTVA\n");
        }

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
