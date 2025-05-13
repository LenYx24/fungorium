package org.nessus.view.entityviews;

import org.nessus.model.tecton.Tecton;
import org.nessus.utility.EntitySelector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class TectonView extends EntitySpriteView{
    private Tecton model;
    private double dx;
    private double dy;
    private int size = 100;
    private boolean locked = false;
    private Random r = new Random();
    private ArrayList<IEntityView> entities = new ArrayList<>();

    public void AddEntity(IEntityView e)
    {
        entities.add(e);
    }

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

    private void drawEntities(Graphics2D g2d)
    {
        if (entities.isEmpty()) return;

        int containerSize = size - 20;
        int cols = (int) Math.ceil(Math.sqrt(entities.size()));
        int rows = (int) Math.ceil((double) entities.size() / cols);
        int cellSize = Math.min(containerSize / cols, containerSize / rows);

        int originX = (int)(x - containerSize / 2.0);
        int originY = (int)(y - containerSize / 2.0);

        for (int i = 0; i < entities.size(); i++) {
            IEntityView entity = entities.get(i);

            int row = i / cols;
            int col = i % cols;
            int px = originX + col * cellSize;
            int py = originY + row * cellSize;

            if (entity instanceof EntitySpriteView ev) {
                ev.setX(px + cellSize / 2.0);
                ev.setY(py + cellSize / 2.0);
                ev.DrawSprite(g2d, cellSize);
            }
        }
    }

    public void SetLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean IsLocked() {
        return locked;
    }

    @Override
    public void Draw(Graphics2D g2d)
    {
        this.DrawSprite(g2d, size);
        drawEntities(g2d);
    }

    @Override
    public int GetLayer() {
        return 0;
    }

    @Override
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
