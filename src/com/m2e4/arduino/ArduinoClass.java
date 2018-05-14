package com.m2e4.arduino;

public class ArduinoClass {
    private arduino.Arduino arduino;

    public ArduinoClass(String port) {
        arduino = new arduino.Arduino(port, 9600);
        arduino.openConnection();
    }

    public void write(char _char) {
        arduino.serialWrite(_char);
    }

    public void write(String _string) {
        arduino.serialWrite(_string);
    }

    public String read() {
        return arduino.serialRead().replace("\n", "");
    }

    public void close() {
        arduino.closeConnection();
    }

}
