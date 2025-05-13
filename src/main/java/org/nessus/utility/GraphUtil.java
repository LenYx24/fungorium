package org.nessus.utility;

import org.nessus.model.tecton.Tecton;
import org.nessus.view.entityviews.TectonView;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.awt.Graphics2D;
import java.awt.Color;

class Edge {
    public TectonView a, b;

    public Edge(TectonView a, TectonView b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        Edge edge = (Edge) obj;
        if (edge.a == a && edge.b == b)
            return true;
        if (edge.a == b && edge.b == a)
            return true;
        
        return false;
    }

    @Override
    public int hashCode() {
        // sorrendfüggetlen hash
        return a.hashCode() + b.hashCode();
    }
};

public class GraphUtil {
    private int width;
    private int height;

    private static final double SPRING_LENGTH = 300;
    private static final double SPRING_STRENGTH = 0.07;
    private static final double REPULSION_STRENGTH = 50;
    private static final double DAMPING = 3;
    private static final int NODE_RADIUS = 100;

    private Set<Edge> edges = new HashSet<>();
    private Map<Tecton, TectonView> tectons;

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
                edges.add(new Edge(tecton, t2));
            }
        }

        applyForces();
    }

    private void applyForces() {
        Tecton maxNeighbourTecton = tectons.keySet().stream().findFirst().orElse(null);
        for(Tecton t : tectons.keySet()) {
            if(t.GetNeighbours().size() > maxNeighbourTecton.GetNeighbours().size()) {
                maxNeighbourTecton = t;
            }
        }
        for (TectonView n : tectons.values()) {
            if(n.GetModel() == maxNeighbourTecton){
                int cp_x = 640;
                int cp_y = 360;
                int v_x = cp_x - (int)n.GetX();
                int v_y = cp_y - (int)n.GetY();
                double length = Math.sqrt(v_x * v_x + v_y * v_y);
                int dx = v_x / 100;
                int dy = v_x / 100;
                if(length < 72){
                    dx = 0;
                    dy = 0;
                }else if (length < 160){
                    dx = v_x / 200;
                    dy = v_x / 200;
                }
                n.setDX(dx);
                n.setDY(dy);
            }
            n.setDX(0);
            n.setDY(0);
        }

        for (TectonView a : tectons.values()) {
            for (TectonView b : tectons.values()) {
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

        for (TectonView n : tectons.values())
        {
            n.setDX(n.DX() * DAMPING);
            n.setDY(n.DY() * DAMPING);

            n.setX(n.X() + n.DX());
            n.setY(n.Y() + n.DY());

            n.setX(Math.max(NODE_RADIUS, Math.min(width - NODE_RADIUS, n.X())));
            n.setY(Math.max(NODE_RADIUS, Math.min(height - NODE_RADIUS, n.Y())));
        }
    }

    public void Draw(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        for (Edge e : edges) {
            g2d.drawLine((int) e.a.X(), (int) e.a.Y(), (int) e.b.X(), (int) e.b.Y());
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
}
