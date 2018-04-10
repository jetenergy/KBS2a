package com.m2e4.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BppCFrame extends JFrame implements ActionListener {
    public BppCFrame() {
        setLayout(new FlowLayout());
        setTitle("BPP Controll panel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(500, 300);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
