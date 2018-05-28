package com.m2e4;

import com.m2e4.DataBase.DataBase;
import com.m2e4.gui.MainFrame;

import java.util.concurrent.*;

public class Main {

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 6, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(6), new ThreadFactory() {
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
                // sluit de threadpool
                threadPool.shutdownNow();
                // sluit de database verbinding
                DataBase.closeConn();
                System.exit(0);
            }
        });
        try {
            // start de database verbinding en haal alle producten op
            DataBase.connectDataBase();
            DataBase.ConnGetProducts();
        }
        catch (Exception e) {
            e.getMessage();
        }
    }

    public static ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }

}
