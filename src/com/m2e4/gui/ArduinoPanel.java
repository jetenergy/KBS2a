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

        PdmPort1 = new PortDropdownMenu();
        PdmPort1.refreshMenu();
        port1 = (String)PdmPort1.getSelectedItem();
        PdmPort1.addActionListener(e -> port1 = (String)PdmPort1.getSelectedItem());
        add(PdmPort1);

        PdmPort2 = new PortDropdownMenu();
        PdmPort2.refreshMenu();
        port2 = (String)PdmPort2.getSelectedItem();
        PdmPort2.addActionListener(e -> port2 = (String)PdmPort2.getSelectedItem());
        PdmPort2.setSelectedIndex(PdmPort2.getItemCount() - 1);
        add(PdmPort2);

        JButton Refresh = new JButton("Refresh Ports");
        Refresh.addActionListener(e -> {
            PdmPort1.refreshMenu();
            PdmPort2.refreshMenu();
            PdmPort2.setSelectedIndex(PdmPort2.getItemCount() - 1);
        });
        add(Refresh);

        JButton CheckBots = new JButton("Set Ports");
        CheckBots.addActionListener(e -> {
            getArduinos();
        });
        add(CheckBots);
    }

    private void getArduinos() {
        resetPort();
        String port = getPort1();
        String arduino = whoDis(port);
        if (arduino != null) {
            setPort(port, arduino);
            System.out.println(port + ", " + arduino);
        } else {
            System.out.println("Port: " + port + " is not accesible.");
        }
        if (!getPort1().equals(getPort2())) {
            port = getPort2();
            arduino = whoDis(port);
            if (arduino != null) {
                setPort(port, arduino);
                System.out.println(port + ", " + arduino);
            } else {
                System.out.println("Port: " + port + " is not accesible.");
            }
        }
    }

    private String whoDis(String port) {
        boolean islive = false;
        int counter = 50;
        ArduinoClass arduino = new ArduinoClass(port);
        while (!islive) {
            if (arduino.read().equals("Ready")) {
                islive = true;
                break;
            }
            if (counter < 0) {
                System.out.println("Timeout");
                arduino.close();
                break;
            }
            counter--;
        }
        if (islive) {
            arduino.write("WhoDis;");
            String ItMe = arduino.read();
            arduino.close();
            return ItMe;
        }
        else return null;
    }

    private void setPort(String port, String location) {
        if (location.equals("MZR")) {
            //MagaZijnRobot
            TspCFrame.setArduino(port);
        } else if (location.equals("IPR")) {
            //InPakRobot
            BppCFrame.setArduino(port);
        }
    }

    private void resetPort() {
        TspCFrame.clearArduino();
        BppCFrame.clearArduino();
    }

    private String getPort1() {
        return port1;
    }

    private String getPort2() {
        return port2;
    }
}
