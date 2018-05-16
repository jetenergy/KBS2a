package com.m2e4.gui.tsp;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel{
    private JButton Start, Stop, Pauze, Statistieken;
    private JRadioButton algo1, algo2, algo3, algo4;
    private ButtonGroup algoGroup;

    public SettingsPanel(Dimension dim) {
        setLayout(new FlowLayout());
        JButton button = new JButton("button");
        add(button);
        setPreferredSize(dim);
        setSize(getPreferredSize());
        setBorder(BorderFactory.createRaisedBevelBorder());

        algo1= new JRadioButton("Algoritme 1", true);
        algo1.addActionListener(e -> System.out.println("algo1"));
        add(algo1);
        algo2= new JRadioButton("Algoritme 2");
        algo2.addActionListener(e -> System.out.println("algo2"));
        add(algo2);
        algo3= new JRadioButton("Algoritme 3");
        algo3.addActionListener(e -> System.out.println("algo3"));
        add(algo3);
        algo4= new JRadioButton("Algoritme 4");
        algo4.addActionListener(e -> System.out.println("algo4"));
        add(algo4);

        //Group the radio buttons.
        algoGroup = new ButtonGroup();
        algoGroup.add(algo1);
        algoGroup.add(algo2);
        algoGroup.add(algo3);
        algoGroup.add(algo4);
        //algo1.
    }
}
