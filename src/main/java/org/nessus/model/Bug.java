package org.nessus.model;

import org.nessus.Skeleton;
import org.nessus.model.effect.BugEffect;

import java.util.ArrayList;
import java.util.List;

public class Bug {
    List<BugEffect> bugEffects = new ArrayList<>();
    ActionPointCatalog actCatalog = new ActionPointCatalog();
    Tecton tecton;

    int collectedNutrients = 0;

    boolean canMove = true;
    boolean canCut = true;

    int moveCost;
    int eatCost;
    int cutThreadCost;

    public Bug() {
        Skeleton.AddObject(this, "bug");
        Skeleton.AddObject(actCatalog, "bugCat");
        ResetPoints();
    }

    public Bug(Tecton tecton) {
        this();
        this.tecton = tecton;
    }

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

    public void AddMoveCost(int value) {
        Skeleton.LogFunctionCall(this, "AddMoveCost", value);
        moveCost += value;
        Skeleton.LogReturnCall(this, "AddMoveCost");
    }

    public void AddNutrients(int nutrients) {
        Skeleton.LogFunctionCall(this, "AddNutrients", nutrients);
        collectedNutrients += nutrients;
        Skeleton.LogReturnCall(this, "AddNutrients");
    }

    public void AddEffect(BugEffect bugEffect) {
        Skeleton.LogFunctionCall(this, "AddEffect", bugEffect);
        bugEffects.add(bugEffect);
        Skeleton.LogReturnCall(this, "AddEffect");
    }

    public void ClearEffect(BugEffect effect) {
        bugEffects.remove(effect);
    }

    public void UpdateBug() {
        Skeleton.LogFunctionCall(this, "UpdateBug");
        LoadDefaultCosts();
        actCatalog.ResetPoints();
        for (BugEffect bugEffect : bugEffects) {
            bugEffect.ApplyOn(this);
        }
        Skeleton.LogReturnCall(this, "UpdateBug");
    }

    public void LoadDefaultCosts() {
        Skeleton.LogFunctionCall(this, "LoadDefaultCosts");
        moveCost = 1;
        eatCost = 4;
        cutThreadCost = 3;
        Skeleton.LogReturnCall(this, "LoadDefaultCosts");
    }

    public void ResetPoints() {
        Skeleton.LogFunctionCall(this, "ResetPoints");
        actCatalog.ResetPoints();
        Skeleton.LogReturnCall(this, "ResetPoints");
    }


    public int getCollectedNutrients() {
        return collectedNutrients;
    }

    public void SetTecton(Tecton tecton) {
        Skeleton.LogFunctionCall(this, "SetTecton", tecton);
        this.tecton = tecton;
        Skeleton.LogReturnCall(this, "SetTecton");
    }

    public void SetCanMove(boolean canMove) {
        Skeleton.LogFunctionCall(this, "SetCanMove", canMove);
        this.canMove = canMove;
        Skeleton.LogReturnCall(this, "SetCanMove");
    }

    public void SetCanCut(boolean canCut) {
        Skeleton.LogFunctionCall(this, "SetCanCut", canCut);
        this.canMove = canCut;
        Skeleton.LogReturnCall(this, "SetCanCut");
    }

    public ActionPointCatalog GetActionPointCatalog() {
        return actCatalog;
    }
}
