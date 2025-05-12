package org.nessus.utility;
import org.nessus.view.SelectionCatalog;
import org.nessus.view.View;
import org.nessus.view.entityviews.BugView;
import org.nessus.view.entityviews.ShroomBodyView;
import org.nessus.view.entityviews.ShroomThreadView;
import org.nessus.view.entityviews.SporeView;
import org.nessus.view.entityviews.TectonView;

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
