package com.m2e4.gui;

import com.m2e4.arduino.ArduinoClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TspCFrame extends JFrame implements ActionListener {
    private JButton JBOn, JBOff;

    private static ArduinoClass arduino;

    TspCFrame() {
        setLayout(new FlowLayout());
        setTitle("TSP Controll panel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(300, 500);

        JBOn = new JButton("ON");
        JBOn.addActionListener(this);
        add(JBOn);
        JBOff = new JButton("OFF");
        JBOff.addActionListener(this);
        add(JBOff);

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
        if (e.getSource() == JBOn) {
            arduino.write('1');

        }
        if (e.getSource() == JBOff) {
            arduino.write('0');
        }
    }
}
