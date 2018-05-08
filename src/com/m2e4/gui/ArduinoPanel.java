package com.m2e4.gui;

import arduino.PortDropdownMenu;
import com.m2e4.arduino.ArduinoClass;

import javax.swing.*;
import java.awt.*;

public class ArduinoPanel extends JPanel {
    private PortDropdownMenu PdmPort1, PdmPort2;
    private JButton Refresh, CheckBots;

    private static String port1, port2;

    public ArduinoPanel() {
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

        Refresh = new JButton("Refresh Ports");
        Refresh.addActionListener(e -> {
            PdmPort1.refreshMenu();
            PdmPort2.refreshMenu();
            PdmPort2.setSelectedIndex(PdmPort2.getItemCount() - 1);
        });
        add(Refresh);

        CheckBots = new JButton("Check bots");
        CheckBots.addActionListener(e -> {
            getArduinos();
        });
        add(CheckBots);
    }

    public static void getArduinos() {

        System.out.println(getPort1());
        ArduinoClass arduino = new ArduinoClass(getPort1());
        boolean islive = false;
        while (!islive) {
            if (arduino.ArduinoRead().equals("Ready")) {
                islive = true;
            }
        }
        arduino.ArduinoWrite("WhoDis");
        System.out.println(arduino.ArduinoRead());
        arduino.ArduinoClose();
        //TspCFrame.setArduino(getPort1());

        System.out.println(getPort2());
        arduino = new ArduinoClass(getPort2());
        islive = false;
        while (!islive) {
            if (arduino.ArduinoRead().equals("Ready")) {
                islive = true;
            }
        }
        arduino.ArduinoWrite("WhoDis");
        String hank = arduino.ArduinoRead();

        System.out.println(hank);
        arduino.ArduinoClose();
    }

    public static String getPort1() {
        return port1;
    }

    public static String getPort2() {
        return port2;
    }
}
