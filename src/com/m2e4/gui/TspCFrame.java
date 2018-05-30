package com.m2e4.gui;

import com.m2e4.DataBase.Product;
import com.m2e4.LoggerFactory;
import com.m2e4.Main;
import com.m2e4.algorithm.TspEigenOplossing;
import com.m2e4.arduino.ArduinoClass;
import com.m2e4.gui.tsp.ItemPanel;
import com.m2e4.gui.tsp.PositionPanel;
import com.m2e4.gui.tsp.CSettingsPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TspCFrame extends JFrame {
    private PositionPanel CPosition;
    private ItemPanel Citems;
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
        // HIDE_ON_CLOSE word gebruikt zodat de gegevens niet verloren gaan
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(960, 500);
        setMinimumSize(new Dimension(940, 420));

        Citems = new ItemPanel();
        CPosition = new PositionPanel("Beste oplossing");

        // het controle frame is opgedeeld in 2 secties het JpTop en JpBottom
        // JpTop heeft de items (links boven) en de oplossing en positie van de arduino (rechts boven)
        JpTop = new JPanel();
        JpTop.setLayout(new GridLayout(1, 2));
        JpTop.add(Citems);
        JpTop.add(CPosition);
        add(JpTop, BorderLayout.CENTER);

        // JpLog dient voor het log paneel
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
        CSettings = new CSettingsPanel(this);
        JpBottom.add(CSettings);
        JpBottom.add(JpLog);
        add(JpBottom, BorderLayout.SOUTH);
    }

    public void startAlgo() {
        // print naar het log paneel dat ge gestart zijn
        logger.println("Starten: ");
        // bereken de oplossing van de order
        ArrayList<Product> oplossing = TspEigenOplossing.EigenOplossing(producten);
        // zet de producten in het position paneel
        CPosition.setProducten(oplossing);
        // stuur de oplossing naar het Bpp paneel
        MainFrame.getInstance().getBppContFrame().startBpp(oplossing);
        repaint();
        // stuur de arduino aan
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                driveDruino(oplossing);
            }
        };

        Main.getThreadPool().submit(runnable);

    }

    private void driveDruino(ArrayList<Product> workingProducts) {
        // zolang er nog producten aanwezig zijn in de workingProducts array ga door
        String pos = "";
        int prod =5;
        boolean send = false;
        while (workingProducts.size() > 0) {
            // stuur de eerstvolgende product naar de arduino
            if (prod > 0) {
                // stuur de arduino naar het volgende ophaal punt
                if (!send) {
                    arduino.write(String.format("NextStop;%d;%d;", workingProducts.get(0).getX(), workingProducts.get(0).getY()));
                    System.out.println(String.format("NextStop;%d;%d;", workingProducts.get(0).getX(), workingProducts.get(0).getY()));
                    send = true;
                }
                while (pos.length() == 0) {
                    // wacht tot hij een response krijgt
                    pos = arduino.read();
                }
                if (pos.equals("NextStop")) {
                    workingProducts.remove(0);
                    prod--;
                    send = false;
                }
                pos = "";
            } else {
                // stuur de arduino naar het aflever punt
                if (!send) {
                    arduino.write("NextStop;5;0;");
                    System.out.println("NextStop;5;0;");
                    send = true;
                }
                while (pos.length() == 0) {
                    // wacht tot hij een response krijgt
                    pos = arduino.read();
                }
                if (pos.equals("NextStop")) {
                    workingProducts.remove(0);
                    prod = 5;
                    send = false;
                }
                pos = "";
            }
        }
        // als hij alle producten heeft moet de arduino zijn producten afleveren
        arduino.write("NextStop;5;0;");
        logger.println("finishing up");
    }

    public void setItems(ArrayList<Product> products) {
        producten = products;
        // zet de tabel gelijk aan alle producten uit de order
        Citems.setTable(products);
        logger.println("Producten opgehaald", LoggerFactory.ErrorLevel.INFO);
    }

    public boolean arduinoHere() {
        // checkt als hier een arduino en op het bpp frame een arduino aanwezig is
        System.out.println((arduino != null) + " :  " + BppCFrame.arduinoHere());
        return arduino != null && BppCFrame.arduinoHere();
    }

    public static void setArduino(String port) {
        // sluit de verbinding met de arduino als hij al een arduino had
        if (arduino != null) {
            arduino.close();
        }
        // maak een nieuwe verbinding met de nieuwe arduino
        arduino = new ArduinoClass(port);
    }

    public static void clearArduino() {
        // leegt de arduino
        if (arduino != null) {
            arduino.close();
        }
    }

    private void saveLog() {
        // slaat de text uit het log paneeltje op in de map TspControll
        logger.saveLog("TspControll");
    }
}