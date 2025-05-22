package org.nessus.utility;
import org.nessus.view.SelectionCatalog;
import org.nessus.view.entities.*;

/**
 * Az EntitySelector osztály felelős a különböző entitások kiválasztásának kezeléséért.
 * A látogatóminta (Visitor Pattern) implementációját használja a különböző entitástípusok kezelésére.
 */
public class EntitySelector {
    private SelectionCatalog selection; // A kiválasztási katalógus, amelyet használni fogunk

    /**
     * Az EntitySelector osztály konstruktora.
     * 
     * @param selection A kiválasztási katalógus, amelyet használni fog
     * @return void
     */
    public EntitySelector(SelectionCatalog selection) {
        this.selection = selection;
    }

    /**
     * Feldolgozza a BugView típusú entitást.
     * 
     * @param bugView A feldolgozandó BugView
     * @return void
     */
    public void Visit(BugView bugView) {
        selection.SelectBug(bugView.GetModel());
    }

    /**
     * Feldolgozza a ShroomBodyView típusú entitást.
     * 
     * @param bodyView A feldolgozandó ShroomBodyView
     * @return void
     */
    public void Visit(ShroomBodyView bodyView) {
        selection.SelectShroomBody(bodyView.GetModel());
    }

    /**
     * Feldolgozza a ShroomThreadView típusú entitást.
     * 
     * @param threadView A feldolgozandó ShroomThreadView
     * @return void
     */
    public void Visit(ShroomThreadView threadView) {
        selection.SelectShroomThread(threadView.GetModel());
    }

    /**
     * Feldolgozza a SporeView típusú entitást.
     * 
     * @param sporeView A feldolgozandó SporeView
     * @return void
     */
    public void Visit(SporeView sporeView) {
        selection.SelectSpore(sporeView.GetModel());
    }

    /**
     * Feldolgozza a TectonView típusú entitást.
     * 
     * @param tectonView A feldolgozandó TectonView
     * @return void
     */
    public void Visit(TectonView tectonView) {
        selection.SelectTecton(tectonView.GetModel());
    }
}
