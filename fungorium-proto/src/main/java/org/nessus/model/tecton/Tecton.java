package org.nessus.model.tecton;

import org.nessus.controller.*;
import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.*;
import org.nessus.view.View;

import java.util.ArrayList;
import java.util.Iterator;
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
public class Tecton implements ITectonController {
    protected List<Tecton> neighbours = new ArrayList<>(); // A szomszédos tektonok listája
    protected ShroomBody body = null; // A tektonon található gombatest
    protected List<ShroomThread> threads = new ArrayList<>(); // A tektonon található gombafonalak listája
    protected List<Bug> bugs = new ArrayList<>(); // A tektonon található rovarok listája
    protected List<Spore> spores = new ArrayList<>(); // A tektonon található spórák listája

    /**
     * Törés közben a tekton szomszédos tektonjaira átkerülnek a rovarok és spórák.
     * @see Tecton#Split()
     * @param copyTecton
     */
    protected void SpreadEntities(Tecton copyTecton) {
        copyTecton.SetNeighbour(this);
        for (Tecton neighbour : neighbours) {
            copyTecton.neighbours.add(neighbour);
            neighbour.neighbours.add(copyTecton);
        }

        neighbours.add(copyTecton);

        IRandomProvider randProvider = View.GetObjectStore().GetRandomProvider();

        Iterator<Bug> bugIterator = bugs.iterator();
        while (bugIterator.hasNext()) {
            Bug bug = bugIterator.next();
            boolean transferBug = randProvider.RandomBoolean();
            System.out.println("átrakás: "+ transferBug);
            if (transferBug) {
                copyTecton.AddBug(bug);
                bug.SetTecton(copyTecton);
                bugIterator.remove();
            }
        }

        Iterator<Spore> sporeIterator = spores.iterator();
        while (sporeIterator.hasNext()) {
            Spore spore = sporeIterator.next();
            boolean transferSpore = randProvider.RandomBoolean();
            if (transferSpore) {
                copyTecton.ThrowSpore(spore);
                spore.SetTecton(copyTecton);
                sporeIterator.remove();
            }
        }

        if (this.body != null) {
            boolean transferShroomBody = randProvider.RandomBoolean();
            if (transferShroomBody) {
                copyTecton.SetShroomBody(body);
                body.SetTecton(copyTecton);
                this.ClearShroomBody();
            }
        }

    }

    /**
     * A tekton törése.
     * A tekton törésekor a tektonon található rovarok és spórák átkerülnek a szomszédos tektonokra.
     * @see org.nessus.model.bug.Bug
     * @see org.nessus.model.shroom.Shroom
     */
    public void Split() {
        Tecton copyTecton = Copy();
        SpreadEntities(copyTecton);
        View.GetObjectStore().AddObjectWithNameGen("tecton", copyTecton);

        //Konkurens Módosítás Kivétel elkerülése érdekében másolat
        List.copyOf(threads).forEach(ShroomThread::Remove);
    }

    /**
     * Gombafonal növesztése a tektonon.
     * @param thread - A növesztendő gombafonal
     * @return Boolean - Sikeres volt-e a növesztés
     */
    public boolean GrowShroomThread(ShroomThread thread) {
        threads.add(thread);
        return true;
    }

    /**
     * Gombafonal eltávolítása a tektonról.
     * @param thread - Az eltávolítandó gombafonal
     * @return void
     */
    public void RemoveShroomThread(ShroomThread thread) {
        threads.remove(thread);
    }

    /**
     * Gombatest növesztése a tektonon.
     * A gombatest növesztéséhez szükséges két spóra, amit a tektonon található spórák közül kell kiválasztani.
     * @param body - A növesztendő gombatest
     * @return Boolean - Sikeres volt-e a növesztés
     */
    public boolean GrowShroomBody(ShroomBody body) {
        if (this.body != null)
            return false;

        var usableSpores = spores.stream()
            .filter(x -> x.GetShroom() == body.GetShroom())
            .toList();

        if (usableSpores.size() < 2)
            return false;
        
        var spore = usableSpores.get(0);
        RemoveSpore(spore);
        
        this.body = body;
        body.SetTecton(this);

        return true;
    }

    /**
     * Gombatest beállítása a tektonra.
     * @param body - A beállítandó gombatest
     * @return void
     */
    public void SetShroomBody(ShroomBody body) {
        this.body = body;
    }

    /**
     * Gombatest eltávolítása a tektonról.
     */
    public void ClearShroomBody() {
        body = null;
    }

    /**
     * Visszaadja a tektonon található gombatestet.
     * @see ShroomBody
     * @return ShroomBody - A tektonon található gombatest
     */
    public ShroomBody GetShroomBody() {
        return body;
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
     * Visszaadja a tektonon található rovarok számát.
     * @return int - A tektonon található rovarok száma
     */
    public List<Spore> GetSporesOfShroom(Shroom shroom) {
        return spores.stream().filter(x -> x.GetShroom() == shroom).toList();
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
        return new Tecton();
    }

    /**
     * Van-e ehhez a tektonhoz szomszédos tekton.
     * @param tecton - A másik tekton
     * @return Boolean - Szomszédos-e a két tekton
     */
    public boolean HasGrownShroomThreadTo(Tecton tecton) {
        for (ShroomThread shroomThread : threads) {
            if (shroomThread.IsTectonReachable(tecton)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Visszaadja a tektonon található gombafonalakat.
     * @see ShroomThread
     * @return List<ShroomThread> - A tektonon található gombafonalak listája
     */
    public List<ShroomThread> GetShroomThreads() {
        return threads;
    }

    /**
     * Van-e ezen a tektonon ehhez a gombafajhoz tartozó spóra.
     * @param shroom
     * @return Boolean - Van-e a tektonon ehhez a gombafajhoz tartozó spóra
     */
    public boolean HasSporeOfShroom(Shroom shroom)
    {
        return !GetSporesOfShroom(shroom).isEmpty();
    }

    /**
     * Szomszédos-e a két tekton.
     * @param tecton - A másik tekton
     * @return Boolean - Szomszédos-e a két tekton
     */
    public boolean IsNeighbourOf(Tecton tecton) {
        return neighbours.contains(tecton);
    }

    /**
     * A tekton frissítése.
     * @return void
     */
    @Override
    public void UpdateTecton() {
        /*
         * Az alaposztályon nem hajt végre műveletet
         */
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
     * Szomszédos tektonok listájának visszaadása.
     * @return List<Tecton> - A szomszédos tektonok listája
     */
    public List<Tecton> GetNeighbours() {
        return neighbours;
    }

    /**
     * Tartalmaz-e a tekton adott gombafonalat.
     * @param thread - A vizsgált gombafonal
     * @return Boolean - Tartalmaz-e a tekton adott gombafonalat
     */
    public boolean ContainsThread(ShroomThread thread) {
        return threads.contains(thread);
    }

    /**
     * Visszaadja a tektonon található gombatestet.
     * @return List<ShroomBody> - A tektonon található gombatestek listája
     */
    public List<ShroomThread> GetThreads() {
        return threads;
    }
}
