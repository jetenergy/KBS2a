package com.m2e4.gui;

import com.m2e4.DataBase.DataBase;
import com.m2e4.DataBase.Product;
import com.m2e4.LoggerFactory;
import com.m2e4.algorithm.TspGreedy;
import com.m2e4.arduino.ArduinoClass;
import com.m2e4.gui.tsp.CItemPanel;
import com.m2e4.gui.tsp.CPositionPanel;
import com.m2e4.gui.tsp.CSettingsPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class TspCFrame extends JFrame {
    private CPositionPanel CPosition;
    private CItemPanel Citems;
    private CSettingsPanel CSettings;
    private JPanel JpTop, JpBottom, JpLog;

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private JTextPane TaLog = new JTextPane();

    private ArrayList<Product> producten;

    private LoggerFactory.Logger logger = LoggerFactory.makeLogger(TaLog);

    private static ArduinoClass arduino;

    public TspCFrame() {
        setLayout(new BorderLayout());
        setTitle("TSP Controle paneel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 500);
        setMinimumSize(new Dimension(940, 420));

        Citems = new CItemPanel();
        CPosition = new CPositionPanel("Beste oplossing");

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
        CSettings = new CSettingsPanel(this);
        JpBottom.add(CSettings);
        JpBottom.add(JpLog);
        add(JpBottom, BorderLayout.SOUTH);


        logger.println("TSP Controll geopend");
    }

    public void startAlgo() {
        logger.println("starting: Greedy");
        CPosition.setProducten(TspGreedy.Greedy(producten));
        repaint();
    }

    public void getItems() {
        producten = DataBase.getProducts();
        CPosition.setProducten(TspGreedy.Greedy(producten));
        Citems.setTable(producten);
        logger.println("Items Got", LoggerFactory.ErrorLevel.INFO);
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

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if(b) getItems();
    }
}