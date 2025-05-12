package org.nessus.view.entityviews;
import org.nessus.view.SelectionCatalog;
import org.nessus.view.View;

public class EntitySelector {
    private SelectionCatalog selection;

    public EntitySelector(SelectionCatalog selection) {
        this.selection = selection;
    }

    public void Visit(BugView bugView) {
        selection.SelectBug(bugView.GetModel());
    }

    public void Visit(ShroomBodyView bodyView) {
        selection.SelectShroomBody(bodyView.GetModel());
    }

    public void Visit(ShroomThreadView threadView) {
        selection.SelectShroomThread(threadView.GetModel());
    }

    public void Visit(SporeView sporeView) {
        selection.SelectSpore(sporeView.GetModel());
    }

    public void Visit(TectonView tectonView) {
        selection.SelectTecton(tectonView.GetModel());
    }
}
