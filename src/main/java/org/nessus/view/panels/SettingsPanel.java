package org.nessus.view.panels;

import org.nessus.view.View;
import org.nessus.view.bugowner.*;
import org.nessus.view.buttons.BaseButton;
import org.nessus.view.shroom.*;
import org.nessus.model.shroom.Shroom;
import org.nessus.model.bug.BugOwner;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import javax.swing.text.AttributeSet;

import java.awt.*;

public class SettingsPanel extends JPanel {
    private Image backgroundImage;
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
        backgroundImage = new ImageIcon(getClass().getResource("/textures/settingsbg.gif")).getImage();

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(" ",SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Roboto", Font.BOLD, 40));

        // 3x2 grid
        JPanel gridJPanel = new JPanel();
        gridJPanel.setOpaque(false);
        gridJPanel.setLayout(new GridLayout(3, 2));

        JLabel gombaszLabel = new JLabel("",SwingConstants.CENTER);
        gombaszLabel.setFont(new Font("Arial", Font.BOLD, 35));
        gombaszLabel.setForeground(Color.MAGENTA);
        gombaszLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gridJPanel.add(gombaszLabel);

        JLabel rovaraszLabel = new JLabel("",SwingConstants.CENTER);
        rovaraszLabel.setFont(new Font("Arial", Font.BOLD, 35));
        rovaraszLabel.setForeground(Color.MAGENTA);
        rovaraszLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gridJPanel.add(rovaraszLabel);

        JPanel settingsLeft = new JPanel();
        settingsLeft.setOpaque(false);
        settingsLeft.setPreferredSize(new Dimension(300, 125));
        settingsLeft.setLayout(new BoxLayout(settingsLeft, BoxLayout.Y_AXIS));

        JPanel settingsRight = new JPanel();
        settingsRight.setOpaque(false);
        settingsRight.setPreferredSize(new Dimension(300, 125));
        settingsRight.setLayout(new BoxLayout(settingsRight, BoxLayout.Y_AXIS));

        for (int i = 0; i < 3; i++) {
            final int playerNum = i + 1;
            JCheckBox checkBox = new JCheckBox();
            checkBox.setPreferredSize(new Dimension(40, 40));
            checkBox.setOpaque(false);
            gombaszCheckBoxes[i] = checkBox;

            JTextField textField = new JTextField("Gombász" + playerNum);
            textField.setPreferredSize(new Dimension(100, 40));
            textField.setEnabled(false);
            gombaszTextFields[i] = textField;

            JPanel colorBox = new JPanel();
            colorBox.setPreferredSize(new Dimension(40, 40));
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
            playerRow.setOpaque(false);
            playerRow.add(checkBox);
            playerRow.add(textField);
            playerRow.add(colorBox);
            settingsLeft.add(playerRow);
        }

        for (int i = 0; i < 3; i++) {
            final int playerNum = i + 1;
            JCheckBox checkBox = new JCheckBox();
            checkBox.setOpaque(false);
            checkBox.setPreferredSize(new Dimension(40, 40));
            rovaraszCheckBoxes[i] = checkBox;
            
            JTextField textField = new JTextField("Rovarász" + playerNum);
            textField.setPreferredSize(new Dimension(100, 40));
            textField.setEnabled(false);
            rovaraszTextFields[i] = textField;

            JPanel colorBox = new JPanel();
            colorBox.setPreferredSize(new Dimension(40, 40));
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
            playerRow.setOpaque(false);
            playerRow.add(checkBox);
            playerRow.add(textField);
            playerRow.add(colorBox);
            settingsRight.add(playerRow);
        }

        JPanel leftWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        leftWrapper.setOpaque(false);
        leftWrapper.add(settingsLeft);
        gridJPanel.add(leftWrapper);

        JPanel rightWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        rightWrapper.setOpaque(false);
        rightWrapper.add(settingsRight);
        gridJPanel.add(rightWrapper);

        panel.add(label);
        panel.add(gridJPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel tectonPanel = new JPanel();
        tectonPanel.setOpaque(false);
        tectonPanel.setLayout(new GridLayout(1, 2));

        JLabel tectonNumber = new JLabel("");
        tectonNumber.setFont(new Font("Arial", Font.BOLD, 35));
        tectonNumber.setForeground(Color.MAGENTA);
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
        leftPanel.setOpaque(false);
        leftPanel.add(tectonNumber);
        leftPanel.add(intInput);

        JButton actionButton = new BaseButton("");
        actionButton.setContentAreaFilled(false);
        actionButton.setBorderPainted(false);
        JPanel rightPanel = new JPanel();
        actionButton.setPreferredSize(new Dimension(200,50));
        rightPanel.setOpaque(false);

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

            for (int i = 0; i < gombaszTextFields.length; i++) {
                String gombaszName = gombaszTextFields[i].getText();
                if (gombaszName.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Kérlek add meg a gombászok nevét!", 
                        "Hibás név", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            for (int i = 0; i < rovaraszTextFields.length; i++) {
                String rovaraszName = rovaraszTextFields[i].getText();
                if (rovaraszName.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Kérlek add meg a rovarászok nevét!", 
                        "Hibás név", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            for (int i = 0; i < gombaszTextFields.length; i++) {
                for (int j = 0; j < rovaraszTextFields.length; j++) {
                    if (gombaszTextFields[i].getText().equals(rovaraszTextFields[j].getText())) {
                        JOptionPane.showMessageDialog(this,
                            "Nem lehet két vagy több játékosnak ugyan az a neve!", 
                            "Hibás név", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }

            for (int i = 0; i < rovaraszTextFields.length; i++) {
                for (int j = 0; j < gombaszTextFields.length; j++) {
                    if (rovaraszTextFields[i].getText().equals(gombaszTextFields[j].getText())) {
                        JOptionPane.showMessageDialog(this,
                            "Nem lehet két vagy több játékosnak ugyan az a neve!", 
                            "Hibás név", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }

            for (int i = 0; i < gombaszTextFields.length; i++) {
                for (int j = i + 1; j < gombaszTextFields.length; j++) {
                    if (gombaszTextFields[i].getText().equals(gombaszTextFields[j].getText())) {
                        JOptionPane.showMessageDialog(this,
                            "Nem lehet két vagy több gombásznak ugyan az a neve!", 
                            "Hibás név", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }

            for (int i = 0; i < rovaraszTextFields.length; i++) {
                for (int j = i + 1; j < rovaraszTextFields.length; j++) {
                    if (rovaraszTextFields[i].getText().equals(rovaraszTextFields[j].getText())) {
                        JOptionPane.showMessageDialog(this,
                            "Nem lehet két vagy több rovarásznak ugyan az a neve!", 
                            "Hibás név", 
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }

            for (int i = 0; i < gombaszTextFields.length; i++) {
                String gombaszName = gombaszTextFields[i].getText();
                if (gombaszName.length() > 10) {
                    JOptionPane.showMessageDialog(this,
                        "A gombász neve nem lehet hosszabb mint 10 karakter!", 
                        "Hibás név", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            for (int i = 0; i < rovaraszTextFields.length; i++) {
                String rovaraszName = rovaraszTextFields[i].getText();
                if (rovaraszName.length() > 10) {
                    JOptionPane.showMessageDialog(this,
                        "A rovarász neve nem lehet hosszabb mint 10 karakter!", 
                        "Hibás név", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            String inputText = intInput.getText().trim();

            int tectonCount;
            try {
                tectonCount = Integer.parseInt(inputText);
                if (tectonCount <= 1 || tectonCount > 50) {
                    JOptionPane.showMessageDialog(this,
                        "A tektonok számának 2 és 50 között kell lennie!", 
                        "Hibás szám", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "A megadott szám túl nagy! Kérlek adj meg egy 2 és 50 közötti értéket!", 
                    "Túl nagy szám", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            int gombaszok = 0;

            for (int i = 0; i < 3; i++)
            {
                if (gombaszCheckBoxes[i].isSelected())
                    gombaszok++;
            }

            if (gombaszok > tectonCount)
            {
                JOptionPane.showMessageDialog(this,
                        "Legalább annyi tekton legyen, ahány gombász játszik!",
                        "Nincs elég tekton",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            ShroomViewFactory[] shroomFactories = {
                new RedTeamFactory(),
                new GreenTeamFactory(),
                new BlueTeamFactory()
            };

            var controller = view.GetController();
            controller.ClearMap();
            view.GetObjectStore().Clear();

            System.out.println("--- LÉTREHOZOTT GOMBÁSZOK ---");
            for (int i = 0; i < gombaszCheckBoxes.length; i++) {
                if (gombaszCheckBoxes[i].isSelected()) {
                    Shroom shroom = new Shroom();
                    String name = gombaszTextFields[i].getText();

                    var objectStore = view.GetObjectStore();
                    objectStore.AddShroom(shroom, shroomFactories[i], name);
                    controller.AddShroom(shroom);

                    System.out.println("Név: " + name + ", Típus: Shroom, Szín: " + gombaszColors[i]);
                }
            }

            BugViewFactory[] bugFactories = {
                new WhiteTeamFactory(),
                new BlackTeamFactory(),
                new BrownTeamFactory()
            };

            System.out.println("--- LÉTREHOZOTT ROVARÁSZOK ---");
            for (int i = 0; i < rovaraszCheckBoxes.length; i++) {
                if (rovaraszCheckBoxes[i].isSelected()) {
                    BugOwner bugOwner = new BugOwner();
                    String name = rovaraszTextFields[i].getText();
                    
                    var objectStore = view.GetObjectStore();
                    objectStore.AddBugOwner(bugOwner, bugFactories[i], name);
                    controller.AddBugOwner(bugOwner);

                    System.out.println("Név: " + name + ", Típus: BugOwner, Szín: " + rovaraszColors[i]);
                }
            }

            tectonCount = Integer.parseInt(intInput.getText());
            System.out.println("--- TEKTONOK ---");
            System.out.println("Tektonok száma: " + tectonCount);

            controller.GenerateMap(tectonCount);
            view.OpenGame();
        });

        rightPanel.add(actionButton);

        JButton backBtn = new BaseButton("");
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
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

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
