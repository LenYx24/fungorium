package org.nessus.view.entityviews;

import org.nessus.model.tecton.Tecton;
import org.nessus.utility.EntitySelector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TectonView extends EntitySpriteView{
    private Tecton model;
    private double dx;
    private double dy;
    private int size = 100;

    private Random r = new Random();

    public TectonView(Tecton t, BufferedImage sprite) {
        this.model = t;
        this.x = r.nextInt(1280);
        this.y = r.nextInt(720);

        BufferedImage newImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();

        g2.drawImage(sprite, 0, 0,size,size, null);
        g2.dispose();
        this.image = newImage;
    }

    @Override
    public void Draw(Graphics2D g2d) {
        this.DrawSprite(g2d,size);
    }

    @Override
    public int GetLayer() {
        return 0;
    }

    @Override
    public boolean ContainsPoint(int x, int y) {
        int _x = (int)this.x - size/2;
        int _y = (int)this.y - size/2;
        boolean hit = _x < x && _x + size < x && _y < y && _y + size < y;
        if(hit){
            System.out.println("_x: " + _x + ", _y: " + _y + "  this "+ this.model);
            System.out.println("x: " + x + ", y: " + y);
            System.out.println();
        }
        return hit;
    }

    @Override
    public boolean IsViewing(Object obj) {
        return false;
    }

    @Override
    public String GetEntityInfo() {
        return "";
    }

    public Tecton GetModel() {
        return model;
    }

    public double X()
    {
        return this.x;
    }

    public double Y()
    {
        return this.y;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double DX()
    {
        return dx;
    }

    public double DY()
    {
        return dy;
    }

    public void setDX(double dx)
    {
        this.dx = dx;
    }

    public void setDY(double dy)
    {
        this.dy = dy;
    }

    @Override
    public void Accept(EntitySelector selector) {
        selector.Visit(this);
    }
}
