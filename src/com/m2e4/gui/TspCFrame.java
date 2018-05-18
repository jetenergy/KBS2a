package com.m2e4.gui;

import com.m2e4.arduino.ArduinoClass;
import com.m2e4.gui.tsp.CItemPanel;
import com.m2e4.gui.tsp.CPositionPanel;
import com.m2e4.gui.tsp.CSettingsPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TspCFrame extends JFrame {
    //private JButton JBOn, JBOff;
    private CPositionPanel CPosition;
    private CItemPanel Citems;
    private CSettingsPanel CSettings;
    private JPanel JpTop, JpBottom, JpLog;

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private JTextPane TaLog = new JTextPane();

    private static ArduinoClass arduino;

    public TspCFrame() {
        setLayout(new BorderLayout());
        setTitle("TSP Controle paneel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 500);
        setMinimumSize(new Dimension(940, 420));

        Citems = new CItemPanel();
        CPosition = new CPositionPanel();

        JpTop = new JPanel();
        JpTop.setLayout(new GridLayout(1, 2));
        JpTop.add(Citems);
        JpTop.add(CPosition);
        add(JpTop, BorderLayout.CENTER);

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
        CSettings = new CSettingsPanel();
        JpBottom.add(CSettings);
        JpBottom.add(JpLog);
        add(JpBottom, BorderLayout.SOUTH);


        //logger.println("BPP simulator geopend");
    }

    public void getItemTable() {
        System.out.println("PING!");
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

    private void saveLog() {

    }
}
/*
    private Object[][] itemData;
    private Object[] columnNames = new Object[]{ "Item", "Hoogte" };

    private JPanel JpTop, JpBottom;
    private JPanel JpItems, JpSolution, JpBest, JpOptions, JpLog;

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






    }



 */