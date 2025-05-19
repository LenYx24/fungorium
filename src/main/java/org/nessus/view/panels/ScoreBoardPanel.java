package org.nessus.view.panels;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.nessus.view.ObjectStore;
import org.nessus.view.View;

import java.awt.*;

public class ScoreBoardPanel extends JPanel {
    private Image backgroundImage;

    private final JLabel titleLabel;
    private final JLabel scoreLabel;
    private final JPanel bugPanel;
    private final JPanel shPanel;
    private final View view;

    public ScoreBoardPanel(JPanel mainPanel, View view) {
        this.view = view;
        backgroundImage = new ImageIcon(getClass().getResource("/textures/scoreboardbg.png")).getImage();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        // Dark background with purple tint
        
        // Spacer
        add(Box.createVerticalStrut(80));

        // Glowing title
        titleLabel = new JLabel(" ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 52));
        titleLabel.setForeground(new Color(218, 0, 218)); // Purple glow
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(titleLabel);

        add(Box.createVerticalStrut(20));

        // Subtitle with tech feel
        scoreLabel = new JLabel(" ", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        scoreLabel.setForeground(new Color(230, 180, 230)); // Lighter purple
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(scoreLabel);

        add(Box.createVerticalStrut(60));

        // Create futuristic border
        Border glowBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(218, 0, 218), 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );

        // BugOwners Panel with tech styling
        bugPanel = new JPanel();
        bugPanel.setLayout(new BoxLayout(bugPanel, BoxLayout.Y_AXIS));
        bugPanel.setBackground(new Color(40, 15, 40));
        bugPanel.setPreferredSize(new Dimension(300, 200)); // Set larger preferred size
        bugPanel.setBorder(BorderFactory.createTitledBorder(
            glowBorder, 
            "",
            TitledBorder.CENTER, 
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16), 
            new Color(218, 0, 218)
        ));
        
        // Shrooms Panel with matching style
        shPanel = new JPanel();
        shPanel.setLayout(new BoxLayout(shPanel, BoxLayout.Y_AXIS));
        shPanel.setBackground(new Color(40, 15, 40));
        shPanel.setPreferredSize(new Dimension(300, 200)); // Set larger preferred size
        shPanel.setBorder(BorderFactory.createTitledBorder(
            glowBorder, 
            "",
            TitledBorder.CENTER, 
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16), 
            new Color(218, 0, 218)
        ));
        
        // Side panel with futuristic styling
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        sidePanel.setOpaque(false);
        sidePanel.add(shPanel);
        sidePanel.add(bugPanel);

        add(sidePanel);  
        add(Box.createVerticalStrut(40));

        // Futuristic button
        JButton backButton = new JButton();
        backButton.setFocusPainted(false);
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        
        backButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, "menu");
        });

        add(backButton);
        backButton.setPreferredSize(new Dimension(400, 50));
        backButton.setMaximumSize(new Dimension(400, 50));
        add(Box.createVerticalStrut(40));
    }
    
    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            updateScores();
        }
        super.setVisible(visible);
    }
    
    private void updateScores() {
        // Clear existing content
        bugPanel.removeAll();
        shPanel.removeAll();
        
        ObjectStore store = view.GetObjectStore();
        var controller = view.GetController();
        var bugOwners = controller.GetBugOwners()
            .stream()
            .sorted((x,y) -> y.GetScore() - x.GetScore()) // Reversed to show highest score first
            .toList();
        var shrooms = controller.GetShrooms()
            .stream()
            .sorted((x,y) -> y.GetScore() - x.GetScore()) // Reversed to show highest score first
            .toList();
            
        // Add bug owners
        for (var item : bugOwners) {
            JLabel label = new JLabel(store.GetBugOwnerName(item) + ": " + item.GetScore() + " megevett tápanyag");
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setForeground(new Color(255, 220, 255));
            label.setAlignmentX(CENTER_ALIGNMENT);
            // Set preferred width for the label
            label.setPreferredSize(new Dimension(230, 20));
            // Make sure text is fully visible
            label.setMinimumSize(new Dimension(230, 20));
            bugPanel.add(label);
            bugPanel.add(Box.createVerticalStrut(8)); // Add spacer to bugPanel
        }
        
        // Add shrooms
        for (var item : shrooms) { 
            JLabel label = new JLabel(store.GetShroomName(item) + ": " + item.GetScore() + " növesztett test");
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setForeground(new Color(255, 220, 255));
            label.setAlignmentX(CENTER_ALIGNMENT);
            // Set preferred width for the label
            label.setPreferredSize(new Dimension(230, 20));
            // Make sure text is fully visible
            label.setMinimumSize(new Dimension(230, 20));
            shPanel.add(label);
            shPanel.add(Box.createVerticalStrut(8)); // Add spacer to shPanel
        }
        
        // Ensure panels are visible
        bugPanel.setVisible(true);
        shPanel.setVisible(true);
        
        // Force revalidation and repaint
        bugPanel.revalidate();
        shPanel.revalidate();
        bugPanel.repaint();
        shPanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
