package com.m2e4.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BppFrame extends JFrame {

    private Object[][] itemData;

    public BppFrame() {
        setLayout(new BorderLayout());
        setTitle("BPP simulatie");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 420);
        setMinimumSize(new Dimension(800, 420));


        JPanel JpTop, JpBottom;
        JPanel JpItems, JpSolution, JpBest, JpOptions, JpLog;
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        JpItems = new JPanel();
        JpItems.setLayout(new FlowLayout());
        JpItems.setBorder(border);
        {
            JPanel container = new JPanel();
            container.setLayout(new GridLayout(2, 1));

            JTable table = new JTable((itemData != null ? itemData : new Object[][]{}), new Object[]{ "Item", "Hoogte" });

            container.add(table.getTableHeader());
            container.add(table);

            JpItems.add(new JScrollPane(container));
        }

        JpSolution = new JPanel();
        JpSolution.setLayout(new FlowLayout());
        JpSolution.setBorder(border);
        {
            JLabel title = new JLabel("Huidige oplossing");

            JpSolution.add(title);
        }

        JpBest = new JPanel();
        JpBest.setLayout(new FlowLayout());
        JpBest.setBorder(border);
        {
            JLabel title = new JLabel("Beste oplossing");

            JpBest.add(title);
        }

        JpTop = new JPanel();
        JpTop.setLayout(new GridLayout(1, 3));
        JpTop.add(JpItems);
        JpTop.add(JpSolution);
        JpTop.add(JpBest);
        add(JpTop, BorderLayout.CENTER);

        JpOptions = new JPanel();
        JpOptions.setLayout(new FlowLayout());
        JpOptions.setBorder(border);
        {
            GridLayout layout = new GridLayout(0, 1);
            layout.setVgap(4);
            JPanel buttons = new JPanel();
            buttons.setLayout(layout);
            {
                JButton startControl = new JButton("Start / Hervatten");
                startControl.addActionListener(e -> startResume());

                JButton stopControl = new JButton("Stop");
                stopControl.setEnabled(false);
                stopControl.addActionListener(e -> stop());

                JButton pauseControl = new JButton("Pauze");
                pauseControl.setEnabled(false);
                pauseControl.addActionListener(e -> pause());

                JButton statisticsControl = new JButton("Statistieken");
                statisticsControl.addActionListener(e -> showStatistics());

                buttons.add(startControl);
                buttons.add(stopControl);
                buttons.add(pauseControl);
                buttons.add(statisticsControl);
            }

            JPanel algos = new JPanel();
            algos.setLayout(layout);
            {
                JRadioButton algoBranchandbound = new JRadioButton("Branch-and-Bound");
                JRadioButton algoTwoFase = new JRadioButton("Two Fase");
                JRadioButton algoBruteForce = new JRadioButton("Brute Force");
                JRadioButton algoCustom = new JRadioButton("Eigen Oplossing");

                algos.add(algoBranchandbound);
                algos.add(algoTwoFase);
                algos.add(algoBruteForce);
                algos.add(algoCustom);
            }

            JPanel newItems = new JPanel();
            newItems.setLayout(layout);
            {
                FlowLayout flow = new FlowLayout();
                flow.setAlignment(FlowLayout.RIGHT);
                Dimension spinnerSize = new Dimension(50, 20);

                JPanel amount = new JPanel();
                amount.setLayout(flow);
                JLabel lblAmount = new JLabel("Aantal", JLabel.TRAILING);
                JSpinner spAmount = new JSpinner(new SpinnerNumberModel(3, 1, 8, 1));
                spAmount.setPreferredSize(spinnerSize);
                amount.add(lblAmount);
                amount.add(spAmount);

                JPanel sizeMin = new JPanel();
                sizeMin.setLayout(flow);
                JLabel lblSizeMin = new JLabel("Grootte", JLabel.TRAILING);
                JSpinner spSizeMin = new JSpinner(new SpinnerNumberModel(1.0, 1.0, 4.0, 0.01));
                spSizeMin.setPreferredSize(spinnerSize);
                sizeMin.add(lblSizeMin);
                sizeMin.add(spSizeMin);

                JPanel sizeMax = new JPanel();
                sizeMax.setLayout(flow);
                JSpinner spSizeMax = new JSpinner(new SpinnerNumberModel(4.0, 2, 5.0, 0.01));
                spSizeMax.setPreferredSize(spinnerSize);
                sizeMax.add(spSizeMax);

                newItems.add(amount);
                newItems.add(sizeMin);
                newItems.add(sizeMax);
            }


            JpOptions.add(buttons);
            JpOptions.add(algos);
            JpOptions.add(newItems);
        }

        JpLog = new JPanel();
        JpLog.setLayout(new FlowLayout());
        JpLog.setBorder(border);

        JpBottom = new JPanel();
        JpBottom.setLayout(new GridLayout(1, 2));
        JpBottom.add(JpOptions);
        JpBottom.add(JpLog);
        add(JpBottom, BorderLayout.SOUTH);
    }

    private void startResume() {

    }

    private void stop() {

    }

    private void pause() {

    }

    private void showStatistics() {

    }

}
