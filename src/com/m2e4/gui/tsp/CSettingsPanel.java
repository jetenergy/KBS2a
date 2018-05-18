package com.m2e4.gui.tsp;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;

public class CSettingsPanel extends JPanel{
    //private JButton Start, Stop, Pauze, Statistieken;
    //private JRadioButton algo1, algo2, algo3, algo4;
    //private ButtonGroup algoGroup;

    private Object[][] itemData;
    private Object[] columnNames = new Object[]{"Item", "X" , "Y"};

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    private JButton startControl = new JButton("Start / Hervatten");
    private JButton stopControl = new JButton("Stop");
    private JButton pauseControl = new JButton("Pauze");
    private JButton statisticsControl = new JButton("Statistieken");
    private JRadioButton algoGreedy = new JRadioButton("Greedy");
    private JRadioButton algoTwoOptSwap = new JRadioButton("Two Opt Swap");
    private JRadioButton algoSimulatedAnnealing = new JRadioButton("Simulated Annealing");
    private JRadioButton algoCustom = new JRadioButton("Eigen Oplossing");

    private JSpinner spAmount = new JSpinner(new SpinnerNumberModel(3, 1, 50, 1));
    private JSpinner spSizeMin = new JSpinner(new SpinnerNumberModel(1.0, 1.0, 4.0, 0.01));
    private JSpinner spSizeMax = new JSpinner(new SpinnerNumberModel(4.0, 2, 5.0, 0.01));

    public CSettingsPanel() {
        setLayout(new FlowLayout());
        setBorder(border);
        GridLayout layout = new GridLayout(0, 1);
        layout.setVgap(4);
        JPanel buttons = new JPanel();
        buttons.setLayout(layout);

        startControl.addActionListener(e -> startResume());

        stopControl.setEnabled(false);
        stopControl.addActionListener(e -> stop());

        pauseControl.setEnabled(false);
        pauseControl.addActionListener(e -> pause());

        statisticsControl.addActionListener(e -> showStatistics());

        buttons.add(startControl);
        buttons.add(stopControl);
        buttons.add(pauseControl);
        buttons.add(statisticsControl);

        JPanel algos = new JPanel();
        algos.setLayout(layout);
        {
            ButtonGroup group = new ButtonGroup();
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
            spSizeMin.setPreferredSize(spinnerSize);
            sizeMin.add(lblSizeMin);
            sizeMin.add(spSizeMin);

            JPanel sizeMax = new JPanel();
            sizeMax.setLayout(flow);
            spSizeMax.setPreferredSize(spinnerSize);
            sizeMax.add(spSizeMax);

            newItems.add(amount);
            newItems.add(sizeMin);
            newItems.add(sizeMax);
        }


        add(buttons);
        add(algos);
        add(newItems);


    }


    private void startResume() {
        /*startControl.setEnabled(false);
        stopControl.setEnabled(true);
        pauseControl.setEnabled(true);

        itemData = new Object[(int)spAmount.getValue()][];
        for (int i = 0; i < (int)spAmount.getValue(); ++i) {
            itemData[i] = new Object[] { i + 1, Math.round(((double)spSizeMin.getValue() + ((double)spSizeMax.getValue() - (double)spSizeMin.getValue()))
                    * new Random().nextDouble() * 100.0) / 100.0 };
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(itemData, columnNames);*/
        //.getItemTable().setModel(model);

        //logger.println(String.format("Items aangemaakt (c:%d,n:%f,x:%f)", (int)spAmount.getValue(),
        //        (double)spSizeMin.getValue(), (double)spSizeMax.getValue()), LoggerFactory.ErrorLevel.WARNING);
    }

    private void stop() {
        startControl.setEnabled(true);
        stopControl.setEnabled(false);
        pauseControl.setEnabled(false);
    }

    private void pause() {
        startControl.setEnabled(true);
        stopControl.setEnabled(true);
        pauseControl.setEnabled(false);
    }

    private void showStatistics() {

    }
}
