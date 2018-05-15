package com.m2e4.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BppFrame extends JFrame {

    public BppFrame() {
        setLayout(new BorderLayout());
        setTitle("BPP simulatie");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 420);


        JPanel JpTop, JpBottom;
        JPanel JpItems, JpSolution, JpBest, JpOptions, JpLog;
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        JpItems = new JPanel();
        JpItems.setLayout(new FlowLayout());
        JpItems.setBorder(border);

        JpSolution = new JPanel();
        JpSolution.setLayout(new FlowLayout());
        JpSolution.setBorder(border);

        JpBest = new JPanel();
        JpBest.setLayout(new FlowLayout());
        JpBest.setBorder(border);

        JpTop = new JPanel();
        JpTop.setLayout(new GridLayout(1, 3));
        JpTop.add(JpItems);
        JpTop.add(JpSolution);
        JpTop.add(JpBest);
        add(JpTop, BorderLayout.CENTER);

        JpOptions = new JPanel();
        JpOptions.setLayout(new FlowLayout());
        JpOptions.setBorder(border);

        JpLog = new JPanel();
        JpLog.setLayout(new FlowLayout());
        JpLog.setBorder(border);

        JpBottom = new JPanel();
        JpBottom.setLayout(new GridLayout(1, 2));
        JpBottom.add(JpOptions);
        JpBottom.add(JpLog);
        add(JpBottom, BorderLayout.SOUTH);
    }

}
