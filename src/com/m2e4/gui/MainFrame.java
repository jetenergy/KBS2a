package com.m2e4.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private JButton JbTspSim, JbBppSim, JbTspControl, JbBppControl;
    private TspFrame TspSimFrame;
    private BppFrame BppSimFrame;
    private TspCFrame TspContFrame;
    private BppCFrame BppContFrame;

    public MainFrame() {
        setLayout(new FlowLayout());
        setTitle("Main Panel");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 300);

        TspSimFrame = new TspFrame();
        BppSimFrame = new BppFrame();
        TspContFrame = new TspCFrame();
        BppContFrame = new BppCFrame();

        JbTspSim = new JButton("TSP Simulator");
        JbTspSim.addActionListener(this);
        add(JbTspSim);
        JbBppSim = new JButton("BPP Simulator");
        JbBppSim.addActionListener(this);
        add(JbBppSim);
        JbTspControl = new JButton("TSP Control");
        JbTspControl.addActionListener(this);
        add(JbTspControl);
        JbBppControl = new JButton("BPP Control");
        JbBppControl.addActionListener(this);
        add(JbBppControl);

        ArduinoPanel ArduinoConfigPanel = new ArduinoPanel();
        add(ArduinoConfigPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JbTspSim) {
            if (!TspSimFrame.isVisible()) {
                TspSimFrame.setVisible(true);
            }
        }
        if (e.getSource() == JbBppSim) {
            if (!BppSimFrame.isVisible()) {
                BppSimFrame.setVisible(true);
            }
        }
        if (e.getSource() == JbTspControl) {
            if (!TspContFrame.isVisible()) {
                TspContFrame.setVisible(true);
            }
        }
        if (e.getSource() == JbBppControl) {
            if (!BppContFrame.isVisible()) {
                BppContFrame.setVisible(true);
            }
        }
    }
}
