package org.nessus.view.buttons;

import javax.swing.*;
import java.awt.*;

public class BaseButton extends JButton {
    public BaseButton(String text) {
        super(text);
        setMinimumSize(new Dimension(100, 40));
        setPreferredSize(new Dimension(200, 40));
        setMaximumSize(new Dimension(200, 80));
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
