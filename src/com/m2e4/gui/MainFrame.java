package com.m2e4.gui;

import com.m2e4.arduino.ArduinoClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private JButton JbTspSim, JbBppSim, JTspControl, JbBppControl;
    private TspFrame TspSimFrame;
    private BppFrame BppSimFrame;
    private TspCFrame TspContFrame;
    private BppCFrame BppContFrame;

    public MainFrame(ArduinoClass arduino) {
        setLayout(new FlowLayout());
        setTitle("Main Panel");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(200, 200);

        TspSimFrame = new TspFrame();
        BppSimFrame = new BppFrame();
        TspContFrame = new TspCFrame(arduino);
        BppContFrame = new BppCFrame();


        JbTspSim = new JButton("TSP Simulator");
        JbTspSim.addActionListener(this);
        add(JbTspSim);
        JbBppSim = new JButton("BPP Simulator");
        JbBppSim.addActionListener(this);
        add(JbBppSim);
        JTspControl = new JButton("TSP Control");
        JTspControl.addActionListener(this);
        add(JTspControl);
        JbBppControl = new JButton("BPP Control");
        JbBppControl.addActionListener(this);
        add(JbBppControl);

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
        if (e.getSource() == JTspControl) {
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
