package org.nessus.view.panels;

import javax.swing.*;

import org.nessus.view.buttons.BaseButton;

import java.awt.*;

public class MainMenuPanel extends JPanel {
    JButton newGameBtn;
    JButton exitBtn;
    private Image backgroundImage;

    public MainMenuPanel(JPanel mainPanel) {
        backgroundImage = new ImageIcon(getClass().getResource("/textures/menu.gif")).getImage();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton newGameButton = new BaseButton("");
        newGameButton.setPreferredSize(new Dimension(600, 20));
        newGameButton.setContentAreaFilled(false);
        newGameButton.setBorderPainted(false);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 35));
        newGameButton.setForeground(Color.MAGENTA);
        newGameButton.addActionListener(e -> {
            System.out.println("Uj játék");
            CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
            cardLayout.show(mainPanel,"settings");
        });

        JButton exitButton = new BaseButton("");
        exitButton.setPreferredSize(new Dimension(600, 20));
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFont(new Font("Arial", Font.BOLD, 35));
        exitButton.setForeground(Color.MAGENTA);
        exitButton.addActionListener(e -> System.exit(0));

        this.add(Box.createVerticalStrut(410));
        this.add(newGameButton);
        this.add(Box.createVerticalStrut(50));
        this.add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
