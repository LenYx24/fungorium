package org.nessus.view.panels;
import org.nessus.utility.GraphUtil;
import org.nessus.utility.geometry.Camera;
import org.nessus.utility.geometry.Point2;
import org.nessus.view.ObjectStore;
import org.nessus.view.View;
import org.nessus.view.entities.IEntityView;
import org.nessus.view.entities.TectonView;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.*;

/**
 * A játéktér megjelenítéséért felelős panel.
 * Kezeli a játéktér kirajzolását és a felhasználói interakciókat.
 */
public class MapPanel extends JPanel {
    /**
     * A nézet, amelyhez a panel tartozik.
     */
    private View view;
    
    /**
     * A gráf megjelenítéséért felelős segédosztály.
     */
    private GraphUtil graphRenderer;
    
    /**
     * Az éppen húzott tekton nézet.
     */
    private TectonView dragged = null;
    
    /**
     * A háttérkép.
     */
    private Image backgroundImage;

    private Camera camera;

    private Point2 CursorToWorld(int x, int y) {
        double worldX = (x - getWidth() / 2.0) / camera.GetZoom() + camera.GetPos().x;
        double worldY = (y - getHeight() / 2.0) / camera.GetZoom() + camera.GetPos().y;
        return new Point2(worldX, worldY);
    }

    /**
     * Létrehoz egy új játéktér panelt.
     * @param view A nézet, amelyhez a panel tartozik
     * @param width A panel szélessége
     * @param height A panel magassága
     */
    public MapPanel(View view, int width, int height) {
        this.view = view;
        this.camera = new Camera(width / 2, height / 2);
        this.graphRenderer = new GraphUtil(width, height, view.GetObjectStore().GetTectonViews());
        graphRenderer.registerKeyListener(view);
        setPreferredSize(new Dimension(width, height));
        backgroundImage = new ImageIcon(getClass().getResource("/textures/mapbg.gif")).getImage();
        view.addKeyListener(camera);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                var cursor = e.getPoint();

                var cursorWorld = CursorToWorld(cursor.x, cursor.y);

                ObjectStore objectStore = view.GetObjectStore();
                List<IEntityView> entityViews = objectStore.GetEntityViews();
                for(IEntityView entity: entityViews){
                    if(entity.ContainsPoint((int)cursorWorld.x, (int)cursorWorld.y)){
                        view.HandleSelection(entity);
                        return;
                    }
                }
                Collection<TectonView> tectonViews = objectStore.GetTectonViews().values();
                for(TectonView tectonView: tectonViews){
                    if(tectonView.ContainsPoint((int)cursorWorld.x, (int)cursorWorld.y)){
                        tectonView.SetLocked(true);
                        dragged = tectonView;
                        view.HandleSelection(tectonView);
                        return;
                    }
                }

                //A játékos sem tektonra sem entitásra nem nyomott
                view.GetSelection().Clear();
                view.GetGamePanel().GetControlPanel().ClearInfo();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragged != null) {
                    dragged.SetLocked(false);
                    dragged = null;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = e.getPoint();
                var cursorWorld = CursorToWorld(p.x, p.y);
                if(dragged != null){
                    dragged.setX(cursorWorld.x);
                    dragged.setY(cursorWorld.y);
                }
            }
        });
    }

    /**
     * Kirajzolja a panel tartalmát.
     * @param g A grafikus kontextus
     * @return void
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;

        camera.Update();
        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(camera.GetZoom(), camera.GetZoom());
        g2d.translate(-camera.GetPos().x, -camera.GetPos().y);

        graphRenderer.AlignGraph();
        graphRenderer.DrawNeighbourMarkers(g2d);
        
        var store = view.GetObjectStore();
        var renderBuffer = new PriorityQueue<IEntityView>((a, b) -> a.GetLayer() - b.GetLayer());
        
        store.GetTectonViews().values().forEach(x -> {
            x.CalculateEntityPositions();
            x.CalculateShroomThreadPositions();
            renderBuffer.add(x);
        });

        store.GetEntityViews().forEach(renderBuffer::add);

        while(!renderBuffer.isEmpty())
            renderBuffer.poll().Draw(g2d);
    }
}