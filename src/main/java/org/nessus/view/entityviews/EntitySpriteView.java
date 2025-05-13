package org.nessus.view.entityviews;

import org.nessus.model.tecton.Tecton;
import org.nessus.view.IGameObjectStore;
import org.nessus.view.View;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class EntitySpriteView implements IEntityView{
    protected double x;
    protected double y;
    protected BufferedImage image;
    public double GetX(){
        return x;
    }
    public double GetY(){
        return y;
    }
    protected void DrawSprite(Graphics2D g2d, int size){
        int _x = (int)x - size/2;
        int _y = (int)y - size/2;
        g2d.drawImage(image, _x, _y,size,size,null);
    }
    protected void SetPositionToTecton(Tecton tecton){
        TectonView tectonView = View.GetGameObjectStore().FindTectonView(tecton);
        x = tectonView.GetX();
        y = tectonView.GetY();
    }
}
