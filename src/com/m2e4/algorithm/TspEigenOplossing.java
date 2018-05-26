package com.m2e4.algorithm;

import com.m2e4.DataBase.Product;

import java.util.ArrayList;

public class TspEigenOplossing {

    public static ArrayList<Product> EigenOplossing(ArrayList<Product> producten){
        // onze eigen oplossing maakt eerst gebruik van simulatedAnnealing en daarna TwoOptSwap om de beste route te vinden
        ArrayList<Product> SimAnnSolution;
        try {
            SimAnnSolution = TspSimulatedAnnealing.SimulatedAnnealing(producten);
            return TspTwoOptSwap.TwoOptSwap(SimAnnSolution);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void stop() {
        TspTwoOptSwap.stop();
        TspSimulatedAnnealing.stop();
    }
}

