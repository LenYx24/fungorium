package org.nessus.view.panels;

import org.nessus.model.bug.BugOwner;
import org.nessus.model.shroom.Shroom;
import org.nessus.view.View;
import org.nessus.view.buttons.ActionButtonFactory;
import org.nessus.view.entities.IEntityView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ControlPanel extends JPanel {
    private View view;
    private List<JButton> bugActions;
    private List<JButton> shroomActions;
    private JButton nextPlayerBtn;
    private JButton endGameBtn;
    private JTextArea infoArea;
    private JLabel playerLabel;
    private JLabel actionPointsLabel;
    private JPanel buttonPanel;

    Dimension buttonSize = new Dimension(250, 30);

    private void StyleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void StyleButton(JButton button){
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(buttonSize);
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
    }

    private void SetPlayerLabelText(String name){
        playerLabel.setText("Játékos: " + name);
    }

    private void SetActionPointsLabelText(String points){
        actionPointsLabel.setText("Akciópontok: " + points);
    }

    public ControlPanel(View view) {
        this.view = view;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(60, 63, 65));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        playerLabel = new JLabel("");

        actionPointsLabel = new JLabel("");

        StyleLabel(playerLabel);
        StyleLabel(actionPointsLabel);
        add(playerLabel);
        add(actionPointsLabel);
        add(Box.createVerticalStrut(10));

        ActionButtonFactory actionButtonFactory = new ActionButtonFactory(view, this);
        buttonPanel = new JPanel(new CardLayout());
        JPanel bugActionPanel = new JPanel();
        bugActionPanel.setLayout(new BoxLayout(bugActionPanel, BoxLayout.Y_AXIS));
        bugActionPanel.setBackground(new Color(60, 63, 65));

        bugActions = new ArrayList<>();
        bugActions.add(actionButtonFactory.CreateBugMoveButton());
        bugActions.add(actionButtonFactory.CreateBugEatButton());
        bugActions.add(actionButtonFactory.CreateBugCutButton());

        for (JButton button : bugActions) {
            StyleButton(button);
            bugActionPanel.add(button);
            bugActionPanel.add(Box.createVerticalStrut(5));
        }
        buttonPanel.add(bugActionPanel,"bugButtons");

        JPanel shroomActionPanel = new JPanel();
        shroomActionPanel.setLayout(new BoxLayout(shroomActionPanel, BoxLayout.Y_AXIS));
        shroomActionPanel.setBackground(new Color(60, 63, 65));

        shroomActions = new ArrayList<>();
        shroomActions.add(actionButtonFactory.CreateThrowSporeButton());
        shroomActions.add(actionButtonFactory.CreatePlaceShroomBodyButton());
        shroomActions.add(actionButtonFactory.CreateUpgradeShroomBodyButton());
        shroomActions.add(actionButtonFactory.CreatePlaceShroomThreadButton());
        shroomActions.add(actionButtonFactory.CreateShroomThreadDevourButton());
    
        for (JButton button : shroomActions) {
            StyleButton(button);
            shroomActionPanel.add(button);
            shroomActionPanel.add(Box.createVerticalStrut(5));
        }

        buttonPanel.add(shroomActionPanel,"shroomButtons");
        buttonPanel.setBackground(new Color(60, 63, 65));
        ((CardLayout)buttonPanel.getLayout()).show(buttonPanel, "bugButtons");

        add(buttonPanel);
        add(Box.createVerticalGlue());

        JLabel objectInfoLabel = new JLabel("Objektum jellemzői:");
        objectInfoLabel.setForeground(Color.WHITE);
        objectInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        objectInfoLabel.setFont(new Font(objectInfoLabel.getFont().getName(), Font.BOLD, objectInfoLabel.getFont().getSize() + 2));

        add(objectInfoLabel);
        add(Box.createVerticalStrut(5));
    
        infoArea = new JTextArea("");
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(60, 63, 65));
        infoArea.setForeground(Color.WHITE);
        infoArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoArea.setMaximumSize(new Dimension(180, 250));
        infoArea.setPreferredSize(new Dimension(180, 250));

        add(infoArea);
        add(Box.createVerticalGlue());

        nextPlayerBtn = new JButton("Következő játékos");
        nextPlayerBtn.addActionListener(e -> {
            view.GetController().NextPlayer();
            view.requestFocus();
        });

        StyleButton(nextPlayerBtn);

        endGameBtn = new JButton("Játék vége");
        endGameBtn.addActionListener(e -> EndGame());
        StyleButton(endGameBtn);

        add(nextPlayerBtn);
        add(Box.createVerticalStrut(5));
        add(endGameBtn);
    }
    
    private void EndGame() {
        view.OpenMenu();
    }

    public void UpdatePlayerInfo(String name) {
        SetPlayerLabelText(name);
        UpdateActionPoints();
        UpdateButtonTexts();
        CardLayout buttonsLayout = (CardLayout)buttonPanel.getLayout();
        if(view.GetController().IsBugOwnerRound()){
            buttonsLayout.show(buttonPanel, "bugButtons");
        }else{
            buttonsLayout.show(buttonPanel, "shroomButtons");
        }
    }

    public void UpdateActionPoints() {
        var controller = view.GetController();
        
        if (controller.IsBugOwnerRound()) {
            BugOwner bugOwner = (BugOwner)controller.GetCurrentBugOwnerController();
            SetActionPointsLabelText(String.valueOf(bugOwner.GetActionPointCatalog().GetCurrentPoints()));
        }
        else {
            Shroom current = (Shroom)controller.GetCurrentShroomController();
            SetActionPointsLabelText(String.valueOf(current.GetActionPointCatalog().GetCurrentPoints()));
        }
    }

    public void UpdateEntityInfo(IEntityView view) {
        if (view != null)
            infoArea.setText(view.GetEntityInfo());
    }

    public void ClearInfo() {
        infoArea.setText("");
    }

    public void UpdateButtonTexts() {
        var controller = view.GetController();

        if (controller.IsBugOwnerRound() && view.GetSelection().GetBug() != null) {
            BugOwner current = (BugOwner) controller.GetCurrentBugOwnerController();
            var bug = view.GetSelection().GetBug();
            
            if (current == bug.GetOwner()) {
                bugActions.get(0).setText("Rovar mozgás: " + bug.GetMoveCost());
                bugActions.get(1).setText("Spóraevés: " + bug.GetEatCost());
                bugActions.get(2).setText("Gombafonal elvágása: " + bug.GetCutCost());
            }
            else {
                bugActions.get(0).setText("---");
                bugActions.get(1).setText("---");
                bugActions.get(2).setText("---");
            }
        }

        if (!view.GetController().IsBugOwnerRound()) {
            Shroom current = (Shroom)view.GetController().GetCurrentShroomController();
            shroomActions.get(0).setText("Spóraköpés: " + current.GetSporeThrowCost());
            shroomActions.get(1).setText("Gombatest elhelyezése: " + current.GetShroomBodyCost());
            shroomActions.get(2).setText("Gombatest fejlesztése: " + current.GetUpgradeCost());
            shroomActions.get(3).setText("Gombafonal növesztése: " + current.GetShroomThreadCost());
            shroomActions.get(4).setText("Rovar felfalása fonallal: " + current.GetDevourCost());
        }
    }
}
