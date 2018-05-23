package com.m2e4.gui.tsp;

import com.m2e4.gui.TspFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SSettingsPanel extends JPanel{

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    private JButton startControl = new JButton("Start");
    private JButton stopControl = new JButton("Stop");
    private JButton statisticsControl = new JButton("Statistieken");
    private JRadioButton algoGreedy = new JRadioButton("Greedy", true);
    private JRadioButton algoTwoOptSwap = new JRadioButton("Two Opt Swap");
    private JRadioButton algoSimulatedAnnealing = new JRadioButton("Simulated Annealing");
    private JRadioButton algoCustom = new JRadioButton("Eigen Oplossing");
    private ButtonGroup group;

    private JSpinner spAmount = new JSpinner(new SpinnerNumberModel(3, 1, 50, 1));
    private JSpinner spMaxHeight = new JSpinner(new SpinnerNumberModel(5, 5, 100, 1));
    private JSpinner spMaxWidth = new JSpinner(new SpinnerNumberModel(5, 5, 100, 1));

    private TspFrame parent;

    public SSettingsPanel(TspFrame parent) {
        setLayout(new FlowLayout());
        setBorder(border);
        GridLayout layout = new GridLayout(0, 1);
        layout.setVgap(4);
        JPanel buttons = new JPanel();
        buttons.setLayout(layout);

        startControl.addActionListener(e -> startResume());

        stopControl.setEnabled(false);
        stopControl.addActionListener(e -> stop());

        statisticsControl.addActionListener(e -> showStatistics());

        buttons.add(startControl);
        buttons.add(stopControl);
        buttons.add(statisticsControl);

        JPanel algos = new JPanel();
        algos.setLayout(layout);
        {
            group = new ButtonGroup();
            group.add(algoGreedy);
            group.add(algoTwoOptSwap);
            group.add(algoSimulatedAnnealing);
            group.add(algoCustom);

            algos.add(algoGreedy);
            algos.add(algoTwoOptSwap);
            algos.add(algoSimulatedAnnealing);
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
            spAmount.setPreferredSize(spinnerSize);
            amount.add(lblAmount);
            amount.add(spAmount);

            JPanel sizeMin = new JPanel();
            sizeMin.setLayout(flow);
            JLabel lblSizeMin = new JLabel("Grootte", JLabel.TRAILING);
            spMaxHeight.setPreferredSize(spinnerSize);
            sizeMin.add(lblSizeMin);
            sizeMin.add(spMaxHeight);

            JPanel sizeMax = new JPanel();
            sizeMax.setLayout(flow);
            spMaxWidth.setPreferredSize(spinnerSize);
            sizeMax.add(spMaxWidth);

            newItems.add(amount);
            newItems.add(sizeMin);
            newItems.add(sizeMax);
        }

        add(buttons);
        add(algos);
        add(newItems);

        this.parent = parent;
    }

    private void startResume() {
        //startControl.setEnabled(false);
        //stopControl.setEnabled(true);

        parent.startAlgo(getSelection(), (int)spAmount.getValue(), (int)spMaxWidth.getValue(), (int)spMaxHeight.getValue());
    }

    private int getSelection() {
        if (group.getSelection().equals(algoGreedy.getModel())) {
            return 0;
        }
        if (group.getSelection().equals(algoTwoOptSwap.getModel())) {
            return 1;
        }
        if (group.getSelection().equals(algoSimulatedAnnealing.getModel())) {
            return 2;
        }
        if (group.getSelection().equals(algoCustom.getModel())) {
            return 3;
        }
        return -1;
    }

    private void stop() {
        startControl.setEnabled(true);
        stopControl.setEnabled(false);
    }

    private void showStatistics() {

    }
}
