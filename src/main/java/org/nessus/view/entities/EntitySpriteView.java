package org.nessus.view.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class EntitySpriteView implements IEntityView{
    protected double x;
    protected double y;
    protected int size = 100;
    protected int padding = 2;

    protected BufferedImage image;
    protected boolean selection = false;

    public double GetX(){
        return x;
    }

    public double GetY(){
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    protected void DrawSprite(Graphics2D g2d){
        int _x = (int)x - size/2;
        int _y = (int)y - size/2;
        
        if(selection) {
            g2d.setStroke(new BasicStroke(padding));
            g2d.setColor(new Color(255,0,0));
            g2d.drawRect(_x - padding,_y - padding,size + 2*padding,size + 2*padding);
        }

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

    public void SetSelected(boolean selected){
        selection = selected;
    }
}
