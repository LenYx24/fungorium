package org.nessus.utility;

import org.nessus.model.tecton.DesertTecton;
import org.nessus.model.tecton.InfertileTecton;
import org.nessus.model.tecton.SingleThreadTecton;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.tecton.ThreadSustainerTecton;

/**
 * A TectonNameReader osztály felelős a különböző tekton típusok neveinek olvasásáért.
 * A látogatóminta (Visitor Pattern) implementációját használja a különböző tekton típusok kezelésére.
 */
public class TectonNameReader implements ITectonVisitor {
    private String name; // A tekton neve

    /**
     * Visszaadja a tekton nevét.
     * 
     * @return String - A tekton neve
     */
    public String GetName() {
        return name;
    }

    /**
     * Feldolgozza az alap Tecton típust.
     * 
     * @param tecton A feldolgozandó Tecton
     * @return void
     */
    @Override
    public void Visit(Tecton tecton) {
        name = "Tekton";
    }

    /**
     * Feldolgozza a DesertTecton típust.
     * 
     * @param tecton A feldolgozandó DesertTecton
     * @return void
     */
    @Override
    public void Visit(DesertTecton tecton) {
        name = "Sivatagi tekton";
    }

    /**
     * Feldolgozza az InfertileTecton típust.
     * 
     * @param tecton A feldolgozandó InfertileTecton
     * @return void
     */
    @Override
    public void Visit(InfertileTecton tecton) {
        name = "Terméketlen tekton";
    }

    /**
     * Feldolgozza a SingleThreadTecton típust.
     * 
     * @param tecton A feldolgozandó SingleThreadTecton
     * @return void
     */
    @Override
    public void Visit(SingleThreadTecton tecton) {
        name = "Egyfonalas tekton";
    }

    /**
     * Feldolgozza a ThreadSustainerTecton típust.
     * 
     * @param tecton A feldolgozandó ThreadSustainerTecton
     * @return void
     */
    @Override
    public void Visit(ThreadSustainerTecton tecton) {
        name = "Fonáltartó tekton";
    }
    
}
