package org.nessus.utility;

import org.nessus.model.tecton.*;

/**
 * Az ITectonVisitor interfész a látogatóminta (Visitor Pattern) implementációját biztosítja a tektonok számára.
 * Lehetővé teszi különböző műveletek végrehajtását a különböző típusú tektonokon anélkül, hogy módosítani kellene a tekton osztályokat.
 */
public interface ITectonVisitor {
    /**
     * Meglátogatja az alap Tecton típust.
     * 
     * @param tecton A meglátogatandó Tecton
     * @return void
     */
    void Visit(Tecton tecton);
    
    /**
     * Meglátogatja a DesertTecton típust.
     * 
     * @param tecton A meglátogatandó DesertTecton
     * @return void
     */
    void Visit(DesertTecton tecton);
    
    /**
     * Meglátogatja az InfertileTecton típust.
     * 
     * @param tecton A meglátogatandó InfertileTecton
     * @return void
     */
    void Visit(InfertileTecton tecton);
    
    /**
     * Meglátogatja a SingleThreadTecton típust.
     * 
     * @param tecton A meglátogatandó SingleThreadTecton
     * @return void
     */
    void Visit(SingleThreadTecton tecton);
    
    /**
     * Meglátogatja a ThreadSustainerTecton típust.
     * 
     * @param tecton A meglátogatandó ThreadSustainerTecton
     * @return void
     */
    void Visit(ThreadSustainerTecton tecton);
}
