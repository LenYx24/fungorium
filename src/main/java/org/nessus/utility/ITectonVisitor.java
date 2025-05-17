package org.nessus.utility;

import org.nessus.model.tecton.*;

public interface ITectonVisitor {
    void Visit(Tecton tecton);
    void Visit(DesertTecton tecton);
    void Visit(InfertileTecton tecton);
    void Visit(SingleThreadTecton tecton);
    void Visit(ThreadSustainerTecton tecton);
}
