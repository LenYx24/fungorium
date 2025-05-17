package org.nessus.utility;

import org.nessus.model.tecton.Tecton;
import org.nessus.view.entities.TectonView;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.awt.Graphics2D;
import java.awt.Color;

class Edge {
    public TectonView t1;
    public TectonView t2;

    public Edge(TectonView a, TectonView b) {
        this.t1 = a;
        this.t2 = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge edge)) return false;
        return (edge.t1 == t1 && edge.t2 == t2) || (edge.t1 == t2 && edge.t2 == t1);
    }

    @Override
    public int hashCode() {
        return t1.hashCode() + t2.hashCode();
    }
}

public class GraphUtil {
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

        ApplyForces();
    }

    private void ApplyRepulsion(List<TectonView> unlockedTectons) {
        for (var tecton : unlockedTectons) {
            for (TectonView b : tectons.values()) {
                if (tecton == b)
                    continue;
                
                double dx = tecton.X() - b.X();
                double dy = tecton.Y() - b.Y();
                double distSq = dx * dx + dy * dy;
                double dist = Math.max(Math.sqrt(distSq), NODE_RADIUS);
                
                double force = REPULSION_STRENGTH / (dist * dist);
                
                tecton.setDx(tecton.getDx() + (dx / dist) * force);
                tecton.setDy(tecton.getDy() + (dy / dist) * force);
            }
        }
    }

    private void ApplyAttraction(List<Edge> unlockedEdges) {
        for (Edge edge : unlockedEdges) {
            double dx = edge.t2.X() - edge.t1.X();
            double dy = edge.t2.Y() - edge.t1.Y();
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist == 0)
                continue;

            double force = SPRING_STRENGTH * (dist - SPRING_LENGTH);
            double fx = dx / dist * force;
            double fy = dy / dist * force;

            if (!edge.t1.IsLocked()) {
                edge.t1.setDx(edge.t1.getDx() + fx);
                edge.t1.setDy(edge.t1.getDy() + fy);
            }

            if (!edge.t2.IsLocked()) {
                edge.t2.setDx(edge.t2.getDx() - fx);
                edge.t2.setDy(edge.t2.getDy() - fy);
            }
        }
    }

    private void ApplyMotionAndBounds(List<TectonView> unlockedTectons) {
        for (TectonView n : unlockedTectons) {
            n.setDx(n.getDx() * DAMPING);
            n.setDy(n.getDy() * DAMPING);

            n.setX(n.X() + n.getDx());
            n.setY(n.Y() + n.getDy());

            n.setX(Math.max(NODE_RADIUS / 2.0 + PADDING, Math.min(width - NODE_RADIUS / 2.0 - PADDING, n.X())));
            n.setY(Math.max(NODE_RADIUS / 2.0 + PADDING, Math.min(height - NODE_RADIUS / 2.0 - PADDING, n.Y())));
        }
    }

    private void ApplyForces() {
        var unlockedTectons = tectons.values().stream().filter(x -> !x.IsLocked()).toList();
        var unlockedEdges = edges.stream().filter(x -> !x.t1.IsLocked() || !x.t2.IsLocked()).toList();

        for (var tecton : unlockedTectons) {
            tecton.setDx(0);
            tecton.setDy(0);
        }

        ApplyRepulsion(unlockedTectons);
        ApplyAttraction(unlockedEdges);
        ApplyMotionAndBounds(unlockedTectons);
    }

    private boolean ConnectedByGrownShroomThread(TectonView t1, TectonView t2) {
        var t2Model = t2.GetModel();

        for (var thread : t1.GetModel().GetShroomThreads()) {
            if (thread.GetEvolution() < 3)
                continue;

            var threadEnd1 = thread.GetTecton1();
            var threadEnd2 = thread.GetTecton2();
            
            if (threadEnd1 == t2Model || threadEnd2 == t2Model)
                return true;
        }

        return false;
    }

    public void DrawNeighbourMarkers(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        
        edges.stream()
            .filter(edge -> !ConnectedByGrownShroomThread(edge.t1, edge.t2))
            .forEach(edge -> g2d.drawLine((int) edge.t1.X(), (int) edge.t1.Y(), (int) edge.t2.X(), (int) edge.t2.Y()));
    }
}
