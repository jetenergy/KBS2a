package com.m2e4.gui;

import com.m2e4.LoggerFactory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Random;

public class BppFrame extends JFrame {

    private Object[][] itemData;
    private Object[] columnNames = new Object[]{ "Item", "Hoogte" };

    private JPanel JpTop, JpBottom;
    private JPanel JpItems, JpSolution, JpBest, JpOptions, JpLog;
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    private JTable itemTable = new JTable((itemData != null ? itemData : new Object[][]{}), columnNames);
    private JPanel solutionPanel = new JPanel();
    private JPanel bestPanel = new JPanel();
    private JButton startControl = new JButton("Start / Hervatten");
    private JButton stopControl = new JButton("Stop");
    private JButton pauseControl = new JButton("Pauze");
    private JButton statisticsControl = new JButton("Statistieken");
    private JRadioButton algoBranchandbound = new JRadioButton("Branch-and-Bound");
    private JRadioButton algoTwoFase = new JRadioButton("Two Fase");
    private JRadioButton algoBruteForce = new JRadioButton("Brute Force");
    private JRadioButton algoCustom = new JRadioButton("Eigen Oplossing");
    private JSpinner spAmount = new JSpinner(new SpinnerNumberModel(3, 1, 50, 1));
    private JSpinner spSizeMin = new JSpinner(new SpinnerNumberModel(1.0, 1.0, 4.0, 0.01));
    private JSpinner spSizeMax = new JSpinner(new SpinnerNumberModel(4.0, 2, 5.0, 0.01));
    private JTextPane TaLog = new JTextPane();

    private LoggerFactory.Logger logger = LoggerFactory.makeLogger(TaLog);

    public BppFrame() {
        setLayout(new BorderLayout());
        setTitle("BPP simulatie");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 500);
        setMinimumSize(new Dimension(940, 420));


        JpItems = new JPanel();
        JpItems.setLayout(new FlowLayout());
        JpItems.setBorder(border);
        {
            JPanel container = new JPanel();
//            container.setLayout(new GridLayout(2, 1));
            container.setLayout(new BorderLayout());
            container.add(itemTable.getTableHeader(), BorderLayout.NORTH);
            container.add(itemTable);

            JScrollPane scrollPane = new JScrollPane(container);
            scrollPane.setPreferredSize(new Dimension(200,300));
            JpItems.add(scrollPane);
        }

        JpSolution = new JPanel();
        JpSolution.setBorder(border);
        {
            JLabel title = new JLabel("Huidige oplossing");

            JpSolution.add(title);
            JpSolution.add(solutionPanel);
        }

        JpBest = new JPanel();
        JpBest.setBorder(border);
        {
            JLabel title = new JLabel("Beste oplossing");

            JpBest.add(title);
            JpBest.add(bestPanel);
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
            }

            JPanel algos = new JPanel();
            algos.setLayout(layout);
            {
                ButtonGroup group = new ButtonGroup();
                group.add(algoBranchandbound);
                group.add(algoTwoFase);
                group.add(algoBruteForce);
                group.add(algoCustom);

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


            JpOptions.add(buttons);
            JpOptions.add(algos);
            JpOptions.add(newItems);
        }

        JpLog = new JPanel();
        JpLog.setLayout(new FlowLayout());
        JpLog.setBorder(border);
        {
            Dimension size = new Dimension(360, 110);
            TaLog.setEditable(false);

            JButton save = new JButton("Opslaan");
            save.addActionListener(e -> saveLog());

            JScrollPane pane = new JScrollPane(new JPanel().add(TaLog));
            pane.setPreferredSize(size);
            pane.setMaximumSize(size);
            JpLog.add(pane);
            JpLog.add(save);
        }

        JpBottom = new JPanel();
        JpBottom.setLayout(new GridLayout(1, 2));
        JpBottom.add(JpOptions);
        JpBottom.add(JpLog);
        add(JpBottom, BorderLayout.SOUTH);


        logger.println("BPP simulator geopend");
    }


    private void startResume() {
        startControl.setEnabled(false);
        stopControl.setEnabled(true);
        pauseControl.setEnabled(true);

        itemData = new Object[(int)spAmount.getValue()][];
        for (int i = 0; i < (int)spAmount.getValue(); ++i) {
            itemData[i] = new Object[] { i + 1, Math.round(((double)spSizeMin.getValue() + ((double)spSizeMax.getValue() - (double)spSizeMin.getValue()))
                    * new Random().nextDouble() * 100.0) / 100.0 };
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(itemData, columnNames);
        itemTable.setModel(model);

        logger.println(String.format("Items aangemaakt (c:%d,n:%f,x:%f)", (int)spAmount.getValue(),
                (double)spSizeMin.getValue(), (double)spSizeMax.getValue()), LoggerFactory.ErrorLevel.WARNING);
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


    private void saveLog() {

    }

}
