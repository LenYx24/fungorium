package org.nessus.model;

import org.nessus.Skeleton;

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

    /**
     * A gombák osztályának konstruktora
     * A gombákhoz tartozó pontokat tároló objektumot hozzáadja a Skeletonhez
     * A pontokat alaphelyzetbe állítja
     */
    public Shroom() {
        Skeleton.AddObject(actCatalog, "actCatalog");
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
        Skeleton.LogFunctionCall(this, "PlaceShroomThread", tecton1, tecton2);

        boolean enough = actCatalog.HasEnoughPoints(shroomThreadCost);
        boolean neighbours = tecton1.IsNeighbourOf(tecton2);
        boolean connectedToBody = Skeleton.YesNoQuestion("A két tektonra vezet-e olyan fonal amelyik csatlakozik gombatesthez (akár több fonal hálózatán keresztül)?");

        if (enough && neighbours && connectedToBody) {
            ShroomThread thread = new ShroomThread(this, tecton1, tecton2);
            Skeleton.AddObject(thread, "thread");
            boolean t1Success = tecton1.GrowShroomThread(thread);
            boolean t2Success = tecton2.GrowShroomThread(thread);

            if (t1Success && t2Success) {
                threads.add(thread);
                actCatalog.DecreasePoints(shroomThreadCost);
            }
            else {
                thread.Remove();
            }
        }
        Skeleton.LogReturnCall(this, "PlaceShroomThread");
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
        Skeleton.LogFunctionCall(this, "PlaceShroomBody", tecton);

        if (actCatalog.HasEnoughPoints(shroomBodyCost)) {
            ShroomBody newBody = new ShroomBody(this, tecton);
            Skeleton.AddObject(newBody, "newBody");

            boolean success = tecton.GrowShroomBody(newBody);
            if (success) {
                grownShroomBodies++;
                shroomBodies.add(newBody);
                actCatalog.DecreasePoints(shroomBodyCost);
            }
        }

        Skeleton.LogReturnCall(this, "PlaceShroomBody");
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
        Skeleton.LogFunctionCall(this, "UpgradeShroomBody", body);

        boolean enough = actCatalog.HasEnoughPoints(shroomUpgradeCost);
        boolean hasSpore = Skeleton.YesNoQuestion("Van legalább két spóra a tektonon?");
        
        if (enough && hasSpore) {
            body.Upgrade();
            
            Tecton tecton = body.GetTecton();

            var consumedSpore = spores.stream()
                .filter(spore -> spore.GetTecton() == tecton)
                .findFirst();

            consumedSpore.ifPresent(tecton::RemoveSpore);
            
            actCatalog.DecreasePoints(shroomUpgradeCost);
        }

        Skeleton.LogReturnCall(this, "UpgradeShroomBody");
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
        Skeleton.LogFunctionCall(this, "ThrowSpore", body, tecton);
        
        if (actCatalog.HasEnoughPoints(sporeCost)) {
            Spore spore = body.FormSpore(tecton);

            if (spore != null) {
                spores.add(spore);
                actCatalog.DecreasePoints(sporeCost);
            }
        }

        Skeleton.LogReturnCall(this, "ThrowSpore");
    }

    /**
     * Spóra eltávolítása
     * @param spore - Az eltávolítandó spóra
     * @return void
     */
    public void RemoveSpore(Spore spore) {
        Skeleton.LogFunctionCall(this, "RemoveSpore", spore);
        spores.remove(spore);
        Skeleton.LogReturnCall(this, "RemoveSpore");
    }

    /**
     * Gombatest eltávolítása
     * @param body - Az eltávolítandó gombatest
     * @return void
     */
    public void RemoveShroomBody(ShroomBody body) {
        Skeleton.LogFunctionCall(this, "RemoveShroomBody", body);
        shroomBodies.remove(body);
        Skeleton.LogReturnCall(this, "RemoveShroomBody");
    }

    /**
     * Fonal eltávolítása
     * @param thread - Az eltávolítandó fonal
     * @return void
     */
    public void RemoveShroomThread(ShroomThread thread) {
        Skeleton.LogFunctionCall(this, "RemoveShroomThread", thread);
        threads.remove(thread);
        Skeleton.LogReturnCall(this, "RemoveShroomThread");
    }

    /**
     * A gombák növekedését vezérli
     * @apiNote A függvény a kontroller megjelenésével nyer majd értelmet
     * @throws UnsupportedOperationException
     * @return void
     */
    public void UpdateShroom() {
        // Ez a függvény a kontroller megjelenésével nyer majd értelmet
        throw new UnsupportedOperationException();
    }

    /**
     * A gombák alapértelmezett költségeinek beállítása
     * @return void
     */
    public void LoadDefaultCosts() {
        shroomThreadCost = 3;
        shroomBodyCost = 3;
        shroomUpgradeCost = 3;
        sporeCost = 3;
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
        Skeleton.LogFunctionCall(this, "SetShroomBody", body);
        shroomBodies.add(body);
        Skeleton.LogReturnCall(this, "SetShroomBody");
    }

    /**
     * Spóra beállítása
     * @param spore - A beállítandó spóra
     * @return void
     */
    public void SetSpore(Spore spore) {
        Skeleton.LogFunctionCall(this, "SetSpore", spore);
        spores.add(spore);
        Skeleton.LogReturnCall(this, "SetSpore");
    }

    /**
     * Fonal beállítása
     * @param thread - A beállítandó fonal
     * @return void
     */
    public void SetShroomThread(ShroomThread thread) {
        Skeleton.LogFunctionCall(this, "SetShroomThread", thread);
        threads.add(thread);
        Skeleton.LogReturnCall(this, "SetShroomThread");
    }
}
