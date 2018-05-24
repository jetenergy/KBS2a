package com.m2e4.gui;

import com.m2e4.DataBase.DataBase;
import com.m2e4.DataBase.Product;
import com.m2e4.LoggerFactory;
import com.m2e4.algorithm.TspEigenOplossing;
import com.m2e4.algorithm.TspGreedy;
import com.m2e4.algorithm.TspSimulatedAnnealing;
import com.m2e4.algorithm.TspTwoOptSwap;
import com.m2e4.gui.tsp.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TspFrame extends JFrame {
    private PositionPanel SolutionPanel;
    private PositionPanel SolutionPrevious;
    private ItemPanel Sitems;
    private SSettingsPanel SSettings;
    private JPanel JpTop, JpBottom, JpLog;

    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private JTextPane TaLog = new JTextPane();

    private ArrayList<Product> producten;

    private LoggerFactory.Logger logger = LoggerFactory.makeLogger(TaLog);

    public TspFrame() {
        setLayout(new BorderLayout());
        setTitle("TSP Simulator paneel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 500);
        setMinimumSize(new Dimension(940, 420));

        Sitems = new ItemPanel();
        SolutionPanel = new PositionPanel("Beste oplossing");
        SolutionPrevious = new PositionPanel("Vorige Oplossing");

        JpTop = new JPanel();
        JpTop.setLayout(new GridLayout(1, 3));
        JpTop.add(Sitems);
        JpTop.add(SolutionPanel);
        JpTop.add(SolutionPrevious);
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
        SSettings = new SSettingsPanel(this);
        JpBottom.add(SSettings);
        JpBottom.add(JpLog);
        add(JpBottom, BorderLayout.SOUTH);
    }

    public void startAlgo(int algoritme, int amount, int maxX, int maxY) {
        producten = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < amount; i++) {
            int x = r.nextInt(maxX);
            int y = r.nextInt(maxY);
            Product product = new Product("", 0, 0, x, y);
            producten.add(product);
        }

        for (int i = 0; i < producten.size(); i++) {
            for (int y = 0; y < producten.size(); y++) {
                if (!producten.get(i).equals(producten.get(y))) {
                    if (producten.get(i).getY() == producten.get(y).getY() &&
                            producten.get(i).getX() == producten.get(y).getX()) {
                        producten.get(i).setX(r.nextInt(maxX));
                        producten.get(i).setY(r.nextInt(maxY));
                        y = 0;
                        i = 0;
                    }
                }
            }
        }

        logger.println("starten: " + algoritme);
        SolutionPrevious.setProducten(SolutionPanel.getProducten());
        SolutionPrevious.setGridWidth(SolutionPanel.getGridWidth());
        SolutionPrevious.setGridHeight(SolutionPanel.getGridHeight());
        SolutionPanel.setGridHeight(maxY);
        SolutionPanel.setGridWidth(maxX);
        switch (algoritme) {
            case 0:
                SolutionPanel.setProducten(TspGreedy.Greedy(producten));
                break;
            case 1:
                SolutionPanel.setProducten(TspTwoOptSwap.TwoOptSwap(producten));
                break;
            case 2:
                SolutionPanel.setProducten(TspSimulatedAnnealing.SimulatedAnnealing(producten));
                break;
            case 3:
                SolutionPanel.setProducten(TspEigenOplossing.EigenOplossing(producten));
                break;
            case -1:
                break;
        }
        repaint();
        logger.println("Voltooid");
    }

    public void getItems() {
        producten = DataBase.getProducts();
        SolutionPanel.setProducten(TspGreedy.Greedy(producten));
        Sitems.setTable(producten);
        logger.println("Producten opgehaald", LoggerFactory.ErrorLevel.INFO);
    }

    private void saveLog() {
        logger.saveLog("TspSimulator");
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if(b) getItems();
    }
}
