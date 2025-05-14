package org.nessus.view.panels;
import org.nessus.utility.GraphUtil;
import org.nessus.view.ObjectStore;
import org.nessus.view.View;
import org.nessus.view.entityviews.IEntityView;
import org.nessus.view.entityviews.TectonView;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.List;
import javax.swing.*;

public class MapPanel extends JPanel {
    private View view;
    private GraphUtil graphRenderer;
    private TectonView dragged = null;
    private Image backgroundImage;

    public MapPanel(View view, int width, int height) {
        this.view = view;
        this.graphRenderer = new GraphUtil(width, height, view.GetObjectStore().GetTectonViews());
        setPreferredSize(new Dimension(width, height));
        backgroundImage = new ImageIcon("src/main/resources/textures/mapbg.gif").getImage();


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

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;
        graphRenderer.AlignGraph();
        graphRenderer.Draw(g2d);
        var store = view.GetObjectStore();
        store.GetTectonViews().values().forEach(tectonView -> tectonView.Draw(g2d));
        store.GetEntityViews().forEach(x -> {
            x.Draw(g2d);
        });
    }
}