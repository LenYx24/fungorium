package org.nessus.model.bug;

import org.nessus.model.ActionPointCatalog;
import org.nessus.model.effect.BugEffect;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.View;

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
    Tecton tecton; // tekton, amin a rovar tartózkodik

    int collectedNutrients = 0; // összegyűjtött tápanyagok

    boolean canMove = true; // mozgás
    boolean canCut = true; // gombafonal vágás

    int moveCost; // mozgás költsége
    int eatCost; // táplálkozás költsége
    int cutThreadCost; // gombafonal vágás költsége

    BugOwner owner; // rovar tulajdonosa

    /**
     * Konstruktor
     * Alapértelmezetten beállítja a pontokat és a költségeket.
     */
    public Bug() {
        LoadDefaultCosts();
    }

    /**
     * Konstruktor
     * A rovar tektonját beállítja.
     * @param tecton
     */
    public Bug(Tecton tecton) {
        this();
        this.tecton = tecton;
    }


    /**
     * Konstruktor
     * A rovar tektonját és tulajdonosát beállítja.
     * @param tecton
     * @param owner
     */
    public Bug(Tecton tecton, BugOwner owner) {
        this(tecton);
        this.owner = owner;
    }

    /**
     * Rovar tulajdonosának beállítása
     * @param owner - A rovar tulajdonosa
     * @see BugOwner
     * @return void
     */
    void SetOwner(BugOwner owner) {
        this.owner = owner;
    }

    /**
     * Rovar tulajdonosának lekérdezése
     * @see BugOwner
     * @return BugOwner - A rovar tulajdonosa
     */
    public BugOwner GetOwner() {
        return owner;
    }

    /**
     * Lekérdezi, hogy a rovar tud-e mozogni
     * @return boolean - true, ha a rovar tud mozogni, false, ha nem
     */
    public boolean GetCanMove() {
        return canMove;
    }

    /**
     * Megadja a rovar tektonját
     * @return Tecton - A rovar tektonja
     */
    public Tecton GetTecton() {
        return tecton;
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
     * @return boolean - true, ha a rovar átkerült a másik tektonra, false, ha nem
     */
    public boolean Move(Tecton destination) {
        boolean neighbours = tecton.IsNeighbourOf(destination);
        boolean hasGrownShroomThreadTo = tecton.HasGrownShroomThreadTo(destination);

        if (neighbours && hasGrownShroomThreadTo && canMove) {
            tecton.RemoveBug(this);
            destination.AddBug(this);
            tecton = destination;
            return true;
        }
        return false; // nincs elég pont, vagy nem szomszédos a két tekton
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
     * @return boolean - true, ha a rovar megeszi a spórát, false, ha nem
     */
    public boolean Eat(Spore spore) {
        if (tecton == spore.GetTecton()) {
            spore.EatenBy(this);
            return true;
        }
        return false;
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
     * @return boolean - true, ha a rovar levágta a gombafonalat, false, ha nem
     */
    public boolean CutThread(ShroomThread thread) {
        boolean reachable = thread.IsTectonReachable(tecton);

        if (canCut && reachable) {
            thread.Remove();
            return true;
        }

        return false;
    }

    /**
     * Tekton széttörése esetén rovar lekezelése
     * @see Tecton#Split()
     * @return void
     */
    public void Split() {
        Bug newBug = new Bug();

        for (BugEffect bugEffect : this.bugEffects) {
            newBug.bugEffects.add(bugEffect);
        }

        newBug.tecton = tecton;
        newBug.owner = owner;

        owner.AddBug(newBug);
    }

    /**
     * Mozgás költségének növelése
     * @param value - A növelés mértéke
     * @return void
     */
    public void AddMoveCost(int value) {
        moveCost += value;
    }

    public int GetMoveCost() {
        return moveCost;
    }

    public int GetEatCost() {
        return eatCost;
    }

    public int GetCutCost() {
        return cutThreadCost;
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
        
        canCut = true;
        canMove = true;

        bugEffects.forEach(effect -> effect.ApplyOn(this));
    }

    /**
     * Alapértékek betöltése
     * A mozgás, táplálkozás és gombafonal vágás alapértelmezett költségeinek betöltése.
     * @return void
     */
    public void LoadDefaultCosts() {
        moveCost = 1;
        eatCost = 2;
        cutThreadCost = 2;
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
     * Törli a bogarat a tektonról és a hozzá tartozó BugOwner listájából
     * @see BugOwner#RemoveBug(Bug)
     * @see Tecton#RemoveBug(Bug)
     * @return void
     */
    public void Remove() {
        if (tecton != null)
            tecton.RemoveBug(this);
        if (owner != null)
            owner.RemoveBug(this);
    }
}
