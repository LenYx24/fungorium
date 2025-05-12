package org.nessus.view.panels;
import org.nessus.view.View;
import org.nessus.view.entityviews.TectonView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MapPanel extends JPanel implements ActionListener {
    private View view;

    private final int width;
    private final int height;
    private final Timer timer;

    private static final int NODE_RADIUS = 100;
    private static final double SPRING_LENGTH = 100;
    private static final double SPRING_STRENGTH = 0.3;
    private static final double REPULSION_STRENGTH = 10000;
    private static final double DAMPING = 0.85;

    private final List<TectonView> tectonViews = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    private Random r = new Random(0);

    public MapPanel(View view, int width, int height) {
        this.view = view;

        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
        timer = new Timer(0, this);
        timer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                var cursor = e.getPoint();
                view.GetObjectStore()
                    .GetEntityViews()
                    .stream()
                    .filter(entityView -> entityView.ContainsPoint(cursor.x, cursor.y))
                    .forEach(view::HandleSelection);
            }
        });
    }

    static class Edge {
        TectonView a, b;

        Edge(TectonView a, TectonView b) {
            this.a = a;
            this.b = b;
        }
    }

    public void addNode(TectonView t) {
        if (!tectonViews.contains(t)) {
            double x = width / 2 + r.nextDouble(-20, 20);
            double y = height / 2 + r.nextDouble(-20, 20);
            tectonViews.add(t);
        }
    }

    public void addEdge(TectonView t1, TectonView t2) {
        addNode(t1);
        addNode(t2);
        edges.add(new Edge(t1, t2));
    }

    public void removeEdges(Edge e) {
        edges.remove(e);
    }

    public void setNeighbours(TectonView nodeId, List<TectonView> neighbours)
    {
        for (TectonView neighbour : neighbours)
        {
            addEdge(nodeId, neighbour);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        applyForces();
        repaint();
    }

    private void applyForces() {
        for (TectonView n : tectonViews) {
            n.setDX(0);
            n.setDY(0);
        }

        for (TectonView a : tectonViews) {
            for (TectonView b : tectonViews) {
                if (a == b) continue;
                double dx = a.X() - b.X();
                double dy = a.Y() - b.Y();
                double distSq = dx * dx + dy * dy + 0.1;
                double force = REPULSION_STRENGTH / distSq;
                a.setDX(a.DX() + dx * force);
                a.setDY(a.DY() + dy * force);
            }
        }

        for (Edge e : edges)
        {
            double dx = e.b.X() - e.a.X();
            double dy = e.b.Y() - e.a.Y();
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist == 0) continue;
            double force = SPRING_STRENGTH * (dist - SPRING_LENGTH);
            double fx = dx / dist * force;
            double fy = dy / dist * force;
            e.a.setDX(e.a.DX() + fx);
            e.a.setDY(e.a.DY() + fy);
            e.b.setDX(e.b.DX() - fx);
            e.b.setDY(e.b.DY() - fy);
        }

        for (TectonView n : tectonViews)
        {
            n.setDX(n.DX() * DAMPING);
            n.setDY(n.DY() * DAMPING);

            n.setX(n.X() + n.DX());
            n.setY(n.Y() + n.DY());

            n.setX(Math.max(NODE_RADIUS, Math.min(width - NODE_RADIUS, n.X())));
            n.setY(Math.max(NODE_RADIUS, Math.min(height - NODE_RADIUS, n.Y())));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.LIGHT_GRAY);
        for (Edge e : edges) {
            g2.drawLine((int) e.a.X(), (int) e.a.Y(), (int) e.b.X(), (int) e.b.Y());
        }

        for (TectonView n : tectonViews) {
            int x = (int) n.X() - NODE_RADIUS / 2;
            int y = (int) n.Y() - NODE_RADIUS / 2;
            g2.setColor(Color.ORANGE);
            g2.fillOval(x, y, NODE_RADIUS, NODE_RADIUS);
            g2.setColor(Color.BLACK);
            g2.drawOval(x, y, NODE_RADIUS, NODE_RADIUS);
        }
    }

    private static void DelayAction(int delay, Runnable action) {
        Timer t = new Timer(delay, a -> {
            action.run();
        });

        t.setRepeats(false);
        t.start();
    }
}