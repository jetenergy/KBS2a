package com.m2e4;

import com.m2e4.DataBase.DataBase;
import com.m2e4.algorithm.TspEigenOplossing;
import com.m2e4.algorithm.TspGreedy;
import com.m2e4.algorithm.TspSimulatedAnnealing;
import com.m2e4.algorithm.TspTwoOptSwap;
import com.m2e4.gui.MainFrame;

public class Main {

    public static void main(String[] args) {
        DataBase dbc = new DataBase();
        MainFrame frame = new MainFrame();
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dbc.closeConn();
                System.exit(0);
            }
        });
        try {
            dbc.connectDataBase();
            dbc.getProducts();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(TspTwoOptSwap.TwoOptSwap(dbc.products));
        System.out.println(TspGreedy.Greedy(dbc.products));
        System.out.println(TspSimulatedAnnealing.SimulatedAnnealing(dbc.products));
        System.out.println(TspEigenOplossing.EigenOplossing(dbc.products));
    }
}
