package org.nessus.utility;

import org.nessus.model.tecton.DesertTecton;
import org.nessus.model.tecton.InfertileTecton;
import org.nessus.model.tecton.SingleThreadTecton;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.tecton.ThreadSustainerTecton;
import org.nessus.view.entityviews.TectonView;

public class TectonTexturer {
    private TectonView view = null;

    public TectonView GetResult() {
        return view;
    }

    public void Visit(Tecton tecton) {
        view = new TectonView(tecton);
    }

    public void Visit(DesertTecton tecton) {
        view = new TectonView(tecton);
    }

    public void Visit(InfertileTecton tecton) {
        view = new TectonView(tecton);
    }

    public void Visit(ThreadSustainerTecton tecton) {
        view = new TectonView(tecton);
    }

    public void Visit(SingleThreadTecton tecton) {
        view = new TectonView(tecton);
    }
}
