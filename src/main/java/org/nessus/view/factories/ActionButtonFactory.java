package org.nessus.view.factories;

import org.nessus.controller.Controller;
import org.nessus.controller.IActionController;
import org.nessus.controller.IBugOwnerController;
import org.nessus.controller.IShroomController;
import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.View;
import org.nessus.view.panels.GamePanel;

import javax.swing.*;
import java.awt.event.ActionListener;
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
        return CreateActionButton("Rovar lépés",(View view)->{
            List<Tecton> tectons = view.GetSelection().GetTectons();
            if(!tectons.isEmpty()){
                Bug bug = view.GetSelection().GetBug();
                Tecton destination = tectons.getLast();
                IBugOwnerController bugOwner = controller.GetCurrentBugOwnerController();
                if(bugOwner != null){
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
            List<Tecton> tectons = view.GetSelection().GetTectons();
            if(!tectons.isEmpty()){
                Bug bug = view.GetSelection().GetBug();
                Spore spore = view.GetSelection().GetSpore();
                IBugOwnerController bugOwner = controller.GetCurrentBugOwnerController();
                if(bugOwner != null)
                {
                    bugOwner.Eat(bug, spore);
                    UpdateActionPoints();
                    return true;
                }
            }
            return false;
        });
    }
    public JButton CreateBugCutButton(){
        return new JButton("NOT IMPLEMENTED");
    }
    public JButton CreateThrowSporeButton()
    {
        return CreateActionButton("Spóraköpés",(View view)->{
            List<Tecton> tectons = view.GetSelection().GetTectons();
            if(!tectons.isEmpty())
            {
                ShroomBody body = view.GetSelection().GetShroomBody();
                Tecton destination = tectons.getLast();
                IShroomController shroomOwner = controller.GetCurrentShroomController();
                if(shroomOwner != null)
                {
                    shroomOwner.ThrowSpore(body, destination);
                    UpdateActionPoints();
                    return true;
                }
            }
            return false;
        });
    }
    public JButton CreateShroomThreadDevourButton(){
        return new JButton("NOT IMPLEMENTED");
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
        return new JButton("NOT IMPLEMENTED");
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
