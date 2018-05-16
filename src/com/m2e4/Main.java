package com.m2e4;

import com.m2e4.DataBase.DataBase;
import com.m2e4.gui.MainFrame;

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
        DataBase dbc = new DataBase();
        MainFrame frame = new MainFrame();
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dbc.closeConn();
                threadPool.shutdownNow();
                System.exit(0);
            }
        });
        try {
            dbc.connectDataBase();
            dbc.readDataBase("Product");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
