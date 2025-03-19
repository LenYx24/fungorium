package org.nessus.model;

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

    public void GrowThread(ShroomThread thread) {
        shroomThreads.add(thread);
    }

    public void RemoveThread(ShroomThread thread) {
        shroomThreads.remove(thread);
    }

    public boolean GrowShroomBody(ShroomBody body) {
        if (shroomBody != null) return false;
        shroomBody = body;
        return true;
    }

    public void ClearShroomBody() {
        shroomBody = null;
    }

    public void ThrowSpore(Spore spore) {
        spores.add(spore);
    }

    public void RemoveSpore(Spore spore) {
        spores.remove(spore);
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
            copyTecton.AddNeighbour(t);
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

    public boolean HasThreadTo(Tecton tecton) {
        for (ShroomThread shroomThread : shroomThreads) {
            if (shroomThread.tecton1 == tecton || shroomThread.tecton2 == tecton)
                return true;
        }
        return false;
    }

    public boolean containsThread(ShroomThread thread) {
        return shroomThreads.contains(thread);
    }


    public void AddNeighbour(Tecton neighbour) {
        neighbours.add(neighbour);
    }
}
