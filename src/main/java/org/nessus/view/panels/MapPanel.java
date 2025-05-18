package org.nessus.view.panels;
import org.nessus.utility.GraphUtil;
import org.nessus.view.ObjectStore;
import org.nessus.view.View;
import org.nessus.view.entities.IEntityView;
import org.nessus.view.entities.TectonView;

import java.awt.*;
import java.awt.event.*;
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

    /**
     * Létrehoz egy új játéktér panelt.
     * @param view A nézet, amelyhez a panel tartozik
     * @param width A panel szélessége
     * @param height A panel magassága
     */
    public MapPanel(View view, int width, int height) {
        this.view = view;
        this.graphRenderer = new GraphUtil(width, height, view.GetObjectStore().GetTectonViews());
        
        setPreferredSize(new Dimension(width, height));
        backgroundImage = new ImageIcon(getClass().getResource("/textures/mapbg.gif")).getImage();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                var cursor = e.getPoint();
                ObjectStore objectStore = view.GetObjectStore();
                List<IEntityView> entityViews = objectStore.GetEntityViews();
                for(IEntityView entity: entityViews){
                    if(entity.ContainsPoint(cursor.x, cursor.y)){
                        view.HandleSelection(entity);
                        return;
                    }
                }
                Collection<TectonView> tectonViews = objectStore.GetTectonViews().values();
                for(TectonView tectonView: tectonViews){
                    if(tectonView.ContainsPoint(cursor.x, cursor.y)){
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
                if(dragged != null){
                    dragged.setX(p.x);
                    dragged.setY(p.y);
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