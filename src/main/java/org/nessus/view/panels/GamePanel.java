package org.nessus.view.panels;

import javax.swing.*;

import org.nessus.view.View;

import java.awt.*;

public class GamePanel extends JPanel {
    private View view;
    private JPanel mapPanel;
    private JPanel controlPanel;

    public GamePanel(View view) {
        this.view = view;
        mapPanel = new MapPanel();         
        controlPanel = new ControlPanel(view);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 6;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(mapPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        add(controlPanel, gbc);

    }
}

