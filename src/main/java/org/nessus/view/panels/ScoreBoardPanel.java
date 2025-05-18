package org.nessus.view.panels;

import javax.swing.*;

import org.nessus.view.ObjectStore;
import org.nessus.view.View;

import java.awt.*;

public class ScoreBoardPanel extends JPanel {
    private final JLabel titleLabel;
    private final JLabel scoreLabel;

    public ScoreBoardPanel(JPanel mainPanel, View view) {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        setBackground(Color.red);
        
        // Spacer
        add(Box.createVerticalStrut(100));

        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        add(Box.createVerticalStrut(20));

        scoreLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(scoreLabel);

        add(Box.createVerticalStrut(60));

        ObjectStore store = view.GetObjectStore();
        var controller = view.GetController();
        var bugOwners = controller.GetBugOwners()
            .stream()
            .sorted((x,y)->x.GetScore()-y.GetScore())
            .toList();
        var shrooms = controller.GetShrooms()
            .stream()
            .sorted((x,y)->x.GetScore()-y.GetScore())
            .toList();

        // BugOwners Panel
        JPanel bugPanel = new JPanel();
        bugPanel.setLayout(new BoxLayout(bugPanel, BoxLayout.Y_AXIS));
        bugPanel.setBackground(new Color(60, 63, 65));

        for (var item : bugOwners) {
            JLabel label = new JLabel(store.GetBugOwnerName(item));
            bugPanel.add(label);
            add(Box.createVerticalStrut(5));
        }
        bugPanel.setVisible(true);

        // Shrooms Panel
        JPanel shPanel = new JPanel();
        shPanel.setLayout(new BoxLayout(shPanel, BoxLayout.Y_AXIS));
        shPanel.setBackground(new Color(60, 63, 65));

        for (var item : shrooms) { 
            JLabel label = new JLabel(store.GetShroomName(item)); 
            shPanel.add(label);
            add(Box.createVerticalStrut(5));
        }
        shPanel.setVisible(true);

        
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        sidePanel.add(bugPanel);
        sidePanel.add(shPanel);

        add(sidePanel);  

        JButton backButton = new JButton("Vissza a főmenübe");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "menu");
        });

        add(backButton);
    }

}
