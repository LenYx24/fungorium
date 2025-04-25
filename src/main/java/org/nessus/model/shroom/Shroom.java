package org.nessus.model.shroom;

import org.nessus.View;
import org.nessus.model.ActionPointCatalog;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.bug.Bug;

import java.util.ArrayList;
import java.util.List;

/**
 * A gombákat reprezentáló osztály
 * A gombák fonalakból, gombatestekből és spórákból állnak
 * A gombák növekedését a felhasználó vezérli
 * A gombák elhelyezhetnek új gombatesteket, fonalakat illetve spórákat
 * @see Spore
 * @see ShroomBody
 * @see ShroomThread
 */
public class Shroom {
    private List<Spore> spores = new ArrayList<>(); // Spórák listája
    private List<ShroomBody> shroomBodies = new ArrayList<>(); // Gombatestek listája
    private List<ShroomThread> threads = new ArrayList<>(); // Fonalak listája
    private ActionPointCatalog actCatalog = new ActionPointCatalog(); // A gombákhoz tartozó pontokat tároló objektum

    private int grownShroomBodies = 0; // A nőtt gombatestek száma

    private int shroomThreadCost; // A fonalak elhelyezésének 
    private int shroomBodyCost; // A gombatestek elhelyezésének költsége
    private int shroomUpgradeCost; // A gombatestek fejlesztésének költsége
    private int sporeCost; // A spórák elhelyezésének költsége
    private int devourCost; // A gombatestek által a bogarak elfogyasztásának költsége

    /**
     * A gombák osztályának konstruktora
     * A gombákhoz tartozó pontokat tároló objektumot hozzáadja a Skeletonhez
     * A pontokat alaphelyzetbe állítja
     */
    public Shroom() {
        View.AddObject(actCatalog, "actCatalog");
        ResetPoints();
    }

    /**
     * Fonalakat helyez el a tektonok között
     * A fonalakat csak akkor lehet elhelyezni, ha a felhasználónak elég pontja van
     * A fonalakat csak akkor lehet elhelyezni, ha a két tekton szomszédos
     * @param tecton1
     * @param tecton2
     * @see Tecton
     * @see ShroomThread
     * @see ActionPointCatalog
     * @return void
     */
    public void PlaceShroomThread(Tecton tecton1, Tecton tecton2) {
        boolean enough = actCatalog.HasEnoughPoints(shroomThreadCost);
        boolean neighbours = tecton1.IsNeighbourOf(tecton2);

        boolean connectedToBody = false;

        if (tecton1.GetShroomBody().GetShroom() == this || tecton2.GetShroomBody().GetShroom() == this) {
            connectedToBody = true;
        }

        List<ShroomThread> funcThreads = new ArrayList<>();
        funcThreads.addAll(tecton1.GetThreads());
        funcThreads.addAll(tecton2.GetThreads());

        if (!connectedToBody) {
            for (ShroomThread thread : funcThreads) {
                if (thread.GetShroom() == this && thread.connectedToShroomBody) {
                    connectedToBody = true;
                    break;
                }
            }
        }

        if (enough && neighbours && connectedToBody) {
            ShroomThread newThread = new ShroomThread(this, tecton1, tecton2);
            View.AddObject(newThread, "newThread");

            boolean t1success = tecton1.GrowShroomThread(newThread);
            boolean t2success = tecton2.GrowShroomThread(newThread);

            if (t1success && t2success) {
                threads.add(newThread);
                actCatalog.DecreasePoints(shroomThreadCost);
            }
            if (!t1success || !t2success) {
                newThread.Remove();
            }
        }
    }

    /**
     * Gombatestet helyez el a tektonon
     * A gombatestet csak akkor lehet elhelyezni, ha a felhasználónak elég pontja van
     * A gombatestet csak akkor lehet elhelyezni, ha a tektonon nincs még gombatest
     * @param tecton
     * @see Tecton
     * @see ShroomBody
     * @return void
     */
    public void PlaceShroomBody(Tecton tecton) {
        if (actCatalog.HasEnoughPoints(shroomBodyCost)) {
            ShroomBody newBody = new ShroomBody(this, tecton);
            View.AddObject(newBody, "newBody");

            boolean success = tecton.GrowShroomBody(newBody);
            if (success) {
                grownShroomBodies++;
                shroomBodies.add(newBody);
                actCatalog.DecreasePoints(shroomBodyCost);
            }
        }
    }

    /**
     * Gombatestet fejleszt
     * A gombatestet csak akkor lehet fejleszteni, ha a felhasználónak elég pontja van
     * A gombatestet csak akkor lehet fejleszteni, ha a tektonon van legalább két spóra
     * @see ShroomBody
     * @see Spore
     * @see Tecton
     * @param body
     * @return void
     */
    public void UpgradeShroomBody(ShroomBody body) {
        boolean enough = actCatalog.HasEnoughPoints(shroomUpgradeCost);
        boolean enoughSpore = body.GetTecton().GetSporeCount() > 2;

        if (enough && enoughSpore) {
            body.Upgrade();
            Tecton t = body.GetTecton();
            actCatalog.DecreasePoints(shroomUpgradeCost);
            
            Spore consumedSpore = body.GetTecton().GetFirstSpore();

            if (consumedSpore != null) {
                consumedSpore.GetShroom().RemoveSpore(consumedSpore);
                t.RemoveSpore(consumedSpore);
                body.GetTecton().RemoveSpore(consumedSpore);
            }
        }
    }

    /**
     * Spórákat helyez el a gombatesten
     * A spórákat csak akkor lehet elhelyezni, ha a felhasználónak elég pontja van
     * @see Spore
     * @see ShroomBody
     * @param body
     * @param tecton
     * @return void
     */
    public void ThrowSpore(ShroomBody body, Tecton tecton) {
        if (actCatalog.HasEnoughPoints(sporeCost)) {
            Spore spore = body.FormSpore(tecton);

            if (spore != null) {
                spores.add(spore);
                actCatalog.DecreasePoints(sporeCost);
            }
        }
    }

    /**
     * Bogarak elfogyasztása
     * @param thread - A fonal, amely elfogyasztja a bogarat
     * @param bug - A bogár, amelyet elfogy
     * @return void
     */
    public void ShroomThreadDevourBug(ShroomThread thread, Bug bug) {
        if (actCatalog.HasEnoughPoints(devourCost)) {
            boolean success = thread.DevourCrippledBug(bug); //TODO: DevourCrippledBug implementálása ShroomThread osztályba

            if (success) {
                actCatalog.DecreasePoints(devourCost);
            }
        }
    }

    /**
     * Spóra eltávolítása
     * @param spore - Az eltávolítandó spóra
     * @return void
     */
    public void RemoveSpore(Spore spore) {
        spores.remove(spore);
    }

    /**
     * Gombatest eltávolítása
     * @param body - Az eltávolítandó gombatest
     * @return void
     */
    public void RemoveShroomBody(ShroomBody body) {
        shroomBodies.remove(body);
    }

    /**
     * Fonal eltávolítása
     * @param thread - Az eltávolítandó fonal
     * @return void
     */
    public void RemoveShroomThread(ShroomThread thread) {
        threads.remove(thread);
    }

    /**
     * A gombák növekedését vezérli
     * @return void
     */
    public void UpdateShroom() {
        LoadDefaultCosts();
        actCatalog.ResetPoints();

        for (ShroomThread thread : threads) {
            thread.SetConnectedToShroomBody(false);
        }

        for (ShroomBody body : shroomBodies) {
            body.ValidateThreadConnections();
            body.ProduceSporeMaterial();
        }

        for (ShroomThread thread : threads) {
            thread.ValidateLife();
        }
    }

    /**
     * A gombák alapértelmezett költségeinek beállítása
     * @return void
     */
    public void LoadDefaultCosts() {
        shroomBodyCost = 3;
        shroomThreadCost = 2;
        shroomUpgradeCost = 3;
        sporeCost = 2;
        devourCost = 3;
    }

    /**
     * A gombák pontjainak alaphelyzetbe állítása
     * @return void
     */
    public void ResetPoints() {
        actCatalog.ResetPoints();
    }

    /**
     * A gombák fonalainak lekérdezése
     * @return int - A fonalak száma
     */
    public int GetGrownShroomBodies() {
        return grownShroomBodies;
    }

    /**
     * A gomba ActionPointCatalogjának lekérése
     * @return ActionPointCatalog
     */
    public ActionPointCatalog GetActionPointCatalog() {
        return actCatalog;
    }

    /**
     * Gombatest beállítása
     * @param body - A beállítandó gombatest
     * @return void
     */
    public void SetShroomBody(ShroomBody body) {
        shroomBodies.add(body);
    }

    /**
     * Spóra beállítása
     * @param spore - A beállítandó spóra
     * @return void
     */
    public void SetSpore(Spore spore) {
        spores.add(spore);
    }

    /**
     * Fonal beállítása
     * @param thread - A beállítandó fonal
     * @return void
     */
    public void SetShroomThread(ShroomThread thread) {
        threads.add(thread);
    }
}
