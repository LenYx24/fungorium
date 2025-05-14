package org.nessus.view.factories;

import org.nessus.controller.Controller;
import org.nessus.controller.IActionController;
import org.nessus.controller.IBugOwnerController;
import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomThread;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.View;

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
                    ShroomThread shroomThread = new ShroomThread(new Shroom(), destination, bug.GetTecton());
                    destination.GrowShroomThread(shroomThread);
                    bug.GetTecton().GrowShroomThread(shroomThread);
                    shroomThread.SetEvolution();
                    bugOwner.Move(bug, destination);
                    return true;
                }
            }
            return false;
        });
    }
    public JButton CreateBugEatButton(){
        return new JButton("NOT IMPLEMENTED");
    }
    public JButton CreateBugCutButton(){
        return new JButton("NOT IMPLEMENTED");
    }
    public JButton CreateThrowSporeButton(){
        return new JButton("NOT IMPLEMENTED");
    }
    public JButton CreateShroomThreadDevourButton(){
        return new JButton("NOT IMPLEMENTED");
    }
    public JButton CreatePlaceShroomBodyButton(){
        return new JButton("NOT IMPLEMENTED");
    }
    public JButton CreateUpgradeShroomBodyButton(){
        return new JButton("NOT IMPLEMENTED");
    }
    public JButton CreatePlaceShroomThreadButton(){
        return new JButton("NOT IMPLEMENTED");
    }
}
