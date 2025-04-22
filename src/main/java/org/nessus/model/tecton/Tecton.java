package org.nessus.model.tecton;

import org.nessus.View;
import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;

import java.util.ArrayList;
import java.util.List;

/**
 * A tektonokat reprezentáló osztály.
 * A tektonok tartalmaznak rovarokat, spórákat, gombatestet és gombafonalakat.
 * A tektonok szomszédosak lehetnek egymással.
 * @see org.nessus.model.bug.Bug
 * @see org.nessus.model.shroom.Spore
 * @see org.nessus.model.shroom.ShroomThread
 * @see org.nessus.model.shroom.ShroomBody
 * @see org.nessus.model.shroom.Shroom
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
            String name = View.GetName(bug);

            // TODO
            // if (transferBug)
            // {
            //     copyTecton.AddBug(bug);
            //     bug.SetTecton(copyTecton);
            //     bugIter.remove();
            // }
        }

        var sporeIter = spores.iterator();

        while(sporeIter.hasNext()) {
            Spore spore = sporeIter.next();
            String name = View.GetName(spore);

            // TODO
            // if (transferSpore)
            // {
            //     copyTecton.ThrowSpore(spore);
            //     spore.SetTecton(copyTecton);
            //     sporeIter.remove();
            // }
        }

        if (this.shroomBody != null) {
            // TODO
            // if (transferBody)
            // {
            //     copyTecton.SetShroomBody(shroomBody);
            //     shroomBody.SetTecton(copyTecton);
            //     this.ClearShroomBody();
            // }
        }
    }

    /**
     * A tekton törése.
     * A tekton törésekor a tektonon található rovarok és spórák átkerülnek a szomszédos tektonokra.
     * @see org.nessus.model.bug.Bug
     * @see org.nessus.model.shroom.Shroom
     */
    public void Split() {
        // A későbbiekben lesz egy tárolóosztály, ami majd számontartja a tektonokat, egyelőre csak elvégzünk egy másolást,
        // a másolatot nem tartjuk meg.
        Tecton copyTecton = Copy();

        //Konkurens Módosítás Kivétel elkerülése érdekében másolat
        List.copyOf(shroomThreads).forEach(ShroomThread::Remove);
    }

    /**
     * Gombafonal növesztése a tektonon.
     * @param thread - A növesztendő gombafonal
     * @return Boolean - Sikeres volt-e a növesztés
     */
    public boolean GrowShroomThread(ShroomThread thread) {
        shroomThreads.add(thread);
        return true;
    }

    /**
     * Gombafonal eltávolítása a tektonról.
     * @param thread - Az eltávolítandó gombafonal
     * @return void
     */
    public void RemoveShroomThread(ShroomThread thread) {
        shroomThreads.remove(thread);
    }

    /**
     * Gombatest növesztése a tektonon.
     * A gombatest növesztéséhez szükséges egy spóra, amit a tektonon található spórák közül kell kiválasztani.
     * @param body - A növesztendő gombatest
     * @return Boolean - Sikeres volt-e a növesztés
     */
    public boolean GrowShroomBody(ShroomBody body) {
        // TODO
        // if (!canGrowShroomBody) {
        //     View.LogReturnCall(this, "GrowShroomBody", false);
        //     return false;
        // }

        // A szekvencia diagramon spore2 van írva, de igazából mindegy,
        // a lényeg, hogy egy spórát elhasznál a növesztés
        var consumedSpore = spores.stream()
                                .filter(spore -> spore.GetShroom() == body.GetShroom())
                                .findFirst();
                                
        consumedSpore.ifPresent(this::RemoveSpore);

        shroomBody = body;
        return true;
    }

    /**
     * Gombatest beállítása a tektonra.
     * @param body - A beállítandó gombatest
     * @return void
     */
    public void SetShroomBody(ShroomBody body) {
        shroomBody = body;
    }

    /**
     * Gombatest eltávolítása a tektonról.
     */
    public void ClearShroomBody() {
        shroomBody = null;
    }

    /**
     * Spóra dobása a tektonon.
     * @param spore - A dobott spóra
     * @return void
     */
    public void ThrowSpore(Spore spore) {
        spores.add(spore);
    }

    /**
     * Spóra eltávolítása a tektonról.
     * @param spore - Az eltávolítandó spóra
     * @return void
     */
    public void RemoveSpore(Spore spore) {
        spores.remove(spore);
        spore.GetShroom().RemoveSpore(spore);
    }

    /**
     * Rovar hozzáadása a tektonhoz.
     * @param bug - A hozzáadandó rovar
     * @return void
     */
    public void AddBug(Bug bug) {
        bugs.add(bug);
    }

    /**
     * Rovar eltávolítása a tektonról.
     * @param bug - Az eltávolítandó rovar
     * @return void
     */
    public void RemoveBug(Bug bug) {
        bugs.remove(bug);
    }

    /**
     * A tekton másolása.
     * A másolás során a tektonon található rovarok és spórák átkerülnek a másolatra.
     * @return Tecton - A másolat
     */
    public Tecton Copy() {
        Tecton copyTecton = new Tecton();
        View.AddObject(copyTecton, "copyTecton");
        SpreadEntities(copyTecton);
        return copyTecton;
    }

    /**
     * Van-e ehhez a tektonhoz szomszédos tekton.
     * @param tecton - A másik tekton
     * @return Boolean - Szomszédos-e a két tekton
     */
    public boolean HasGrownShroomThreadTo(Tecton tecton) {
        boolean hasGrownShroomThread = false;
        for (ShroomThread shroomThread : shroomThreads) {
            if (shroomThread.IsTectonReachable(tecton)) {
                hasGrownShroomThread = true;
            }
        }
        return hasGrownShroomThread;
    }

    /**
     * Van-e ezen a tektonon ehhez a gombafajhoz tartozó spóra.
     * @param shroom
     * @return Boolean - Van-e a tektonon ehhez a gombafajhoz tartozó spóra
     */
    public boolean HasSporeOfShroom(Shroom shroom)
    {
        // TODO
        // return ret;
        return false;
    }

    /**
     * Szomszédos-e a két tekton.
     * @param tecton - A másik tekton
     * @return Boolean - Szomszédos-e a két tekton
     */
    public boolean IsNeighbourOf(Tecton tecton) {
        // boolean isNeighbourOf = View.YesNoQuestion("Szomszédos a két tekton?");
        // TODO
        return false;
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
        neighbours.add(neighbour);
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
