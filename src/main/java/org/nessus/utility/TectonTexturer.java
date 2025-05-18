package org.nessus.utility;

import org.nessus.model.tecton.DesertTecton;
import org.nessus.model.tecton.InfertileTecton;
import org.nessus.model.tecton.SingleThreadTecton;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.tecton.ThreadSustainerTecton;
import org.nessus.view.entities.TectonView;

import static org.nessus.utility.ImageReader.GetImage;

/**
 * A TectonTexturer osztály felelős a különböző tekton típusok textúráinak kezeléséért.
 * A látogatóminta (Visitor Pattern) implementációját használja a különböző tekton típusok kezelésére.
 */
public class TectonTexturer implements ITectonVisitor {
    private TectonView view = null; // A létrehozott TectonView objektum

    /**
     * Visszaadja a létrehozott TectonView objektumot.
     * 
     * @return TectonView - A létrehozott TectonView objektum
     */
    public TectonView GetResult() {
        return view;
    }

    /**
     * Feldolgozza az alap Tecton típust.
     * 
     * @param tecton A feldolgozandó Tecton
     * @return void
     */
    public void Visit(Tecton tecton) {
        view = new TectonView(tecton, GetImage("default_tecton"));
    }

    /**
     * Feldolgozza a DesertTecton típust.
     * 
     * @param tecton A feldolgozandó DesertTecton
     * @return void
     */
    public void Visit(DesertTecton tecton) {
        view = new TectonView(tecton, GetImage("desert_tecton"));
    }

    /**
     * Feldolgozza az InfertileTecton típust.
     * 
     * @param tecton A feldolgozandó InfertileTecton
     * @return void
     */
    public void Visit(InfertileTecton tecton) {
        view = new TectonView(tecton, GetImage("infertile_tecton"));
    }

    /**
     * Feldolgozza a ThreadSustainerTecton típust.
     * 
     * @param tecton A feldolgozandó ThreadSustainerTecton
     * @return void
     */
    public void Visit(ThreadSustainerTecton tecton) {
        view = new TectonView(tecton, GetImage("sustainer_tecton"));
    }

    /**
     * Feldolgozza a SingleThreadTecton típust.
     * 
     * @param tecton A feldolgozandó SingleThreadTecton
     * @return void
     */
    public void Visit(SingleThreadTecton tecton) {
        view = new TectonView(tecton, GetImage("singlethread_tecton"));
    }
}
