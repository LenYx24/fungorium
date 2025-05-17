package org.nessus.utility;

import org.nessus.model.tecton.DesertTecton;
import org.nessus.model.tecton.InfertileTecton;
import org.nessus.model.tecton.SingleThreadTecton;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.tecton.ThreadSustainerTecton;

public class TectonNameReader implements ITectonVisitor {
    private String name;

    public String GetName() {
        return name;
    }

    @Override
    public void Visit(Tecton tecton) {
        name = "Tekton";
    }

    @Override
    public void Visit(DesertTecton tecton) {
        name = "Sivatagi tekton";
    }

    @Override
    public void Visit(InfertileTecton tecton) {
        name = "Terméketlen tekton";
    }

    @Override
    public void Visit(SingleThreadTecton tecton) {
        name = "Egyfonalas tekton";
    }

    @Override
    public void Visit(ThreadSustainerTecton tecton) {
        name = "Fonáltartó tekton";
    }
    
}
