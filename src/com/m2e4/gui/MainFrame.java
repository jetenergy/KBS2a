package com.m2e4.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private JButton JbTspSim, JbBppSim;
    private TspFrame TspFrame;

    public MainFrame() {
        setLayout(new FlowLayout());
        setTitle("Main Panel");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);

        TspFrame = new TspFrame();

        JbBppSim = new JButton("BPP Simulator");
        JbBppSim.addActionListener(this);
        add(JbBppSim);
        JbTspSim = new JButton("TSP Simulator");
        JbTspSim.addActionListener(this);
        add(JbTspSim);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JbTspSim) {
            if (!TspFrame.isVisible()) {
                TspFrame.setVisible(true);
            }
        }
        if (e.getSource() == JbBppSim) {
            System.out.println("BPP");
        }
    }
}
