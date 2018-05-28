package com.m2e4.gui;

import arduino.PortDropdownMenu;
import com.m2e4.arduino.ArduinoClass;

import javax.swing.*;
import java.awt.*;

class ArduinoPanel extends JPanel {
    private PortDropdownMenu PdmPort1, PdmPort2;

    private String port1, port2;

    ArduinoPanel() {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(300, 100));

        // maak een drop down menu aan voor de eerste poort
        PdmPort1 = new PortDropdownMenu();
        PdmPort1.refreshMenu();
        port1 = (String)PdmPort1.getSelectedItem();
        PdmPort1.addActionListener(e -> port1 = (String)PdmPort1.getSelectedItem());
        add(PdmPort1);

        // maak een drop down menu aan voor de tweede poort
        PdmPort2 = new PortDropdownMenu();
        PdmPort2.refreshMenu();
        port2 = (String)PdmPort2.getSelectedItem();
        PdmPort2.addActionListener(e -> port2 = (String)PdmPort2.getSelectedItem());
        PdmPort2.setSelectedIndex(PdmPort2.getItemCount() - 1);
        add(PdmPort2);

        JButton Refresh = new JButton("Refresh Ports");
        Refresh.addActionListener(e -> {
            // als je op refresh drukt ververst hij beide drop down menus en zet hij het tweede menu op het laaste item
            PdmPort1.refreshMenu();
            PdmPort2.refreshMenu();
            PdmPort2.setSelectedIndex(PdmPort2.getItemCount() - 1);
        });
        add(Refresh);

        JButton CheckBots = new JButton("Set Ports");
        CheckBots.addActionListener(e -> getArduinos());
        add(CheckBots);
    }

    private void getArduinos() {
        // kijkt of de gekozen ports wel correct zijn en wat daar aan hangt
        // leeg eerst de arduinos van beide panelen
        resetPort();
        // pak de eerste poort
        String port = port1;
        // kijk welke robot achter de eerste deur zit
        String arduino = whoDis(port);
        if (arduino != null) {
            // als hij een arduino heeft gevonden zet voert hij setPort uit met zijn naam en de port
            setPort(port, arduino);
            System.out.println(port + ", " + arduino);
        } else {
            // anders heeft hij geen arduino
            System.out.println("Port: " + port + " is not accesible.");
        }
        // als beide geselecteerde poorten anders zijn
        if (!port1.equals(port2)) {
            // pak de tweede poort
            port = port2;
            // kijk welke robot achter de tweede deur zit
            arduino = whoDis(port);
            if (arduino != null) {
                // als hij een arduino heeft gevonden zet voert hij setPort uit met zijn naam en de port
                setPort(port, arduino);
                System.out.println(port + ", " + arduino);
            } else {
                // anders heeft hij geen arduino
                System.out.println("Port: " + port + " is not accesible.");
            }
        }
    }

    private String whoDis(String port) {
        boolean islive = false;
        int counter = 50;
        // maak de arduino aan op de port
        ArduinoClass arduino = new ArduinoClass(port);
        while (!islive) {
            // zolang hij niks heeft gehoord of counter > 0
            if (arduino.read().equals("Ready")) {
                // als er een arduino achter hangt en ready terug krijgt
                // hij zet islive op true zodat we later hier meer mee kunnen doen
                islive = true;
                break;
            }
            if (counter < 0) {
                // als hij lang genoeg niks te horen krijgt kunnen we ervan uitgaan dat er geen arduino achter zit
                System.out.println("Timeout");
                arduino.close();
                break;
            }
            counter--;
        }
        // als hij een arduino heeft gevonden stuurt hij het commando WhoDis om de naam terug te krijgen
        if (islive) {
            arduino.write("WhoDis;");
            String ItMe = arduino.read();
            // hij sluit de verbinding en returnd de naam
            arduino.close();
            return ItMe;
        }
        // als hij geen arduino gevonden heeft dus niet islive op true zet maar gewoon uit de while breakt dan retourneren we null
        else return null;
    }

    private void setPort(String port, String location) {
        // als de naam/location MZR is geven we deze aan het TSP controle frame
        if (location.equals("MZR")) {
            //MagaZijnRobot
            TspCFrame.setArduino(port);
        } else if (location.equals("IPR")) { // als de naam/location IPRis geven we deze aan het BPP controle frame
            //InPakRobot
            BppCFrame.setArduino(port);
        }
    }

    private void resetPort() {
        // leegt de arduinos van beide frames
        TspCFrame.clearArduino();
        BppCFrame.clearArduino();
    }
}
