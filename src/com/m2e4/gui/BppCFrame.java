package com.m2e4.gui;

import com.m2e4.arduino.ArduinoClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BppCFrame extends JFrame implements ActionListener {


    private static ArduinoClass arduino;

    BppCFrame() {
        setLayout(new FlowLayout());
        setTitle("BPP Controll panel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(500, 300);

        setVisible(false);
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
