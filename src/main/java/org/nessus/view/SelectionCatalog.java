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

    public void SelectBug(Bug bug) {
        selectedBug = bug;
    }

    public void SelectShroomThread(ShroomThread thread){
        selectedShroomThread = thread;
    }

    public void SelectShroomBody(ShroomBody body) {
        selectedShroomBody = body;
    }

    public void SelectSpore(Spore spore) {
        selectedSpore = spore;
    }

    public void SelectTecton(Tecton tecton) {
        if(selectedTectons.size() > 1){
            selectedTectons.remove(0);
        }
        selectedTectons.add(tecton);
    }

    public void ClearSelection(){
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
