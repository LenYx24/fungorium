package org.nessus.view.panels;

import org.nessus.view.BaseButton;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    JButton newGameBtn;
    JButton exitBtn;
    // Ezt majd ki lehet mozgatni egy külső button factoryba
    public MainMenuPanel(JPanel mainPanel){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Fungorium",SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Roboto", Font.BOLD, 30));

        JButton newGameButton = new BaseButton("Új játék");
        newGameButton.addActionListener(e -> {
            System.out.println("Uj játék");
            CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
            cardLayout.show(mainPanel,"settings");
        });

        JButton exitButton = new BaseButton("Kilépés");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(newGameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(exitButton);

        this.add(panel);
    }
}
