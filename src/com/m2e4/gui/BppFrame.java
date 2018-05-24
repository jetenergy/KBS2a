package com.m2e4.gui;

import com.m2e4.LoggerFactory;
import com.m2e4.Main;
import com.m2e4.algorithm.*;
import com.m2e4.algorithm.Box;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class BppFrame extends JFrame {

    private static final int boxCount = 2;
    private static final double boxSize = 10.0;


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
    private JRadioButton algoBestFit = new JRadioButton("Best Fit");
    private JRadioButton algoBruteForce = new JRadioButton("Brute Force");
    private JRadioButton algoCustom = new JRadioButton("Eigen Oplossing", true);
    private JSpinner spAmount = new JSpinner(new SpinnerNumberModel(3, 1, 50, 1));
    private JSpinner spSizeMin = new JSpinner(new SpinnerNumberModel(1.0, 1.0, 4.0, 0.01));
    private JSpinner spSizeMax = new JSpinner(new SpinnerNumberModel(4.0, 2, 5.0, 0.01));
    private JTextPane TaLog = new JTextPane();

    private LoggerFactory.Logger logger = LoggerFactory.makeLogger(TaLog);

    private Runnable runnable;

    public BppFrame() {
        setLayout(new BorderLayout());
        setTitle("BPP simulatie");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 500);
        setMinimumSize(new Dimension(940, 420));


        /*
        This frame contains many elements. Because of this, code can get quite messy.
        That issue was solved by placing child components in code blocks underneath the parent component.
        Some components, however, are defined outside the constructor.
         */

        // Displays the Items generated before running an algorithm
        JpItems = new JPanel();
        JpItems.setLayout(new FlowLayout());
        JpItems.setBorder(border);
        {
            JPanel container = new JPanel();
            container.setLayout(new BorderLayout());
            container.add(itemTable.getTableHeader(), BorderLayout.NORTH);
            container.add(itemTable);

            JScrollPane scrollPane = new JScrollPane(container);
            scrollPane.setPreferredSize(new Dimension(200,300));
            JpItems.add(scrollPane);
        }

        // Displays the solution from running an algorithm
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

        // A collection of panels displayed on the top half of the frame
        JpTop = new JPanel();
        JpTop.setLayout(new GridLayout(1, 2));
        JpTop.add(JpItems);
        JpTop.add(JpSolution);
        add(JpTop, BorderLayout.CENTER);

        // Displays the options for running the algorithms and generating the items
        JpOptions = new JPanel();
        JpOptions.setLayout(new FlowLayout());
        JpOptions.setBorder(border);
        {
            GridLayout layout = new GridLayout(0, 1);
            layout.setVgap(4);
            JPanel buttons = new JPanel();
            buttons.setLayout(layout);
            {
                startControl.addActionListener(e -> start());

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
                group.add(algoBestFit);
                group.add(algoBruteForce);
                group.add(algoCustom);

                algos.add(algoNextfit);
                algos.add(algoBestFit);
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
                {
                    JLabel lblAmount = new JLabel("Aantal", JLabel.TRAILING);
                    spAmount.setPreferredSize(spinnerSize);
                    amount.add(lblAmount);
                    amount.add(spAmount);
                }

                JPanel sizeMin = new JPanel();
                sizeMin.setLayout(flow);
                {
                    JLabel lblSizeMin = new JLabel("Grootte", JLabel.TRAILING);
                    spSizeMin.setPreferredSize(spinnerSize);
                    sizeMin.add(lblSizeMin);
                    sizeMin.add(spSizeMin);
                }

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

        // Displays the log container and save button
        JpLog = new JPanel();
        JpLog.setLayout(new FlowLayout());
        JpLog.setBorder(border);
        {
            Dimension size = new Dimension(360, 110);
            TaLog.setEditable(false);

            JScrollPane pane = new JScrollPane(new JPanel().add(TaLog));
            pane.setPreferredSize(size);
            pane.setMaximumSize(size);

            JButton save = new JButton("Opslaan");
            save.addActionListener(e -> saveLog());

            JpLog.add(pane);
            JpLog.add(save);
        }

        // A collection of panels displayed on the bottom half of the frame
        JpBottom = new JPanel();
        JpBottom.setLayout(new GridLayout(1, 2));
        JpBottom.add(JpOptions);
        JpBottom.add(JpLog);
        add(JpBottom, BorderLayout.SOUTH);


        logger.println("BPP simulator geopend");
    }


    /**
     * Runs the selected algorithm
     */
    private void start() {
        startControl.setEnabled(false);
        stopControl.setEnabled(true);

        solutionPanel.removeAll();

        // Generating random items and placing them in an array
        itemData = new Object[(int)spAmount.getValue()][];
        for (int i = 0; i < (int)spAmount.getValue(); ++i) {
            itemData[i] = new Object[] { i + 1, (double)spSizeMin.getValue() + Math.round(( ((double)spSizeMax.getValue() - (double)spSizeMin.getValue()))
                    * new Random().nextDouble() * 100.0) / 100.0 };
        }

        // Placing the items into the item table
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(itemData, columnNames);
        itemTable.setModel(model);

        if (algoNextfit.isSelected())
            runnable = () -> runAlgorithm(BppNextFit.class);
        else if (algoBestFit.isSelected())
            runnable = () -> runAlgorithm(BppBestFit.class);
        else if (algoBruteForce.isSelected())
            runnable = () -> runAlgorithm(BppBruteForce.class);
        else if (algoCustom.isSelected())
            runnable = () -> runAlgorithm(BppCustom.class);
        else {
            runnable = () -> logger.println("Geen algoritme geselecteerd!", LoggerFactory.ErrorLevel.ERROR);
        }

        // Running algorithm
        Main.getThreadPool().execute(runnable);
    }

    /**
     * Runs an algorithm and causes the solution display to update
     * @param type Any class that implements BppAlgorithm
     * @param <T> Any algorithm
     */
    private <T extends BppAlgorithm> void runAlgorithm(Class<T> type) {
        // Constructing an algorithm of type T
        BppAlgorithm algo;
        try {
            algo = (BppAlgorithm) type.getConstructors()[0].newInstance(boxCount, boxSize);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return;
        }

        logger.println(String.format("Algoritme %s", type.getSimpleName()));

        // Creates an array of Items from the random item array
        Item[] items = new Item[itemData.length];
        for (int i = 0; i < itemData.length; ++i)
            items[i] = new Item((double)itemData[i][1]);

        // Preparing and running algorithm
        algo.setItems(items);
        long startTime = System.nanoTime();
        algo.run();
        long endTime = System.nanoTime();

        // Updating the solution display
        Object solution = algo.getSolution();
        displaySolutionInfo(startTime, endTime, (ArrayList<Box>) solution);

        startControl.setEnabled(true);
        stopControl.setEnabled(false);
    }

    /**
     * Places solution info onto the solution panel
     * @param startTime Time at beginning of algorithm
     * @param endTime Time at end of algorithm
     * @param solution ArrayList containing the solution of the algorithm
     */
    private void displaySolutionInfo(long startTime, long endTime, ArrayList<Box> solution) {
        if (solution == null) {
            logger.println("Items passen niet!", LoggerFactory.ErrorLevel.ERROR);
        }
        else {
            StringBuilder logOut = new StringBuilder("\r\n");
            for (int i = 0; i < solution.size(); i++) {
                logOut.append(String.format("Doos %d:\r\n", i));

                for (Item item : solution.get(i).getItems()) {
                    logOut.append(String.format("\tItem (grootte: %s)\r\n", new DecimalFormat("#.##").format(item.getHeight())));
                }

                logOut.append("\r\n");
            }
            logger.println(logOut.toString(), LoggerFactory.ErrorLevel.RESULT);

            logger.println(String.format("Oplossing gevonden in %s milliseconden", new DecimalFormat("#.####").format((endTime - startTime) / 1000000.0)));

            // Displaying all boxes
            for (int i = 0; i < solution.size(); ++i) {
                solutionPanel.add(new JLabel(String.format("Box %d:", i)));

                // Displaying all items
                for (Item item : solution.get(i).getItems()) {
                    solutionPanel.add(new JLabel(String.format("Item (grootte: %s)", new DecimalFormat("#.##").format(item.getHeight()))));
                }
                solutionPanel.add(new JLabel(" "));
            }

            // Updating UI to display new components
            solutionPanel.updateUI();
        }
    }

    /**
     * Stops the currently running algorithm
     */
    private void stop() {
        Main.getThreadPool().remove(runnable);
        logger.println("Algoritme gestopt", LoggerFactory.ErrorLevel.WARNING);

        startControl.setEnabled(true);
        stopControl.setEnabled(false);
    }

    private void showStatistics() {

    }


    /**
     * Saves the text log to a TXT file
     */
    private void saveLog() {
        // Opens, and if necessary creates, a directory
        File dir = new File("BppSimulator");
        if (!dir.exists()) dir.mkdir();

        File[] files = dir.listFiles();

        try {
            // Opens and writes to a new file
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
