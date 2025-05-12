package org.nessus.view.panels;

import javax.swing.*;
import org.nessus.view.View;
import java.awt.*;

public class GamePanel extends JPanel {
    private View view;
    private MapPanel mapPanel;
    private ControlPanel controlPanel;

    public GamePanel(View view) {
        this.view = view;
        mapPanel = new MapPanel(view, 1280, 720);         
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

    public ControlPanel GetControlPanel() {
        return controlPanel;
    }
}

