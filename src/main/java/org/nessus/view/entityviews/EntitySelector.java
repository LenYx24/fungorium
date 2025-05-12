package org.nessus.view.entityviews;
import org.nessus.view.View;

public class EntitySelector {
    private View view;

    public EntitySelector(View view) {
        this.view = view;
    }

    public void Visit(BugView bugView) {
        view.SelectBug(bugView.GetModel());
    }

    public void Visit(ShroomBodyView bodyView) {
        view.SelectShroomBody(bodyView.GetModel());
    }

    public void Visit(ShroomThreadView threadView) {
        view.SelectShroomThread(threadView.GetModel());
    }

    public void Visit(SporeView sporeView) {
        view.SelectSpore(sporeView.GetModel());
    }

    public void Visit(TectonView tectonView) {
        view.SelectTecton(tectonView.GetModel());
    }
}
