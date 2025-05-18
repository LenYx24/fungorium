package org.nessus.view.panels;

import javax.swing.*;
import org.nessus.view.View;
import java.awt.*;

/**
 * A játék fő panele, amely tartalmazza a játékteret és a vezérlőpanelt.
 * Elrendezi a játék felületének komponenseit.
 */
public class GamePanel extends JPanel {
    /**
     * A játéktér panel.
     */
    private MapPanel mapPanel;
    
    /**
     * A vezérlőpanel.
     */
    private ControlPanel controlPanel;

    /**
     * Létrehoz egy új játék panelt.
     * @param view A nézet, amelyhez a panel tartozik
     */
    public GamePanel(View view) {
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

    /**
     * Visszaadja a vezérlőpanelt.
     * @return ControlPanel - A vezérlőpanel
     */
    public ControlPanel GetControlPanel() {
        return controlPanel;
    }
}

