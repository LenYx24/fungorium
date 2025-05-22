package org.nessus.model;

import org.nessus.Skeleton;

import java.util.ArrayList;
import java.util.List;

/**
 * A tektonokat reprezentáló osztály.
 * A tektonok tartalmaznak rovarokat, spórákat, gombatestet és gombafonalakat.
 * A tektonok szomszédosak lehetnek egymással.
 * @see org.nessus.model.Bug
 * @see org.nessus.model.Spore
 * @see org.nessus.model.ShroomThread
 * @see org.nessus.model.ShroomBody
 * @see org.nessus.model.Shroom
 */
public class Tecton {
    protected List<Tecton> neighbours = new ArrayList<>(); // A szomszédos tektonok listája
    protected List<Spore> spores = new ArrayList<>(); // A tektonon található spórák listája
    protected List<ShroomThread> shroomThreads = new ArrayList<>(); // A tektonon található gombafonalak listája
    protected List<Bug> bugs = new ArrayList<>(); // A tektonon található rovarok listája
    protected ShroomBody shroomBody = null; // A tektonon található gombatest

    protected void SpreadEntities(Tecton copyTecton) {
        neighbours.forEach(neighbour -> copyTecton.neighbours.add(neighbour));
        neighbours.add(copyTecton);

        var bugIter = bugs.iterator();

        while(bugIter.hasNext()) {
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

        while(sporeIter.hasNext()) {
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

        if (this.shroomBody != null) {
            boolean transferBody = Skeleton.YesNoQuestion("Törés után átkerüljön-e a gombatest a copyTecton tektonra?");

            if (transferBody)
            {
                copyTecton.SetShroomBody(shroomBody);
                shroomBody.SetTecton(copyTecton);
                this.ClearShroomBody();
            }
        }
    }

    /**
     * A tekton törése.
     * A tekton törésekor a tektonon található rovarok és spórák átkerülnek a szomszédos tektonokra.
     * @see org.nessus.model.Bug
     * @see org.nessus.model.Shroom
     */
    public void Split() {
        Skeleton.LogFunctionCall(this, "Split");

        // A későbbiekben lesz egy tárolóosztály, ami majd számontartja a tektonokat, egyelőre csak elvégzünk egy másolást,
        // a másolatot nem tartjuk meg.
        Tecton copyTecton = Copy();

        //Konkurens Módosítás Kivétel elkerülése érdekében másolat
        List.copyOf(shroomThreads).forEach(ShroomThread::Remove);

        Skeleton.LogReturnCall(this, "Split");
    }

    /**
     * Gombafonal növesztése a tektonon.
     * @param thread - A növesztendő gombafonal
     * @return Boolean - Sikeres volt-e a növesztés
     */
    public boolean GrowShroomThread(ShroomThread thread) {
        Skeleton.LogFunctionCall(this, "GrowShroomThread", thread);
        shroomThreads.add(thread);
        Skeleton.LogReturnCall(this, "GrowShroomThread", true);
        return true;
    }

    /**
     * Gombafonal eltávolítása a tektonról.
     * @param thread - Az eltávolítandó gombafonal
     * @return void
     */
    public void RemoveShroomThread(ShroomThread thread) {
        Skeleton.LogFunctionCall(this, "RemoveShroomThread", thread);
        shroomThreads.remove(thread);
        Skeleton.LogReturnCall(this, "RemoveShroomThread");
    }

    /**
     * Gombatest növesztése a tektonon.
     * A gombatest növesztéséhez szükséges egy spóra, amit a tektonon található spórák közül kell kiválasztani.
     * @param body - A növesztendő gombatest
     * @return Boolean - Sikeres volt-e a növesztés
     */
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
                                
        consumedSpore.ifPresent(this::RemoveSpore);

        shroomBody = body;
        Skeleton.LogReturnCall(this, "GrowShroomBody", true);
        return true;
    }

    /**
     * Gombatest beállítása a tektonra.
     * @param body - A beállítandó gombatest
     * @return void
     */
    public void SetShroomBody(ShroomBody body) {
        Skeleton.LogFunctionCall(this, "SetShroomBody", body);
        shroomBody = body;
        Skeleton.LogReturnCall(this, "SetShroomBody");
    }

    /**
     * Gombatest eltávolítása a tektonról.
     */
    public void ClearShroomBody() {
        Skeleton.LogFunctionCall(this, "ClearShroomBody");
        shroomBody = null;
        Skeleton.LogReturnCall(this, "ClearShroomBody");
    }

    /**
     * Spóra dobása a tektonon.
     * @param spore - A dobott spóra
     * @return void
     */
    public void ThrowSpore(Spore spore) {
        Skeleton.LogFunctionCall(this, "ThrowSpore", spore);
        spores.add(spore);
        Skeleton.LogReturnCall(this, "ThrowSpore");
    }

    /**
     * Spóra eltávolítása a tektonról.
     * @param spore - Az eltávolítandó spóra
     * @return void
     */
    public void RemoveSpore(Spore spore) {
        Skeleton.LogFunctionCall(this, "RemoveSpore", spore);
        spores.remove(spore);
        spore.GetShroom().RemoveSpore(spore);
        Skeleton.LogReturnCall(this, "RemoveSpore");
    }

    /**
     * Rovar hozzáadása a tektonhoz.
     * @param bug - A hozzáadandó rovar
     * @return void
     */
    public void AddBug(Bug bug) {
        Skeleton.LogFunctionCall(this, "AddBug", bug);
        bugs.add(bug);
        Skeleton.LogReturnCall(this, "AddBug");
    }

    /**
     * Rovar eltávolítása a tektonról.
     * @param bug - Az eltávolítandó rovar
     * @return void
     */
    public void RemoveBug(Bug bug) {
        Skeleton.LogFunctionCall(this, "RemoveBug", bug);
        bugs.remove(bug);
        Skeleton.LogReturnCall(this, "RemoveBug");
    }

    /**
     * A tekton másolása.
     * A másolás során a tektonon található rovarok és spórák átkerülnek a másolatra.
     * @return Tecton - A másolat
     */
    public Tecton Copy() {
        Skeleton.LogFunctionCall(this, "Copy");

        Tecton copyTecton = new Tecton();
        Skeleton.AddObject(copyTecton, "copyTecton");
        SpreadEntities(copyTecton);

        Skeleton.LogReturnCall(this, "Copy");
        return copyTecton;
    }

    /**
     * Van-e ehhez a tektonhoz szomszédos tekton.
     * @param tecton - A másik tekton
     * @return Boolean - Szomszédos-e a két tekton
     */
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

    /**
     * Van-e ezen a tektonon ehhez a gombafajhoz tartozó spóra.
     * @param shroom
     * @return Boolean - Van-e a tektonon ehhez a gombafajhoz tartozó spóra
     */
    public boolean HasSporeOfShroom(Shroom shroom)
    {
        Skeleton.LogFunctionCall(this, "HasSporeOfShroom", shroom);
        boolean ret = Skeleton.YesNoQuestion("Van-e ezen a tektonon ehhez a gombafajhoz tartozó spóra?");
        Skeleton.LogReturnCall(this, "HasSporeOfShroom", ret);
        return ret;
    }

    /**
     * Szomszédos-e a két tekton.
     * @param tecton - A másik tekton
     * @return Boolean - Szomszédos-e a két tekton
     */
    public boolean IsNeighbourOf(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "IsNeighbourOf", tecton);
        boolean isNeighbourOf = Skeleton.YesNoQuestion("Szomszédos a két tekton?");
        Skeleton.LogReturnCall(this, "IsNeighbourOf", isNeighbourOf);
        return isNeighbourOf;
    }

    /**
     * A tekton frissítése.
     * @apiNote A metódus implementációja hiányzik.
     * @throws UnsupportedOperationException
     * @return void
     */
    public void UpdateTecton() {
        throw new UnsupportedOperationException();
    }

    /**
     * Szomszédos tekton beállítása.
     * @param neighbour - A szomszédos tekton
     * @return void
     */
    public void SetNeighbour(Tecton neighbour) {
        Skeleton.LogFunctionCall(this, "SetNeighbour", neighbour);
        neighbours.add(neighbour);
        Skeleton.LogReturnCall(this, "SetNeighbour");
    }

    /**
     * Tartalmaz-e a tekton adott gombafonalat.
     * @param thread - A vizsgált gombafonal
     * @return Boolean - Tartalmaz-e a tekton adott gombafonalat
     */
    public boolean ContainsThread(ShroomThread thread) {
        return shroomThreads.contains(thread);
    }

    /**
     * Visszaadja a tektonon található gombatestet.
     * @return List<ShroomBody> - A tektonon található gombatestek listája
     */
    public List<ShroomThread> GetThreads() {
        return shroomThreads;
    }
}
