package org.nessus.view.panels;

import java.awt.List;
import java.util.ArrayList;

import org.nessus.model.bug.BugOwner;
import org.nessus.model.shroom.Shroom;

import javax.swing.*;

public class GamePanel extends JPanel{
    private JPanel mapPanel;
    private JPanel controlPanel;

    ArrayList<BugOwner> bugOwners = new ArrayList<>();
    ArrayList<Shroom> shrooms = new ArrayList<>();
    int tectonCount;

    public GamePanel() {}

    public GamePanel(JPanel mapPanel, JPanel controlPanel, ArrayList<BugOwner> bugOwners, ArrayList<Shroom> shrooms, int tectonCount){
        this.mapPanel = mapPanel;
        this.controlPanel = controlPanel;
        this.bugOwners = bugOwners;
        this.shrooms = shrooms;
        this.tectonCount = tectonCount;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(mapPanel);
        add(controlPanel);
    }
}
