package com.m2e4.gui;

import com.m2e4.LoggerFactory;
import com.m2e4.Main;
import com.m2e4.algorithm.BppBruteForce;
import com.m2e4.algorithm.BppCustom;
import com.m2e4.algorithm.BppNextFit;
import com.m2e4.algorithm.Item;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class BppFrame extends JFrame {

    private Object[][] itemData;
    private Object[] columnNames = new Object[]{ "Item", "Hoogte" };

    private JPanel JpTop, JpBottom;
    private JPanel JpItems, JpSolution, JpOptions, JpLog;
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    private JTable itemTable = new JTable((itemData != null ? itemData : new Object[][]{}), columnNames);
    private JPanel solutionPanel = new JPanel();
    private JButton startControl = new JButton("Start");
    private JButton stopControl = new JButton("Stop");
    private JButton statisticsControl = new JButton("Statistieken");
    private JRadioButton algoNextfit = new JRadioButton("Next Fit");
    private JRadioButton algoTwoFase = new JRadioButton("Two Fase");
    private JRadioButton algoBruteForce = new JRadioButton("Brute Force");
    private JRadioButton algoCustom = new JRadioButton("Eigen Oplossing", true);
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
        JpSolution.setLayout(new BoxLayout(JpSolution, BoxLayout.Y_AXIS));
        JpSolution.setBorder(border);
        {
            JLabel title = new JLabel("Oplossing");
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            solutionPanel.setLayout(new BoxLayout(solutionPanel, BoxLayout.Y_AXIS));
            solutionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JpSolution.add(title);
            JpSolution.add(solutionPanel);
        }

        JpTop = new JPanel();
        JpTop.setLayout(new GridLayout(1, 2));
        JpTop.add(JpItems);
        JpTop.add(JpSolution);
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

                statisticsControl.addActionListener(e -> showStatistics());

                buttons.add(startControl);
                buttons.add(stopControl);
                buttons.add(statisticsControl);
            }

            JPanel algos = new JPanel();
            algos.setLayout(layout);
            {
                ButtonGroup group = new ButtonGroup();
                group.add(algoNextfit);
                group.add(algoTwoFase);
                group.add(algoBruteForce);
                group.add(algoCustom);

                algos.add(algoNextfit);
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

        solutionPanel.removeAll();

        itemData = new Object[(int)spAmount.getValue()][];
        for (int i = 0; i < (int)spAmount.getValue(); ++i) {
            itemData[i] = new Object[] { i + 1, (double)spSizeMin.getValue() + Math.round(( ((double)spSizeMax.getValue() - (double)spSizeMin.getValue()))
                    * new Random().nextDouble() * 100.0) / 100.0 };
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(itemData, columnNames);
        itemTable.setModel(model);

        if (algoNextfit.isSelected()) {
            Main.getThreadPool().execute(() -> {
                BppNextFit algo = new BppNextFit(2, 10);
                Item[] items = new Item[itemData.length];
                for (int i = 0; i < itemData.length; ++i)
                    items[i] = new Item((double)itemData[i][1]);
                algo.setItems(items);
                long startTime = System.nanoTime();
                algo.run();
                long endTime = System.nanoTime();

                Object solution = algo.getSolution();
                if (solution == null) {
                    logger.println("Items passen niet!", LoggerFactory.ErrorLevel.ERROR);
                }
                else {
                    ArrayList<ArrayList<Item>> solutionList = (ArrayList<ArrayList<Item>>) solution;
                    logger.println(String.format("Oplossing gevonden in %s milliseconden", new DecimalFormat("#.####").format((endTime - startTime) / 1000000.0)));

                    for (int i = 0; i < solutionList.size(); ++i) {
                        solutionPanel.add(new JLabel(String.format("Box %d:", i)));

                        for (Item item : solutionList.get(i)) {
                            solutionPanel.add(new JLabel(String.format("Item (grootte: %s)", new DecimalFormat("#.##").format(item.getHeight()))));
                        }
                        solutionPanel.add(new JLabel(" "));
                    }

                    solutionPanel.updateUI();
                }

                startControl.setEnabled(true);
                stopControl.setEnabled(false);
            });
        }
        else if (algoTwoFase.isSelected()) {

        }
        else if (algoBruteForce.isSelected()) {
            Main.getThreadPool().execute(() -> {
                BppBruteForce algo = new BppBruteForce(2, 10);
                Item[] items = new Item[itemData.length];
                for (int i = 0; i < itemData.length; ++i)
                    items[i] = new Item((double)itemData[i][1]);
                algo.setItems(items);
                long startTime = System.nanoTime();
                algo.run();
                long endTime = System.nanoTime();

                Object solution = algo.getSolution();
                if (solution == null) {
                    logger.println("Items passen niet!", LoggerFactory.ErrorLevel.ERROR);
                }
                else {
                    ArrayList<BppBruteForce.Box> solutionList = (ArrayList<BppBruteForce.Box>) solution;
                    logger.println(String.format("Oplossing gevonden in %s milliseconden", new DecimalFormat("#.####").format((endTime - startTime) / 1000000.0)));

                    for (int i = 0; i < solutionList.size(); ++i) {
                        solutionPanel.add(new JLabel(String.format("Box %d:", i)));

                        for (Item item : solutionList.get(i).getItems()) {
                            solutionPanel.add(new JLabel(String.format("Item (grootte: %s)", new DecimalFormat("#.##").format(item.getHeight()))));
                        }
                        solutionPanel.add(new JLabel(" "));
                    }

                    solutionPanel.updateUI();
                }

                startControl.setEnabled(true);
                stopControl.setEnabled(false);

            });
        }
        else {
            Main.getThreadPool().execute(() -> {
                BppCustom algo = new BppCustom(2, 10);
                Item[] items = new Item[itemData.length];
                for (int i = 0; i < itemData.length; ++i)
                    items[i] = new Item((double)itemData[i][1]);
                algo.setItems(items);
                long startTime = System.nanoTime();
                algo.run();
                long endTime = System.nanoTime();

                Object solution = algo.getSolution();
                if (solution == null) {
                    logger.println("Items passen niet!", LoggerFactory.ErrorLevel.ERROR);
                }
                else {
                    ArrayList<ArrayList<Item>> solutionList = (ArrayList<ArrayList<Item>>) solution;
                    logger.println(String.format("Oplossing gevonden in %s milliseconden", new DecimalFormat("#.####").format((endTime - startTime) / 1000000.0)));

                    for (int i = 0; i < solutionList.size(); ++i) {
                        solutionPanel.add(new JLabel(String.format("Box %d:", i)));

                        for (Item item : solutionList.get(i)) {
                            solutionPanel.add(new JLabel(String.format("Item (grootte: %s)", new DecimalFormat("#.##").format(item.getHeight()))));
                        }
                        solutionPanel.add(new JLabel(" "));
                    }

                    solutionPanel.updateUI();
                }

                startControl.setEnabled(true);
                stopControl.setEnabled(false);
            });
        }
    }

    private void stop() {
        startControl.setEnabled(true);
        stopControl.setEnabled(false);
    }

    private void pause() {
        startControl.setEnabled(true);
        stopControl.setEnabled(true);
    }

    private void showStatistics() {

    }


    private void saveLog() {
        File dir = new File("BppSimulator");
        if (!dir.exists()) dir.mkdir();

        System.out.print(dir.toString());

        File[] files = dir.listFiles();

        try {
            PrintWriter writer = new PrintWriter(
                    String.format("BppSimulator/log_%s_%d.txt", LocalDateTime.now().toString(), files.length),
                    "UTF-8");
            String text = TaLog.getDocument().getText(0, TaLog.getDocument().getLength());
            writer.println(text);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException | BadLocationException e) {
            e.printStackTrace();
        }
    }

}
