package org.nessus.view.entityviews;

import org.nessus.model.tecton.Tecton;
import org.nessus.utility.EntitySelector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Queue;
import org.nessus.utility.Point;

public class TectonView extends EntitySpriteView{
    private Tecton model;
    private double dx;
    private double dy;
    private boolean locked = false;
    private Random r = new Random();
    Queue<Point> pointsForEntites = new LinkedList<Point>();
    int cellSize = 0;

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

    private void CalculateEntityPositions()
    {
        pointsForEntites.clear();
        int entityCount = model.GetEntityCount();
        if (entityCount == 0) return;

        int containerSize = size - 20;
        int cols = (int) Math.ceil(Math.sqrt(entityCount));
        int rows = (int) Math.ceil((double) entityCount / cols);
        cellSize = Math.min(containerSize / cols, containerSize / rows);

        int originX = (int)(x - containerSize / 2.0);
        int originY = (int)(y - containerSize / 2.0);

        for (int i = 0; i < entityCount; i++) {
            int row = i / cols;
            int col = i % cols;
            int px = originX + col * cellSize;
            int py = originY + row * cellSize;

            double _x = px + cellSize / 2.0;
            double _y = py + cellSize / 2.0;
            pointsForEntites.add(new Point(_x, _y));
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
        this.DrawSprite(g2d);
        this.CalculateEntityPositions();
    }
    public void InsertEntity(EntitySpriteView entityView){
        Point p = pointsForEntites.poll();
        if(p == null){
            return;
        }
        entityView.x = p.x;
        entityView.y = p.y;
        entityView.size = cellSize;
    }

    @Override
    public int GetLayer() {
        return 0;
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
