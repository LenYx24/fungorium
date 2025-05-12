package org.nessus.view.panels;

import org.nessus.view.BaseButton;
import org.nessus.view.View;

import org.nessus.model.shroom.Shroom;
import org.nessus.model.bug.BugOwner;

import org.nessus.controller.Controller;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import javax.swing.text.AttributeSet;

import java.awt.*;
import java.util.ArrayList;

public class SettingsPanel extends JPanel {
    private JButton nextBtn;
    private View view;

    private JCheckBox[] gombaszCheckBoxes = new JCheckBox[3];
    private JTextField[] gombaszTextFields = new JTextField[3];
    private JCheckBox[] rovaraszCheckBoxes = new JCheckBox[3];
    private JTextField[] rovaraszTextFields = new JTextField[3];
    private Color[] gombaszColors = {Color.RED, Color.GREEN, Color.BLUE};
    private Color[] rovaraszColors = {Color.WHITE, Color.GRAY, new Color(139, 69, 19)};

    public SettingsPanel(View view, JPanel mainPanel) {
        this.view = view;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("JÁTÉK BEÁLLÍTÁSA",SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Roboto", Font.BOLD, 40));

        // 3x2 grid
        JPanel gridJPanel = new JPanel();
        gridJPanel.setLayout(new GridLayout(3, 2));

        JLabel gombaszLabel = new JLabel("Gombászok");
        gombaszLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        gombaszLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridJPanel.add(gombaszLabel);

        JLabel rovaraszLabel = new JLabel("Rovarászok");
        rovaraszLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        rovaraszLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridJPanel.add(rovaraszLabel);

        JPanel settingsLeft = new JPanel();
        settingsLeft.setLayout(new BoxLayout(settingsLeft, BoxLayout.Y_AXIS));

        JPanel settingsRight = new JPanel();
        settingsRight.setLayout(new BoxLayout(settingsRight, BoxLayout.Y_AXIS));

        for (int i = 0; i < 3; i++) {
            final int playerNum = i + 1;
            JCheckBox checkBox = new JCheckBox();
            checkBox.setMargin(new Insets(0, 0, 0, 0));
            gombaszCheckBoxes[i] = checkBox;

            JTextField textField = new JTextField("Gombász" + playerNum);
            textField.setPreferredSize(new Dimension(100, 25));
            textField.setEnabled(false);
            gombaszTextFields[i] = textField;

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

        for (int i = 0; i < 3; i++) {
            final int playerNum = i + 1;
            JCheckBox checkBox = new JCheckBox();
            checkBox.setMargin(new Insets(0, 0, 0, 0));
            rovaraszCheckBoxes[i] = checkBox;
            
            JTextField textField = new JTextField("Rovarász" + playerNum);
            textField.setPreferredSize(new Dimension(100, 25));
            textField.setEnabled(false);
            rovaraszTextFields[i] = textField;

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
        gridJPanel.add(leftWrapper);

        JPanel rightWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        rightWrapper.add(settingsRight);
        gridJPanel.add(rightWrapper);

        panel.add(label);
        panel.add(gridJPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel tectonPanel = new JPanel();
        tectonPanel.setLayout(new GridLayout(1, 2));

        JLabel tectonNumber = new JLabel("Tektonok száma:");
        tectonNumber.setFont(new Font("Roboto", Font.BOLD, 20));
        JTextField intInput = new JTextField();

        // Csak int engedése a tekton szám mezőben
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

        JButton actionButton = new BaseButton("Tovább");
        JPanel rightPanel = new JPanel();

        actionButton.addActionListener(e -> {
            System.out.println("JÁTÉK INIT%");

            boolean hasGombasz = false;
            boolean hasRovarasz = false;
            boolean hasTectonNumber = !intInput.getText().isEmpty();

            for (JCheckBox checkBox : gombaszCheckBoxes) {
                if (checkBox.isSelected()) {
                    hasGombasz = true;
                    break;
                }
            }

            for (JCheckBox checkBox : rovaraszCheckBoxes) {
                if (checkBox.isSelected()) {
                    hasRovarasz = true;
                    break;
                }
            }

            if (!hasGombasz || !hasRovarasz || !hasTectonNumber) {
                JOptionPane.showMessageDialog(this,
                    "Kérlek válassz legalább egy gombászt, egy rovarászt, és add meg a tektonok számát!", 
                    "Hiányzó beállítások", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String inputText = intInput.getText().trim();

            int tectonCount;
            try {
                tectonCount = Integer.parseInt(inputText);
                if (tectonCount <= 0) {
                    JOptionPane.showMessageDialog(this,
                        "A tektonok számának 1 és " + Integer.MAX_VALUE + " között kell lennie!", 
                        "Hibás szám", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "A megadott szám túl nagy! Kérlek adj meg egy 1 és " + Integer.MAX_VALUE + " közötti értéket!", 
                    "Túl nagy szám", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            ArrayList<Shroom> gombaszokList = new ArrayList<>();
            ArrayList<BugOwner> rovaraszokList = new ArrayList<>();

            System.out.println("--- LÉTREHOZOTT GOMBÁSZOK ---");
            for (int i = 0; i < gombaszCheckBoxes.length; i++) {
                if (gombaszCheckBoxes[i].isSelected()) {
                    Shroom shroom = new Shroom();
                    String name = gombaszTextFields[i].getText();

                    View.GetObjectStore().AddObject(name, shroom);
                    gombaszokList.add(shroom);

                    System.out.println("Név: " + name + ", Típus: Shroom, Szín: " + gombaszColors[i]);
                }
            }

            System.out.println("--- LÉTREHOZOTT ROVARÁSZOK ---");
            for (int i = 0; i < rovaraszCheckBoxes.length; i++) {
                if (rovaraszCheckBoxes[i].isSelected()) {
                    BugOwner bugOwner = new BugOwner();
                    String name = rovaraszTextFields[i].getText();

                    View.GetObjectStore().AddObject(name, bugOwner);
                    rovaraszokList.add(bugOwner);

                    System.out.println("Név: " + name + ", Típus: BugOwner, Szín: " + rovaraszColors[i]);
                }
            }

            tectonCount = Integer.parseInt(intInput.getText());
            System.out.println("--- TEKTONOK ---");
            System.out.println("Tektonok száma: " + tectonCount);

            Controller.InitGame(gombaszokList, rovaraszokList, tectonCount);

            CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
            cardLayout.show(mainPanel, "game");
        });

        rightPanel.add(actionButton);

        JButton backBtn = new BaseButton("Vissza");
        backBtn.addActionListener(e -> {
            System.out.println("VISSZA%");
            CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
            cardLayout.show(mainPanel,"menu");
        });

        tectonPanel.add(leftPanel);
        tectonPanel.add(rightPanel);

        panel.add(tectonPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(backBtn);

        
        this.add(panel);
        

    }
}
