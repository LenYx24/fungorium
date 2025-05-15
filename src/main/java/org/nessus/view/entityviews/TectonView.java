package org.nessus.view.entityviews;

import org.nessus.model.tecton.Tecton;
import org.nessus.utility.EntitySelector;
import org.nessus.model.shroom.ShroomThread;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.*;

import org.nessus.utility.Point;
import org.nessus.utility.Vec2;
import org.nessus.view.View;

public class TectonView extends EntitySpriteView{
    private Tecton model;
    
    private double dx;
    private double dy;
    
    private boolean locked = false;
    
    private Random r = new Random();
    
    private Queue<Point> pointsForEntites = new LinkedList<>();
    private Map<Tecton, Queue<Point>> shroomThreadOffsets = new HashMap<>();
    
    private int cellSize = 0;

    private static final double BASE_SHROOMTHREAD_OFFSET = 10;

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

    private void CalculateShroomThreadPositions() {
        shroomThreadOffsets.clear();
        var store = View.GetGameObjectStore();

        for (var neighbour : model.GetNeighbours()) {
            var neighbourView = store.FindTectonView(neighbour);
            
            var shroomThreads = model.GetShroomThreads()
                .stream()
                .filter(x -> x.GetTecton1() == neighbour || x.GetTecton2() == neighbour)
                .toList();

            var threadCount = shroomThreads.size();

            // Ha a szomszédos tektonnak vannak fonal offsettjei beállítva erre a tektonra, akkor már itt nem szabad
            // offsetet számolni. Ha nincs fonal, akkor szintén nincs teendő.
            if (neighbourView.shroomThreadOffsets.containsKey(model) || threadCount == 0)
                continue;
        
            var tectonCenter = new Point(x, y);
            var neighbourCenter = new Point(neighbourView.x, neighbourView.y);

            var vectorToNeighbour = new Vec2(tectonCenter, neighbourCenter);
            var offsetVector = vectorToNeighbour.Normalize().Rotate(Math.PI / 2);
            var offset = threadCount / 2 * BASE_SHROOMTHREAD_OFFSET;
            
            Queue<Point> pointQueue = new LinkedList<>();

            for (int i = 0; i < threadCount; i++) {
                pointQueue.add(tectonCenter.Translate(offsetVector.Scale(offset)));
                offset -= BASE_SHROOMTHREAD_OFFSET;
            }

            shroomThreadOffsets.put(neighbour, pointQueue);
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
        this.CalculateShroomThreadPositions();
    }

    public void InsertEntity(EntitySpriteView entityView){
        Point p = pointsForEntites.poll();
        if(p == null){
            System.out.println("NULLL A PÉÉÉÉÉÉÉ");
            return;
        }
        entityView.x = p.x;
        entityView.y = p.y;
        entityView.size = cellSize;
    }

    public void InsertShroomThread(ShroomThreadView shroomThreadView) {
        var threadModel = shroomThreadView.GetModel();
        var t1 = threadModel.GetTecton1();
        var t2 = threadModel.GetTecton2();

        var neighbour = t1 == model ? t2 : t1;
        if (!shroomThreadOffsets.containsKey(neighbour))
            return;

        var neighbourView = View.GetGameObjectStore().FindTectonView(neighbour);
        
        var tectonCenter = new Point(x, y);
        var neighbourCenter = new Point(neighbourView.x, neighbourView.y);
        var distanceVector = new Vec2(tectonCenter, neighbourCenter);
        
        var startPoint = shroomThreadOffsets.get(neighbour).poll();
        var endPoint = startPoint.Translate(distanceVector);

        shroomThreadView.SetLocation(startPoint, endPoint);
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
