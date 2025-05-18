package org.nessus.utility;

import org.nessus.model.tecton.Tecton;
import org.nessus.view.entities.TectonView;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Az Edge osztály két TectonView közötti kapcsolatot reprezentál.
 * A gráf éleit képviseli a GraphUtil osztályban.
 */
class Edge {
    public TectonView t1; // az első TectonView

    public TectonView t2; // a második TectonView

    /**
     * Létrehoz egy új élet két TectonView között.
     * 
     * @param a Az első TectonView
     * @param b A második TectonView
     */
    public Edge(TectonView a, TectonView b) {
        this.t1 = a;
        this.t2 = b;
    }

    /**
     * Összehasonlítja ezt az élet egy másik objektummal.
     * Két él egyenlő, ha ugyanazokat a TectonView-kat köti össze, függetlenül a sorrendtől.
     * 
     * @param obj Az összehasonlítandó objektum
     * @return boolean - Igaz, ha a két él egyenlő, hamis egyébként
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Edge edge)) return false;
        return (edge.t1 == t1 && edge.t2 == t2) || (edge.t1 == t2 && edge.t2 == t1);
    }

    /**
     * Kiszámítja az él hash kódját.
     * A hash kód a két TectonView hash kódjának összege.
     * 
     * @return int - Az él hash kódja
     */
    @Override
    public int hashCode() {
        return t1.hashCode() + t2.hashCode();
    }
}

/**
 * A GraphUtil osztály felelős a gráf elrendezéséért és megjelenítéséért.
 * Erő-alapú algoritmusokat használ a csúcsok elrendezéséhez.
 */
public class GraphUtil {
    private int width; // a gráf szélessége
    private int height; // a gráf magassága
    private static final double PADDING = 10; // a gráf szélén lévő párnázás
    private static final double SPRING_LENGTH = 200; // a rugó hossza
    private static final double SPRING_STRENGTH = 0.01; // a rugó ereje
    private static final double REPULSION_STRENGTH = 30000; // a taszító erő
    private static final double DAMPING = 0.9; // a csillapítás 
    private static final int NODE_RADIUS = 100; // a csúcsok sugara
    private Set<Edge> edges = new HashSet<>(); // az élek halmaza
    private Map<Tecton, TectonView> tectons; // a tektonokat és a hozzájuk tartozó nézeteket tároló térkép
    
    /**
     * Jelzi, hogy a szomszédsági vonalak láthatóak-e.
     */
    private boolean showNeighbourLines = true;
    
    /**
     * A billentyűzet eseményeket kezelő adapter.
     */
    private KeyAdapter keyAdapter;

    /**
     * Létrehoz egy új GraphUtil objektumot a megadott paraméterekkel.
     * 
     * @param width A gráf szélessége
     * @param height A gráf magassága
     * @param tectons A tektonokat és a hozzájuk tartozó nézeteket tároló térkép
     * @return void
     */
    public GraphUtil(int width, int height, Map<Tecton, TectonView> tectons) {
        this.width = width;
        this.height = height;
        this.tectons = tectons;
        
        // Billentyűzet eseménykezelő inicializálása
        this.keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    showNeighbourLines = !showNeighbourLines;
                    // Újrarajzolás kérése, ha van hozzá kapcsolódó komponens
                    if (e.getComponent() != null) {
                        e.getComponent().repaint();
                    }
                }
            }
        };
    }
    
    /**
     * Visszaadja a billentyűzet eseménykezelőt.
     * 
     * @return KeyAdapter - A billentyűzet eseménykezelő
     */
    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }
    
    /**
     * Regisztrálja a billentyűzet eseménykezelőt egy komponenshez.
     * 
     * @param component A komponens, amelyhez a billentyűzet eseménykezelőt regisztráljuk
     */
    public void registerKeyListener(java.awt.Component component) {
        component.addKeyListener(keyAdapter);
        // Biztosítjuk, hogy a komponens fókuszálható legyen
        component.setFocusable(true);
    }

    /**
     * Elrendezi a gráfot.
     * Létrehozza az éleket a tektonok szomszédsági kapcsolatai alapján, majd alkalmazza az erőket.
     * 
     * @return void
     */
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

    /**
     * Alkalmazza a taszító erőket a nem rögzített tektonokra.
     * 
     * @param unlockedTectons A nem rögzített tektonok listája
     * @return void
     */
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

    /**
     * Alkalmazza a vonzó erőket a nem rögzített élekre.
     * 
     * @param unlockedEdges A nem rögzített élek listája
     * @return void
     */
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

    /**
     * Alkalmazza a mozgást a nem rögzített tektonokra.
     * 
     * @param unlockedTectons A nem rögzített tektonok listája
     * @return void
     */
    private void ApplyMotion(List<TectonView> unlockedTectons) {
        for (TectonView n : unlockedTectons) {
            n.setDx(n.getDx() * DAMPING);
            n.setDy(n.getDy() * DAMPING);

            n.setX(n.X() + n.getDx());
            n.setY(n.Y() + n.getDy());
        }
    }

    /**
     * Alkalmazza az összes erőt a gráfra.
     * 
     * @return void
     */
    private void ApplyForces() {
        var unlockedTectons = tectons.values().stream().filter(x -> !x.IsLocked()).toList();
        var unlockedEdges = edges.stream().filter(x -> !x.t1.IsLocked() || !x.t2.IsLocked()).toList();

        for (var tecton : unlockedTectons) {
            tecton.setDx(0);
            tecton.setDy(0);
        }

        ApplyRepulsion(unlockedTectons);
        ApplyAttraction(unlockedEdges);
        ApplyMotion(unlockedTectons);
    }

    /**
     * Ellenőrzi, hogy két tekton között van-e kifejlett gombafonal.
     * 
     * @param t1 Az első TectonView
     * @param t2 A második TectonView
     * @return boolean - Igaz, ha van kifejlett gombafonal a két tekton között, hamis egyébként
     */
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

    /**
     * Kirajzolja a szomszédsági jelölőket.
     * Csak azokat az éleket rajzolja ki, amelyek között nincs kifejlett gombafonal.
     * 
     * @param g2d A Graphics2D objektum, amelyre rajzolni kell
     * @return void
     */
    public void DrawNeighbourMarkers(Graphics2D g2d) {
        if (!showNeighbourLines) {
            return;
        }
        
        g2d.setColor(Color.LIGHT_GRAY);
        
        edges.stream()
            .filter(edge -> !ConnectedByGrownShroomThread(edge.t1, edge.t2))
            .forEach(edge -> g2d.drawLine((int) edge.t1.X(), (int) edge.t1.Y(), (int) edge.t2.X(), (int) edge.t2.Y()));
    }
}
