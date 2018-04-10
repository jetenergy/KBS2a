package com.m2e4.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TspFrame extends JFrame implements ActionListener {
    public TspFrame() {
        setLayout(new FlowLayout());
        setTitle("TSP panel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(200, 300);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
