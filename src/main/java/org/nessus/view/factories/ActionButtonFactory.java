package org.nessus.view.factories;

import org.nessus.controller.Controller;

import javax.swing.*;

public class ActionButtonFactory {
    private Controller controller;

    public ActionButtonFactory(Controller controller) {
        this.controller = controller;
    }

    public JButton CreateBugMoveButton(){
        return new JButton("NOT IMPLEMENTED");
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
