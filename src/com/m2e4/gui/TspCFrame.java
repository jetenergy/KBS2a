package com.m2e4.gui;

import com.m2e4.arduino.ArduinoClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TspCFrame extends JFrame implements ActionListener {
    private JButton JBOn, JBOff;
    private ArduinoClass arduino;

    public TspCFrame(ArduinoClass arduino) {
        setLayout(new FlowLayout());
        setTitle("TSP Controll panel");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(300, 500);

        this.arduino = arduino;

        JBOn = new JButton("ON");
        JBOn.addActionListener(this);
        add(JBOn);
        JBOff = new JButton("OFF");
        JBOff.addActionListener(this);
        add(JBOff);

        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBOn) {
            arduino.ArduinoWrite('1');
        }
        if (e.getSource() == JBOff) {
            arduino.ArduinoWrite('0');
        }
    }
}
