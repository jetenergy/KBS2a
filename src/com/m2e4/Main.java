package com.m2e4;

import com.m2e4.DataBase.DataBase;
import com.m2e4.gui.MainFrame;

public class Main {

    public static void main(String[] args) {
        //DataBase dbc = new DataBase();
        MainFrame frame = new MainFrame();
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //dbc.closeConn();
                System.exit(0);
            }
        });
        /*try {
            dbc.connectDataBase();
            dbc.readDataBase("Product");
        }
        catch (Exception e) {
            System.out.println(e);
        }*/
    }
}
