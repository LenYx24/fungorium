package org.nessus.view.entityviews;

import org.nessus.model.tecton.Tecton;

import java.awt.*;

public class TectonView extends EntitySpriteView{
    private Tecton model;
    private double dx;
    private double dy;

    public TectonView(Tecton t)
    {
        this.model = t;
    }

    @Override
    public void Draw(Graphics2D g2d) {

    }

    @Override
    public int GetLayer() {
        return 0;
    }

    @Override
    public boolean ContainsPoint(int x, int y) {
        return false;
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
