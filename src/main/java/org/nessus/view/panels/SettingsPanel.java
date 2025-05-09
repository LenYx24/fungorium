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
        settingsLeft.setLayout(new BoxLayout(settingsLeft, BoxLayout.Y_AXIS));

        JPanel settingsRight = new JPanel();
        settingsRight.setLayout(new BoxLayout(settingsRight, BoxLayout.Y_AXIS));

        Color[] gombaszColors = {Color.RED, Color.GREEN, Color.BLUE};
        for (int i = 0; i < 3; i++) {
            final int playerNum = i + 1;
            JCheckBox checkBox = new JCheckBox();
            checkBox.setMargin(new Insets(0, 0, 0, 0));
            
            JTextField textField = new JTextField("Gombász" + playerNum);
            textField.setPreferredSize(new Dimension(100, 25));
            textField.setEnabled(false);

            JPanel colorBox = new JPanel();
            colorBox.setPreferredSize(new Dimension(20, 20));
            colorBox.setBackground(gombaszColors[i]);
            colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            checkBox.addActionListener(e -> {
                boolean isSelected = checkBox.isSelected();
                textField.setEnabled(isSelected);
                if (isSelected) {
                    textField.setText("Gombász" + playerNum);
                }
            });

            JPanel playerRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            playerRow.add(checkBox);
            playerRow.add(textField);
            playerRow.add(colorBox);
            settingsLeft.add(playerRow);
        }

        Color[] rovaraszColors = {Color.WHITE, Color.GRAY, new Color(139, 69, 19)};
        for (int i = 0; i < 3; i++) {
            final int playerNum = i + 1;
            JCheckBox checkBox = new JCheckBox();
            checkBox.setMargin(new Insets(0, 0, 0, 0));
            
            JTextField textField = new JTextField("Rovarász" + playerNum);
            textField.setPreferredSize(new Dimension(100, 25));
            textField.setEnabled(false);

            JPanel colorBox = new JPanel();
            colorBox.setPreferredSize(new Dimension(20, 20));
            colorBox.setBackground(rovaraszColors[i]);
            colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            checkBox.addActionListener(e -> {
                boolean isSelected = checkBox.isSelected();
                textField.setEnabled(isSelected);
                if (isSelected) {
                    textField.setText("Rovarász" + playerNum);
                }
            });

            JPanel playerRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            playerRow.add(checkBox);
            playerRow.add(textField);
            playerRow.add(colorBox);
            settingsRight.add(playerRow);
        }

        JPanel leftWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        leftWrapper.add(settingsLeft);
        griJPanel.add(leftWrapper);

        JPanel rightWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        rightWrapper.add(settingsRight);
        griJPanel.add(rightWrapper);

        panel.add(label);
        panel.add(griJPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel tectonPanel = new JPanel();
        tectonPanel.setLayout(new GridLayout(1, 2));

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

        JButton actionButton = new BaseButton("Következő");
        JPanel rightPanel = new JPanel();
        rightPanel.add(actionButton);

        JButton newGameButton = new BaseButton("Vissza");
        newGameButton.addActionListener(e -> {
            System.out.println("VISSZA!%");
            CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
            cardLayout.show(mainPanel,"menu");
        });

        tectonPanel.add(leftPanel);
        tectonPanel.add(rightPanel);

        panel.add(tectonPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(newGameButton);

        this.add(panel);
    }
}
