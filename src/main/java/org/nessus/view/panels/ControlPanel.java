package org.nessus.view.panels;

import org.nessus.view.View;
import org.nessus.view.entityviews.IEntityView;
import org.nessus.view.factories.ActionButtonFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

    Dimension buttonSize = new Dimension(180, 30); // width: 180px, height: 30px

    private void styleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    

    public ControlPanel(View view) {
        this.view = view;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        setBorder(new EmptyBorder(5, 5, 5, 5));
    
        // --- Top Labels ---
        JLabel playerLabel = new JLabel("Játékos: gombász1");
        JLabel actionPointsLabel = new JLabel("Akciópontok: 5");
        styleLabel(playerLabel);
        styleLabel(actionPointsLabel);
        add(playerLabel);
        add(actionPointsLabel);
        add(Box.createVerticalStrut(10)); // spacing
    
        // --- Action Buttons ---
        ActionButtonFactory actionButtonFactory = new ActionButtonFactory(view.GetController());
    
        shroomBodyActions = new ArrayList<>();
        shroomBodyActions.add(actionButtonFactory.CreateThrowSporeButton());
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
    
        JTextArea infoArea = new JTextArea("Spóra anyagok: 2\nSzint: 3\nHátralévő köpések: 1\nMilán szereti Ádámot");
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

    public void UpdatePlayerInfo(String name, int actionPoints){
        //TODO
    }

    public void UpdateEntityInfo(IEntityView view){
        // TODO
    }
}
