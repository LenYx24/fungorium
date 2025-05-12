package org.nessus.view;

import org.nessus.model.tecton.DesertTecton;
import org.nessus.model.tecton.InfertileTecton;
import org.nessus.model.tecton.SingleThreadTecton;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.tecton.ThreadSustainerTecton;
import org.nessus.view.entityviews.TectonView;

public class TectonTexturer {
    public TectonView visit(Tecton tecton) {
        throw new UnsupportedOperationException();
    }

    public TectonView visit(DesertTecton tecton) {
        throw new UnsupportedOperationException();
    }

    public TectonView visit(InfertileTecton tecton) {
        throw new UnsupportedOperationException();
    }

    public TectonView visit(ThreadSustainerTecton tecton) {
        throw new UnsupportedOperationException();
    }

    public TectonView visit(SingleThreadTecton tecton) {
        throw new UnsupportedOperationException();
    }
}
