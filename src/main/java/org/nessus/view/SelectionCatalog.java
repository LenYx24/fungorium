package org.nessus.view;

import java.util.ArrayList;
import java.util.List;

import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;

public class SelectionCatalog {
    private List<Tecton> selectedTectons = new ArrayList<>();
    private Spore selectedSpore = null;
    private Bug selectedBug = null;
    private ShroomThread selectedShroomThread = null;
    private ShroomBody selectedShroomBody = null;
    private ObjectStore store;

    public SelectionCatalog(ObjectStore store) {
        this.store = store;
    }

    private void SetViewSelection(Object obj){
        var entityView = store.FindEntityView(obj);
        if (entityView != null)
            entityView.SetSelected(true);
    }

    private void SetViewSelection(Tecton tecton){
        store.FindTectonView(tecton).SetSelected(true);
    }

    public void UnsetViewSelection(Object obj){
        var entityView = store.FindEntityView(obj);
        if (entityView != null)
            store.FindEntityView(obj).SetSelected(false);
    }

    private void UnsetViewSelection(Tecton tecton){
        var tectonView = store.FindTectonView(tecton);
        if (tectonView != null)
            store.FindTectonView(tecton).SetSelected(false);
    }

    public void SelectBug(Bug bug)
    {
        if (selectedBug == bug)
        {
            UnsetViewSelection(bug);
            selectedBug = null;
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
        if (selectedShroomThread == thread)
        {
            SetViewSelection(thread);
            selectedShroomThread = null;
        }
        else
        {
            UnsetViewSelection(selectedShroomThread);
            selectedShroomThread = thread;
            SetViewSelection(thread);
        }
    }

    public void SelectShroomBody(ShroomBody body) {
        if (selectedShroomBody == body)
        {
            UnsetViewSelection(body);
            selectedShroomBody = null;
        }
        else
        {
            UnsetViewSelection(selectedShroomBody);
            selectedShroomBody = body;
            SetViewSelection(body);
        }
    }

    public void SelectSpore(Spore spore) {
        if (selectedSpore == spore)
        {
            UnsetViewSelection(spore);
            selectedSpore = null;
        }
        else
        {
            UnsetViewSelection(selectedSpore);
            selectedSpore = spore;
            SetViewSelection(spore);
        }
    }

    public void SelectTecton(Tecton tecton) {
        if(selectedTectons.contains(tecton)){
            UnsetViewSelection(tecton);
            selectedTectons.remove(tecton);
        }else{
            if(selectedTectons.size() > 1){
                UnsetViewSelection(selectedTectons.get(0));
                selectedTectons.remove(0);
            }
            selectedTectons.add(tecton);
            SetViewSelection(tecton);
        }
    }

    public void Clear(){
        UnsetViewSelection(selectedBug);
        UnsetViewSelection(selectedShroomBody);
        UnsetViewSelection(selectedShroomThread);
        UnsetViewSelection(selectedSpore);
        selectedTectons.forEach(this::UnsetViewSelection);

        selectedBug = null;
        selectedShroomBody = null;
        selectedShroomThread = null;
        selectedSpore = null;
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
