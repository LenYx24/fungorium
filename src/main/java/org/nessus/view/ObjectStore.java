package org.nessus.view;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nessus.controller.IBugOwnerController;
import org.nessus.controller.IRandomProvider;
import org.nessus.controller.IShroomController;
import org.nessus.model.bug.Bug;
import org.nessus.model.bug.BugOwner;
import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;
import org.nessus.utility.TectonTexturer;
import org.nessus.view.entityviews.*;
import org.nessus.view.factories.BugViewFactory;
import org.nessus.view.factories.ShroomViewFactory;

public class ObjectStore implements IGameObjectStore {
    private Map<BugOwner, SimpleEntry<BugViewFactory, String>> bugOwners = new HashMap<>();
    private Map<Shroom, SimpleEntry<ShroomViewFactory, String>> shrooms = new HashMap<>();

    private List<IEntityView> views = new ArrayList<>();
    private Map<Tecton, TectonView> tectons = new HashMap<>();

    public void Clear() {
        bugOwners.clear();
        shrooms.clear();
        views.clear();
        tectons.clear();
    }

    public void AddShroomBody(ShroomBody shroomBody)
    {
            var shroom = shrooms.get(shroomBody.GetShroom());
            var factory = shroom.getKey();
            ShroomBodyView sbview = factory.CreateShroomBodyView(shroomBody);
            views.add(sbview);
    }

    public void AddShroomThread(ShroomThread shroomThread){
        var shroom = shrooms.get(shroomThread.GetShroom());
        var factory = shroom.getKey();
        views.add(factory.CreateShroomThreadView(shroomThread));
    }

    public void AddSpore(Spore spore) {
        var shroom = shrooms.get(spore.GetShroom());
        var factory = shroom.getKey();
        SporeView spview = factory.CreateSporeView(spore);
        views.add(spview);
    }

    public void RemoveSpore(Spore spore)
    {
        views.removeIf(e -> e.GetModel() == spore);
    }
    
    public void AddBug(Bug bug){
        var bugOwner = bugOwners.get(bug.GetOwner());
        var factory = bugOwner.getKey();
        BugView bview = factory.CreateBugView(bug);
        views.add(bview);
    }

    public void AddTecton(Tecton tecton) {
        var texturer = new TectonTexturer();
        tecton.Accept(texturer);
        tectons.put(tecton, texturer.GetResult());
    }

    public List<IEntityView> GetEntityViews() {
        return views;
    }
    public IEntityView FindEntityView(Object obj){
        for(IEntityView e : views){
            if(e.GetModel() == obj)
                return e;
        };
        return null;
    }
    public TectonView FindTectonView(Tecton tecton){
        return tectons.get(tecton);
    }

    public Map<Tecton, TectonView> GetTectonViews() {
        return tectons;
    }

    public void AddShroom(Shroom shroom, ShroomViewFactory factory, String name) {
        shrooms.put(shroom, new SimpleEntry<>(factory, name));
    }

    public void AddBugOwner(BugOwner bugOwner, BugViewFactory factory, String name) {
        bugOwners.put(bugOwner, new SimpleEntry<>(factory, name));
    }

    public String GetBugOwnerName(IBugOwnerController bugOwner){
        return bugOwners.get((BugOwner)bugOwner).getValue();
    }
    public String GetShroomName(IShroomController shroom){
        return shrooms.get((Shroom) shroom).getValue();
    }
}
