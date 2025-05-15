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
    public JButton CreateBugEatButton() {
        return CreateActionButton("Spóraevés",(View view)->{
            List<Tecton> tectons = view.GetSelection().GetTectons();
            if(!tectons.isEmpty()){
                Bug bug = view.GetSelection().GetBug();
                Spore spore = view.GetSelection().GetSpore();
                IBugOwnerController bugOwner = controller.GetCurrentBugOwnerController();
                if(bugOwner != null)
                {
                    bugOwner.Eat(bug, spore);
                    spore.EatenBy(bug);
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
                if(shroomOwner != null && body.InRange(destination))
                {
                    //Teszt miatt rögtön 2-re állítom a SporeMatot
                    body.SetSporeMaterials(2);
                    Spore spore = body.FormSpore(destination);
                    if (spore != null)
                    {
                        view.GetObjectStore().AddSpore(spore);
                        return true;
                    }
                    return false;
                }
            }
            return false;
        });
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
