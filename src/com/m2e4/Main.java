package com.m2e4;

import com.m2e4.arduino.ArduinoClass;
import com.m2e4.gui.MainFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main {

    public static void main(String[] args) {
        ArduinoClass arduinoTsp = new ArduinoClass("COM3");

	    MainFrame frame = new MainFrame(arduinoTsp);
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                arduinoTsp.ArduinoClose();
            }
        });
    }
}
