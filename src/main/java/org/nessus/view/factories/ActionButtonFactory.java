package org.nessus.view.factories;

import org.nessus.controller.Controller;
import org.nessus.controller.IActionController;
import org.nessus.controller.IBugOwnerController;
import org.nessus.controller.IShroomController;
import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.View;
import org.nessus.view.entityviews.IEntityView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ActionButtonFactory {
    private Controller controller;

    public ActionButtonFactory(Controller controller) {
        this.controller = controller;
    }

    private void UpdateActionPoints() {
        controller.GetView().GetGamePanel().GetControlPanel().UpdateActionPoints();
    }

    private void UpdateButtonTexts() {
        controller.GetView().GetGamePanel().GetControlPanel().UpdateButtonTexts();
    }

    private void UpdateEntityInfo(IEntityView e)
    {
        controller.GetView().GetGamePanel().GetControlPanel().UpdateEntityInfo(e);
    }

    private JButton CreateActionButton(String name, IActionController action){
        JButton button = new JButton(name);
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                controller.StartAction(action);
            }
        });
        return button;
    }
    public JButton CreateBugMoveButton(){
        return CreateActionButton("Rovar mozgás" ,(View view)->{
            List<Tecton> tectons = view.GetSelection().GetTectons();
            if(!tectons.isEmpty()){
                Bug bug = view.GetSelection().GetBug();
                Tecton destination = tectons.getLast();
                IBugOwnerController bugOwner = controller.GetCurrentBugOwnerController();
                if(bug != null && bugOwner != null && bugOwner == bug.GetOwner() && bug.GetCanMove()){
                    bugOwner.Move(bug, destination);
                    UpdateActionPoints();
                    return true;
                }
            }
            return false;
        });
    }
    public JButton CreateBugEatButton()
    {
        return CreateActionButton("Spóraevés",(View view)->{
            Bug bug = view.GetSelection().GetBug();
            Spore spore = view.GetSelection().GetSpore();
            if(bug != null && spore != null)
            {
                IBugOwnerController bugOwner = controller.GetCurrentBugOwnerController();
                if(bugOwner != null && bugOwner == bug.GetOwner())
                {
                    view.GetSelection().SelectSpore(null);
                    bugOwner.Eat(bug, spore);
                    UpdateActionPoints();
                    UpdateButtonTexts();
                    UpdateEntityInfo(view.GetObjectStore().FindEntityView(bug));
                    return true;
                }
            }
            return false;
        });
    }
    public JButton CreateBugCutButton(){
        return CreateActionButton("Gombafonal elvágása",(View view)->{
            var bug = view.GetSelection().GetBug();
            var shroomThread = view.GetSelection().GetShroomThread();
            if(bug != null && shroomThread != null)
            {
                IBugOwnerController bugOwner = controller.GetCurrentBugOwnerController();
                if(bugOwner != null && bugOwner == bug.GetOwner() && bug.GetCanCut())
                {
                    bugOwner.CutThread(bug, shroomThread);
                    UpdateActionPoints();
                    return true;
                }
            }
            return false;
        });
    }
    public JButton CreateThrowSporeButton()
    {
        return CreateActionButton("Spóraköpés",(View view)->{
            List<Tecton> tectons = view.GetSelection().GetTectons();
            ShroomBody body = view.GetSelection().GetShroomBody();
            if(!tectons.isEmpty())
            {
                Tecton destination = tectons.getLast();
                IShroomController shroomOwner = controller.GetCurrentShroomController();
                if(shroomOwner != null && body != null)
                {
                    shroomOwner.ThrowSpore(body, destination);
                    UpdateActionPoints();
                    UpdateEntityInfo(view.GetObjectStore().FindEntityView(body));
                    return true;
                }
            }
            return false;
        });
    }
    public JButton CreateShroomThreadDevourButton(){
        return CreateActionButton("Rovar elfogyasztása gombafonallal", view -> {
            var shroomThread = view.GetSelection().GetShroomThread();
            var bug = view.GetSelection().GetBug();
            if (shroomThread != null && bug != null) {
                var shroom = controller.GetCurrentShroomController();
                if (shroomThread.GetShroom() == view.GetController().GetCurrentShroomController()) {
                    shroom.ShroomThreadDevourBug(shroomThread, bug);
                    UpdateActionPoints();
                    UpdateButtonTexts();
                    return true;
                }
            }
            return false;
        });
    }
    public JButton CreatePlaceShroomBodyButton()
    {
        return CreateActionButton("Gombatest elhelyezése", (View view)->{
            List<Tecton> tectons = view.GetSelection().GetTectons();
            if(!tectons.isEmpty())
            {
                Tecton destination = tectons.getLast();
                IShroomController shroomOwner = controller.GetCurrentShroomController();
                if(shroomOwner != null)
                {
                    shroomOwner.PlaceShroomBody(destination);
                    UpdateActionPoints();
                    return true;
                }
            }
            return false;
        });
    }
    public JButton CreateUpgradeShroomBodyButton(){
        return CreateActionButton("Gombatest fejlesztése", view -> {
            var shroomBody = view.GetSelection().GetShroomBody();
            if (shroomBody != null) {
                var shroom = controller.GetCurrentShroomController();
                if (shroom != null && shroomBody.GetShroom() == shroom) {
                    shroom.UpgradeShroomBody(shroomBody);
                    UpdateActionPoints();
                    UpdateEntityInfo(view.GetObjectStore().FindEntityView(shroomBody));
                    return true;
                }
            }
            return false;
        });
    }
    public JButton CreatePlaceShroomThreadButton(){
        return CreateActionButton("Gombafonal növesztése", view -> {
            var tectons = view.GetSelection().GetTectons();
            if (tectons.size() == 2) {
                Tecton t1 = tectons.get(0);
                Tecton t2 = tectons.get(1);
                var shroom = controller.GetCurrentShroomController();
                if (shroom != null) {
                    shroom.PlaceShroomThread(t1, t2);
                    UpdateActionPoints();
                    return true;
                }
            }
            return false;
        });
    }
}
