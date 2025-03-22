package org.nessus.model;

import org.nessus.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class Shroom {
    private List<Spore> spores = new ArrayList<>();
    private List<ShroomBody> shroomBodies = new ArrayList<>();
    private List<ShroomThread> threads = new ArrayList<>();
    private ActionPointCatalog actCatalog = new ActionPointCatalog();

    private int grownShroomBodies = 0;

    private int shroomThreadCost;
    private int shroomBodyCost;
    private int shroomUpgradeCost;
    private int sporeCost;

    public Shroom() {
        ResetPoints();
    }

    public void PlaceShroomThread(Tecton tecton1, Tecton tecton2)
    {
        Skeleton.LogFunctionCall(this, "PlaceShroomThread", tecton1, tecton2);

        boolean enough = actCatalog.HasEnoughPoints(shroomThreadCost);
        boolean neighbours = tecton1.IsNeighbourOf(tecton2);
        boolean connectedToBody = Skeleton.YesNoQuestion("A két tektonra vezet-e olyan fonal amelyik csatlakozik gombatesthez (akár több fonal hálózatán keresztül)?");

        if (enough && neighbours && connectedToBody)
        {
            ShroomThread thread = new ShroomThread(this, tecton1, tecton2);
            Skeleton.AddObject(thread, "thread");
            boolean t1Success = tecton1.GrowShroomThread(thread);
            boolean t2Success = tecton2.GrowShroomThread(thread);

            if(t1Success && t2Success)
            {
                threads.add(thread);
                actCatalog.DecreasePoints(shroomThreadCost);
            }
            else
            {
                thread.Remove();
            }
        }
        Skeleton.LogReturnCall(this, "PlaceShroomThread");
    }

    public void PlaceShroomBody(Tecton tecton) {
        if (actCatalog.HasEnoughPoints(shroomBodyCost)) {
            ShroomBody shroomBody = new ShroomBody(this, tecton);
            boolean success = tecton.GrowShroomBody(shroomBody);
            if (success) {
                grownShroomBodies++;
                shroomBodies.add(shroomBody);
            }
        }
    }

    public void UpgradeShroomBody(ShroomBody body) {
        body.Upgrade();
    }

    public void ThrowSpore(ShroomBody body) {

    }

    public void RemoveSpore(Spore spore) {
    }

    public void RemoveShroomBody(ShroomBody body) {
    }

    public void RemoveShroomThread(ShroomThread thread)
    {
        Skeleton.LogFunctionCall(this, "RemoveShroomThread");
        Skeleton.LogReturnCall(this, "RemoveShroomThread");
    }

    public void UpdateShroom() {
    }

    public void LoadDefaultCosts() {
        shroomThreadCost = 3;
        shroomBodyCost = 3;
        shroomUpgradeCost = 3;
        sporeCost = 3;
    }

    public void ResetPoints() {
        actCatalog.ResetPoints();
    }

    public int getGrownShroomBodies() {
        return grownShroomBodies;
    }

    public ActionPointCatalog GetActionPointCatalog() {
        return actCatalog;
    }
}
