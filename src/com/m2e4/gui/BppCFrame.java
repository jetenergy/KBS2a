package com.m2e4.gui;

import com.m2e4.DataBase.Product;
import com.m2e4.LoggerFactory;
import com.m2e4.Main;
import com.m2e4.algorithm.Box;
import com.m2e4.algorithm.BppAlgorithm;
import com.m2e4.algorithm.BppCustom;
import com.m2e4.arduino.ArduinoClass;
import com.m2e4.gui.bpp.BoxDrawPanel;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BppCFrame extends JFrame {

    private static final int boxCount = 2;
    private static final double boxSize = 10.0;

    private static ArduinoClass arduino;


    // itemData and columnNames are used for the JTable that displays the current order
    private Object[][] itemData;
    private Object[] columnNames = new Object[]{ "Item", "Hoogte" };

    // Important components
    private JPanel JpTop, JpBottom;
    private JPanel JpOrder, JpOptions, JpLog;
    private BoxDrawPanel JpCurrentBoxes, JpFilledBoxes;
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    // Components used for several methods
    private JTable itemTable = new JTable((itemData != null ? itemData : new Object[][]{}), columnNames);
    private JPanel currentPanel = new JPanel();
    private JPanel filledPanel = new JPanel();
    private JButton stopControl = new JButton("Stop");
    private JButton continueControl = new JButton("Verder");
    private JTextPane TaLog = new JTextPane();

    private LoggerFactory.Logger logger = LoggerFactory.makeLogger(TaLog);

    // Indicates that the class is calculating the algorithm and controlling the arduino
    private boolean started = false;
    // Indicates that the continue button has been pressed, allowing execution to continue
    private boolean continued = false;

    public BppCFrame() {
        setLayout(new BorderLayout());
        setTitle("BPP controlepaneel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 500);
        setMinimumSize(new Dimension(940, 420));


        /*
        This frame contains many components. Because of this, code can get quite messy.
        That issue was solved by placing child components in code blocks underneath the parent component.
        Some components, however, are defined outside the constructor.
         */

        // Displays the current order
        JpOrder = new JPanel();
        JpOrder.setLayout(new FlowLayout());
        JpOrder.setBorder(border);
        {
            JPanel container = new JPanel();
            container.setLayout(new BorderLayout());

            itemTable.setDefaultEditor(Object.class, null);

            container.add(itemTable.getTableHeader(), BorderLayout.NORTH);
            container.add(itemTable);

            JScrollPane scrollPane = new JScrollPane(container);
            scrollPane.setPreferredSize(new Dimension(200,300));
            JpOrder.add(scrollPane);
        }

        // Displays the boxes that are currently being filled
        JpCurrentBoxes = new BoxDrawPanel();
        JpCurrentBoxes.setLayout(new BoxLayout(JpCurrentBoxes, BoxLayout.Y_AXIS));
        JpCurrentBoxes.setBorder(border);
        {
            JLabel title = new JLabel("Huidige dozen");
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            currentPanel.setLayout(new BoxLayout(currentPanel, BoxLayout.Y_AXIS));
            currentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JpCurrentBoxes.add(title);
            JpCurrentBoxes.add(currentPanel);
        }

        // Displays the boxes that have already been filled
        JpFilledBoxes = new BoxDrawPanel();
        JpFilledBoxes.setLayout(new BoxLayout(JpFilledBoxes, BoxLayout.Y_AXIS));
        JpFilledBoxes.setBorder(border);
        {
            JLabel title = new JLabel("Gevulde dozen");
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            filledPanel.setLayout(new BoxLayout(filledPanel, BoxLayout.Y_AXIS));
            filledPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JpFilledBoxes.add(title);
            JpFilledBoxes.add(filledPanel);
        }

        // A collection of panels displayed on the top half of the frame
        JpTop = new JPanel();
        JpTop.setLayout(new GridLayout(1, 3));
        JpTop.add(JpOrder);
        JpTop.add(JpCurrentBoxes);
        JpTop.add(JpFilledBoxes);
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
                stopControl.setEnabled(false);
                stopControl.addActionListener(e -> stop());

                continueControl.setEnabled(false);
                continueControl.addActionListener(e -> continuePacking());

                buttons.add(stopControl);
                buttons.add(continueControl);
            }

            JpOptions.add(buttons);
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


        logger.println("BPP controlepaneel geopend");
    }


    /**
     * Runs the algorithm and communicates with the Arduino
     */
    private void start() {
        System.out.println("START");
        started = true;

        // Placing the items into the item table
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(itemData, columnNames);
        itemTable.setModel(model);

        // Getting items out of the item table
        Product[] items = new Product[itemData.length];
        for (int i = 0; i < itemData.length; ++i)
            items[i] = new Product((String)itemData[i][0], (double)itemData[i][1], 0, 0, 0);


        System.out.println("ALGO");
        // Preparing and running algorithm
        BppAlgorithm algorithm = new BppCustom(5, 12.0);
        algorithm.setItems(items);
        long startTime = System.nanoTime();
        try {
            logger.println("Start algoritme", LoggerFactory.ErrorLevel.INFO);
            algorithm.run();
        } catch (InterruptedException e) {
            logger.println("Algoritme gestopt", LoggerFactory.ErrorLevel.WARNING);
            return;
        }
        long endTime = System.nanoTime();

        logger.println(String.format("Algoritme afgerond in %s milliseconden", new DecimalFormat("#.####").format((endTime - startTime) / 1000000.0)), LoggerFactory.ErrorLevel.RESULT);
        System.out.println(algorithm.getSolution());

        // Communicating with the arduino
        System.out.println("COMMUNICATE");
        stopControl.setEnabled(true);

        ArrayList<Box> solution = (ArrayList<Box>) algorithm.getSolution();
        displayBoxes(solution, JpFilledBoxes);

        // Creating pakbon
        // Opens, and if necessary creates, a directory
        String folder = "pakbon";
        File dir = new File(folder);
        if (!dir.exists()) dir.mkdir();

        File[] files = dir.listFiles();

        for (int i = 0; i < solution.size(); i++) {
            Box b = solution.get(i);
            if (b.getUsedHeight() == 0.0) continue;
            try {
                // Opens and writes to a new file
                PrintWriter writer = new PrintWriter(
                        String.format("%s/pakbon_%s_%d.txt", folder, LocalDate.now().toString(), files.length),
                        "UTF-8");
                writer.println(String.format("Doos %d", i));
                writer.println("==========");
                for (Product p : b.getItems()) {
                    writer.println(String.format("%s: x=%d, y=%d, b=%f, h=%f", p.getNaam(), p.getX(), p.getY(), p.getBreedte(), p.getHoogte()));
                }
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        // NOTE: From this comment on, the code might send items to the wrong box if there are more than
        //   two boxes in the solution.
        Box box1 = solution.get(0);
        int box1Taken = 0;
        Box box2 = solution.get(1);
        int box2Taken = 0;
        int boxIndex = 2;

        boolean done = false;

        System.out.println("2");
        // Keeps track of products that are queued for being packed
        System.out.println(Arrays.toString(items));
        ArrayList<Product> productsWaiting = new ArrayList<>();
        productsWaiting.addAll(Arrays.asList(items));
//        ArrayList<Product>  = (ArrayList<Product>) Arrays.asList(items);
        System.out.println("2.5");
        int productsUsed = 0;
        ArrayList<Box> boxesFilled = new ArrayList<>();

        System.out.println("3");
        while (!done) {
            System.out.println("IN WHILE");
            // Replacing full box with the next one
            if (boxIndex < solution.size()) {
                if (box1 == null) box1 = solution.get(boxIndex++);
                else if (box2 == null) box2 = solution.get(boxIndex++);
            }

            displayBoxes(new ArrayList<>(), JpCurrentBoxes);

            // Sending order info to the Arduino
            StringBuilder command = new StringBuilder("BPPOrder;");
            StringBuilder startCommand = new StringBuilder("PakIn;");
            System.out.println("BEFORE FOR");
            System.out.println(productsWaiting);
            ArrayList<Product> productsWaitingTemp = new ArrayList<>(productsWaiting);
            for (Product p : productsWaitingTemp) {
                System.out.println("INSIDE FOR, FIRST IF");
                // Adding info to the command depending on what box the item is in
                if (box1.getItems().contains(p)) {
                    command.append("-1;");
                    productsWaiting.remove(p);
                    ++box1Taken;
                    ++productsUsed;
                } else if (box2.getItems().contains(p)) {
                    command.append("1;");
                    productsWaiting.remove(p);
                    ++box2Taken;
                    ++productsUsed;
                }

                System.out.println("INSIDE FOR, SECOND IF");
                System.out.println(box1);
                System.out.println(box1Taken);
                // When a box is full...
                if (box1Taken >= box1.getItems().size()) {
                    System.out.println("INSIDE FIRST IF");
                    boxesFilled.add(new Box(box1));
                    box1 = null;
                    startCommand.append(productsUsed).append(";");
                    productsUsed = 0;
                    System.out.println("BREAK 1");
                    break;
                } else if (box2Taken >= box2.getItems().size()) {
                    System.out.println("INSIDE SECOND IF");
                    boxesFilled.add(new Box(box2));
                    box2 = null;
                    startCommand.append(productsUsed).append(";");
                    productsUsed = 0;
                    System.out.println("BREAK 1");
                    break;
                }
                System.out.println("LAST");
            }
            System.out.println("4");
            if (productsWaiting.size() == 0) done = true;
            arduino.write(command.toString());
            System.out.println(command.toString());
            arduino.write(startCommand.toString());
            System.out.println(startCommand.toString());

            if (!done) {
                while (true) {
                    String input = arduino.read();
                    if (input.equals("NextStop")) {
                        break;
                    }
                }

                continueControl.setEnabled(true);

                while (true) {
                    if (continued) break;
                }
            }

            ++boxIndex;
        }

        stopControl.setEnabled(false);

        started = false;
    }

    /**
     * Places box information onto a given JPanel
     * @param boxes Information to be shown
     * @param panel The panel that should hold the information
     */
    private void displayBoxes(ArrayList<Box> boxes, BoxDrawPanel panel) {
//        panel.removeAll();
//
//        // Displaying all boxes
//        for (int i = 0; i < boxes.size(); ++i) {
//            panel.add(new JLabel(String.format("Doos %d:", i + 1)));
//
//            // Displaying all items
//            for (Product item : boxes.get(i).getItems()) {
//                panel.add(new JLabel(String.format("Item (grootte: %s)", new DecimalFormat("#.##").format(item.getHoogte()))));
//            }
//            panel.add(new JLabel(" "));
//        }
//
//        // Updating UI to display new components
//        panel.updateUI();

        panel.setBoxes(new ArrayList<>(boxes));
        panel.repaint();
    }

    /**
     * Stops the Arduino
     */
    private void stop() {
        stopControl.setEnabled(false);
    }

    /**
     * Continues packing boxes after a box became full
     */
    private void continuePacking() {
        continued = true;
        continueControl.setEnabled(false);
    }


    /**
     * Saves the text log to a TXT file
     */
    private void saveLog() {
        // Opens, and if necessary creates, a directory
        File dir = new File("BppControl");
        if (!dir.exists()) dir.mkdir();

        File[] files = dir.listFiles();

        try {
            // Opens and writes to a new file
            PrintWriter writer = new PrintWriter(
                    String.format("BppControl/log_%s_%d.txt", LocalDateTime.now().toString(), files.length),
                    "UTF-8");
            String text = TaLog.getDocument().getText(0, TaLog.getDocument().getLength());
            writer.println(text);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException | BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepares the products and fills the itemData array for later use
     * Called by BppCFrame
     * @param products
     */
    public void startBpp(ArrayList<Product> products) {
        ArrayList<Product> newProducts = new ArrayList<>(products);
        Collections.reverse(newProducts);

        itemData = new Object[newProducts.size()][];
        int i = 0;
        for (Product p : newProducts) {
            itemData[i++] = new Object[] { p.getNaam(), p.getHoogte() };
        }

        Main.getThreadPool().submit(this::start);
    }


    /**
     * Sets the arduino variable to hold a connection to an Arduino
     * Closes existing connections if necessary
     * @param port
     */
    public static void setArduino(String port) {
        if (arduino != null) {
            arduino.close();
        }
        arduino = new ArduinoClass(port);
    }

    /**
     * Closes the existing connection
     */
    public static void clearArduino() {
        if (arduino != null) {
            arduino.close();
        }
    }

    /**
     * Checks if a connection to an Arduino is available
     * @return If the connection is available
     */
    public static boolean arduinoHere() {
        return arduino != null;
    }

}
