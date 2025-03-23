package org.nessus.model;

import org.nessus.Skeleton;
import org.nessus.model.effect.BugEffect;

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
        Skeleton.AddObject(this, "bug");
        Skeleton.AddObject(actCatalog, "bugCat");
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
     * @see Skeleton
     * @return void
     */
    public void Move(Tecton destination) {
        Skeleton.LogFunctionCall(this, "Move", tecton);

        boolean enough = actCatalog.HasEnoughPoints(moveCost);
        boolean neighbours = tecton.IsNeighbourOf(destination);
        boolean hasGrownShroomThreadTo = tecton.HasGrownShroomThreadTo(destination);

        canMove = !Skeleton.YesNoQuestion("Van-e a rovaron bénító effect?");

        if (enough && neighbours && hasGrownShroomThreadTo && canMove) { //
            tecton.RemoveBug(this);
            destination.AddBug(this);
            tecton = destination;
            actCatalog.DecreasePoints(moveCost);
        }
        Skeleton.LogReturnCall(this, "Move");
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
     * @see Skeleton
     * @return void
     */
    public void Eat(Spore spore) {
        Skeleton.LogFunctionCall(this, "Eat", spore);
        spore.GetTecton();
        boolean enough = actCatalog.HasEnoughPoints(eatCost);
        boolean isOnSameTecton = Skeleton.YesNoQuestion("A rovar és a spóra egy tektonon van?");
        if (isOnSameTecton && enough) {
            spore.EatenBy(this);
            actCatalog.DecreasePoints(eatCost);
        }
        Skeleton.LogReturnCall(this, "Eat");
    }

    /**
     * Gombafonal vágás
     * A rovar gombafonalat vág egy tektonon.
     * A gombafonal vágás feltételei:
     * - van elég pontja a gombafonal vágásra
     * - a rovaron nincs bénító hatás
     * Ha teljesülnek a feltételek, akkor a rovar levágja a gombafonalat.
     * @param shroomThread - A gombafonal, amit a rovar levág.
     * @see ShroomThread
     * @see ActionPointCatalog
     * @see BugEffect
     * @see Skeleton
     * @return void
     */
    public void CutThread(ShroomThread thread) {
        Skeleton.LogFunctionCall(this, "CutThread", thread);

        boolean enough = actCatalog.HasEnoughPoints(cutThreadCost);
        boolean reachable = thread.IsTectonReachable(tecton);
        canCut = !Skeleton.YesNoQuestion("Van-e a rovaron szájzár effect?");

        if (canCut && enough && reachable) {
            thread.Remove();
            actCatalog.DecreasePoints(cutThreadCost);
        }

        Skeleton.LogReturnCall(this, "CutThread");
    }

    /**
     * Mozgás költségének növelése
     * @param value - A növelés mértéke
     * @return void
     */
    public void AddMoveCost(int value) {
        Skeleton.LogFunctionCall(this, "AddMoveCost", value);
        moveCost += value;
        Skeleton.LogReturnCall(this, "AddMoveCost");
    }

    /**
     * Tápanyag hozzáadása
     * @param nutrients - A hozzáadandó tápanyagok mennyisége
     * @return void
     */
    public void AddNutrients(int nutrients) {
        Skeleton.LogFunctionCall(this, "AddNutrients", nutrients);
        collectedNutrients += nutrients;
        Skeleton.LogReturnCall(this, "AddNutrients");
    }

    /**
     * Hatás hozzáadása
     * @param bugEffect - A hozzáadandó hatás
     * @return void
     */
    public void AddEffect(BugEffect bugEffect) {
        Skeleton.LogFunctionCall(this, "AddEffect", bugEffect);
        bugEffects.add(bugEffect);
        Skeleton.LogReturnCall(this, "AddEffect");
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
        Skeleton.LogFunctionCall(this, "UpdateBug");
        LoadDefaultCosts();
        actCatalog.ResetPoints();
        for (BugEffect bugEffect : bugEffects) {
            bugEffect.ApplyOn(this);
        }
        Skeleton.LogReturnCall(this, "UpdateBug");
    }

    /**
     * Alapértékek betöltése
     * A mozgás, táplálkozás és gombafonal vágás alapértelmezett költségeinek betöltése.
     * @return void
     */
    public void LoadDefaultCosts() {
        Skeleton.LogFunctionCall(this, "LoadDefaultCosts");
        moveCost = 1;
        eatCost = 4;
        cutThreadCost = 3;
        Skeleton.LogReturnCall(this, "LoadDefaultCosts");
    }

    /**
     * Pontok visszaállítása
     * Az ActionPointCatalog pontjainak visszaállítása.
     * @see ActionPointCatalog#defaultActionPoints
     * @return void
     */
    public void ResetPoints() {
        Skeleton.LogFunctionCall(this, "ResetPoints");
        actCatalog.ResetPoints();
        Skeleton.LogReturnCall(this, "ResetPoints");
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
        Skeleton.LogFunctionCall(this, "SetTecton", tecton);
        this.tecton = tecton;
        Skeleton.LogReturnCall(this, "SetTecton");
    }

    /**
     * Rovar mozgásának engedélyezése vagy letiltása
     * @param canMove - boolean
     */
    public void SetCanMove(boolean canMove) {
        Skeleton.LogFunctionCall(this, "SetCanMove", canMove);
        this.canMove = canMove;
        Skeleton.LogReturnCall(this, "SetCanMove");
    }

    /**
     * Rovar vágóképességének engedélyezése vagy letiltása
     * @param canCut - boolean
     * @return void
     */
    public void SetCanCut(boolean canCut) {
        Skeleton.LogFunctionCall(this, "SetCanCut", canCut);
        this.canMove = canCut;
        Skeleton.LogReturnCall(this, "SetCanCut");
    }

    /**
     * A rovar ActionPointCatalogjának lekérése
     * @return {@link ActionPointCatalog#ActionPointCatalog()}
     */
    public ActionPointCatalog GetActionPointCatalog() {
        return actCatalog;
    }
}
