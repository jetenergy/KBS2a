package com.m2e4.gui;

import com.m2e4.DataBase.Product;
import com.m2e4.LoggerFactory;
import com.m2e4.algorithm.TspEigenOplossing;
import com.m2e4.algorithm.TspGreedy;
import com.m2e4.algorithm.TspSimulatedAnnealing;
import com.m2e4.algorithm.TspTwoOptSwap;
import com.m2e4.gui.tsp.ItemPanel;
import com.m2e4.gui.tsp.PositionPanel;
import com.m2e4.gui.tsp.SSettingsPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.text.DecimalFormat;
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

    private ArrayList<Product> producten = new ArrayList<>();

    private LoggerFactory.Logger logger = LoggerFactory.makeLogger(TaLog);

    public TspFrame() {
        setLayout(new BorderLayout());
        setTitle("TSP Simulator paneel");
        // HIDE_ON_CLOSE word gebruikt zodat de gegevens niet verloren gaan
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 500);
        setMinimumSize(new Dimension(940, 420));

        Sitems = new ItemPanel();
        SolutionPanel = new PositionPanel("Beste oplossing");
        SolutionPrevious = new PositionPanel("Vorige Oplossing");

        // het controle frame is opgedeeld in 2 secties het JpTop en JpBottom
        // JpTop heeft de items (links boven) en de oplossing en positie van de arduino (rechts boven)
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

        // JpBottom heeft 2 elementen het settings panel en het log paneel
        JpBottom = new JPanel();
        JpBottom.setLayout(new GridLayout(1, 2));
        SSettings = new SSettingsPanel(this);
        JpBottom.add(SSettings);
        JpBottom.add(JpLog);
        add(JpBottom, BorderLayout.SOUTH);
    }

    public void startAlgo(int algoritme, int amount, int maxX, int maxY) {
        logger.println("Starten: " + algoName(algoritme));
        // als 1 van de spinners veranderd is dan maakt hij nieuwe random producten aan
        if (producten.size() != amount ||
                SolutionPanel.getGridHeight() != maxY ||
                SolutionPanel.getGridWidth() != maxX) {
            producten = randomizeProducten(amount, maxX, maxY);
            Sitems.setTable(producten);
        }

        // zet de SolutionPrevious paneel gelijk aan alles uit het SolutionPanel
        SolutionPrevious.setProducten(SolutionPanel.getProducten());
        SolutionPrevious.setGridWidth(SolutionPanel.getGridWidth());
        SolutionPrevious.setGridHeight(SolutionPanel.getGridHeight());
        // zet de grid breedte en hoogte gelijk aan de spinners
        SolutionPanel.setGridHeight(maxY);
        SolutionPanel.setGridWidth(maxX);
        ArrayList<Product> solution = new ArrayList<>();
        long startTime = System.nanoTime();
        try {
            // als het gekozen algoritme gelijk is aan een van deze cijfers doe dat algoritme
            switch (algoritme) {
                case 0:
                    solution = TspGreedy.Greedy(producten);
                    break;
                case 1:
                    solution = TspTwoOptSwap.TwoOptSwap(producten);
                    break;
                case 2:
                    solution = TspSimulatedAnnealing.SimulatedAnnealing(producten);
                    break;
                case 3:
                    solution = TspEigenOplossing.EigenOplossing(producten);
                    break;
                case -1:
                    break;
            }
            long endTime = System.nanoTime();

            // plaats de producten van de oplossing in de SolutionPanel
            if (solution.size() > 0) {
                SolutionPanel.setProducten(solution);
                logger.println(solutionFormat(solution), LoggerFactory.ErrorLevel.RESULT);
                logger.println(String.format("Oplossing gevonden in %s milliseconden", new DecimalFormat("#.####").format((endTime - startTime) / 1000000.0)));
            }
        } catch (InterruptedException e) {
            e.getMessage();
            // als je terijl hij bezig was op stop hebt gedrukt stopt hij het algoritme en logt hij dit
            logger.println("Algoritme gestopt", LoggerFactory.ErrorLevel.WARNING);
        }
        repaint();
    }

    private String solutionFormat(ArrayList<Product> solution) {
        StringBuilder retString = new StringBuilder("\n\t");
        for (Product aSolution : solution) {
            retString.append(aSolution.toString()).append("\n\t");
        }
        return retString.toString();
    }

    private ArrayList<Product> randomizeProducten(int amount, int maxX, int maxY) {
        // maak een nieuwe lijst
        ArrayList<Product> randomProducten = new ArrayList<>();
        Random r = new Random();
        // maak een nieuw product voor een totaal van amount op random x en y waardes
        for (int i = 0; i < amount; i++) {
            int x = r.nextInt(maxX);
            int y = r.nextInt(maxY);
            Product product = new Product("#" + (i + 1) , 0, 0, x, y);
            randomProducten.add(product);
        }

        // deze check dient voor alle random gegenereerde randomProducten zodat deze niet op elkaar kunnenkomen
        for (int i = 0; i < randomProducten.size(); i++) {
            for (int y = 0; y < randomProducten.size(); y++) {
                if (!randomProducten.get(i).equals(randomProducten.get(y))) {
                    if (randomProducten.get(i).getY() == randomProducten.get(y).getY() &&
                            randomProducten.get(i).getX() == randomProducten.get(y).getX()) {

                        randomProducten.get(i).setX(r.nextInt(maxX));
                        randomProducten.get(i).setY(r.nextInt(maxY));
                        y = 0;
                        i = 0;
                    }
                }
            }
        }
        return randomProducten;
    }

    public void stop(int algo) {
        // stuur stop naar de juiste algoritme
        switch (algo) {
            case 0:
                TspGreedy.stop();
                break;
            case 1:
                TspTwoOptSwap.stop();
                break;
            case 2:
                TspSimulatedAnnealing.stop();
                break;
            case 3:
                TspEigenOplossing.stop();
                break;
            case -1:
                break;
        }
    }

    private String algoName(int a) {
        // returnt de naam van het algoritme zodat het mooier kan worden gelogd dan een cijfer
        switch (a) {
            case 0:
                return "Greedy";
            case 1:
                return "2-Opt Swap";
            case 2:
                return "Simulated Annealing";
            case 3:
                return "Eigen Oplossing";
        }
        return "error";
    }

    public void log(String text, LoggerFactory.ErrorLevel errlvl) {
        logger.println(text, errlvl);
    }

    private void saveLog() {
        // slaat de text uit het log paneeltje op in de map TspControll
        logger.saveLog("TspSimulator");
    }
}