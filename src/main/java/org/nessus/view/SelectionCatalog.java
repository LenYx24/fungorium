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
    private View view;

    public SelectionCatalog(View view) {
        this.view = view;
    }

    private void SetViewSelection(Object obj){
        view.GetObjectStore().FindEntityView(obj).SetSelected(true);
    }
    private void SetViewSelection(Tecton tecton){
        view.GetObjectStore().FindTectonView(tecton).SetSelected(true);
    }
    private void UnsetViewSelection(Object obj){
        if(obj != null){
            view.GetObjectStore().FindEntityView(obj).SetSelected(false);
        }
    }
    private void UnsetViewSelection(Tecton tecton){
        if(tecton != null){
            view.GetObjectStore().FindTectonView(tecton).SetSelected(false);
        }
    }
    public void SelectBug(Bug bug) {
        selectedBug = bug;
        SetViewSelection(bug);
    }

    public void SelectShroomThread(ShroomThread thread){
        selectedShroomThread = thread;
        SetViewSelection(thread);
    }

    public void SelectShroomBody(ShroomBody body) {
        selectedShroomBody = body;
        SetViewSelection(body);
    }

    public void SelectSpore(Spore spore) {
        selectedSpore = spore;
        SetViewSelection(spore);
    }

    public void SelectTecton(Tecton tecton) {
        if(selectedTectons.size() > 1){
            selectedTectons.remove(0);
            UnsetViewSelection(tecton);
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
