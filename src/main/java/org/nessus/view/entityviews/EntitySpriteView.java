package org.nessus.view.entityviews;

import org.nessus.model.tecton.Tecton;
import org.nessus.utility.Point;
import org.nessus.view.IGameObjectStore;
import org.nessus.view.View;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class EntitySpriteView implements IEntityView{
    protected double x;
    protected double y;
    protected BufferedImage image;
    protected int size = 100;
    public double GetX(){
        return x;
    }
    public double GetY(){
        return y;
    }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    protected void DrawSprite(Graphics2D g2d){
        int _x = (int)x - size/2;
        int _y = (int)y - size/2;
        g2d.drawImage(image, _x, _y,size,size,null);
    }
    public boolean ContainsPoint(int x, int y) {
        int tX = (int)GetX();
        int tY = (int)GetY();

        int topLeftX = tX - size / 2;
        int topLeftY = tY - size / 2;

        boolean boundCheckX = x >= topLeftX && x <= topLeftX + size;
        boolean boundCheckY = y >= topLeftY && y <= topLeftY + size;
        if (boundCheckX && boundCheckY) {
            System.out.println("HIT: " + this);
        }
        return boundCheckX && boundCheckY;
    }
}
