package com.m2e4;

import com.m2e4.DataBase.DataBase;
import com.m2e4.algorithm.TspEigenOplossing;
import com.m2e4.algorithm.TspGreedy;
import com.m2e4.algorithm.TspSimulatedAnnealing;
import com.m2e4.algorithm.TspTwoOptSwap;
import com.m2e4.gui.MainFrame;

import javax.xml.crypto.Data;
import java.sql.DatabaseMetaData;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {

    private static ExecutorService threadPool = Executors.newCachedThreadPool(new ThreadFactory() {
        private int counter = 0;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, String.format("KBSPool-Thread-%d", counter++));
        }
    });

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                threadPool.shutdownNow();
                DataBase.closeConn();
                System.exit(0);
            }
        });
        try {
            DataBase.connectDataBase();
            DataBase.ConnGetProducts();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        /*System.out.println(TspTwoOptSwap.TwoOptSwap(DataBase.products));
        System.out.println(TspGreedy.Greedy(dbc.products, true));
        System.out.println(TspSimulatedAnnealing.SimulatedAnnealing(dbc.products));
        System.out.println(TspEigenOplossing.EigenOplossing(dbc.products));*/
    }

    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
