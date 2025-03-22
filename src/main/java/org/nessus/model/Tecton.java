package org.nessus.model;

import org.nessus.Skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tecton {
    protected List<Tecton> neighbours = new ArrayList<>();
    protected List<Spore> spores = new ArrayList<>();
    protected List<ShroomThread> shroomThreads = new ArrayList<>();
    protected List<Bug> bugs = new ArrayList<>();
    protected ShroomBody shroomBody = null;

    public void Split() {
        Skeleton.LogFunctionCall(this, "Split");
        Tecton tecton2 = this.Copy();
        Skeleton.LogReturnCall(this, "Split");
    }

    public void GrowShroomThread(ShroomThread thread)
    {
        Skeleton.LogFunctionCall(this, "GrowShroomThread", thread);
        shroomThreads.add(thread);
        Skeleton.LogReturnCall(this, "GrowShroomThread");
    }

    public void RemoveShroomThread(ShroomThread thread)
    {
        Skeleton.LogFunctionCall(this, "RemoveShroomThread", thread);
        shroomThreads.remove(thread);
        Skeleton.LogReturnCall(this, "RemoveShroomThread");
    }

    public boolean GrowShroomBody(ShroomBody body)
    {
        Skeleton.LogFunctionCall(this, "GrowShroomBody", body);
        if (shroomBody != null)
        {
            Skeleton.LogReturnCall(this, "GrowShroomBody", false);
            return false;
        }
        shroomBody = body;
        Skeleton.LogReturnCall(this, "GrowShroomBody", true);
        return true;
    }

    public void ClearShroomBody()
    {
        Skeleton.LogFunctionCall(this, "ClearShroomBody");
        shroomBody = null;
        Skeleton.LogReturnCall(this, "ClearShroomBody");
    }

    public void ThrowSpore(Spore spore) {
        Skeleton.LogFunctionCall(this, "ThrowSpore", spore);
        spores.add(spore);
        Skeleton.LogReturnCall(this, "ThrowSpore");
    }

    public void RemoveSpore(Spore spore)
    {
        Skeleton.LogFunctionCall(this, "RemoveSpore", spore);
        spores.remove(spore);
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

        for (Tecton t : this.neighbours) {
            copyTecton.AddNeighbour(t);
        }

        for (int i=0; i < spores.size(); i++)
        {
            boolean transferSpore = Skeleton.YesNoQuestion("Törés után átkerüljön-e a(z) " + i + ". spóra a copyTecton tektonra?");

            if (transferSpore)
            {
                copyTecton.ThrowSpore(spores.get(i));
                this.RemoveSpore(spores.get(i));
            }
        }

        for (int i=0; i < bugs.size(); i++)
        {
            boolean transferBug = Skeleton.YesNoQuestion("Törés után átkerüljön-e a(z) " + i + ". rovar a copyTecton tektonra?");

            if (transferBug)
            {
                copyTecton.AddBug(bugs.get(i));
                this.RemoveBug(bugs.get(i));
            }
        }

        if (this.shroomBody != null)
        {
            boolean transferBody = Skeleton.YesNoQuestion("Törés után átkerüljön-e a gombatest a copyTecton tektonra?");

            if (transferBody)
            {
                copyTecton.GrowShroomBody(shroomBody);
                this.ClearShroomBody();
            }
        }

        for (ShroomThread thread : this.shroomThreads)
        {
            thread.Remove();

            for (Tecton t : this.neighbours)
            {
                t.RemoveShroomThread(thread);
            }
        }

        shroomThreads.clear();

        Skeleton.LogReturnCall(this, "Copy", copyTecton);
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


    public void AddNeighbour(Tecton neighbour) {
        Skeleton.LogFunctionCall(this, "AddNeighbour", neighbour);
        neighbours.add(neighbour);
        Skeleton.LogReturnCall(this, "AddNeighbour");
    }

    public boolean containsThread(ShroomThread thread) {
        return shroomThreads.contains(thread);
    }

    public List<ShroomThread> getThreads()
    {
        return shroomThreads;
    }
}
