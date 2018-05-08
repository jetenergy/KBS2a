package com.m2e4.arduino;

public class ArduinoClass {
    private arduino.Arduino arduino;

    public ArduinoClass(String port) {
        arduino = new arduino.Arduino(port, 9600);
        arduino.openConnection();
    }

    public void ArduinoWrite(char c) {
        arduino.serialWrite(c);
    }

    public void ArduinoWrite(String s) {
        arduino.serialWrite(s);
    }

    public String ArduinoRead() {
        return arduino.serialRead().replace("\n", "");
    }

    public void ArduinoClose() {
        arduino.closeConnection();
    }

}
