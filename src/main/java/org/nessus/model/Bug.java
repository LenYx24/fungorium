package org.nessus.model;

import org.nessus.Skeleton;
import org.nessus.model.effect.BugEffect;

import java.util.ArrayList;
import java.util.List;

public class Bug {
    List<BugEffect> bugEffects = new ArrayList<BugEffect>();
    ActionPointCatalog actCatalog = new ActionPointCatalog();
    Tecton tecton;

    int collectedNutrients = 0;

    boolean canMove = true;
    boolean canCut = true;

    int moveCost;
    int eatCost;
    int cutThreadCost;

    public Bug() {
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
        spore.EatenBy(this);
    }

    public void CutThread(ShroomThread shroomThread) {
        Skeleton.LogFunctionCall(this, "CutThread", shroomThread);
        boolean enough = actCatalog.HasEnoughPoints(cutThreadCost);
        boolean canReachThread = shroomThread.IsTectonReachable(this.tecton);
        boolean canCut = !Skeleton.YesNoQuestion("Van-e a rovaron szájzár effect?");
        if(enough && canReachThread && canCut) {
            shroomThread.Remove();
            actCatalog.DecreasePoints(cutThreadCost);
        }

        Skeleton.LogReturnCall(this, "CutThread");
    }

    public void AddMoveCost(int value) {
        moveCost += value;
    }

    public void AddNutrients(int nutrients) {
        collectedNutrients += nutrients;
    }

    public void AddEffect(BugEffect bugEffect) {
        bugEffects.add(bugEffect);
    }

    public void ClearEffect(BugEffect effect) {
        bugEffects.remove(effect);
    }

    public void UpdateBug() {
        ResetPoints();
        LoadDefaultCosts();
        for (BugEffect bugEffect : bugEffects) {
            bugEffect.Apply(this);
        }
    }

    public void LoadDefaultCosts() {
        moveCost = 1;
        eatCost = 4;
        cutThreadCost = 3;
    }

    public void ResetPoints() {
        actCatalog.ResetPoints();
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
        this.canMove = canMove;
    }

    public void SetCanCut(boolean canCut) {
        this.canMove = canCut;
    }

    public ActionPointCatalog GetActionPointCatalog() {
        return actCatalog;
    }
}
