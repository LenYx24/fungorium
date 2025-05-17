package org.nessus.utility;

import org.nessus.model.tecton.DesertTecton;
import org.nessus.model.tecton.InfertileTecton;
import org.nessus.model.tecton.SingleThreadTecton;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.tecton.ThreadSustainerTecton;
import org.nessus.view.entities.TectonView;

import static org.nessus.utility.ImageReader.GetImage;

public class TectonTexturer implements ITectonVisitor {
    private TectonView view = null;

    public TectonView GetResult() {
        return view;
    }

    public void Visit(Tecton tecton) {
        view = new TectonView(tecton, GetImage("default_tecton"));
    }

    public void Visit(DesertTecton tecton) {
        view = new TectonView(tecton, GetImage("desert_tecton"));
    }

    public void Visit(InfertileTecton tecton) {
        view = new TectonView(tecton, GetImage("infertile_tecton"));
    }

    public void Visit(ThreadSustainerTecton tecton) {
        view = new TectonView(tecton, GetImage("sustainer_tecton"));
    }

    public void Visit(SingleThreadTecton tecton) {
        view = new TectonView(tecton, GetImage("singlethread_tecton"));
    }
}
