package org.nessus.view;

import java.util.ArrayList;
import java.util.List;

import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.entityviews.IEntityView;

public class SelectionCatalog {
    private List<Tecton> selectedTectons = new ArrayList<>();
    private Spore selectedSpore = null;
    private Bug selectedBug = null;
    private ShroomThread selectedShroomThread = null;
    private ShroomBody selectedShroomBody = null;
    private View view;

    public SelectionCatalog(View view) {
        this.view = view;
    }

    private void SetViewSelection(Object obj){
        var entityView = view.GetObjectStore().FindEntityView(obj);
        if (entityView != null)
            entityView.SetSelected(true);
    }

    private void SetViewSelection(Tecton tecton){
        view.GetObjectStore().FindTectonView(tecton).SetSelected(true);
    }

    public void UnsetViewSelection(Object obj){
        var entityView = view.GetObjectStore().FindEntityView(obj);
        if (entityView != null)
            view.GetObjectStore().FindEntityView(obj).SetSelected(false);
    }

    private void UnsetViewSelection(Tecton tecton){
        view.GetObjectStore().FindTectonView(tecton).SetSelected(false);
    }

    public void SelectBug(Bug bug)
    {
        if (selectedBug == null)
        {
            selectedBug = bug;
            SetViewSelection(bug);
        }
        else
        {
            UnsetViewSelection(selectedBug);
            selectedBug = bug;
            SetViewSelection(bug);
        }
    }

    public void SelectShroomThread(ShroomThread thread)
    {
        if (selectedShroomThread == null)
        {
            selectedShroomThread = thread;
            SetViewSelection(thread);
        }
        else
        {
            UnsetViewSelection(selectedShroomThread);
            selectedShroomThread = thread;
            SetViewSelection(thread);
        }
    }

    public void SelectShroomBody(ShroomBody body) {
        if (selectedShroomBody == null)
        {
            selectedShroomBody = body;
            SetViewSelection(body);
        }
        else
        {
            UnsetViewSelection(selectedShroomBody);
            selectedShroomBody = body;
            SetViewSelection(body);
        }
    }

    public void SelectSpore(Spore spore) {
        if (selectedSpore == null)
        {
            selectedSpore = spore;
            SetViewSelection(spore);
        }
        else
        {
            UnsetViewSelection(selectedSpore);
            selectedSpore = spore;
            SetViewSelection(spore);
        }
    }

    public void SelectTecton(Tecton tecton) {
        if(selectedTectons.size() > 1){
            UnsetViewSelection(selectedTectons.get(0));
            selectedTectons.remove(0);
        }
        selectedTectons.add(tecton);
        SetViewSelection(tecton);
    }
    public void ClearSelection(){
        UnsetViewSelection(selectedBug);
        selectedBug = null;
        UnsetViewSelection(selectedShroomBody);
        selectedShroomBody = null;
        UnsetViewSelection(selectedShroomThread);
        selectedShroomThread = null;
        UnsetViewSelection(selectedSpore);
        selectedSpore = null;
        for(Tecton t : selectedTectons){
            UnsetViewSelection(t);
        }
        selectedTectons.clear();
    }

    public Bug GetBug() {
        return selectedBug;
    }

    public ShroomThread GetShroomThread() {
        return selectedShroomThread;
    }

    public ShroomBody GetShroomBody() {
        return selectedShroomBody;
    }

    public Spore GetSpore() {
        return selectedSpore;
    }

    public List<Tecton> GetTectons() {
        return selectedTectons;
    }
}
