package com.m2e4.gui;

import com.m2e4.gui.tsp.ViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TspFrame extends JFrame implements ActionListener {
    TspFrame() {
        setLayout(new FlowLayout());
        setTitle("TSP panel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(500, 600);

        ViewPanel viewPanel = new ViewPanel();
        add(viewPanel);

        //CSettingsPanel settings = new CSettingsPanel(getSize());
        //add(settings);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
