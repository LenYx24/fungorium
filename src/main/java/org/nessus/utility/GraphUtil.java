package org.nessus.utility;

import org.nessus.model.tecton.Tecton;
import org.nessus.view.entityviews.TectonView;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Edge {
    public TectonView a, b;

    public Edge(TectonView a, TectonView b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge edge)) return false;
        return (edge.a == a && edge.b == b) || (edge.a == b && edge.b == a);
    }

    @Override
    public int hashCode() {
        return a.hashCode() + b.hashCode();
    }
}

public class GraphUtil implements KeyListener {
    private int width;
    private int height;

    private static final double PADDING = 10;
    private static final double SPRING_LENGTH = 200;
    private static final double SPRING_STRENGTH = 0.01;
    private static final double REPULSION_STRENGTH = 30000;
    private static final double DAMPING = 0.9;
    private static final int NODE_RADIUS = 100;

    private Set<Edge> edges = new HashSet<>();
    private Map<Tecton, TectonView> tectons;
    
    // Add a flag to track edge visibility
    private boolean showEdges = true;

    public GraphUtil(int width, int height, Map<Tecton, TectonView> tectons) {
        this.width = width;
        this.height = height;
        this.tectons = tectons;
    }

    public void AlignGraph() {
        edges.clear();
        for (var tecton : tectons.values()) {
            var neighbours = tecton.GetModel().GetNeighbours();
            for (var neighbour : neighbours) {
                var t2 = tectons.get(neighbour);
                if (t2 != null)
                    edges.add(new Edge(tecton, t2));
            }
        }
        applyForces();
    }

    private void applyForces() {
        for (TectonView n : tectons.values()) {
            if (!n.IsLocked()) {
                n.setDX(0);
                n.setDY(0);
            }
        }

        // Repulsion
        for (TectonView a : tectons.values()) {
            if (a.IsLocked()) continue;

            for (TectonView b : tectons.values()) {
                if (a == b) continue;

                double dx = a.X() - b.X();
                double dy = a.Y() - b.Y();
                double distSq = dx * dx + dy * dy;
                double dist = Math.sqrt(distSq);
                if (dist < NODE_RADIUS) dist = NODE_RADIUS;

                double force = REPULSION_STRENGTH / (dist * dist);

                a.setDX(a.DX() + (dx / dist) * force);
                a.setDY(a.DY() + (dy / dist) * force);
            }
        }

        // Spring attraction
        for (Edge e : edges) {
            if (e.a.IsLocked() && e.b.IsLocked()) continue;

            double dx = e.b.X() - e.a.X();
            double dy = e.b.Y() - e.a.Y();
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist == 0) continue;

            double force = SPRING_STRENGTH * (dist - SPRING_LENGTH);
            double fx = dx / dist * force;
            double fy = dy / dist * force;

            if (!e.a.IsLocked()) {
                e.a.setDX(e.a.DX() + fx);
                e.a.setDY(e.a.DY() + fy);
            }
            if (!e.b.IsLocked()) {
                e.b.setDX(e.b.DX() - fx);
                e.b.setDY(e.b.DY() - fy);
            }
        }

        // Apply motion and bounds
        for (TectonView n : tectons.values()) {
            if (n.IsLocked()) continue;

            n.setDX(n.DX() * DAMPING);
            n.setDY(n.DY() * DAMPING);
            n.setX(n.X() + n.DX());
            n.setY(n.Y() + n.DY());

            n.setX(Math.max(NODE_RADIUS / 2.0 + PADDING, Math.min(width - NODE_RADIUS / 2.0 - PADDING, n.X())));
            n.setY(Math.max(NODE_RADIUS / 2.0 + PADDING, Math.min(height - NODE_RADIUS / 2.0 - PADDING, n.Y())));
        }
    }

    public void Draw(Graphics2D g2d) {
        // Only draw edges if showEdges is true
        if (showEdges) {
            g2d.setColor(Color.LIGHT_GRAY);
            for (Edge e : edges) {
                g2d.drawLine((int) e.a.X(), (int) e.a.Y(), (int) e.b.X(), (int) e.b.Y());
            }
        }

        for (TectonView n : tectons.values()) {
            int x = (int) n.X() - NODE_RADIUS / 2;
            int y = (int) n.Y() - NODE_RADIUS / 2;
            g2d.setColor(Color.ORANGE);
            g2d.fillOval(x, y, NODE_RADIUS, NODE_RADIUS);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(x, y, NODE_RADIUS, NODE_RADIUS);
        }
    }
    
    // Toggle edge visibility
    public void toggleEdgeVisibility() {
        showEdges = !showEdges;
    }
    
    // Implement KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            toggleEdgeVisibility();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this implementation
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this implementation
    }
    
    // Method to register this KeyListener with a component
    public void registerKeyListener(java.awt.Component component) {
        component.addKeyListener(this);
        // Ensure the component can receive focus
        component.setFocusable(true);
    }
}
