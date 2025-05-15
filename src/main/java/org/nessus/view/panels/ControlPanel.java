package org.nessus.view.panels;

import org.nessus.model.bug.BugOwner;
import org.nessus.model.shroom.Shroom;
import org.nessus.view.View;
import org.nessus.view.entityviews.IEntityView;
import org.nessus.view.factories.ActionButtonFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class ControlPanel extends JPanel {
    private View view;
    private List<JButton> bugActions;
    private List<JButton> shroomBodyActions;
    private List<JButton> shroomThreadActions;
    private List<JButton> tectonActions;
    private JButton nextPlayerBtn;
    private JButton endGameBtn;
    private JTextArea infoArea;
    private JLabel playerLabel;
    private JLabel actionPointsLabel;

    Dimension buttonSize = new Dimension(180, 30); // width: 180px, height: 30px

    private void styleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        playerLabel = new JLabel("TODO");
        // Az alábbi kód azért nem jó, mert az ObjectStoreban még nincs benne a bugOwner, mivel a ControlPanel,
        // már az alkalmazás legelején létrejön, nem a settingsPanel után
        // TODO: Megírni, hogy amikor előtérbe kerül a ControlPanel akkor legyen updateelve a PlayerLabel
        addComponentListener(new ComponentAdapter() {
            public void ComponentShown(ComponentEvent e) {
                SetPlayerLabelText(view.GetObjectStore().GetBugOwnerName(view.GetController().GetCurrentBugOwnerController()));
            }
        });

        actionPointsLabel = new JLabel();
        // TODO: Kell egy új metódus az ActionPointCatalog osztályba, és ezen felül a BugOwner és Shroom osztályokba
        // amelyek visszaadják az akciópont értékét
        SetActionPointsLabelText("TODO");

        styleLabel(playerLabel);
        styleLabel(actionPointsLabel);
        add(playerLabel);
        add(actionPointsLabel);
        add(Box.createVerticalStrut(10)); // spacing

        ActionButtonFactory actionButtonFactory = new ActionButtonFactory(view.GetController());
        // --- Shroom Action Buttons ---

        bugActions = new ArrayList<>();
        bugActions.add(actionButtonFactory.CreateBugMoveButton());
        bugActions.add(actionButtonFactory.CreateBugEatButton());

        for (JButton button : bugActions) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(buttonSize);
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            add(button);
            add(Box.createVerticalStrut(5));
        }
        // --- Shroom Action Buttons ---

        shroomBodyActions = new ArrayList<>();
        shroomBodyActions.add(actionButtonFactory.CreateThrowSporeButton());
        shroomBodyActions.add(actionButtonFactory.CreatePlaceShroomBodyButton());
        shroomBodyActions.add(actionButtonFactory.CreateUpgradeShroomBodyButton());
    
        for (JButton button : shroomBodyActions) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(buttonSize);
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            add(button);
            add(Box.createVerticalStrut(5));
        }
    
        // --- Glue to push middle section to vertical center ---
        add(Box.createVerticalGlue());
    
        // --- Centered Object Info Section ---
        JLabel objectInfoLabel = new JLabel("Objektum jellemzői:");
        styleLabel(objectInfoLabel);
        add(objectInfoLabel);
    
        infoArea = new JTextArea("Spóra anyagok: 2\nSzint: 3\nHátralévő köpések: 1\nMilán szereti Ádámot");
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

        endGameBtn = new JButton("Játék vége");
        endGameBtn.addActionListener(e -> EndGame());

        nextPlayerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextPlayerBtn.setMaximumSize(buttonSize);
        nextPlayerBtn.setPreferredSize(buttonSize);
        nextPlayerBtn.setMinimumSize(buttonSize);

        endGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        endGameBtn.setMaximumSize(buttonSize);
        endGameBtn.setPreferredSize(buttonSize);
        endGameBtn.setMinimumSize(buttonSize);

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
    }

    public void UpdateActionPoints()
    {
        if (view.GetController().IsBugOwnerRound())
        {
            BugOwner current = (BugOwner)view.GetController().GetCurrentPlayer();
            SetActionPointsLabelText(String.valueOf(current.GetActionPointCatalog().GetCurrentPoints()));
        }
        else
        {
            Shroom current = (Shroom)view.GetController().GetCurrentPlayer();
            SetActionPointsLabelText(String.valueOf(current.GetActionPointCatalog().GetCurrentPoints()));
        }
    }

    public void UpdateEntityInfo(IEntityView view)
    {
        infoArea.setText(view.GetEntityInfo());
    }
}
