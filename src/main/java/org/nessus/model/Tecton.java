package org.nessus.model;

import org.nessus.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class Tecton {
    protected List<Tecton> neighbours = new ArrayList<>();
    protected List<Spore> spores = new ArrayList<>();
    protected List<ShroomThread> shroomThreads = new ArrayList<>();
    protected List<Bug> bugs = new ArrayList<>();
    protected ShroomBody shroomBody = null;

    public void Split() {
        Skeleton.LogFunctionCall(this, "Split");
        Tecton tecton2 = this.Copy();
        
        //Konkurens Módosítás Kivétel elkerülése érdekében
        ArrayList<ShroomThread> temp = new ArrayList<>(shroomThreads);

        for (ShroomThread thread : temp)
        {
            thread.Remove();
        }

        shroomThreads.clear();

        Skeleton.LogReturnCall(this, "Split");
    }

    public boolean GrowShroomThread(ShroomThread thread)  {
        Skeleton.LogFunctionCall(this, "GrowShroomThread", thread);
        shroomThreads.add(thread);
        Skeleton.LogReturnCall(this, "GrowShroomThread", true);
        return true;
    }

    public void RemoveShroomThread(ShroomThread thread)
    {
        Skeleton.LogFunctionCall(this, "RemoveShroomThread", thread);
        shroomThreads.remove(thread);
        Skeleton.LogReturnCall(this, "RemoveShroomThread");
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

    public void AddBug(Bug bug)
    {
        Skeleton.LogFunctionCall(this, "AddBug", bug);
        bugs.add(bug);
        Skeleton.LogReturnCall(this, "AddBug");
    }

    public void RemoveBug(Bug bug)
    {
        Skeleton.LogFunctionCall(this, "RemoveBug", bug);
        bugs.remove(bug);
        Skeleton.LogReturnCall(this, "RemoveBug");
    }

    public Tecton Copy()
    {
        Skeleton.LogFunctionCall(this, "Copy");

        Tecton copyTecton = new Tecton();
        Skeleton.AddObject(copyTecton, "copyTecton");

        neighbours.forEach(neighbour -> copyTecton.neighbours.add(neighbour));
        neighbours.add(copyTecton);

        var bugIter = bugs.iterator();

        while(bugIter.hasNext())
        {
            Bug bug = bugIter.next();
            String name = Skeleton.GetName(bug);
            boolean transferBug = Skeleton.YesNoQuestion("Törés után átkerüljön-e a(z) " + name + " rovar a copyTecton tektonra?");

            if (transferBug)
            {
                copyTecton.AddBug(bug);
                bug.SetTecton(copyTecton);
                bugIter.remove();
            }
        }

        var sporeIter = spores.iterator();

        while(sporeIter.hasNext())
        {
            Spore spore = sporeIter.next();
            String name = Skeleton.GetName(spore);
            boolean transferSpore = Skeleton.YesNoQuestion("Törés után átkerüljön-e a(z) " + name + " spóra a copyTecton tektonra?");

            if (transferSpore)
            {
                copyTecton.ThrowSpore(spore);
                spore.SetTecton(copyTecton);
                sporeIter.remove();
            }
        }

        if (this.shroomBody != null)
        {
            boolean transferBody = Skeleton.YesNoQuestion("Törés után átkerüljön-e a gombatest a copyTecton tektonra?");

            if (transferBody)
            {
                copyTecton.GrowShroomBody(shroomBody);
                shroomBody.SetTecton(copyTecton);
                this.ClearShroomBody();
            }
        }

        Skeleton.LogReturnCall(this, "Copy");
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

    public boolean HasSporeOfShroom(Shroom shroom)
    {
        Skeleton.LogFunctionCall(this, "HasSporeOfShroom");
        boolean ret = Skeleton.YesNoQuestion("Van-e ezen a tektonon ehhez a gombafajhoz tartozó spóra?");
        Skeleton.LogReturnCall(this, "HasSporeOfShroom", ret);
        return ret;
    }

    public boolean IsNeighbourOf(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "IsNeighbourOf", tecton);
        boolean isNeighbourOf = Skeleton.YesNoQuestion("Szomszédos a két tekton?");
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

    public List<ShroomThread> getThreads()
    {
        return shroomThreads;
    }
}
