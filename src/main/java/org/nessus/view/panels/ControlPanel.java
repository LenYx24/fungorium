package org.nessus.view.panels;

import org.nessus.view.View;
import org.nessus.view.entityviews.IEntityView;
import org.nessus.view.factories.ActionButtonFactory;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;


public class ControlPanel extends JPanel {
    private List<JButton> bugActions;
    private List<JButton> shroomBodyActions;
    private List<JButton> shroomThreadActions;
    private List<JButton> tectonActions;
    private JButton nextPlayerBtn;
    private JButton endGameBtn;



    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        nextPlayerBtn = new JButton();
        endGameBtn = new JButton();

        //TODO - ez így nem jó
        View view = (View)View.GetObjectStore();
        ActionButtonFactory actionButtonFactory = new ActionButtonFactory(view.GetController());

        bugActions = new ArrayList<>();
        shroomBodyActions = new ArrayList<>();
        shroomThreadActions = new ArrayList<>();
        tectonActions = new ArrayList<>();

        bugActions.add(actionButtonFactory.CreateBugMoveButton());
        bugActions.add(actionButtonFactory.CreateBugEatButton());
        bugActions.add(actionButtonFactory.CreateBugCutButton());

        shroomBodyActions.add(actionButtonFactory.CreateThrowSporeButton());
        shroomBodyActions.add(actionButtonFactory.CreateUpgradeShroomBodyButton());

        shroomThreadActions.add(actionButtonFactory.CreatePlaceShroomThreadButton());

        tectonActions.add(actionButtonFactory.CreatePlaceShroomBodyButton());
        tectonActions.add(actionButtonFactory.CreatePlaceShroomThreadButton());

        for (JButton button : shroomBodyActions) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(button);
        }
        

        setBackground(Color.DARK_GRAY);
        
    }




    public void UpdatePlayerInfo(String name, int actionPoints){
        //TODO
    }

    public void UpdateEntityInfo(IEntityView view){
        // TODO
    }
}
