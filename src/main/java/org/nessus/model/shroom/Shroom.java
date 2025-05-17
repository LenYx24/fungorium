package org.nessus.model.shroom;

import org.nessus.controller.IShroomController;
import org.nessus.model.ActionPointCatalog;
import org.nessus.model.bug.Bug;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.View;

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
public class Shroom implements IShroomController {
    private List<Spore> spores = new ArrayList<>(); // Spórák listája
    private List<ShroomBody> shroomBodies = new ArrayList<>(); // Gombatestek listája
    private List<ShroomThread> threads = new ArrayList<>(); // Fonalak listája
    private ActionPointCatalog actCatalog; // A gombákhoz tartozó pontokat tároló objektum

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
        actCatalog = new ActionPointCatalog();
        ResetPoints();
        LoadDefaultCosts();
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
    @Override
    public void PlaceShroomThread(Tecton tecton1, Tecton tecton2) {
        boolean enough = actCatalog.HasEnoughPoints(shroomThreadCost);
        boolean neighbours = tecton1.IsNeighbourOf(tecton2);
        
        ShroomBody body1 = tecton1.GetShroomBody();
        ShroomBody body2 = tecton2.GetShroomBody();
        
        Tecton start = null;
        Tecton end = null;

        boolean connectedToBody = false;
        
        if (body1 != null && body1.GetShroom() == this) {
            connectedToBody = true;
            start = tecton1;
            end = tecton2;
        }

        if (body2 != null && body2.GetShroom() == this) {
            connectedToBody = true;
            start = tecton2;
            end = tecton1;
        }

        if (!connectedToBody) {
            for (ShroomThread thread : tecton1.GetShroomThreads()) {
                if (thread.GetShroom() == this && thread.connectedToShroomBody && thread.GetEvolution() == 3) {
                    connectedToBody = true;
                    start = tecton1;
                    end = tecton2;
                    break;
                }
            }

            for (ShroomThread thread : tecton2.GetShroomThreads()) {
                if (thread.GetShroom() == this && thread.connectedToShroomBody && thread.GetEvolution() == 3) {
                    connectedToBody = true;
                    start = tecton2;
                    end = tecton1;
                }
            }
        }

        if (enough && neighbours && connectedToBody) {
            ShroomThread newThread = new ShroomThread(this, start, end);
            newThread.SetConnectedToShroomBody(connectedToBody);

            boolean t1success = tecton1.GrowShroomThread(newThread);
            boolean t2success = tecton2.GrowShroomThread(newThread);

            if (t1success && t2success) {
                View.GetGameObjectStore().AddShroomThread(newThread);
                actCatalog.DecreasePoints(shroomThreadCost);
            } else {
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
    @Override
    public void PlaceShroomBody(Tecton tecton) {
        if (actCatalog.HasEnoughPoints(shroomBodyCost)) {
            ShroomBody newBody = new ShroomBody(this, tecton);

            boolean success = tecton.GrowShroomBody(newBody);
            if (success) {
                View.GetGameObjectStore().AddShroomBody(newBody);
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
    @Override
    public void UpgradeShroomBody(ShroomBody body) {
        Tecton bodyTecton = body.GetTecton();
        Shroom bodyShroom = body.GetShroom();

        List<Spore> usableSpores = bodyTecton.GetSporesOfShroom(bodyShroom);
        
        boolean enough = actCatalog.HasEnoughPoints(shroomUpgradeCost);
        boolean enoughSpore = usableSpores.size() >= 2;
        if (enough && enoughSpore) {
            body.Upgrade();
            
            Spore consumedSpore = usableSpores.get(0);

            bodyShroom.RemoveSpore(consumedSpore);
            bodyTecton.RemoveSpore(consumedSpore);
            View.GetGameObjectStore().RemoveEntity(consumedSpore);
            actCatalog.DecreasePoints(shroomUpgradeCost);
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
    @Override
    public void ThrowSpore(ShroomBody body, Tecton tecton) {
        if (actCatalog.HasEnoughPoints(sporeCost))
        {
            Spore spore = body.FormSpore(tecton);

            System.out.println("nemnull: " + spore != null);

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
            // Ez azért kell mert a shroomBodyCost nullára csökken ha sikeresen elfogyasztja a fonal a rovart
            int originalShroomBodyCost = shroomBodyCost;
            boolean success = thread.DevourCrippledBug(bug);
            if (success){
                actCatalog.DecreasePoints(devourCost);
                actCatalog.IncreasePoints(originalShroomBodyCost);
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

        for (ShroomThread thread : threads)
            thread.SetConnectedToShroomBody(false);

        for (ShroomBody body : shroomBodies) {
            body.ValidateThreadConnections();
            body.ProduceSporeMaterial();
        }

        // A fonalak törlődhetnek bejárás közben, ezért másolaton iterálunk
        for (ShroomThread thread : List.copyOf(threads))
            thread.ValidateLife();
    }

    /**
     * Megnöveli a “gombatest elhelyezése” cselekvés akciópont költségét cost-nyival.
     * @param cost - A költség, amelyet hozzáadunk
     * @return void
     */
    public void AddShroomBodyCost(int cost) {
        shroomBodyCost += cost;
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

    public int GetSporeThrowCost()
    {
        return sporeCost;
    }

    public int GetShroomThreadCost()
    {
        return shroomThreadCost;
    }

    public int GetShroomBodyCost()
    {
        return shroomBodyCost;
    }

    public int GetUpgradeCost()
    {
        return shroomUpgradeCost;
    }

    public int GetDevourCost()
    {
        return devourCost;
    }
}
