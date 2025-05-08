package org.nessus.view.panels;

import org.nessus.view.BaseButton;
import org.nessus.view.View;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import javax.swing.text.AttributeSet;

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

        // 3x2 grid
        JPanel griJPanel = new JPanel();
        griJPanel.setLayout(new GridLayout(3, 2));

        JLabel gombaszLabel = new JLabel("Gombászok");
        gombaszLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        gombaszLabel.setHorizontalAlignment(SwingConstants.CENTER);
        griJPanel.add(gombaszLabel);

        JLabel rovaraszLabel = new JLabel("Rovarászok");
        rovaraszLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        rovaraszLabel.setHorizontalAlignment(SwingConstants.CENTER);
        griJPanel.add(rovaraszLabel);

        JPanel settingsLeft = new JPanel();
        JPanel settingsRight = new JPanel();
        griJPanel.add(settingsLeft);
        griJPanel.add(settingsRight);

        JLabel tectonNumber = new JLabel("Tektonok száma:");
        tectonNumber.setFont(new Font("Roboto", Font.BOLD, 20));
        JTextField intInput = new JTextField();

        ((AbstractDocument) intInput.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        intInput.setPreferredSize(new Dimension(100, 30));
        JPanel leftPanel = new JPanel();
        leftPanel.add(tectonNumber);
        leftPanel.add(intInput);
        griJPanel.add(leftPanel);

        JButton actionButton = new BaseButton("Következő");
        JPanel rightPanel = new JPanel();
        rightPanel.add(actionButton);
        griJPanel.add(rightPanel);

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
