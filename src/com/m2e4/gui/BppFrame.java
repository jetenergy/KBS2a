package com.m2e4.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BppFrame extends JFrame implements ActionListener {
    public BppFrame() {
        setLayout(new FlowLayout());
        setTitle("BPP panel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(300, 200);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
