package org.nessus.model;

import org.nessus.Skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tecton {
    private List<Tecton> neighbours = new ArrayList<>();
    private List<Spore> spores = new ArrayList<>();
    private List<ShroomThread> shroomThreads = new ArrayList<>();
    private List<Bug> bugs = new ArrayList<>();
    private ShroomBody shroomBody = null;

    public void Split() {
        Tecton tecton2 = this.Copy();
    }

    public void GrowShroomThread(ShroomThread thread) {
        Skeleton.LogFunctionCall(this, "GrowShroomThread", thread);
        shroomThreads.add(thread);
        Skeleton.LogReturnCall(this, "GrowShroomThread");
    }

    public void RemoveShroomThread(ShroomThread thread) {
        shroomThreads.remove(thread);
    }

    public boolean GrowShroomBody(ShroomBody body) {
        Skeleton.LogFunctionCall(this, "GrowShroomBody", body);
        boolean canGrowShroomBody = Skeleton.YesNoQuestion("Lehet t2-re gombatestet növeszteni?");
        
        if (!canGrowShroomBody) {
            Skeleton.LogReturnCall(this, "GrowShroomBody", false);
            return false;
        }
        
        // A szekvencia diagramon spore2 van írva, de igazából mindegy,
        // a lényeg, hogy egy spórát elhasznál a növesztés
        var consumedSpore = spores.stream()
                                .filter(spore -> spore.GetShroom() == body.GetShroom())
                                .findFirst();
                                
        consumedSpore.ifPresent(spore -> RemoveSpore(spore));
        
        shroomBody = body;
        Skeleton.LogReturnCall(this, "GrowShroomBody", true);
        return true;
    }

    public void SetShroomBody(ShroomBody body) {
        Skeleton.LogFunctionCall(this, "SetShroomBody", body);
        shroomBody = body;
        Skeleton.LogReturnCall(this, "SetShroomBody");
    }

    public void ClearShroomBody() {
        Skeleton.LogFunctionCall(this, "ClearShroomBody");
        shroomBody = null;
        Skeleton.LogReturnCall(this, "ClearShroomBody");
    }

    public void ThrowSpore(Spore spore) {
        Skeleton.LogFunctionCall(this, "ThrowSpore", spore);
        spores.add(spore);
        Skeleton.LogReturnCall(this, "ThrowSpore");
    }

    public void RemoveSpore(Spore spore) {
        Skeleton.LogFunctionCall(this, "RemoveSpore", spore);
        spores.remove(spore);
        spore.GetShroom().RemoveSpore(spore);
        Skeleton.LogReturnCall(this, "RemoveSpore");
    }

    public void AddBug(Bug bug) {
        bugs.add(bug);
    }

    public void RemoveBug(Bug bug) {
        bugs.remove(bug);
    }

    public Tecton Copy() {
        Tecton copyTecton = new Tecton();
        for (Tecton t : neighbours) {
            copyTecton.SetNeighbour(t);
        }

        Random rand = new Random();

        for (Spore spore : spores) {
            if (rand.nextInt(2) == 0) {
                copyTecton.ThrowSpore(spore);
                this.RemoveSpore(spore);
            }
        }

        for (Bug bug : bugs) {
            if (rand.nextInt(2) == 0) {
                copyTecton.AddBug(bug);
                this.RemoveBug(bug);
            }
        }
        if (rand.nextInt(2) == 0) {
            copyTecton.GrowShroomBody(shroomBody);
            this.ClearShroomBody();
        }

        for (ShroomThread thread : shroomThreads) {
            thread.Remove();
        }
        shroomThreads.clear();

        return copyTecton;
    }

    public boolean HasGrownShroomThreadTo(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "HasGrownShroomThreadTo", tecton);
        boolean hasGrownShroomThread = false;
        for (ShroomThread shroomThread : shroomThreads) {
            if (shroomThread.IsTectonReachable(tecton)) {
                hasGrownShroomThread = true;
            }
        }
        Skeleton.LogReturnCall(this, "HasGrownShroomThreadTo", hasGrownShroomThread);
        return hasGrownShroomThread;
    }

    public boolean HasSporeOfShroom(Shroom shroom) {
        return true;
    }

    public boolean IsNeighbourOf(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "IsNeighbourOf", tecton);
        boolean isNeighbourOf = Skeleton.YesNoQuestion("A kiindulási és céltekton azonos?");
        Skeleton.LogReturnCall(this, "IsNeighbourOf", isNeighbourOf);
        return isNeighbourOf;
    }

    public void UpdateTecton() {

    }


    public void SetNeighbour(Tecton neighbour) {
        Skeleton.LogFunctionCall(this, "SetNeighbour", neighbour);
        neighbours.add(neighbour);
        Skeleton.LogReturnCall(this, "SetNeighbour");
    }

    public boolean containsThread(ShroomThread thread) {
        return shroomThreads.contains(thread);
    }
}
