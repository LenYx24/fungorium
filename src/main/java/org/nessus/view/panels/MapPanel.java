package org.nessus.view.panels;
import org.nessus.utility.GraphUtil;
import org.nessus.view.View;
import org.nessus.view.entityviews.IEntityView;
import org.nessus.view.entityviews.TectonView;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

public class MapPanel extends JPanel {
    private View view;
    private GraphUtil graphRenderer;
    private TectonView dragged = null;

    public MapPanel(View view, int width, int height) {
        this.view = view;
        this.graphRenderer = new GraphUtil(width, height, view.GetObjectStore().GetTectonViews());
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLUE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                var cursor = e.getPoint();
                view.GetObjectStore()
                    .GetEntityViews()
                    .stream()
                    .filter(entityView -> entityView.ContainsPoint(cursor.x, cursor.y))
                    .forEach(view::HandleSelection);

                TectonView tectonView = view.GetObjectStore()
                    .GetTectonViews()
                    .values()
                    .stream()
                    .filter(t -> t.ContainsPoint(cursor.x, cursor.y))
                    .findFirst()
                    .orElse(null);

                if (tectonView != null) {
                    tectonView.SetLocked(true);    
                    dragged = tectonView;
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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