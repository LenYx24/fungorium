package org.nessus.view.panels;

import org.nessus.model.bug.BugOwner;
import org.nessus.model.shroom.Shroom;
import org.nessus.view.View;
import org.nessus.view.entityviews.IEntityView;
import org.nessus.view.factories.ActionButtonFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    Dimension buttonSize = new Dimension(250, 30); // width: 180px, height: 30px

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
        setBackground(Color.DARK_GRAY);
        setBorder(new EmptyBorder(5, 5, 5, 5));
    
        // --- Top Labels ---
        playerLabel = new JLabel("");

        actionPointsLabel = new JLabel("");

        StyleLabel(playerLabel);
        StyleLabel(actionPointsLabel);
        add(playerLabel);
        add(actionPointsLabel);
        add(Box.createVerticalStrut(10)); // spacing

        ActionButtonFactory actionButtonFactory = new ActionButtonFactory(view.GetController());
        buttonPanel = new JPanel(new CardLayout());
        // --- Bug Action Buttons ---
        JPanel bugActionPanel = new JPanel();
        bugActionPanel.setLayout(new BoxLayout(bugActionPanel, BoxLayout.Y_AXIS));

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
        // --- Shroom Action Buttons ---
        JPanel shroomActionPanel = new JPanel();
        shroomActionPanel.setLayout(new BoxLayout(shroomActionPanel, BoxLayout.Y_AXIS));

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
        ((CardLayout)buttonPanel.getLayout()).show(buttonPanel, "bugButtons");
        add(buttonPanel);
        // --- Glue to push middle section to vertical center ---
        add(Box.createVerticalGlue());
    
        // --- Centered Object Info Section ---
        JLabel objectInfoLabel = new JLabel("Objektum jellemzői:");
        StyleLabel(objectInfoLabel);
        add(objectInfoLabel);
    
        infoArea = new JTextArea("");
        infoArea.setEditable(false);
        infoArea.setBackground(Color.WHITE);
        infoArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoArea.setMaximumSize(new Dimension(180, 250));
        infoArea.setPreferredSize(new Dimension(180, 250)); // increased height
        add(infoArea);
    
        // --- Glue to push bottom buttons to bottom ---
        add(Box.createVerticalGlue());
    
        // --- Bottom Buttons ---
        nextPlayerBtn = new JButton("Következő játékos");
        nextPlayerBtn.addActionListener(e -> view.GetController().NextPlayer());
        StyleButton(nextPlayerBtn);

        endGameBtn = new JButton("Játék vége");
        endGameBtn.addActionListener(e -> EndGame());
        StyleButton(nextPlayerBtn);

        add(nextPlayerBtn);
        add(Box.createVerticalStrut(5));
        add(endGameBtn);
    }
    
    private void EndGame() {
        view.OpenMenu();
    }

    public void UpdatePlayerInfo(String name)
    {
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

    public void UpdateActionPoints()
    {
        if (view.GetController().IsBugOwnerRound())
        {
            BugOwner bugOwner = (BugOwner)view.GetController().GetCurrentPlayer();
            SetActionPointsLabelText(String.valueOf(bugOwner.GetActionPointCatalog().GetCurrentPoints()));
        }
        else
        {
            Shroom current = (Shroom)view.GetController().GetCurrentPlayer();
            SetActionPointsLabelText(String.valueOf(current.GetActionPointCatalog().GetCurrentPoints()));
        }
    }

    public void UpdateEntityInfo(IEntityView view)
    {
        if (view != null)
            infoArea.setText(view.GetEntityInfo());
    }

    public void ClearInfo()
    {
        infoArea.setText("");
    }

    public void UpdateButtonTexts()
    {
        if (view.GetController().IsBugOwnerRound() && view.GetSelection().GetBug() != null)
        {
            BugOwner current = (BugOwner) view.GetController().GetCurrentBugOwnerController();
            if (current == view.GetSelection().GetBug().GetOwner())
            {
                bugActions.get(0).setText("Rovar mozgás: " + view.GetSelection().GetBug().GetMoveCost());
                bugActions.get(1).setText("Spóraevés: " + view.GetSelection().GetBug().GetEatCost());
                bugActions.get(2).setText("Gombafonal elvágása: " + view.GetSelection().GetBug().GetCutCost());
            }
            else
            {
                bugActions.get(0).setText("---");
                bugActions.get(1).setText("---");
                bugActions.get(2).setText("---");
            }
        }

        if (!view.GetController().IsBugOwnerRound())
        {
            Shroom current = (Shroom)view.GetController().GetCurrentShroomController();
            shroomActions.get(0).setText("Spóraköpés: " + current.GetSporeThrowCost());
            shroomActions.get(1).setText("Gombatest elhelyezése: " + current.GetShroomBodyCost());
            shroomActions.get(2).setText("Gombatest fejlesztése: " + current.GetUpgradeCost());
            shroomActions.get(3).setText("Gombafonal növ.: " + current.GetShroomThreadCost());
            shroomActions.get(4).setText("Rovar felfalása fonallal: " + current.GetDevourCost());
        }
    }
}
