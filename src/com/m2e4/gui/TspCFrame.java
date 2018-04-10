package com.m2e4.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TspCFrame extends JFrame implements ActionListener {
    public TspCFrame() {
        setLayout(new FlowLayout());
        setTitle("TSP Controll panel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(300, 500);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
