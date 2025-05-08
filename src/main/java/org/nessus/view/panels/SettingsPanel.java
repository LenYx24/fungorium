package org.nessus.view.panels;

import org.nessus.model.bug.BugOwner;
import org.nessus.view.BaseButton;
import org.nessus.view.View;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class SettingsPanel extends JPanel {
    private View view;
    Map<JCheckBox, JTextField> shrooms;
    Map<JCheckBox, JTextField> bugOwners;
    JButton nextBtn;

    public SettingsPanel(JPanel mainPanel){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("JÁTÉK BEÁLLÍTÁSA",SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Roboto", Font.BOLD, 40));

        // 2x2 grid
        JPanel griJPanel = new JPanel();
        griJPanel.setLayout(new GridLayout(2, 2));

        // 0,0
        JLabel gombaszLabel = new JLabel("Gombászok");
        gombaszLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        gombaszLabel.setHorizontalAlignment(SwingConstants.CENTER);
        griJPanel.add(gombaszLabel, 0, 0);

        // 1,0
        JLabel rovaraszLabel = new JLabel("Rovarászok");
        rovaraszLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        rovaraszLabel.setHorizontalAlignment(SwingConstants.CENTER);
        griJPanel.add(rovaraszLabel, 0, 1);

        JButton newGameButton = new BaseButton("vissza");
        newGameButton.addActionListener(e -> {
            System.out.println("VISSZA!%");
            CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
            cardLayout.show(mainPanel,"menu");
        });

        panel.add(label);
        panel.add(griJPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(newGameButton);

        this.add(panel);
    }
}
