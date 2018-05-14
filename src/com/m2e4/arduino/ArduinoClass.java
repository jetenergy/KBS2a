package com.m2e4.arduino;

public class ArduinoClass {
    private arduino.Arduino arduino;

    public ArduinoClass(String port) {
        arduino = new arduino.Arduino(port, 9600);
        arduino.openConnection();
    }

    public void write(char c) {
        arduino.serialWrite(c);
    }

    public void write(String s) {
        arduino.serialWrite(s);
    }

    public String read() {
        return arduino.serialRead().replace("\n", "");
    }

    public void close() {
        arduino.closeConnection();
    }

}
