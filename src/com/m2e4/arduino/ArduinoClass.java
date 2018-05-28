package com.m2e4.arduino;

public class ArduinoClass {
    private arduino.Arduino arduino;

    public ArduinoClass(String port) {
        // dit start de connectie met de arduino op de port (COM1, COM2, enz)
        arduino = new arduino.Arduino(port, 9600);
        arduino.openConnection();
    }

    public void write(char _char) {
        // deze method schrijft een char naar de arduino
        arduino.serialWrite(_char);
    }

    public void write(String _string) {
        // deze method schrijft een string naar de arduino
        arduino.serialWrite(_string);
    }

    public String read() {
        // leest wat er nu op de com port klaar staat om uitgelezen te worden en haalt de new line achter de string weg
        return arduino.serialRead().replace("\n", "");
    }

    public void close() {
        arduino.closeConnection();
    }

}
