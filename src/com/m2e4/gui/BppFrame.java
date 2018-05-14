package com.m2e4.gui;

import com.m2e4.arduino.ArduinoClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BppFrame extends JFrame implements ActionListener {
    private ArduinoClass arduino;

    public BppFrame() {
        setLayout(new BorderLayout());
        setTitle("BPP simulatie");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(640, 420);


        JPanel JpItems, JpSolution, JpBest, JpOptions, JpLog;
        JSplitPane JspRoot, JspTop1, JspTop2, JspBottom;

        // TOP PANE
        JpItems = new JPanel();
        JpItems.setLayout(new FlowLayout());
        JpItems.setBackground(Color.CYAN);
        JpItems.setMinimumSize(new Dimension(120, 200));

        JpSolution = new JPanel();
        JpSolution.setLayout(new FlowLayout());
        JpSolution.setBackground(Color.BLUE);

        JpBest = new JPanel();
        JpBest.setLayout(new FlowLayout());

        JspTop1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, JpItems, JpSolution);
        JspTop1.setDividerLocation(200);

        JspTop2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, JspTop1, JpBest);
        JspTop2.setDividerLocation(420);

        // BOTTOM PANE
        JpOptions = new JPanel();
        JpOptions.setLayout(new FlowLayout());

        JpLog = new JPanel();
        JpLog.setLayout(new FlowLayout());

        JspBottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, JpOptions, JpLog);
        JspBottom.setDividerLocation(320);


        JSplitPane JspX = new JSplitPane(JSplitPane.VERTICAL_SPLIT, JspTop2, JspBottom);
        JspX.setDividerLocation(280);

        add(JspX, BorderLayout.CENTER);


        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void setVisible(boolean b) {
        if (b) arduino = new ArduinoClass(MainFrame.getPort());
        super.setVisible(b);
    }
}
