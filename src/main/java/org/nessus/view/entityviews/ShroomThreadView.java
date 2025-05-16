package org.nessus.view.entityviews;

import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.tecton.Tecton;
import org.nessus.utility.EntitySelector;
import org.nessus.utility.Point;
import org.nessus.utility.Vec2;
import org.nessus.view.View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class ShroomThreadView implements IEntityView {
    private ShroomThread model;

    private Point p1 = null;
    private Point p2 = null;

    private Color color = null;
    private boolean selection = false;

    public ShroomThreadView(ShroomThread shroomThread, Color color) {
        this.model = shroomThread;
        this.color = color;
    }

    public void SetLocation(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public void Draw(Graphics2D g2d) {
        if (model.GetTecton1() == model.GetTecton2()) {
            System.out.println("HUROKÉL");
            System.exit(-1);
        }

        var store = View.GetGameObjectStore();

        var t1View = store.FindTectonView(model.GetTecton1());
        var t2View = store.FindTectonView(model.GetTecton2());

        t1View.InsertShroomThread(this);
        t2View.InsertShroomThread(this);
        
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(color);

        var distanceVector = new Vec2(p1, p2);
        var growthRate = (model.GetEvolution() + 1) / 4.0;
        var endPoint = p1.Translate(distanceVector.Scale(growthRate));
        g2d.drawLine((int)p1.x, (int)p1.y, (int)endPoint.x, (int)endPoint.y);
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
        return model == obj;
    }

    @Override
    public String GetEntityInfo() {
        return "";
    }

    public ShroomThread GetModel() {
        return model;
    }

    @Override
    public void Accept(EntitySelector selector) {
        selector.Visit(this);
    }
    public void SetSelected(boolean selected){
        selection = selected;
    }
}
