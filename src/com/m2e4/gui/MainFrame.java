package com.m2e4.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static MainFrame instance;

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
        JbTspSim.addActionListener(e -> {if (!TspSimFrame.isVisible()) TspSimFrame.setVisible(true);});
        add(JbTspSim);
        JbBppSim = new JButton("BPP Simulator");
        JbBppSim.addActionListener(e -> {if (!BppSimFrame.isVisible()) BppSimFrame.setVisible(true);});
        add(JbBppSim);
        JbTspControl = new JButton("TSP Control");
        JbTspControl.addActionListener(e -> {if (!TspContFrame.isVisible()) TspContFrame.setVisible(true);});
        add(JbTspControl);
        JbBppControl = new JButton("BPP Control");
        JbBppControl.addActionListener(e -> {if (!BppContFrame.isVisible()) BppContFrame.setVisible(true);});
        add(JbBppControl);

        ArduinoPanel ArduinoConfigPanel = new ArduinoPanel();
        add(ArduinoConfigPanel);

        instance = this;

        setVisible(true);
    }

    public static MainFrame getInstance() {
        return instance;
    }

    public BppCFrame getBppContFrame() {
        return BppContFrame;
    }
}
