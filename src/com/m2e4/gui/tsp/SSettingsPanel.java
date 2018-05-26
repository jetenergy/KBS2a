package com.m2e4.gui.tsp;

import com.m2e4.Main;
import com.m2e4.gui.TspFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SSettingsPanel extends JPanel{

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    private JButton startControl = new JButton("Start");
    private JButton stopControl = new JButton("Stop");
    private JRadioButton algoGreedy = new JRadioButton("Greedy", true);
    private JRadioButton algoTwoOptSwap = new JRadioButton("Two Opt Swap");
    private JRadioButton algoSimulatedAnnealing = new JRadioButton("Simulated Annealing");
    private JRadioButton algoCustom = new JRadioButton("Eigen Oplossing");
    private ButtonGroup group;

    private JSpinner spAmount = new JSpinner(new SpinnerNumberModel(3, 2, 25, 1));
    private JSpinner spMaxHeight = new JSpinner(new SpinnerNumberModel(5, 5, 20, 1));
    private JSpinner spMaxWidth = new JSpinner(new SpinnerNumberModel(5, 5, 20, 1));

    private TspFrame parent;

    private Runnable runnable;

    public SSettingsPanel(TspFrame parent) {
        setLayout(new FlowLayout());
        setBorder(border);
        GridLayout layout = new GridLayout(0, 1);
        layout.setVgap(4);
        JPanel buttons = new JPanel();
        buttons.setLayout(layout);

        startControl.addActionListener(e -> startResume());

        stopControl.setEnabled(false);
        stopControl.addActionListener(e -> forceStop());

        buttons.add(startControl);
        buttons.add(stopControl);

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

            JPanel sizeY = new JPanel();
            sizeY.setLayout(flow);
            JLabel lblSizeY = new JLabel("Hoogte", JLabel.TRAILING);
            spMaxHeight.setPreferredSize(spinnerSize);
            spMaxHeight.addChangeListener(e -> setAmountMax());
            sizeY.add(lblSizeY);
            sizeY.add(spMaxHeight);

            JPanel sizeX = new JPanel();
            sizeX.setLayout(flow);
            JLabel lblSizeX = new JLabel("Breedte", JLabel.TRAILING);
            spMaxWidth.setPreferredSize(spinnerSize);
            spMaxWidth.addChangeListener(e -> setAmountMax());
            sizeX.add(lblSizeX);
            sizeX.add(spMaxWidth);

            newItems.add(amount);
            newItems.add(sizeY);
            newItems.add(sizeX);
        }

        add(buttons);
        add(algos);
        add(newItems);

        this.parent = parent;
    }

    private void setAmountMax() {
        int max = (int)spMaxWidth.getValue() * (int)spMaxHeight.getValue();
        if ((int)spAmount.getValue() > max) spAmount.setValue(max);
        spAmount.setModel(new SpinnerNumberModel((int)spAmount.getValue(), 2, max, 1));
    }

    private void startResume() {
        startControl.setEnabled(false);
        stopControl.setEnabled(true);

        runnable = () -> {
            parent.startAlgo(getSelection(), (int)spAmount.getValue(), (int)spMaxWidth.getValue(), (int)spMaxHeight.getValue());
            stop();
        };

        Main.getThreadPool().execute(runnable);
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

    private void forceStop() {
        Main.getThreadPool().remove(runnable);
        stop();
    }

    private void stop() {
        startControl.setEnabled(true);
        stopControl.setEnabled(false);
        parent.stop(getSelection());
    }
}
