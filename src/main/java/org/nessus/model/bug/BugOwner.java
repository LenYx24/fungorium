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
import org.nessus.view.View;

public class BugOwner implements IBugOwnerController {
    private ActionPointCatalog actCatalog;
    private List<Bug> bugs = new ArrayList<>();

    private void PerformAction(IntSupplier actionCost, BooleanSupplier actionResult) {
        int cost = actionCost.getAsInt();

        boolean enough = actCatalog.HasEnoughPoints(cost);
        if (!enough)
            return;

        boolean result = actionResult.getAsBoolean();
        if (result)
            actCatalog.DecreasePoints(cost);

    }

    public BugOwner() {
        actCatalog = new ActionPointCatalog();
        View.GetObjectStore().AddObject("actCatalog", actCatalog);
    }

    public void Move(Bug bug, Tecton tecton) {
        PerformAction(bug::GetMoveCost, () -> bug.Move(tecton));
    }

    public void Eat(Bug bug, Spore spore) {
        PerformAction(bug::GetEatCost, () -> bug.Eat(spore));
    }

    public void CutThread(Bug bug, ShroomThread thread) {
        PerformAction(bug::GetCutCost, () -> bug.CutThread(thread));
    }

    public void UpdateBugOwner() {
        ResetPoints();
        bugs.forEach(Bug::UpdateBug);
    }

    public void AddBug(Bug bug) {
        bugs.add(bug);
    }

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
}
