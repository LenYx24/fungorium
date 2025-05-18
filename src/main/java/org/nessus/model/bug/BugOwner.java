package org.nessus.model.bug;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

import org.nessus.controller.IBugOwnerController;
import org.nessus.model.ActionPointCatalog;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;

/**
 * A rovarok tulajdonosát reprezentáló osztály.
 * A rovarok tulajdonosa felelős a rovarok mozgásáért, táplálkozásáért és gombafonal vágásáért.
 * A rovarok tulajdonosának van egy ActionPointCatalog-ja, amelyben tárolják a mozgáshoz, táplálkozáshoz és gombafonál vágáshoz szükséges pontjaikat.
 * A rovarok tulajdonosának van egy lista a rovarokról, amelyeket irányít.
 */
public class BugOwner implements IBugOwnerController {
    private List<Bug> bugs = new ArrayList<>(); // rovarok listája
    private ActionPointCatalog actCatalog; // ActionPointCatalog, amely a rovarok mozgásához, táplálkozásához és gombafonal vágásához szükséges pontokat tárolja

    /**
     * Ez a metódus végrehajtja a rovarok mozgását, táplálkozását és gombafonal vágását.
     * @param actionCost - a rovar mozgásának, táplálkozásának és gombafonal vágásának költsége
     * @param actionResult - a rovar mozgásának, táplálkozásának és gombafonal vágásának eredménye
     * @see Bug#GetMoveCost()
     * @see Bug#GetEatCost()
     * @see Bug#GetCutCost()
     * @see Bug#Move(Tecton)
     * @see Bug#Eat(Spore)
     * @see Bug#CutThread(ShroomThread)
     * @see ActionPointCatalog#HasEnoughPoints(int)
     * @see ActionPointCatalog#DecreasePoints(int)
     * @see ActionPointCatalog#ResetPoints()
     * @return void
     */
    private void PerformAction(IntSupplier actionCost, BooleanSupplier actionResult) {
        int cost = actionCost.getAsInt();

        boolean enough = actCatalog.HasEnoughPoints(cost);
        if (!enough)
            return;

        boolean result = actionResult.getAsBoolean();
        if (result)
            actCatalog.DecreasePoints(cost);
    }

    /**
     * Konstruktor
     * A rovar tulajdonosának ActionPointCatalog-ját inicializálja.
     * @see ActionPointCatalog#ActionPointCatalog()s
     */
    public BugOwner() {
        actCatalog = new ActionPointCatalog();
    }

    /**
     * A rovarok mozgásának végrehajtása
     * @param bug - a rovar, amelynek mozgását végre kell hajtani
     * @param tecton - a tekton, amelyen a rovar mozog
     * @see Bug#GetMoveCost()
     * @see Bug#Move(Tecton)
     * @return void
     */
    public void Move(Bug bug, Tecton tecton) {
        PerformAction(bug::GetMoveCost, () -> bug.Move(tecton));
    }

    /**
     * A rovarok táplálkozásának végrehajtása
     * @param bug - a rovar, amelynek táplálkozását végre kell hajtani
     * @param spore - a spóra, amelyet a rovar megeszik
     * @see Bug#GetEatCost()
     * @see Bug#Eat(Spore)
     * @return void
     */
    public void Eat(Bug bug, Spore spore) {
        PerformAction(bug::GetEatCost, () -> bug.Eat(spore));
    }

    /**
     * A rovarok gombafonal vágásának végrehajtása
     * @param bug - a rovar, amelynek gombafonalát vágni kell
     * @param thread - a gombafonal, amelyet a rovar elvág
     * @see Bug#GetCutCost()
     * @see Bug#CutThread(ShroomThread)
     * @return void
     */
    public void CutThread(Bug bug, ShroomThread thread) {
        PerformAction(bug::GetCutCost, () -> bug.CutThread(thread));
    }

    /**
     * A rovarok állapotának frissítése, ResetPoints() hívásával
     * A rovarok állapotának frissítése a rovarok listáján iterálva.
     * @see Bug#UpdateBug()
     * @return void
     */
    public void UpdateBugOwner() {
        ResetPoints();
        // Ha osztódás effekt van a rovaron, akkor előfordulhat, hogy a rovar állapotának frissítése után új
        // rovar keletkezik, ezért a rovarlista egy másolatán iterálunk, hogy ne legyen konkurens módosítás
        List.copyOf(bugs).forEach(Bug::UpdateBug);
    }

    /**
     * Rovar hozzáadása a rovarok listájához
     * @param bug - a rovar, amelyet hozzá kell adni a rovarok listájához
     * @see Bug
     * @return void
     */
    public void AddBug(Bug bug) {
        bugs.add(bug);
    }

    /**
     * Rovar eltávolítása a rovarok listájából
     * @param bug - a rovar, amelyet el kell távolítani a rovarok listájából
     * @see Bug
     * @return void
     */
    public void RemoveBug(Bug bug) {
        bugs.remove(bug);
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
     * A rovar ActionPointCatalogjának lekérése
     * @return {@link ActionPointCatalog#ActionPointCatalog()}
     */
    public ActionPointCatalog GetActionPointCatalog() {
        return actCatalog;
    }

    /**
     * A rovarok listájának lekérése
     * @return List<Bug> - a rovarok listája
     */
    public List<Bug> GetBugs(){
        return bugs;
    }

    public int GetScore() {
        return bugs.stream().mapToInt(Bug::GetCollectedNutrients).sum();
    }
}
