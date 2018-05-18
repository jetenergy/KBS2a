package com.m2e4.gui.tsp;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CPositionPanel extends JPanel{

    private JPanel solutionPanel;
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    public CPositionPanel() {

        setBorder(border);
        JLabel title = new JLabel("Beste oplossing");
        add(title);

        solutionPanel = new JPanel();
        add(solutionPanel);
    }
}
