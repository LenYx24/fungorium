package org.nessus.view.entities;

import org.nessus.model.tecton.*;
import org.nessus.utility.EntitySelector;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.*;

import org.nessus.utility.Point;
import org.nessus.utility.TectonNameReader;
import org.nessus.utility.Vec2;
import org.nessus.view.View;

/**
 * A tekton mezők megjelenítéséért felelős osztály.
 * Kezeli a tektonon lévő entitások és gombafonalak elhelyezését és megjelenítését.
 */
public class TectonView extends EntitySpriteView{
    /**
     * A tekton modell, amelyet ez a nézet megjelenít.
     */
    private Tecton model;
    
    /**
     * Az x irányú elmozdulás a grafikus elrendezéshez.
     */
    private double dx;
    
    /**
     * Az y irányú elmozdulás a grafikus elrendezéshez.
     */
    private double dy;
    
    /**
     * Jelzi, hogy a tekton zárolva van-e (pl. húzás közben).
     */
    private boolean locked = false;
    
    /**
     * Véletlenszám generátor a kezdeti pozíció meghatározásához.
     */
    private Random r = new Random();
    
    /**
     * A tektonon lévő entitások pozícióinak tárolója.
     */
    private Queue<Point> pointsForEntites = new LinkedList<>();
    
    /**
     * A tektonhoz kapcsolódó gombafonalak eltolásainak tárolója.
     */
    private Map<Tecton, Queue<Vec2>> shroomThreadOffsets = new HashMap<>();
    
    /**
     * Az entitások cellájának mérete a tektonon belül.
     */
    private int cellSize = 0;

    /**
     * A gombafonalak alapértelmezett eltolása.
     */
    private static final double BASE_SHROOMTHREAD_OFFSET = 10;
    
    /**
     * A tekton nevének olvasásához használt segédosztály.
     */
    private static TectonNameReader tectonNameReader = new TectonNameReader();

    /**
     * Létrehoz egy új tekton nézetet.
     * @param t A tekton modell
     * @param sprite A tekton képe
     */
    public TectonView(Tecton t, BufferedImage sprite) {
        this.model = t;
        this.x = r.nextInt(1280);
        this.y = r.nextInt(720);
        this.image = sprite;
    }

    /**
     * Kiszámítja az entitások pozícióit a tektonon belül.
     * Az entitásokat egy rácsos elrendezésben helyezi el.
     */
    public void CalculateEntityPositions() {
        pointsForEntites.clear();
        int entityCount = model.GetEntityCount();
        if (entityCount == 0)
            return;

        int containerSize = size - 30;
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

    /**
     * Kiszámítja a gombafonalak pozícióit a tektonok között.
     * Biztosítja, hogy a párhuzamos fonalak ne fedjék egymást.
     */
    public void CalculateShroomThreadPositions() {
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
            var offset = threadCount / 2.0 * BASE_SHROOMTHREAD_OFFSET;
            
            Queue<Vec2> offsets = new LinkedList<>();

            for (int i = 0; i < threadCount; i++) {
                offsets.add(offsetVector.Scale(offset));
                offset -= BASE_SHROOMTHREAD_OFFSET;
            }

            shroomThreadOffsets.put(neighbour, offsets);
        }
    }

    /**
     * Beállítja a tekton zárolási állapotát.
     * @param locked Igaz, ha a tekton zárolva van
     */
    public void SetLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Lekérdezi a tekton zárolási állapotát.
     * @return Igaz, ha a tekton zárolva van
     */
    public boolean IsLocked() {
        return locked;
    }

    /**
     * Kirajzolja a tekton nézetet.
     * @param g2d A grafikus kontextus
     */
    @Override
    public void Draw(Graphics2D g2d) {
        this.DrawSprite(g2d);
    }

    /**
     * Elhelyez egy entitás nézetet a tektonon.
     * @param entityView Az elhelyezendő entitás nézet
     */
    public void InsertEntity(EntitySpriteView entityView){
        Point p = pointsForEntites.poll();
        if(p == null){
            return;
        }
        entityView.x = p.x;
        entityView.y = p.y;
        entityView.size = cellSize;
    }

    /**
     * Elhelyez egy gombafonal nézetet a tektonok között.
     * @param shroomThreadView Az elhelyezendő gombafonal nézet
     */
    public void InsertShroomThread(ShroomThreadView shroomThreadView) {
        var threadModel = shroomThreadView.GetModel();
        var t1 = threadModel.GetTecton1();
        var t2 = threadModel.GetTecton2();

        var neighbour = t1 == model ? t2 : t1;
        if (!shroomThreadOffsets.containsKey(neighbour))
            return;

        var offset = shroomThreadOffsets.get(neighbour).poll();
        shroomThreadView.SetOffset(offset);
    }

    /**
     * Visszaadja a tekton nézet rétegét.
     * @return A réteg száma
     */
    @Override
    public int GetLayer() {
        return 1;
    }

    /**
     * Ellenőrzi, hogy ez a nézet az adott objektumhoz tartozik-e.
     * @param obj Az ellenőrizendő objektum
     * @return Igaz, ha a nézet az adott objektumhoz tartozik
     */
    @Override
    public boolean IsViewing(Object obj) {
        return model == obj;
    }

    /**
     * Visszaadja a tekton információit szöveges formában.
     * @return A tekton információi
     */
    @Override
    public String GetEntityInfo() {
        model.Accept(tectonNameReader);
        return tectonNameReader.GetName();
    }

    /**
     * Visszaadja a tekton modellt.
     * @return A tekton modell
     */
    public Tecton GetModel() {
        return model;
    }

    /**
     * Visszaadja a tekton x koordinátáját.
     * @return Az x koordináta
     */
    public double X() {
        return this.x;
    }

    /**
     * Visszaadja a tekton y koordinátáját.
     * @return Az y koordináta
     */
    public double Y() {
        return this.y;
    }

    /**
     * Visszaadja az x irányú elmozdulást.
     * @return Az x irányú elmozdulás
     */
    public double getDx() {
        return dx;
    }

    /**
     * Visszaadja az y irányú elmozdulást.
     * @return Az y irányú elmozdulás
     */
    public double getDy() {
        return dy;
    }

    /**
     * Beállítja az x irányú elmozdulást.
     * @param dx Az új x irányú elmozdulás
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Beállítja az y irányú elmozdulást.
     * @param dy Az új y irányú elmozdulás
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Fogadja a látogatót a Visitor tervezési minta szerint.
     * @param selector A látogató objektum
     */
    @Override
    public void Accept(EntitySelector selector) {
        selector.Visit(this);
    }
}
