package org.nessus.view.panels;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private JPanel mapPanel;
    private JPanel controlPanel;

    public GamePanel() {
        mapPanel = new MapPanel();         
        controlPanel = new ControlPanel();

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

