package com.m2e4.gui;

import com.m2e4.LoggerFactory;
import com.m2e4.arduino.ArduinoClass;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public class BppCFrame extends JFrame {

    private static final int boxCount = 2;
    private static final double boxSize = 10.0;

    private static ArduinoClass arduino;


    private Object[][] itemData;
    private Object[] columnNames = new Object[]{ "Item", "Hoogte" };

    private JPanel JpTop, JpBottom;
    private JPanel JpOrder, JpCurrentBoxes, JpFilledBoxes, JpOptions, JpLog;
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    private JTable itemTable = new JTable((itemData != null ? itemData : new Object[][]{}), columnNames);
    private JPanel currentPanel = new JPanel();
    private JPanel filledPanel = new JPanel();
    private JButton startControl = new JButton("Start");
    private JButton stopControl = new JButton("Stop");
    private JButton continueControl = new JButton("Verder");
    private JTextPane TaLog = new JTextPane();

    private LoggerFactory.Logger logger = LoggerFactory.makeLogger(TaLog);

    public BppCFrame() {
        setLayout(new BorderLayout());
        setTitle("BPP controlepaneel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 500);
        setMinimumSize(new Dimension(940, 420));


        /*
        This frame contains many elements. Because of this, code can get quite messy.
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
            container.add(itemTable.getTableHeader(), BorderLayout.NORTH);
            container.add(itemTable);

            JScrollPane scrollPane = new JScrollPane(container);
            scrollPane.setPreferredSize(new Dimension(200,300));
            JpOrder.add(scrollPane);
        }

        // Displays the boxes that are currently being filled
        JpCurrentBoxes = new JPanel();
        JpCurrentBoxes.setLayout(new BoxLayout(JpCurrentBoxes, BoxLayout.Y_AXIS));
        JpCurrentBoxes.setBorder(border);
        {
            JLabel title = new JLabel("Huidige dozen");
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            currentPanel.setLayout(new BoxLayout(currentPanel, BoxLayout.Y_AXIS));
            currentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JpCurrentBoxes.add(title);
            JpCurrentBoxes.add(filledPanel);
        }

        // Displays the boxes that have already been filled
        JpFilledBoxes = new JPanel();
        JpFilledBoxes.setLayout(new BoxLayout(JpFilledBoxes, BoxLayout.Y_AXIS));
        JpFilledBoxes.setBorder(border);
        {
            JLabel title = new JLabel("Gevulde dozen");
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            filledPanel.setLayout(new BoxLayout(filledPanel, BoxLayout.Y_AXIS));
            filledPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JpFilledBoxes.add(title);
            JpFilledBoxes.add(currentPanel);
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
                startControl.addActionListener(e -> start());

                stopControl.setEnabled(false);
                stopControl.addActionListener(e -> stop());

                continueControl.setEnabled(false);
                continueControl.addActionListener(e -> showStatistics());

                buttons.add(startControl);
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


    private void start() {
        // TODO: Control Arduino
    }

    // TODO: Display current working boxes

    // TODO: Display finished boxes

    /**
     * Stops the Arduino
     */
    private void stop() {
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



    public static void setArduino(String port) {
        if (arduino != null) {
            arduino.close();
        }
        arduino = new ArduinoClass(port);
    }

    public static void clearArduino() {
        if (arduino != null) {
            arduino.close();
        }
    }

}
