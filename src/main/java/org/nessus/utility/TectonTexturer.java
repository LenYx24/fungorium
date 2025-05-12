package org.nessus.utility;

import org.nessus.model.tecton.DesertTecton;
import org.nessus.model.tecton.InfertileTecton;
import org.nessus.model.tecton.SingleThreadTecton;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.tecton.ThreadSustainerTecton;
import org.nessus.view.entityviews.TectonView;

public class TectonTexturer {
    public TectonView Visit(Tecton tecton) {
        throw new UnsupportedOperationException();
    }

    public TectonView Visit(DesertTecton tecton) {
        throw new UnsupportedOperationException();
    }

    public TectonView Visit(InfertileTecton tecton) {
        throw new UnsupportedOperationException();
    }

    public TectonView Visit(ThreadSustainerTecton tecton) {
        throw new UnsupportedOperationException();
    }

    public TectonView Visit(SingleThreadTecton tecton) {
        throw new UnsupportedOperationException();
    }
}
