package org.nessus.model.bug;

import org.nessus.View;
import org.nessus.model.ActionPointCatalog;
import org.nessus.model.effect.BugEffect;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;

import java.util.ArrayList;
import java.util.List;

/**
 * A rovarokat reprezentáló osztály.
 * A rovarok képesek mozogni, táplálkozni, és gombafonalakat vágni.
 * A rovaroknak van egy ActionPointCatalog-ja, amelyben tárolják a mozgáshoz, táplálkozáshoz és gombafonál vágáshoz szükséges pontjaikat.
 * A rovaroknak lehetnek különböző hatások, amelyek befolyásolják a mozgásukat, táplálkozásukat és gombafonal vágásukat.
 * A rovaroknak van egy collectedNutrients változója, amelyben tárolják a táplálkozás során összegyűjtött tápanyagok mennyiségét.
 */
public class Bug {
    List<BugEffect> bugEffects = new ArrayList<>(); // hatások
    ActionPointCatalog actCatalog = new ActionPointCatalog(); // pontok
    Tecton tecton; // tekton, amin a rovar tartózkodik

    int collectedNutrients = 0; // összegyűjtött tápanyagok

    boolean canMove = true; // mozgás
    boolean canCut = true; // gombafonal vágás

    int moveCost; // mozgás költsége
    int eatCost; // táplálkozás költsége
    int cutThreadCost; // gombafonal vágás költsége

    /**
     * Konstruktor
     * Alapértelmezetten beállítja a pontokat és a költségeket.
     */
    public Bug() {
        View.AddObject(this, "bug");
        View.AddObject(actCatalog, "bugCat");
        ResetPoints();
    }

    public Bug(Tecton tecton) {
        this();
        this.tecton = tecton;
    }

    /**
     * Mozgás
     * A rovar mozgatása egy másik tektonra.
     * A mozgás feltételei:
     * - van elég pontja a mozgásra
     * - a két tekton szomszédos
     * - van gombafonal a két tekton között
     * - a rovaron nincs bénító hatás
     * Ha teljesülnek a feltételek, akkor a rovar átkerül a másik tektonra.
     * @param destination - A tekton, amire a rovar mozogni szeretne.
     * @see Tecton
     * @see ActionPointCatalog
     * @see BugEffect
     * @see View
     * @return void
     */
    public void Move(Tecton destination) {
        boolean enough = actCatalog.HasEnoughPoints(moveCost);
        boolean neighbours = tecton.IsNeighbourOf(destination);
        boolean hasGrownShroomThreadTo = tecton.HasGrownShroomThreadTo(destination);

        if (enough && neighbours && hasGrownShroomThreadTo && canMove) {
            tecton.RemoveBug(this);
            destination.AddBug(this);
            tecton = destination;
            actCatalog.DecreasePoints(moveCost);
        }
    }

    /**
     * Evés
     * A rovar táplálkozása egy spóráról.
     * A táplálkozás feltételei:
     * - van elég pontja a táplálkozásra
     * - a rovaron nincs bénító hatás
     * Ha teljesülnek a feltételek, akkor a rovar megeszi a spórát.
     * @param spore - A spóra, amit a rovar megeszik.
     * @see Spore
     * @see ActionPointCatalog
     * @see BugEffect
     * @see View
     * @return void
     */
    public void Eat(Spore spore) {
        spore.GetTecton();
        boolean enough = actCatalog.HasEnoughPoints(eatCost);
        // TODO
        // if (isOnSameTecton && enough) {
        //     spore.EatenBy(this);
        //     actCatalog.DecreasePoints(eatCost);
        // }
    }

    /**
     * Gombafonal vágás
     * A rovar gombafonalat vág egy tektonon.
     * A gombafonal vágás feltételei:
     * - van elég pontja a gombafonal vágásra
     * - a rovaron nincs bénító hatás
     * Ha teljesülnek a feltételek, akkor a rovar levágja a gombafonalat.
     * @param thread - A gombafonal, amit a rovar levág.
     * @see ShroomThread
     * @see ActionPointCatalog
     * @see BugEffect
     * @see View
     * @return void
     */
    public void CutThread(ShroomThread thread) {
        boolean enough = actCatalog.HasEnoughPoints(cutThreadCost);
        boolean reachable = thread.IsTectonReachable(tecton);

        if (canCut && enough && reachable) {
            thread.Remove();
            actCatalog.DecreasePoints(cutThreadCost);
        }
    }

    /**
     * Mozgás költségének növelése
     * @param value - A növelés mértéke
     * @return void
     */
    public void AddMoveCost(int value) {
        moveCost += value;
    }

    /**
     * Tápanyag hozzáadása
     * @param nutrients - A hozzáadandó tápanyagok mennyisége
     * @return void
     */
    public void AddNutrients(int nutrients) {
        collectedNutrients += nutrients;
    }

    /**
     * Hatás hozzáadása
     * @param bugEffect - A hozzáadandó hatás
     * @return void
     */
    public void AddEffect(BugEffect bugEffect) {
        bugEffects.add(bugEffect);
    }

    /**
     * Hatás törlése
     * @param effect - A törlendő hatás
     * @return void
     */
    public void ClearEffect(BugEffect effect) {
        bugEffects.remove(effect);
    }

    /**
     * Rovar frissítése
     * A rovar hatásainak alkalmazása.
     * @see BugEffect
     * @return void
     */
    public void UpdateBug() {
        LoadDefaultCosts();
        actCatalog.ResetPoints();
        bugEffects.forEach(effect -> effect.ApplyOn(this));
    }

    /**
     * Alapértékek betöltése
     * A mozgás, táplálkozás és gombafonal vágás alapértelmezett költségeinek betöltése.
     * @return void
     */
    public void LoadDefaultCosts() {
        moveCost = 1;
        eatCost = 4;
        cutThreadCost = 3;
    }

    /**
     * Pontok visszaállítása
     * Az ActionPointCatalog pontjainak visszaállítása.
     * @see ActionPointCatalog#defaultActionPoints
     * @return void
     */
    public void ResetPoints() {
        actCatalog.ResetPoints();
    }

    /**
     * Az összedetett tápanyagérték lekérdezése
     * @return int - A tápanyagok értéke
     */
    public int getCollectedNutrients() {
        return collectedNutrients;
    }

    /**
     * Rovar tektonjának beállítása
     * @param tecton
     * @return void
     */
    public void SetTecton(Tecton tecton) {
        this.tecton = tecton;
    }

    /**
     * Rovar mozgásának engedélyezése vagy letiltása
     * @param canMove - boolean
     */
    public void SetCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * Rovar vágóképességének engedélyezése vagy letiltása
     * @param canCut - boolean
     * @return void
     */
    public void SetCanCut(boolean canCut) {
        this.canMove = canCut;
    }

    /**
     * A rovar ActionPointCatalogjának lekérése
     * @return {@link ActionPointCatalog#ActionPointCatalog()}
     */
    public ActionPointCatalog GetActionPointCatalog() {
        return actCatalog;
    }
}
