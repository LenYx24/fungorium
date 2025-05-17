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
    private Point p1;
    private Point p2;
    private int size = 5;

    private Vec2 offset;

    private Color color = null;
    private boolean selection = false;

    public ShroomThreadView(ShroomThread shroomThread, Color color) {
        this.model = shroomThread;
        this.color = color;
    }

    public void SetOffset(Vec2 offset) {
        this.offset = offset;
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

        Point t1Center = new Point(t1View.GetX(), t1View.GetY()); // Center of tecton 1
        Point t2Center = new Point(t2View.GetX(), t2View.GetY()); // Center of tecton 2

        Vec2 direction = new Vec2(t1Center, t2Center).Normalize();
        
        // Shift from center to edge along direction
        double tectonEdgeOffset = 50.0;
        p1 = t1Center.Translate(direction.Scale(tectonEdgeOffset)).Translate(offset);
        p2 = t2Center.Translate(direction.Scale(-tectonEdgeOffset)).Translate(offset);


        var distanceVector = new Vec2(p1, p2);
        var growthRate = (model.GetEvolution() + 1) / 4.0;
        var endPoint = p1.Translate(distanceVector.Scale(growthRate));

        if(selection){
            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke((float)size / 2.0f));
            var norm = direction.Scale(size).Rotate(Math.PI / 2);

            var pBorder1 = p1.Translate(norm);
            var pEndPoint1 = endPoint.Translate(norm);
            g2d.drawLine((int)pBorder1.x, (int)pBorder1.y, (int)pEndPoint1.x, (int)pEndPoint1.y);

            var pBorder2 = p1.Translate(norm.Scale(-1));
            var pEndPoint2 = endPoint.Translate(norm.Scale(-1));
            g2d.drawLine((int)pBorder2.x, (int)pBorder2.y, (int)pEndPoint2.x, (int)pEndPoint2.y);

        }
        g2d.setColor(color);
        if(model.IsDying()){
            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 80));
        }
        g2d.setStroke(new BasicStroke(size));
        g2d.drawLine((int)p1.x, (int)p1.y, (int)endPoint.x, (int)endPoint.y);
    }


    @Override
    public int GetLayer() {
        return 0;
    }

    @Override
    public boolean ContainsPoint(int x, int y) {
        Point hit = new Point(x, y);
        Vec2 p1p2 = new Vec2(p1,p2);
        double lineLength = p1p2.Length();
        Vec2 v0 = p1p2.Normalize();
        Point p = p1;
        Point r = hit;
        Vec2 prVec = new Vec2(p,r);
        double distance = prVec.Dot(v0);
        if(distance < 0 || distance > lineLength){
            return false;
        }
        Vec2 vProjected = v0.Scale(distance);
        double dist = prVec.Subtract(vProjected).Length();

        return dist < (double)size;
    }

    @Override
    public boolean IsViewing(Object obj) {
        return model == obj;
    }

    @Override
    public String GetEntityInfo()
    {
        String info = "Tulajdonos: ";

        info = info.concat(View.GetGameObjectStore().GetShroomName(model.GetShroom()) + "\n");

        info = info.concat("Növekedés állapota: " + model.GetEvolution() + "\n");

        if (model.IsDying())
        {
            info = info.concat("HALDOKLIK\n");
        }

        return info;
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
