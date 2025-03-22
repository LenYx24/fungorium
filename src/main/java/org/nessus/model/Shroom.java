package org.nessus.model;

import org.nessus.Skeleton;

import java.util.ArrayList;
import java.util.List;

import org.nessus.Skeleton;

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
        Skeleton.AddObject(actCatalog, "actCatalog");
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

    public void UpgradeShroomBody(ShroomBody body) {
        Skeleton.LogFunctionCall(this, "UpgradeShroomBody", body);

        boolean enough = actCatalog.HasEnoughPoints(shroomUpgradeCost);
        boolean hasSpore = Skeleton.YesNoQuestion("Van legalább két spóra a tektonon?");
        
        if (enough && hasSpore) {
            body.Upgrade();
            Tecton t = body.GetTecton();
            // MISSING RemoveSpore(spore3)
            
            actCatalog.DecreasePoints(shroomUpgradeCost);
        }

        Skeleton.LogReturnCall(this, "UpgradeShroomBody");
    }

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

    public void RemoveSpore(Spore spore) {
        Skeleton.LogFunctionCall(this, "RemvoeSpore", spore);
        spores.remove(spore);
        Skeleton.LogReturnCall(this, "RemoveSpore");
    }

    public void RemoveShroomBody(ShroomBody body) {
        Skeleton.LogFunctionCall(this, "RemoveShroomBody", body);
        shroomBodies.remove(body);
        Skeleton.LogReturnCall(this, "RemoveShroomBody");
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

    public int GetGrownShroomBodies() {
        return grownShroomBodies;
    }

    public ActionPointCatalog GetActionPointCatalog() {
        return actCatalog;
    }
  
    public void SetShroomBody(ShroomBody body) {
        Skeleton.LogFunctionCall(this, "SetShroomBody", body);
        shroomBodies.add(body);
        Skeleton.LogReturnCall(this, "SetShroomBody");
    }
}
